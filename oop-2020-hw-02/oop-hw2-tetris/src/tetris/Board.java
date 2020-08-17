// Board.java
package tetris;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
 */
public class Board	{
    // Some ivars are stubbed out for you:
    private int width;
    private int height;
    private boolean[][] grid;
    private boolean DEBUG = true;
    boolean committed;

    /* variables to make some operations faster */
    private int[] widths;
    private int[] heights;
    private int maxHeight;

    /* variables to do backup */
    private boolean[][] gridBackUp;
    private int[] widthsBackUp;
    private int[] heightsBackUp;
    private int maxHeightBackUp;

    // Here a few trivial methods are provided:

    /**
     Creates an empty board of the given width and height
     measured in blocks.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new boolean[width][height];
        committed = true;

        widths = new int[height];
        heights = new int[width];

        initBackUpVariables();
    }

    private void initBackUpVariables(){
        widthsBackUp = new int[widths.length];
        heightsBackUp = new int[heights.length];
        gridBackUp = new boolean[width][height];
        maxHeightBackUp = 0;
    }


    /**
     Returns the width of the board in blocks.
     */
    public int getWidth() {
        return width;
    }


    /**
     Returns the height of the board in blocks.
     */
    public int getHeight() {
        return height;
    }


    /**
     Returns the max column height present in the board.
     For an empty board this is 0.
     */
    public int getMaxHeight() {
        return maxHeight;
    }


    /**
     Checks the board for internal consistency -- used
     for debugging.
     */
    public void sanityCheck() {
        if (DEBUG) {
            assertTrue(getMaxHeight() == getBruteMaxHeight());
            assertTrue(widthsAndHeightsAreCorrect());
            assertTrue(getHeight() > -1);
            assertTrue(getHeight() > -1);
        }
    }

    /* Uses brute force to find real value of max height, used in sanityCheck */
    private int getBruteMaxHeight(){
        int bruteMaxHeight = 0;
        for(int h : heights){
            bruteMaxHeight = Math.max(bruteMaxHeight, h);
        }
        return bruteMaxHeight;
    }

    private boolean widthsAndHeightsAreCorrect(){
        int[] checkerWidths = new int[height];
        int[] checkerHeights = new int[width];
        for(int row = 0; row < height; row++){
            for(int col = 0; col < width; col++){
                if(grid[col][row]){
                    checkerHeights[col] = Math.max(checkerHeights[col], row+1);
                    checkerWidths[row]++;
                }
            }
        }

        boolean areCorrect = true;

        for(int i = 0; i < widths.length; i++)
            assertTrue(widths[i] == checkerWidths[i]);
        for(int i = 0; i < heights.length; i++)
            assertTrue(heights[i] == checkerHeights[i]);
        return areCorrect;
    }

    /**
     Given a piece and an x, returns the y
     value where the piece would come to rest
     if it were dropped straight down at that x.

     <p>
     Implementation: use the skirt and the col heights
     to compute this fast -- O(skirt length).
     */
    public int dropHeight(Piece piece, int x) {
        int dropHeight = 0;
        int[] skirt = piece.getSkirt();
        for(int i = 0; i < piece.getWidth(); i++){
            int newDropHeight = heights[x+i] - skirt[i];
            dropHeight = Math.max(dropHeight, newDropHeight);
        }
        return dropHeight;
    }


    /**
     Returns the height of the given column --
     i.e. the y value of the highest block + 1.
     The height is 0 if the column contains no blocks.
     */
    public int getColumnHeight(int col) {
        return heights[col];
    }


    /**
     Returns the number of filled blocks in
     the given row.
     */
    public int getRowWidth(int row) {
        return widths[row];
    }


    /**
     Returns true if the given block is filled in the board.
     Blocks outside of the valid width/height area
     always return true.
     */
    public boolean getGrid(int x, int y) {
        if(isOutOfBounds(x, y))
            return true;
        return grid[x][y];
    }

    private boolean isOutOfBounds(int x, int y){
        return (x < 0) || (x >= width) || (y < 0) || (y >= height);
    }

    public static final int PLACE_OK = 0;
    public static final int PLACE_ROW_FILLED = 1;
    public static final int PLACE_OUT_BOUNDS = 2;
    public static final int PLACE_BAD = 3;

    /**
     Attempts to add the body of a piece to the board.
     Copies the piece blocks into the board grid.
     Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
     for a regular placement that causes at least one row to be filled.

     <p>Error cases:
     A placement may fail in two ways. First, if part of the piece may falls out
     of bounds of the board, PLACE_OUT_BOUNDS is returned.
     Or the placement may collide with existing blocks in the grid
     in which case PLACE_BAD is returned.
     In both error cases, the board may be left in an invalid
     state. The client can use undo(), to recover the valid, pre-place state.
     */
    public int place(Piece piece, int x, int y) {
//         flag !committed problem
        if (!committed) throw new RuntimeException("place commit problem");

        backUp();
        committed = false;

        if(placeIsOutBounds(piece, x, y))
            return PLACE_OUT_BOUNDS;

        if(isPlaceBad(piece, x, y))
            return PLACE_BAD;

        return safePlace(piece, x, y);
    }


