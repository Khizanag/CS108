// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringCodeTest {
	
	//
	// blowup
	//
	@Test
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	
	@Test
	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}
	
	@Test
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}
	
	@Test
	public void testBlowup4() {
		assertEquals("test      ", StringCode.blowup("test 4 "));
	}
	
	@Test
	public void testBloup5() {
		StringCode test = new StringCode();
		assertEquals("GGGGGGGGG", StringCode.blowup("3G2G1G"));
		assertEquals("22", StringCode.blowup("22"));
		assertEquals("2222", StringCode.blowup("222"));
		assertEquals("222222", StringCode.blowup("2222"));
		
		assertEquals("", StringCode.blowup("1"));
		assertEquals("1", StringCode.blowup("11"));
		assertEquals("11", StringCode.blowup("111"));
		assertEquals("111", StringCode.blowup("1111"));
	}
	
	
	//
	// maxRun
	//
	@Test
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	
	@Test
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}
	
	@Test
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
	}
	
	@Test
	public void testMaxRunEmptyString() {
		assertEquals(0, StringCode.maxRun(""));
	}
	
	@Test
	public void testMaxRunAllEquals() {
		assertEquals(3, StringCode.maxRun("111222333444555666777111222555000999333"));
		assertEquals(2, StringCode.maxRun("8811009966338899221100"));
		assertEquals(1, StringCode.maxRun("0123456789876543210"));
	}
	
	@Test
	public void testMaxRunOnSpaces() {
		assertEquals(4, StringCode.maxRun("    "));
		assertEquals(10, StringCode.maxRun("          "));
		assertEquals(4, StringCode.maxRun("www    freeuni   edu   ge"));
	}
	
	@Test
	public void testMaxRunShufled() {
		assertEquals(3, StringCode.maxRun("ppooiiuu00011jj88cx pfjfiajfiqe99aonxaz"));
		assertEquals(1, StringCode.maxRun("1t2y3b5 68.z 0kjshyt"));
	}
	
	@Test
	public void testMaxRunInTheBegining() {
		assertEquals(5, StringCode.maxRun("aaaaabbvvccoooopplzfhe"));
		assertEquals(9, StringCode.maxRun("999999999888888887777777lllllggggggss"));
	}
	
	@Test
	public void testMaxRunAtTheEnd() {
		assertEquals(10, StringCode.maxRun("oopoopoopoopoopoopgggggggggg"));
		assertEquals(2, StringCode.maxRun("qwertyuiopp"));
	}

	//
	// stringIntersect
	//
	@Test
	public void testStringIntersect1() {
		assertEquals(true, StringCode.stringIntersect("abc", "abc", 1));
		assertEquals(true, StringCode.stringIntersect("abc", "abc", 2));
		assertEquals(true, StringCode.stringIntersect("abc", "abc", 3));
	}
	
	@Test
	public void testStringIntersect2() {
		assertEquals(false, StringCode.stringIntersect("aaaaa", "aaa", 4));
		assertEquals(true, StringCode.stringIntersect("aaaaa", "aaaaa", 1));
	}
	
	@Test
	public void testStringIntersect3() {
		assertEquals(true, StringCode.stringIntersect("1234567890", "456", 3));
		assertEquals(true, StringCode.stringIntersect("1234567890", "7890", 4));
		assertEquals(true, StringCode.stringIntersect("a", "a", 1));
		assertEquals(false, StringCode.stringIntersect("a", "a", 2));
	}
	
	@Test
	public void testStringIntersect4() {
		assertEquals(true, StringCode.stringIntersect("1234567890", "234567890", 9));
		assertEquals(false, StringCode.stringIntersect("123456890", "1234567890", 9));
	}
	
	@Test
	public void testStringIntersect5() {
		assertEquals(false, StringCode.stringIntersect("12345", "54321", 5));
		assertEquals(false, StringCode.stringIntersect("12345", "54321", 4));
		assertEquals(false, StringCode.stringIntersect("12345", "54321", 2));
	}
	
}
