package model;
/**
 * Cache Entry for inserting into our Cache.
 * 
 * @author Rowan and Fortune
 *
 */
public class CacheEntry
{

	/** The current CacheEntry's tag */
	private int			myTag;
	/** The current CacheEntry's tag */
	private MesiStates	myMesiState;
	/** The current CacheEntry's tag */
	private int			myAddress;
	/** The current CacheEntry's tag */
	private int			myWriteValue;

	/**
	 * Constructor for creating a Cache Entry
	 * 
	 * @param theTag
	 *            the tag to be initialized.
	 * @param theAddress
	 *            the address to be initialized.
	 * @param theWriteValue
	 *            the write value to be initialized.
	 */
	public CacheEntry(int theTag, int theAddress, int theWriteValue)
	{
		myTag = theTag;
		myAddress = theAddress;
		myWriteValue = theWriteValue;
		myMesiState = MesiStates.EXCLUSIVE;
	}

	/**
	 * Returns this Cache Entry's tag.
	 * 
	 * @return
	 */
	public int getMyTag()
	{
		return myTag;
	}

	/**
	 * Sets this Cache Entry's tag.
	 */
	public void setMyTag(int theTag)
	{
		myTag = theTag;
	}

	/**
	 * Returns this Cache Entry's address.
	 * 
	 * @return
	 */
	public int getMyAddress()
	{
		return myAddress;
	}

	/**
	 * Returns this Cache Entry's write value.
	 * 
	 * @return
	 */
	public int getMyWriteValue()
	{
		return myWriteValue;
	}

	/**
	 * Returns this Cache Entry's MESI state.
	 * 
	 * @return
	 */
	public MesiStates getMyState()
	{
		return myMesiState;
	}

	/**
	 * Sets this Cache Entry's MESI state.
	 * 
	 */
	public void setMyState(MesiStates theMesiState)
	{
		myMesiState = theMesiState;
	}

	/**
	 * Returns this Cache Entry's string representation.
	 * 
	 * @return
	 */
	public String toString()
	{
		return "tag = " + Integer.toHexString (myTag) + " MesiState = " + myMesiState;

	}
}