    /* This method is called only if we know that it is
     * possible to place piece on the table without errors
     * Used in: place
     */
    private int safePlace(Piece piece, int x, int y){
        int placeStatus = PLACE_OK;

        TPoint[] body = piece.getBody();
        for(int i = 0; i < body.length; i++){
            int newX = x + body[i].x;
            int newY = y + body[i].y;
            grid[newX][newY] = true;

            widths[newY]++;
            if(widths[newY] == width)
                placeStatus = PLACE_ROW_FILLED;
            heights[newX] = Math.max(heights[newX], newY+1);
            maxHeight = Math.max(maxHeight, heights[newX]);
        }

        sanityCheck();

        return placeStatus;
    }

    private boolean placeIsOutBounds(Piece piece, int x, int y) {
        TPoint[] body = piece.getBody();
        for(TPoint tp : body){
            int newX = x + tp.x;
            int newY = y + tp.y;
            if(isOutOfBounds(newX, newY))
                return true;
        }
        return false;
    }


    private boolean isPlaceBad(Piece piece, int x, int y){
        TPoint[] body = piece.getBody();
        for(TPoint tp : body){
            int newX = x + tp.x;
            int newY = y + tp.y;
            if(grid[newX][newY])
                return true;
        }
        return false;
    }


    /**
     Deletes rows that are filled all the way across, moving
     things above down. Returns the number of rows cleared.
     */
    public int clearRows() {
        if(committed) {
            backUp();
            committed = false;
        }

        int whereRow = 0;

        for(int row = 0; row < height; row++){
            if(rowShouldBeCleared(row)) // should be cleared
                continue;
            if(whereRow != row){
                copyRow(whereRow, row);
                widths[whereRow] = widths[row];
            }
            whereRow++; // for the next time our dest row will ++
        }

        formallyClearUpperRows(whereRow);
        updateHeights();

        sanityCheck();

        return (height - whereRow);
    }

    /*
     * Used in: clearRows()
     */
    private void copyRow(int destRow, int srcRow){
        for(int col = 0; col < width; col++)
            grid[col][destRow] = grid[col][srcRow];
    }

    private boolean rowShouldBeCleared(int row){
        return (widths[row] == width);
    }

    /*
     * Updates heights array and maxHeight;
     * Used in: clearRows()
     */
    void updateHeights(){
        maxHeight = 0;
        for(int x = 0; x < width; x++){
            heights[x] = getHeightCount(x);
            maxHeight = Math.max(maxHeight, heights[x]);
        }
    }

    /* Uses brute force and returns real height of column;
     * Height of column in coordinate of highest point + 1
     *  Used in: sanityCheck()
     */
    private int getHeightCount(int x){
        for(int y = height-1; y >= 0; y--){
            if(grid[x][y])
                return y+1;
        }
        return 0;
    }

    /* fill upper rows (which should be fallen down) with false */
    private void formallyClearUpperRows(int whereRow) {
        for(int row = whereRow; row < height; row++){
            for(int col = 0; col < width; col++)
                grid[col][row] = false;
            widths[row] = 0;
        }
    }


    /**
     Reverts the board to its state before up to one place
     and one clearRows();
     If the conditions for undo() are not met, such as
     calling undo() twice in a row, then the second undo() does nothing.
     See the overview docs.
     */
    public void undo() {
        if(!committed){ // else it's useless
            commit();

            System.arraycopy(widthsBackUp, 0, widths, 0, height);
            System.arraycopy(heightsBackUp, 0, heights, 0, width);
            for (int i = 0; i < width; i++) {
                System.arraycopy(gridBackUp[i], 0, grid[i], 0, height);
            }
            maxHeight = maxHeightBackUp;

            sanityCheck();
        }
    }

    private void backUp(){
        System.arraycopy(widths, 0, widthsBackUp, 0, height);
        System.arraycopy(heights, 0, heightsBackUp, 0, width);
        for (int i = 0; i < width; i++) {
            System.arraycopy(grid[i], 0, gridBackUp[i], 0, height);
        }
        maxHeightBackUp = maxHeight;
        sanityCheck();
    }


    /**
     Puts the board in the committed state.
     */
    public void commit() {
        committed = true;
    }

    /*
     Renders the board state as a big String, suitable for printing.
     This is the sort of print-obj-state utility that can help see complex
     state change over time.
     (provided debugging utility)
     */
    public String toString() {
        StringBuilder buff = new StringBuilder();
        for (int y = height-1; y>=0; y--) {
            buff.append('|');
            for (int x=0; x<width; x++) {
                if (getGrid(x,y)) buff.append('+');
                else buff.append(' ');
            }
            buff.append("|\n");
        }
        for (int x=0; x<width+2; x++) buff.append('-');
        return(buff.toString());
    }
}


