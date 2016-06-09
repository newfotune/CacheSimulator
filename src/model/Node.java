package model;
/**
 * 
 * @author Rowan and Fortune
 *
 */
public class Node
{

	/** Our node's Instruction address */
	private int	myInstructionAddress;
	/** Our node's Write Value */
	private int	myWriteValue;
	/** Our node's Data address */
	private int	myDataAddress;

	/**
	 * Constructor for 3 value input (when we would start adding to the L1i.)
	 * 
	 * @param theInstructionAddress
	 *            the input Instruction Address from our CSV file.
	 * @param theWriteValue
	 *            the input Write Value from our CSV file.
	 * @param theDataAddress
	 *            the input Data Address from our CSV file.
	 */
	public Node(int theInstructionAddress, int theWriteValue, int theDataAddress)
	{
		this.myInstructionAddress = theInstructionAddress;
		this.myWriteValue = theWriteValue;
		this.myDataAddress = theDataAddress;
	}

	/**
	 * Constructor for 1 value input (when we would start adding to L1d.)
	 * 
	 * @param theInstructionaddress
	 *            the input Instruction Address from our CSV file.
	 */
	public Node(int theInstructionaddress)
	{
		myInstructionAddress = theInstructionaddress;
		myWriteValue = -1;
		myDataAddress = -1;
	}

	/**
	 * Returns this node's Instruction Address
	 * 
	 * @return myInstructionAddress
	 */
	public int getMyInstructionAddress()
	{
		return myInstructionAddress;
	}

	/**
	 * Returns this node's Write Value
	 * 
	 * @return myWriteValue
	 */
	public int getMyWriteValue()
	{
		return myWriteValue;
	}

	/**
	 * Returns this node's Data Address
	 * 
	 * @return myDataAddress
	 */
	public int getMyDataAddress()
	{
		return myDataAddress;
	}

}