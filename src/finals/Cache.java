package finals;
import finals.Runner;

public class Cache {
	
	private int entries;
	private int latency;
	private int hit;
	private int miss;
	private int access;
	
	private int number_of_offset_bits;
	private int number_of_sets;
	private int number_of_index_bits;
	private int index_mask;
	
	private CacheEntry[] theCache;
	
	//1 word = 4 bytes
	public Cache(int entries, int latency) {
		this.entries = entries; //number of cache entries 
		this.latency = latency; //latency when reaching for latency.
		
		theCache = new CacheEntry[entries];//cache block
		hit = 0;
		miss = 0;
		
		number_of_offset_bits = (int) (Math.log(Runner.getCacheLineSize()) / Math //gets the offset bits
				.log(2));
		number_of_sets = entries / Runner.getNumberOfWays(); //gets the number of sets
		number_of_index_bits = (int) (Math.log(number_of_sets) / Math.log(2)); //number of index bits
		index_mask = ((1 << number_of_index_bits) - 1); //index masks
	}
	
	
	public void addEntry(CacheEntry entry, int real_index)
	{
		theCache[real_index] = entry;
	}

	public int getOffset(int address)
	{
		return address & ((1 << number_of_offset_bits) - 1);
	}

	public int getIndex(int address)
	{
		return (address >>> number_of_offset_bits) & (index_mask);
	}

	public int getTag(int address)
	{
		return (address >>> (number_of_offset_bits + number_of_index_bits));
	}
	
	int getEntries() {
		return entries;
	}
	
	void isHit() {
		hit++;
	}
	
	void isMiss() {
		miss++;
	}
	int getAccess() {
		return access;
	}
	
	void isAccess() {
		access++;
	}
	//public abstract void add (Integer newItem);
	
	//public abstract boolean contains(Integer theItem);
	
//	@Override
//	public String toString(){
//		
//	}
}