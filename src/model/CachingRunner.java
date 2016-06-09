package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @author Rowan and Fortune
 *
 */
public class CachingRunner
{
	/** Array index for L1 Size. */
	private static int			L1_SIZE_INDEX		= 0;
	/** Array index for L2 Size. */
	private static int			L2_SIZE_INDEX		= 1;
	/** Array index for L3 Size. */
	private static int			L3_SIZE_INDEX		= 2;
	/** Array index for LM1 Size. */
	private static int			LM1_SIZE_INDEX		= 3;
	/** Array index for LM2 Size. */
	private static int			LM2_SIZE_INDEX		= 4;
	/** Array index for L1 Latency. */
	private static int			L1_LAT_INDEX		= 5;
	/** Array index for L2 Latency. */
	private static int			L2_LAT_INDEX		= 6;
	/** Array index for L3 Latency. */
	private static int			L3_LAT_INDEX		= 7;
	/** Array index for LM1 Latency. */
	private static int			LM1_LAT_INDEX		= 8;
	/** Array index for LM2 Read Latency. */
	private static int			LM2_READ_LAT_INDEX	= 9;
	/** Array index for LM2 Write Latency. */
	private static int			LM2_WRITE_LAT_INDEX	= 10;
	/** Array index for Associativity. */
	private static int			ASSOCIATIVITY_INDEX	= 11;
	/** An array of user selected values for caching. */
	private int[]				mySettings;
	/** Total latency after running the program. */
	private int					myTotalLatency;
	/** The first Core of our Dual Core Processor. */
	private CPU					myCPU1;
	/** The second Core of our Dual Core Processor. */
	private CPU					myCPU2;
	/** The L3 cache used by CPU1 and CPU2. */
	private Cache				myL3;
	/** The LM1 memory used by CPU1 and CPU2. */
	private Cache				myLM1;
	/** the LM2 memory used by CPU1 and CPU2. */
	private Cache				myLM2;
	/** String builder to build result's string. */
	private StringBuilder		myStringBuilder;
	private ArrayList<String[]>	myInputEntries;

	public CachingRunner(ArrayList<String[]> theInputEntries, int[] theSettings)
	{
		myInputEntries = theInputEntries;
		mySettings = theSettings;

	}

