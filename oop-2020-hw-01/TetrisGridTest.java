package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class TetrisGridTest {
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	@Test
	public void testClear1() {
		boolean[][] before =
		{	
			{true, true, false, },
			{false, true, true, }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear2() {
		boolean[][] before =
		{	
			{}
		};
		
		boolean[][] after =
		{	
			{}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear3() {
		boolean[][] before =
		{	
		};
		
		boolean[][] after =
		{	
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear4() {
		boolean[][] before =
		{	
			{true, true, true, true}
		};
		
		boolean[][] after =
		{	
			{false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear5() {
		boolean[][] before =
		{	
			{false, true,  true,  false},
			{false, true,  true,  true },
			{false, false, true,  false},
			{false, true,  true,  true }
		};
		
		boolean[][] after =
		{	
			{false, true,  false, false},
			{false, true,  true,  false},
			{false, false, false, false},
			{false, true,  true,  false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear6() {
		boolean[][] before =
		{	
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, false, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true}
		};
		
		boolean[][] after =
		{	
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{false,false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false},
			{true, false, false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear7() {
		boolean[][] before =
		{	
			{true, true, true, true, true, false},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true},
			{true, true, true, true, true, true}
		};
		
		boolean[][] after =
		{	
			{false, false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false},
			{true,  false, false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear8() {
		boolean[][] before =
		{	
			{true, true, true, true, true},
			{true, true, true, true, true},
			{true, true, true, true, true}
		};
		
		boolean[][] after =
		{	
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	
	@Test
	public void testClear9() {
		boolean[][] before =
		{	
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}
		};
		
		boolean[][] after =
		{	
			{false, false, false, false, false},
			{false, false, false, false, false},
			{false, false, false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	@Test
	public void testClear10() {
		boolean[][] before =
		{	
			{true,  true,  false},
			{true,  false, true },
			{true,  true,  false}
		};
		
		boolean[][] after =
		{	
			{true,  false, false},
			{false, true , false},
			{true,  false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	@Test
	public void testClear11() {
		boolean[][] before =
		{	
			{false, false, false, false, false},
			{true, true, true, true, true},
			{true, true, true, true, true}
		};
		
		boolean[][] after =
		{	
			{false, false, false, false, false},
			{true, true, true, true, true},
			{true, true, true, true, true}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	@Test
	public void testClear12() {
		boolean[][] before =
		{	
			{false, true , false, true , false},
			{false, true , true, true , true }
		};
		
		boolean[][] after =
		{	
			{false, false, false, false, false},
			{false, true , true , false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	//
	// getGrid
	//
	
	@Test
	public void testGetGrid1() {
		boolean[][] grid = 
		{
				{},
				{},
				{}
		};
		
		TetrisGrid tetris = new TetrisGrid(grid);

		assertTrue( Arrays.deepEquals(grid, tetris.getGrid()) );
	}
	
	@Test
	public void testGetGrid2() {
		boolean[][] grid = 
		{
		};
		
		TetrisGrid tetris = new TetrisGrid(grid);

		assertTrue( Arrays.deepEquals(grid, tetris.getGrid()) );
	}
	
	@Test
	public void testGetGrid3() {
		boolean[][] grid = 
		{
				{false}
		};
		
		TetrisGrid tetris = new TetrisGrid(grid);

		assertTrue( Arrays.deepEquals(grid, tetris.getGrid()) );
	}
	
	@Test
	public void testGetGrid4() {
		boolean[][] grid = 
		{
				{false, false, true},
				{false, true, false},
				{false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(grid);

		assertTrue( Arrays.deepEquals(grid, tetris.getGrid()) );
	}
}
