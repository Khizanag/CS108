// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	@Test
	public void testCharArea3() {
		// test edges
		char[][] grid = new char[][] {
			{'c', 'a', ' ', 'G'},
			{'b', ' ', 'b', ' '},
			{'G', ' ', 'a', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(12, cg.charArea('G'));
	}
	
	@Test
	public void testCharArea4() {
		// test edges
		char[][] grid = new char[][] {
			{'c', 'a', ' ', 'G'},
			{'b', ' ', 'b', ' '},
			{'G', ' ', 'a', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.charArea('X'));
	}
	
	@Test
	public void testCharArea5() {
		// test edges
		char[][] grid = new char[][] {
			{'c', 'a', ' ', 'G'},
			{'b', ' ', 'b', ' '},
			{'G', ' ', 'a', ' '}
		};
		CharGrid cg = new CharGrid(grid);
		assertEquals(12, cg.charArea('G'));
	}
	
	
	
	@Test
	public void testCountPlus1() {
		char[][] grid = new char[][] {
			{}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus2() {
		char[][] grid = new char[][] {
			{' '}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus3() {
		char[][] grid = new char[][] {
			{'x', 'x'},
			{'x', 'x'}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus4() {
		char[][] grid = new char[][] {
			{'x', 'x', 'x', 'x', 'x'}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus6() {
		char[][] grid = new char[][] {
			{' ', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', ' ', 'x', ' ', ' ', ' ', '-', ' '},
			{' ', 'x', 'x', 'x', 'x', 'x', ' ', ' ', ' '},
			{'-', ' ', ' ', 'x', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', '-', 'x', ' ', '-', ' ', '-', ' '},
			{' ', '-', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', '-', 'z', 'z', 'z', 'z', 'z', 'z'},
			{' ', '-', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	
	@Test
	public void testCountPlus7() {
		char[][] grid = new char[][] {
			{'c', 'a', 'x', 'G'},
			{'b', 'x', 'x', 'x'},
			{'G', ' ', 'x', ' '}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	@Test
	public void testCountPlus8() {
		char[][] grid = new char[][] {
			{'c', 'a', 'x', 'G'},
			{'x', 'x', 'x', 'x'},
			{'G', ' ', 'x', ' '}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus9() {
		char[][] grid = new char[][] {
			{'c', 'a', 'x', 'G'},
			{'c', 'x', 'x', 'l'},
			{'G', ' ', 'x', ' '}
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
	}
	
	@Test
	public void testCountPlus10() {
		char[][] grid = new char[][] {
			{' ', ' ', ' ', 'x', ' ', ' ', ' ', '+', ' '},
			{' ', ' ', ' ', 'x', 'x', ' ', '+', '+', '+'},
			{' ', 'x', 'x', 'x', 'x', 'x', ' ', '+', ' '},
			{'-', ' ', ' ', 'x', 'x', ' ', '-', '+', ' '},
			{' ', ' ', '-', 'x', 'x', '-', '-', '-', ' '},
			{' ', '-', ' ', ' ', ' ', ' ', '-', 'z', ' '},
			{' ', ' ', '-', 'z', 'z', '-', 'z', 'z', 'z'},
			{' ', '-', ' ', ' ', ' ', ' ', ' ', 'z', ' '},
		};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(3, cg.countPlus());
	}
}
