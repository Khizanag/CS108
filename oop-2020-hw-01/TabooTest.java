// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class TabooTest {

	@Test
	public void testNoFollow1() {
		List<Integer> rules = Arrays.asList(new Integer[] {1, 2, 3});
		Taboo<Integer> taboo = new Taboo<>(rules);
		
		Set<Integer> setFor1 = taboo.noFollow(1);
		assertEquals(true, setFor1.contains(2));
		assertEquals(false, setFor1.contains(3));
		
		Set<Integer> setFor2 = taboo.noFollow(2);
		assertEquals(false, setFor2.contains(1));
		assertEquals(true, setFor2.contains(3));
		
		Set<Integer> setFor3 = taboo.noFollow(3);
		assertEquals(false, setFor3.contains(1));
		assertEquals(false, setFor3.contains(2));
		
	}
	
	@Test
	public void testNoFollow2() {
		List<Integer> rules = Arrays.asList(new Integer[] {1, 2, 3, 1, 2, 1, 3, 1, 2, 1, 3, 3, 2, 1, 1, 2, 2});
		Taboo<Integer> taboo = new Taboo<>(rules);
		
		Set<Integer> setFor1 = taboo.noFollow(1);
		assertEquals(true, setFor1.contains(1));
		assertEquals(true, setFor1.contains(2));
		assertEquals(true, setFor1.contains(3));
		
		Set<Integer> setFor2 = taboo.noFollow(2);
		assertEquals(true, setFor2.contains(1));
		assertEquals(true, setFor2.contains(2));
		assertEquals(true, setFor2.contains(3));
		
		Set<Integer> setFor3 = taboo.noFollow(3);
		assertEquals(true, setFor3.contains(1));
		assertEquals(true, setFor3.contains(2));
		assertEquals(true, setFor3.contains(3));
	}
	
	@Test
	public void testNoFollow3() {
		List<Integer> rules = Arrays.asList(new Integer[] {1, null, 2, null, 3, null, 1});
		Taboo<Integer> taboo = new Taboo<>(rules);
		
		Set<Integer> setFor1 = taboo.noFollow(1);
		assertEquals(false, setFor1.contains(1));
		assertEquals(false, setFor1.contains(2));
		assertEquals(false, setFor1.contains(3));
		
		Set<Integer> setFor2 = taboo.noFollow(2);
		assertEquals(false, setFor2.contains(1));
		assertEquals(false, setFor2.contains(2));
		assertEquals(false, setFor2.contains(3));
		
		Set<Integer> setFor3 = taboo.noFollow(3);
		assertEquals(false, setFor3.contains(1));
		assertEquals(false, setFor3.contains(2));
		assertEquals(false, setFor3.contains(3));
	}
	
	@Test
	public void testNoFollow4() {
		List<Integer> rules = Arrays.asList(new Integer[] {1, 2, 3, 1});
		Taboo<Integer> taboo = new Taboo<>(rules);
		
		assertEquals(Collections.emptySet(), taboo.noFollow(5));
		assertEquals(Collections.emptySet(), taboo.noFollow(0));
		assertEquals(Collections.emptySet(), taboo.noFollow(-1));
	}
	
	//
	// reduce
	//
	
	@Test
	public void testReduce1() {
		List<Integer> rules = Arrays.asList(new Integer[] {1, 2, 3});
		Taboo<Integer> taboo = new Taboo<>(rules);

		List<Integer> list = new ArrayList<>(Arrays.asList(new Integer[] {1, 2, 1, 2, 1, 1, 90, 100, 3, 1}));
		taboo.reduce(list);

		assertEquals(true, list.equals(Arrays.asList(new Integer[] {1, 1, 1, 1, 90, 100, 3, 1})));
	}
	
	@Test
	public void testReduce2() {
		List<String> rules = Arrays.asList(new String[] {"a", "c", "a", "b"});
		Taboo<String> taboo = new Taboo<>(rules);
		List<String> list = new ArrayList<>(Arrays.asList(new String[] {"a", "c", "b", "x", "c", "a"}));
		taboo.reduce(list);
		assertEquals(true, list.equals(Arrays.asList(new String[] {"a", "x", "c"} )));
	}
	
	@Test
	public void testReduce3() {
		List<String> rules = Arrays.asList(new String[] {});
		Taboo<String> taboo = new Taboo<>(rules);
		List<String> list = new ArrayList<>(Arrays.asList(new String[] {"a", "c", "b", "x", "c", "a"}));
		taboo.reduce(list);
		assertEquals(true, list.equals(Arrays.asList(new String[] {"a", "c", "b", "x", "c", "a"} )));
	}

	@Test
	public void testReduce4() {
		List<String> rules = Arrays.asList(new String[] {"1", "2", "2", "2", "1", "1"});
		Taboo<String> taboo = new Taboo<>(rules);
		List<String> list = new ArrayList<>(Arrays.asList(new String[] {"2", "1", "2", "2", "1", "1"}));
		taboo.reduce(list);
		assertEquals(true, list.equals(Arrays.asList(new String[] {"2"} )));
	}
	
	@Test
	public void testReduce5() {
		List<String> rules = Arrays.asList(new String[] {"1", "2", null, null, null, "3", "4", null});
		Taboo<String> taboo = new Taboo<>(rules);
		List<String> list = new ArrayList<>(Arrays.asList(new String[] {"1", "2", "2", "1", "1", "3", "3", "4", "4"}));
		taboo.reduce(list);
		assertEquals(true, list.equals(Arrays.asList(new String[] {"1", "1", "1", "3", "3"} )));
	}
	
	@Test
	public void testReduce6() {
		List<String> rules = Arrays.asList(new String[] {"1", "2", "2", "2", "1", "1"});
		Taboo<String> taboo = new Taboo<>(rules);
		List<String> list = new ArrayList<>(Arrays.asList(new String[] {}));
		taboo.reduce(list);
		assertEquals(true, list.equals(Arrays.asList(new String[] {} )));
	}
	
}
