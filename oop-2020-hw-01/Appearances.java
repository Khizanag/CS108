package assign1;

import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		Map<T, Integer> mapA = new HashMap<>();
		Map<T, Integer> mapB = new HashMap<>();
		
		loadCounts(a, mapA);
		loadCounts(b, mapB);

		int count = 0;
		for(T t : mapA.keySet()) {
			
			if(mapB.containsKey(t) && (mapA.get(t).equals(mapB.get(t))))
					count++;
		}

		return count;
	}
	
	private static <T> void loadCounts(Collection<T> c, Map<T, Integer> map){
		Iterator<T> it = c.iterator();
		while(it.hasNext()) {
			T t = it.next();
			if(map.containsKey(t)) {
				int currCount = map.get(t);
				map.put(t, currCount+1);
			} else {
				map.put(t, 1);
			}
		}
	}
}
