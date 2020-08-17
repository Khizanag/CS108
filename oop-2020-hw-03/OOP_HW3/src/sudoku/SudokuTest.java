package sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {

    public static final int[][] grid1 = Sudoku.stringsToGrid(
"2 0 0 7 3 0 4 6 5",
"3 6 4 0 2 5 1 7 8",
"0 0 5 8 0 4 3 0 0",
"7 1 0 4 8 0 5 0 6",
"0 0 2 1 5 0 0 4 7",
"0 4 0 0 7 9 8 0 1",
"0 5 0 6 4 0 0 0 0",
"0 0 0 5 9 0 0 1 0",
"0 0 0 0 1 0 6 5 9");

    @Test
    public void testDefaultEaseGrid(){
        Sudoku sudoku = new Sudoku(Sudoku.easyGrid);
        assertEquals(1, sudoku.solve());
    }

    @Test
    public void testDefaultMediumGrid(){
        Sudoku sudoku = new Sudoku(Sudoku.mediumGrid);
        assertEquals(1, sudoku.solve());
    }

    @Test
    public void testDefaultHardGrid(){
        Sudoku sudoku = new Sudoku(Sudoku.hardGrid);
        assertEquals(1, sudoku.solve());
    }

    @Test
    public void testMain(){
        Sudoku sudoku = new Sudoku(Sudoku.easyGrid);
        sudoku.main(null);
    }

    @Test
    public void testTimeElapsed(){
        Sudoku sudoku = new Sudoku(Sudoku.hardGrid);
        sudoku.solve();
        long elapsed = sudoku.getElapsed();
        assertTrue(elapsed > 0);
        assertTrue(elapsed < 1000);
    }

    @Test
    public void testMaxSolutions(){
        String zeroedString = "";
        for(int i = 0; i < 81; i++)
            zeroedString += "0 ";
        Sudoku sudoku = new Sudoku(zeroedString);
        assertTrue(sudoku.solve() >= Sudoku.MAX_SOLUTIONS);
    }

    @Test (expected = RuntimeException.class)
    public void testWrongGridInput(){  Sudoku sudoku = new Sudoku("sudokiuri"); }

    @Test
    public void testGrid1(){
        Sudoku sudoku = new Sudoku(grid1);
        System.out.println(sudoku.solve());
        System.out.println(sudoku.getElapsed());
        System.out.println(sudoku.getSolutionText());
    }
}