	public void start()
	{
		myTotalLatency = 0;

		myLM1 = new Cache (mySettings[LM1_SIZE_INDEX],mySettings[LM1_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]);
		myLM2 = new Cache (mySettings[LM2_SIZE_INDEX],mySettings[LM2_READ_LAT_INDEX],mySettings[LM2_WRITE_LAT_INDEX],
				mySettings[ASSOCIATIVITY_INDEX]);
		myCPU1 = new CPU (
				new Cache (mySettings[L1_SIZE_INDEX],mySettings[L1_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]),
				new Cache (mySettings[L1_SIZE_INDEX],mySettings[L1_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]),
				new Cache (mySettings[L2_SIZE_INDEX],mySettings[L2_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]));

		myCPU2 = new CPU (
				new Cache (mySettings[L1_SIZE_INDEX],mySettings[L1_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]),
				new Cache (mySettings[L1_SIZE_INDEX],mySettings[L1_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]),
				new Cache (mySettings[L2_SIZE_INDEX],mySettings[L2_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]));
		myL3 = new Cache (mySettings[L3_SIZE_INDEX],mySettings[L3_LAT_INDEX],mySettings[ASSOCIATIVITY_INDEX]);

		CPU temp;
		CPU otherCPU = myCPU2;
		CPU currentCPU = myCPU1;

		for (int i = 0; i < myInputEntries.size (); i++)
		{
			if (i % 50 == 0)
			{
				temp = currentCPU;
				currentCPU = otherCPU;
				otherCPU = temp;
			}

			int instructionAddress = Integer.valueOf (myInputEntries.get (i)[0]);
			int writeValue;
			int dataAddress;
			Node entryNode;
			if (myInputEntries.get (i).length == 1)
			{
				entryNode = new Node (instructionAddress);

			}
			else
			{

				writeValue = Integer.valueOf (myInputEntries.get (i)[1]);
				dataAddress = Integer.valueOf (myInputEntries.get (i)[2]);
				entryNode = new Node (instructionAddress,writeValue,dataAddress);
			}
			CacheEntry instructionEntry = new CacheEntry (
					currentCPU.getMyL1i ().getTag (entryNode.getMyInstructionAddress ()),
					entryNode.getMyInstructionAddress (),entryNode.getMyWriteValue ());
			placeInCache (currentCPU, otherCPU, instructionEntry, currentCPU.getMyL1i (), otherCPU.getMyL1i ());

			if (entryNode.getMyDataAddress () > -1)
			{
				CacheEntry dataCacheEntry = new CacheEntry (
						currentCPU.getMyL1d ().getTag (entryNode.getMyDataAddress ()),entryNode.getMyDataAddress (),
						entryNode.getMyWriteValue ());
				placeInCache (currentCPU, otherCPU, dataCacheEntry, currentCPU.getMyL1d (), otherCPU.getMyL1d ());

			}
		}
	}
	/**
	 * Checks to see if the entry we loaded is stored in cache already or not.
	 * IE Hit/Miss
	 * 
	 * @param theCurrentCPU
	 *            the CPU we are currently checking
	 * @param theEntry
	 *            the entry we are trying to cache
	 * @param theCurrentCPUL1
	 *            the current CPUs current L1 (depends if the instruction goes
	 *            in L1i or L1d.
	 * @param theOtherCPUL1
	 *            the other CPUs current L1 (depends if the instruction goes in
	 *            L1i or L1d.
	 * @return A boolean representing if the entry exists in cache. True if
	 *         found, False if not.
	 */
	public boolean isAlreadyInCache(CPU theCurrentCPU, CacheEntry theEntry, Cache theCurrentCPUL1, Cache theOtherCPUL1)
	{
		boolean toReturn = false;
		int foundInCacheLevel = 0;
		for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
		{
			if (theCurrentCPUL1.getEntries ()[(theCurrentCPUL1.getIndex (theEntry.getMyAddress ()) * i)] != null
					&& theCurrentCPUL1.getEntries ()[(theCurrentCPUL1.getIndex (theEntry.getMyAddress ()) * i)]
							.getMyTag () == theEntry.getMyTag ())
			{
				myTotalLatency += theCurrentCPUL1.getReadLatency ();
				foundInCacheLevel = 1;
				toReturn = true;
				break;
			}
			else
				if (theCurrentCPU.getMyL2 ()
						.getEntries ()[(theCurrentCPU.getMyL2 ().getIndex (theEntry.getMyAddress ()) * i)] != null
						&& theCurrentCPU.getMyL2 ()
								.getEntries ()[(theCurrentCPU.getMyL2 ().getIndex (theEntry.getMyAddress ()) * i)]
										.getMyTag () == theCurrentCPU.getMyL2 ().getTag (theEntry.getMyAddress ()))
				{
					myTotalLatency += theCurrentCPUL1.getReadLatency () + theCurrentCPU.getMyL2 ().getReadLatency ();
					foundInCacheLevel = 2;

					toReturn = true;
					break;
				}
				else
					if (myL3.getEntries ()[(myL3.getIndex (theEntry.getMyAddress ()) * i)] != null
							&& myL3.getEntries ()[(myL3.getIndex (theEntry.getMyAddress ()) * i)].getMyTag () == myL3
									.getTag (theEntry.getMyAddress ()))
					{

						myTotalLatency += theCurrentCPUL1.getReadLatency () + theCurrentCPU.getMyL2 ().getReadLatency ()
								+ myL3.getReadLatency ();
						foundInCacheLevel = 3;

						toReturn = true;
						break;
					}
					else
						if (myLM1.getEntries ()[(myLM1.getIndex (theEntry.getMyAddress ()) * i)] != null
								&& myLM1.getEntries ()[(myLM1.getIndex (theEntry.getMyAddress ()) * i)]
										.getMyTag () == myLM1.getTag (theEntry.getMyAddress ()))
						{
							myTotalLatency += theCurrentCPUL1.getReadLatency ()
									+ theCurrentCPU.getMyL2 ().getReadLatency () + myL3.getReadLatency ()
									+ myLM1.getReadLatency ();
							;
							foundInCacheLevel = 4;

							toReturn = true;
							break;
						}

						else
							if (myLM2.getEntries ()[(myLM2.getIndex (theEntry.getMyAddress ()) * i)] != null
									&& myLM2.getEntries ()[(myLM2.getIndex (theEntry.getMyAddress ()) * i)]
											.getMyTag () == myLM2.getTag (theEntry.getMyAddress ()))
							{
								myTotalLatency += theCurrentCPUL1.getReadLatency ()
										+ theCurrentCPU.getMyL2 ().getReadLatency () + myL3.getReadLatency ()
										+ myLM1.getReadLatency () + myLM2.getReadLatency ();
								foundInCacheLevel = 5;

								toReturn = true;
								break;
							}
		}

		switch (foundInCacheLevel)
		{
		case 1:
			theCurrentCPUL1.incrementHits ();
			theCurrentCPUL1.incrementAccesses ();
			break;
		case 2:
			theCurrentCPUL1.incrementMisses ();
			theCurrentCPUL1.incrementAccesses ();
			theCurrentCPU.getMyL2 ().incrementHits ();
			theCurrentCPU.getMyL2 ().incrementAccesses ();
			break;
		case 3:
			theCurrentCPUL1.incrementMisses ();
			theCurrentCPUL1.incrementAccesses ();
			theCurrentCPU.getMyL2 ().incrementMisses ();
			theCurrentCPU.getMyL2 ().incrementAccesses ();
			myL3.incrementHits ();
			myL3.incrementAccesses ();
			break;
		case 4:
			theCurrentCPUL1.incrementMisses ();
			theCurrentCPUL1.incrementAccesses ();
			theCurrentCPU.getMyL2 ().incrementMisses ();
			theCurrentCPU.getMyL2 ().incrementAccesses ();
			myL3.incrementMisses ();
			myL3.incrementAccesses ();
			myLM1.incrementHits ();
			myLM1.incrementAccesses ();
			break;
		case 5:
			theCurrentCPUL1.incrementMisses ();
			theCurrentCPUL1.incrementAccesses ();
			theCurrentCPU.getMyL2 ().incrementMisses ();
			theCurrentCPU.getMyL2 ().incrementAccesses ();
			myL3.incrementMisses ();
			myL3.incrementAccesses ();
			myLM1.incrementMisses ();
			myLM1.incrementAccesses ();
			myLM2.incrementHits ();
			myLM2.incrementAccesses ();
			break;
		default: // not found
			theCurrentCPUL1.incrementAccesses ();
			theCurrentCPUL1.incrementMisses ();
			theCurrentCPU.getMyL2 ().incrementMisses ();
			theCurrentCPU.getMyL2 ().incrementAccesses ();
			myL3.incrementMisses ();
			myL3.incrementAccesses ();
			myLM1.incrementAccesses ();
			myLM1.incrementMisses ();
			myLM2.incrementAccesses ();
			myLM2.incrementMisses ();
			break;

		}

		return toReturn;

	}

