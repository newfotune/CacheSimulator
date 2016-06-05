
package finals;

import finals.Cache;

public class CPU {
	
	private Cache myL1I;
	private Cache myL1D;
	private Cache myL2;
	
	public CPU(Cache L1I, Cache L1D, Cache L2) {
		myL1I = L1I;
		myL1D = L1D;
		myL2 = L2;
	}
	
	public Cache getL1I() {
		return myL1I;
	}
	public Cache getL1D() {
			return myL1D;
	}
	public Cache getL2() {
		return myL2;
	}
	
}
