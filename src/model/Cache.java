package model;
/**
 * Our Cache and all values contained within.
 * 
 * @author Rowan and Fortune
 */
public class Cache
{

	/** This Cache's size in #of entries allowed */
	private int				mySize;
	/** This Cache's Write Latency */
	private int				myWriteLatency;
	/** This Cache's Read Latency */
	private int				myReadLatency;
	/** This Cache's entries */
	private CacheEntry[]	myEntries;
	/** This Cache's number of offset bits used */
	private int				myNumberOfOffsetBits;
	/** This Cache's number of sets */
	private int				myNumberOfSets;
	/** This Cache's number of index bits used */
	private int				myNumberOfIndexBits;
	/** This Cache's index mask for pulling out the index for a given address */
	private int				myIndexMask;
	/** This Cache's total number of hits */
	private int				myHits;
	/** This Cache's total number of misses */
	private int				myMisses;
	/** This Cache's total number of accesses */
	private int				myAccesses;

	/**
	 * Constructor for creating a cache with a fixed latency.
	 * 
	 * @param theSize
	 *            the desired size of the cache
	 * @param theLatency
	 *            the desired latency of the cache
	 * @param theAssociativity
	 *            the desired Associativity for the cache
	 */
	public Cache(int theSize, int theLatency, int theAssociativity)
	{
		mySize = theSize;
		myWriteLatency = theLatency;
		myReadLatency = theLatency;
		myEntries = new CacheEntry[mySize];
		myNumberOfOffsetBits = (int) (Math.log (mySize) / Math.log (2));
		myNumberOfSets = mySize / theAssociativity;
		myNumberOfIndexBits = (int) (Math.log (myNumberOfSets) / Math.log (2));
		myIndexMask = ((1 << myNumberOfIndexBits) - 1);
		myHits = 0;
		myMisses = 0;
		myAccesses = 0;
		// System.out.println ("//////////////////////////////");
		// System.out.println (theSize);
		// System.out.println (myNumberOfSets);
		// System.out.println (myNumberOfIndexBits);
		// System.out.println (myNumberOfOffsetBits);

	}

	/**
	 * Constructor for creating a cache with a different latencies for
	 * Read/Write.
	 * 
	 * @param theSize
	 *            the desired size of the cache
	 * @param theReadLatency
	 *            the desired read latency of the cache
	 * @param theWriteLatency
	 *            the desired write latency of the cache
	 * @param theAssociativity
	 *            the desired Associativity for the cache
	 */
	public Cache(int theSize, int theReadLatency, int theWriteLatency, int theAssociativity)
	{
		mySize = theSize;
		myWriteLatency = theWriteLatency;
		myReadLatency = theReadLatency;
		myEntries = new CacheEntry[mySize];
		myNumberOfOffsetBits = (int) (Math.log (mySize) / Math.log (2));
		myNumberOfSets = mySize / theAssociativity;
		myNumberOfIndexBits = (int) (Math.log (myNumberOfSets) / Math.log (2));
		myIndexMask = ((1 << myNumberOfIndexBits) - 1);
		myHits = 0;
		myMisses = 0;
		myAccesses = 0;
		// System.out.println ("//////////////////////////////");
		// System.out.println (theSize);
		// System.out.println (myNumberOfSets);
		// System.out.println (myNumberOfIndexBits);
		// System.out.println (myNumberOfOffsetBits);

	}

	/**
	 * Responsible for adding the entry into our cache
	 * 
	 * @param theEntry
	 *            the entry we wish to add
	 * @param theRealIndex
	 *            the index that we are adding into
	 */
	public void addEntry(CacheEntry theEntry, int theRealIndex)
	{
		myEntries[theRealIndex] = theEntry;
	}

	/**
	 * Returns the passed in address's Offset
	 * 
	 * @param theAddress
	 * @return
	 */
	public int getOffset(int theAddress)
	{
		return theAddress & ((1 << myNumberOfOffsetBits) - 1);
	}

	/**
	 * Returns the passed in address's Index
	 * 
	 * @param theAddress
	 * @return
	 */
	public int getIndex(int theAddress)
	{
		// System.out.println ("return * Assoc.");
		// System.out.println ((theAddress >>> myNumberOfOffsetBits) &
		// (myIndexMask));
		return (theAddress >>> myNumberOfOffsetBits) & (myIndexMask);
	}

	/**
	 * Returns the passed in address's tag
	 * 
	 * @param theAddress
	 * @return
	 */
	public int getTag(int theAddress)
	{
		return (theAddress >>> (myNumberOfOffsetBits + myNumberOfIndexBits));
	}

	/**
	 * Returns the Array of Cache Entries contained within our cache
	 * 
	 * @param theAddress
	 * @return
	 */
	public CacheEntry[] getEntries()
	{
		return myEntries;
	}

	/**
	 * returns the read latency for the cache.
	 * 
	 * @return
	 */
	public int getReadLatency()
	{
		return myReadLatency;
	}

	/**
	 * returns the write latency for the cache.
	 * 
	 * @return
	 */
	public int getWriteLatency()
	{
		return myWriteLatency;
	}

	/**
	 * returns this cache's total hits
	 * 
	 * @return
	 */
	public int getMyHits()
	{
		return myHits;
	}

	/**
	 * returns this cache's total misses
	 * 
	 * @return
	 */
	public int getMyMisses()
	{
		return myMisses;
	}

	/**
	 * returns this cache's total accesses
	 * 
	 * @return
	 */
	public int getMyAccesses()
	{
		return myAccesses;
	}

	/**
	 * increments our caches hits counter.
	 */
	public void incrementHits()
	{
		myHits++;
	}

	/**
	 * increments our caches misses counter.
	 */
	public void incrementMisses()
	{
		myMisses++;
	}

	/**
	 * increments our caches accesses counter.
	 */
	public void incrementAccesses()
	{
		myAccesses++;
	}
}