	/**
	 * Places the entry in cache if it was not found.
	 * 
	 * @param theCurentCPU
	 *            the current CPU we are trying to add too
	 * @param theOtherCPU
	 *            the other CPU
	 * @param theEntry
	 *            the Entry we are trying to add
	 * @param theCurrentCPUL1
	 *            the current CPUs L1 cache (could be L1i or L1d depending on
	 *            the instruction being added.
	 * @param theOtherCPUL1
	 *            the other CPUs L1 cache (could be L1i or L1d depending on the
	 *            instruction being added.
	 */
	public void placeInCache(CPU theCurentCPU, CPU theOtherCPU, CacheEntry theEntry, Cache theCurrentCPUL1,
			Cache theOtherCPUL1)
	{
		boolean addNewEntry = isAlreadyInCache (theCurentCPU, theEntry, theCurrentCPUL1, theOtherCPUL1);
		if (!addNewEntry)
		{
			int temp = checkOtherCPU (theEntry, theOtherCPU, theCurrentCPUL1, theOtherCPUL1);
			myTotalLatency += temp;

			theEntry.setMyTag (theCurrentCPUL1.getTag (theEntry.getMyAddress ()));
			int baseIndex = theCurrentCPUL1.getIndex (theEntry.getMyAddress ());
			int indexMultiplier = -1;
			for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
			{

				if (theCurrentCPUL1.getEntries ()[i * baseIndex] == null)
				{
					indexMultiplier = i;
					break;
				}
			}

			if (indexMultiplier == -1)
			{

				indexMultiplier = ThreadLocalRandom.current ().nextInt (1, mySettings[ASSOCIATIVITY_INDEX] + 1);
				CacheEntry evictedL1Entry = theCurrentCPUL1.getEntries ()[baseIndex * indexMultiplier];
				theCurrentCPUL1.addEntry (theEntry, baseIndex * indexMultiplier);
				evictedL1Entry.setMyTag (theCurentCPU.getMyL2 ().getTag (evictedL1Entry.getMyAddress ()));
				baseIndex = theCurentCPU.getMyL2 ().getIndex (evictedL1Entry.getMyAddress ());
				indexMultiplier = -1;
				for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
				{
					if (theCurentCPU.getMyL2 ().getEntries ()[i * baseIndex] == null)
					{
						indexMultiplier = i;
						break;
					}
				}
				if (indexMultiplier == -1)
				{
					indexMultiplier = ThreadLocalRandom.current ().nextInt (1, mySettings[ASSOCIATIVITY_INDEX] + 1);
					// System.out.println (into_set);
					CacheEntry evictedL2Entry = theCurentCPU.getMyL2 ().getEntries ()[baseIndex * indexMultiplier];
					theCurentCPU.getMyL2 ().addEntry (evictedL1Entry, baseIndex * indexMultiplier);
					evictedL2Entry.setMyTag (myL3.getTag (evictedL2Entry.getMyAddress ()));
					baseIndex = myL3.getIndex (evictedL2Entry.getMyAddress ());
					indexMultiplier = -1;
					for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
					{
						if (myL3.getEntries ()[i * baseIndex] == null)
						{
							indexMultiplier = i;
							break;
						}
					}
					if (indexMultiplier == -1)
					{
						indexMultiplier = ThreadLocalRandom.current ().nextInt (1, mySettings[ASSOCIATIVITY_INDEX] + 1);
						CacheEntry evictedL3Entry = myL3.getEntries ()[baseIndex * indexMultiplier];
						myL3.addEntry (evictedL2Entry, baseIndex * indexMultiplier);
						evictedL3Entry.setMyTag (myLM1.getTag (evictedL3Entry.getMyAddress ()));
						baseIndex = myLM1.getIndex (evictedL2Entry.getMyAddress ());
						indexMultiplier = -1;
						for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
						{
							if (myLM1.getEntries ()[i * baseIndex] == null)
							{
								indexMultiplier = i;
								break;
							}
						}
						if (indexMultiplier == -1)
						{
							indexMultiplier = ThreadLocalRandom.current ().nextInt (1,
									mySettings[ASSOCIATIVITY_INDEX] + 1);
							CacheEntry evictedLM1Entry = myLM1.getEntries ()[baseIndex * indexMultiplier];
							myLM1.addEntry (evictedL2Entry, baseIndex * indexMultiplier);
							evictedLM1Entry.setMyTag (myLM2.getTag (evictedL3Entry.getMyAddress ()));
							baseIndex = myLM2.getIndex (evictedL2Entry.getMyAddress ());
							indexMultiplier = -1;
							for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
							{
								if (myLM2.getEntries ()[i * baseIndex] == null)
								{
									indexMultiplier = i;
									break;
								}
							}
							if (indexMultiplier == -1)
							{
								indexMultiplier = ThreadLocalRandom.current ().nextInt (1,
										mySettings[ASSOCIATIVITY_INDEX] + 1);
								myLM2.addEntry (evictedLM1Entry, baseIndex * indexMultiplier);
							}
							else
							{
								myLM2.addEntry (evictedLM1Entry, baseIndex * indexMultiplier);
							}
						}
						else
						{
							myLM1.addEntry (evictedL3Entry, baseIndex * indexMultiplier);
						}

					}
					else
					{
						myL3.addEntry (evictedL2Entry, baseIndex * indexMultiplier);
					}
				}
				else
				{
					theCurentCPU.getMyL2 ().addEntry (evictedL1Entry, baseIndex * indexMultiplier);
				}
			}
			else
			{
				theCurrentCPUL1.addEntry (theEntry, baseIndex * indexMultiplier);
			}
		}
	}

