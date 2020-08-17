package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.isEmpty())
			return 0;
		
		int max = 0;
		int currCount = 1;
		for(int i = 1; i < str.length(); i++) {
			if(str.charAt(i) == str.charAt(i-1))
				currCount++;
			else {
				max = Math.max(max, currCount);
				currCount = 1;
			}
		}
		max = Math.max(max, currCount);
		
		return max;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String result = "";
		for(int i = 0; i < str.length(); i++) {
			if(Character.isDigit(str.charAt(i))) {
				if(i == str.length()-1)
					continue;
				int count = str.charAt(i) - '0';
				for(int j = 0; j < count; j++) {
					result += str.charAt(i+1);
				}
			} else 
				result += str.charAt(i);
		}
		return result;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		
		Set<String> set = new HashSet<>();
		
		for(int i = 0; i <= a.length()-len; i++) {
			String s = a.substring(i, i+len);
			set.add(s);
		}
		
		for(int i = 0; i <= b.length()-len; i++) {
			String s = b.substring(i, i+len);
			if(set.contains(s))
				return true;
		}
		
		return false;
	}
}
