/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

public class Taboo<T> {
	
	Map<T, Set<T>> map;
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		map = new HashMap<>();
		for(int i = 0; i < rules.size()-1; i++) {
			T curr = rules.get(i);
			T next = rules.get(i+1);
			
			if(curr == null || next == null)
				continue;
			
			if(!map.containsKey(curr))
				map.put(curr, new HashSet<>());
			map.get(curr).add(next);
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(map.containsKey(elem))
			return map.get(elem);
		else
			return Collections.emptySet();
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		if(list.isEmpty())
			return;
		
		List<T> newList = new ArrayList<>();
		newList.add(list.get(0));

		for(int i = 1; i < list.size(); i++) {

			T prev = newList.get(newList.size()-1);
			T curr = list.get(i);
			
			if( !(map.containsKey(prev) && map.get(prev).contains(curr)) ) {
				newList.add(curr); // keep
			}
		}
	
		list.clear();
		loadListFromTo(newList, list);

	}
	
	private void loadListFromTo(List<T> newList, List<T> list) {
		for(int i = 0; i < newList.size(); i++) {
			list.add(newList.get(i));
		}
	}
	
}