	/**
	 * Checks if the entry we are trying to add to our Current CPU is already in
	 * the Other CPU and Adjusts the Mesi state.
	 * 
	 * @param theEntry
	 *            the entry we are wanting to add
	 * @param theOtherCPU
	 *            the other CPU we are checking on.
	 * @param theCurrentCPUl1
	 *            the current CPUs L1 (could be L1i or L1d depending on the
	 *            instruction being added.)
	 * @param theOtherCPUL1
	 *            the other CPUs L1 cache (could be L1i or L1d depending on the
	 *            instruction being added.)
	 * @return
	 */
	public int checkOtherCPU(CacheEntry theEntry, CPU theOtherCPU, Cache theCurrentCPUl1, Cache theOtherCPUL1)
	{
		for (int i = 1; i <= mySettings[ASSOCIATIVITY_INDEX]; i++)
		{
			if (theOtherCPUL1.getEntries ()[(theOtherCPUL1.getIndex (theEntry.getMyAddress ()) * i)] != null
					&& theOtherCPUL1.getEntries ()[(theOtherCPUL1.getIndex (theEntry.getMyAddress ()) * i)]
							.getMyTag () == theEntry.getMyTag ())
			{
				theEntry.setMyState (MesiStates.MODIFIED);
				theOtherCPUL1.getEntries ()[(theOtherCPUL1.getIndex (theEntry.getMyAddress ()) * i)]
						.setMyState (MesiStates.MODIFIED);
				return theOtherCPUL1.getReadLatency ();
			}
			else
				if (theOtherCPU.getMyL2 ()
						.getEntries ()[(theOtherCPU.getMyL2 ().getIndex (theEntry.getMyAddress ()) * i)] != null
						&& theOtherCPU.getMyL2 ()
								.getEntries ()[(theOtherCPU.getMyL2 ().getIndex (theEntry.getMyAddress ()) * i)]
										.getMyTag () == theEntry.getMyTag ())
				{
					theEntry.setMyState (MesiStates.MODIFIED);
					theOtherCPUL1.getEntries ()[(theOtherCPUL1.getIndex (theEntry.getMyAddress ()) * i)]
							.setMyState (MesiStates.MODIFIED);
					return theOtherCPU.getMyL2 ().getReadLatency ();
				}
		}
		return 0;
	}

