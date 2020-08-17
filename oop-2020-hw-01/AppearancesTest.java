package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class AppearancesTest {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	
	@Test
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	
	@Test
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
	
	@Test
	public void testSameCount3() {
		// empty list cases
		List<Integer> emptyList = Arrays.asList();
		assertEquals(0, Appearances.sameCount(emptyList, Arrays.asList(1)));
		assertEquals(0, Appearances.sameCount(emptyList, Arrays.asList(0)));
		assertEquals(0, Appearances.sameCount(emptyList, Arrays.asList(-1)));
	}
	
	@Test
	public void testSameCount4() {
		// no appearance
		List<Integer> a = Arrays.asList(1, 2, 3, 4, 5, 1, 2, 3, 4, 5);
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(1, 1, 1)));
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 2, 2, 2, 2, 1)));
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(0)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(5, 5)));
		assertEquals(0, Appearances.sameCount(a, Arrays.asList(0, 1, 0)));
	}
	
	@Test
	public void testSameCount5() {
		Appearances app = new Appearances();
		assertEquals(2, Appearances.sameCount(Arrays.asList(0, 1, 0), Arrays.asList(0, 1, 0)));
		assertEquals(5, Appearances.sameCount(Arrays.asList(1, 2, 3, 4, 5), Arrays.asList(5, 4, 3, 2, 1)));
	}
	
	@Test
	public void testSameCountOnChars0() {
		List<String> a = stringToList("aaa");
		List<String> b = stringToList("cccbba");
		assertEquals(0, Appearances.sameCount(a, b));
	}
	
	@Test
	public void testSameCountOnChars1() {
		List<String> a = stringToList("bb");
		List<String> b = stringToList("cccbba");
		assertEquals(1, Appearances.sameCount(a, b));
	}
	
	@Test
	public void testSameCountOnChars() {
		List<String> a = stringToList("aaabbbcccddfggh");
		List<String> b = stringToList("bbbcccggaaahddf");
		assertEquals(7, Appearances.sameCount(a, b));
	}
	
	@Test
	public void testSameCountOnEmptyString() {
		List<String> a = stringToList("");
		List<String> b = stringToList("");
		assertEquals(0, Appearances.sameCount(a, b));
	}
}
