package model;
/**
 * Class for constructing and using the CPU
 * 
 * @author Rowan and Fortune
 */
public class CPU
{
	/**
	 * this CPUs L1i cache
	 */
	private Cache	myL1i;
	/**
	 * this CPUs L1d cache
	 */
	private Cache	myL1d;
	/**
	 * this CPUs L2 cache
	 */
	private Cache	myL2;

	/**
	 * Constructor for creating a CPU
	 * 
	 * @param theL1i
	 *            the CPUs L1i cache
	 * @param theL1d
	 *            the CPUs L1d cache
	 * @param theL2
	 *            the CPUs L2 cache
	 */
	public CPU(Cache theL1i, Cache theL1d, Cache theL2)
	{
		myL1i = theL1i;
		myL1d = theL1d;
		myL2 = theL2;
	}

	/**
	 * Returns the CPUs L1i cache
	 * 
	 * @return
	 */
	public Cache getMyL1i()
	{
		return myL1i;
	}

	/**
	 * Returns the CPUs L1d cache
	 * 
	 * @return
	 */
	public Cache getMyL1d()
	{
		return myL1d;
	}

	/**
	 * Returns the CPUs L2 cache
	 * 
	 * @return
	 */
	public Cache getMyL2()
	{
		return myL2;
	}
}
