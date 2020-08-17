//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

import java.util.ArrayList;
import java.util.List;

public class TetrisGrid {
	
	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		List<Integer> list = new ArrayList<>();
		
		loadColsToSave(list);
		
		int numRows = grid.length;
		int numCols = (numRows > 0) ? grid[0].length : 0;
		boolean[][] newGrid = new boolean[numRows][numCols];

		loadSavedColsToArray(newGrid, list);
		
		loadUpperFalses(newGrid, list);
		
		grid = newGrid;
	}
	
	private void loadColsToSave(List<Integer> list) {
		int numCols = (grid.length > 0) ? grid[0].length : 0;
		for(int col = 0; col < numCols; col++) {
			if(!isClearableColumn(col))
				list.add(col);
		}
	}
	
	private void loadSavedColsToArray(boolean[][] newGrid, List<Integer> list) {
		for(int col = 0; col < list.size(); col++) {
			int gridCol = list.get(col);
			for(int row = 0; row < grid.length; row++) {
				newGrid[row][col] = grid[row][gridCol];
			}
		}
	}
	
	private void loadUpperFalses(boolean[][] newGrid, List<Integer> list) {
		int numRows = grid.length;
		int numCols = (numRows > 0) ? grid[0].length : 0;
		for(int col = list.size(); col < numCols; col++) {
			for(int row = 0; row < numRows; row++) {
				newGrid[row][col] = false;
			}
		}
	}
	
	private boolean isClearableColumn(int col) {
		for(int row = 0; row < grid.length; row++) {
			if(!grid[row][col])
				return false;
		}
		return true;
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
}