	/**
	 * method executed by clicking the enter button. Initializes all CPUs and
	 * Caches used by the program. This method is what makes caching work.
	 */

	public StringBuilder printStats()
	{
		myStringBuilder = new StringBuilder ();
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Total Time in ns:");
		myStringBuilder.append (myTotalLatency);
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("CPU1 L1i:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myCPU1.getMyL1i ().getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myCPU1.getMyL1i ().getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myCPU1.getMyL1i ().getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("CPU1 L1d:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myCPU1.getMyL1d ().getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myCPU1.getMyL1d ().getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myCPU1.getMyL1d ().getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("CPU1 L2:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myCPU1.getMyL2 ().getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myCPU1.getMyL2 ().getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myCPU1.getMyL2 ().getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("CPU2 L1i:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myCPU2.getMyL1i ().getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myCPU2.getMyL1i ().getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myCPU2.getMyL1i ().getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("CPU2 L1d:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myCPU2.getMyL1d ().getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myCPU2.getMyL1d ().getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myCPU2.getMyL1d ().getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("CPU2 L2:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myCPU2.getMyL2 ().getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myCPU2.getMyL2 ().getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myCPU2.getMyL2 ().getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("L3:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myL3.getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myL3.getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myL3.getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("LM1:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myLM1.getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myLM1.getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myLM1.getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("LM2:\n");
		myStringBuilder.append ("Accesses: ");
		myStringBuilder.append (myLM2.getMyAccesses ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Hits: ");
		myStringBuilder.append (myLM2.getMyHits ());
		myStringBuilder.append ("\n");
		myStringBuilder.append ("Misses: ");
		myStringBuilder.append (myLM2.getMyMisses ());
		myStringBuilder.append ("\n\n");

		myStringBuilder.append ("Total Hits/Misses: ");
		myStringBuilder.append (myLM2.getMyHits () + myLM1.getMyHits () + myL3.getMyHits ()
				+ myCPU1.getMyL2 ().getMyHits () + myCPU2.getMyL2 ().getMyHits () + myCPU1.getMyL1i ().getMyHits ()
				+ myCPU1.getMyL1d ().getMyHits () + myCPU2.getMyL1i ().getMyHits () + myCPU2.getMyL1d ().getMyHits ());
		myStringBuilder.append ("/");
		myStringBuilder.append (myLM2.getMyMisses () + myLM1.getMyMisses () + myL3.getMyMisses ()
				+ myCPU1.getMyL2 ().getMyMisses () + myCPU2.getMyL2 ().getMyMisses ()
				+ myCPU1.getMyL1i ().getMyMisses () + myCPU1.getMyL1d ().getMyMisses ()
				+ myCPU2.getMyL1i ().getMyMisses () + myCPU2.getMyL1d ().getMyMisses ());
		myStringBuilder.append ("\n\n");
		myStringBuilder.append ("Average Latency Per Instruction: ");
		myStringBuilder.append (myTotalLatency / myInputEntries.size ());
		return myStringBuilder;

	}
}
