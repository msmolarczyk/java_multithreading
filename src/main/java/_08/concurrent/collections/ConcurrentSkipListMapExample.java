package _08.concurrent.collections;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapExample {
	
	public static class MyComparator implements Comparator<Integer>{
		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 > o2){
				return -1;
			} else if (o1 < o2){
				return 1;
			}
			return 0;
		}
	}
	
	public static void main(String[] args) {
		ConcurrentSkipListMap<Integer, String> clm = new ConcurrentSkipListMap<Integer, String>();
		clm.put(1, "Monday");
		clm.put(2, "Tuesday");
		clm.put(3, "Wednesday");
		clm.put(4, "Thursday");
		clm.put(5, "Friday");
		clm.put(6, "Saturday");
		clm.put(7, "Sunday");
		System.out.print("Mappings = ");
		System.out.println(clm);
		
		Entry<Integer, String> me = clm.lowerEntry(5);
		System.out.println(me);
		System.out.println("Key " + me.getKey());
		System.out.println("Value " + me.getValue());

		System.out.print("Tail elements are: ");
		System.out.println(clm.tailMap(4));

		System.out.println("Ceiling: " + clm.ceilingEntry(6));
		System.out.println("Ceiling: " + clm.floorEntry(6));
		
		// returns the key value pair of least key in the map
		System.out.println("Value with least key: " + clm.firstEntry());

		// returns the key value pair of greatest key in the map
		System.out.println("Value with greatest key: " + clm.lastEntry());

		// returns the lowest entry and also removes it from the map
		System.out.println("value removed from the map:" + clm.pollFirstEntry());

		// returns the greatest entry and also removes from the map
		System.out.println("value removed from the map:" + clm.pollLastEntry());
	}
}