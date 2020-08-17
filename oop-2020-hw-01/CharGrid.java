// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

import java.awt.*;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	 	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {

		Integer upper = -1;
		Integer left  = -1;
		Integer lower = -1;
		Integer right = -1;

		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j] == ch) {

					if(upper.equals(-1)) {
						upper = i;
						left = j;
						lower = i;
						right = j;
					} else {
						upper = Math.max(upper, i);
						lower = Math.min(lower, i);
						right = Math.max(right, j);
						left  = Math.min(left, j);
					}

				}
			}
		}
		
		if(upper.equals(-1))
			return 0;
		else
			return (upper-lower+1) * (right-left+1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int numPluses = 0;
		for(int i = 1; i < grid.length-1; i++) {
			for(int j = 1; j < grid[i].length-1; j++) {
				if(isPlus(i, j))
					numPluses++;
			}
		}
		return numPluses;
	}
	
	private boolean isPlus(int i, int j) {
		int upperLen =  getUpperArmLen(i, j);
		return (upperLen >= 2 && upperLen == getLowerArmLen(i, j) 
				&& upperLen == getLeftArmLen(i, j) && upperLen == getRightArmLen(i, j));
	}
	
	private int getUpperArmLen(int i, int j) {
		return getArmLen(i, j, -1, 0);
	}

	private int getLowerArmLen(int i, int j) {
		return getArmLen(i, j, +1, 0);
	}

	private int getLeftArmLen(int i, int j) {
		return getArmLen(i, j, 0, -1);
	}

	private int getRightArmLen(int i, int j) {
		return getArmLen(i, j, 0, +1);
	}

	private int getArmLen(int i, int j, int di, int dj) {
		char ch = grid[i][j];
		int count = 0;
		while( (i >= 0) && (j >= 0) && (i < grid.length) && (j < grid[0].length) && (grid[i][j] == ch)) {
			count++;
			i += di;
			j += dj;
		}
		return count;
	}
	
}
