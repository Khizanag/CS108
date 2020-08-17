package tetris;

import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
    Board b;
    Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

    // This shows how to build things in setUp() to re-use
    // across tests.

    // In this case, setUp() makes shapes,
    // and also a 3X6 board, with pyr placed at the bottom,
    // ready to be used by tests.
    @Before
    public void setUp() throws Exception {
        b = new Board(3, 6);
        pyr1 = new Piece(Piece.PYRAMID_STR);
        pyr2 = pyr1.computeNextRotation();
        pyr3 = pyr2.computeNextRotation();
        pyr4 = pyr3.computeNextRotation();

        s = new Piece(Piece.S1_STR);
        sRotated = s.computeNextRotation();
        b.place(pyr1, 0, 0);
    }

    @Test
    public void testBoardInit(){
        Board board = new Board(10, 10);
        assertEquals(10, board.getWidth());
        assertEquals(10, board.getHeight());

        board = new Board(3, 7);
        assertEquals(3, board.getWidth());
        assertEquals(7, board.getHeight());

        board = new Board(1, 111);
        assertEquals(1, board.getWidth());
        assertEquals(111, board.getHeight());
    }

    // Check the basic width/height/max after the one placement
    @Test
    public void testSample1() {
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(2, b.getColumnHeight(1));
        assertEquals(2, b.getMaxHeight());
        assertEquals(3, b.getRowWidth(0));
        assertEquals(1, b.getRowWidth(1));
        assertEquals(0, b.getRowWidth(2));
    }

    // Place sRotated into the board, then check some measures
    @Test
    public void testSample2() {
        b.commit();
        int result = b.place(sRotated, 1, 1);
        assertEquals(Board.PLACE_OK, result);
        assertEquals(1, b.getColumnHeight(0));
        assertEquals(4, b.getColumnHeight(1));
        assertEquals(3, b.getColumnHeight(2));
        assertEquals(4, b.getMaxHeight());
    }

    // Make  more tests, by putting together longer series of
    // place, clearRows, undo, place ... checking a few col/row/max
    // numbers that the board looks right after the operations.

    @Test
    public void testPlaceOutOfBounds(){
        Board board = new Board(10, 10);
        Piece stick = new Piece(Piece.STICK_STR);
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(pyr1, -1, 0));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(pyr1, -9, 0));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(pyr1, 5, -1));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(pyr1, -1, -1));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(pyr1, 11, 0));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(stick, 9, 9));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(stick, 0, 7));board.undo();
        assertEquals(Board.PLACE_OUT_BOUNDS, board.place(stick, 11, 11));board.undo();
    }

    @Test
    public void testBadPlace(){
        Board board = new Board(10, 10);
        board.place(pyr1, 0, 0); board.commit();
        assertEquals(Board.PLACE_BAD, board.place(pyr1, 0, 1)); board.undo();
        assertEquals(Board.PLACE_BAD, board.place(pyr1, 2, 0)); board.undo();
        assertEquals(Board.PLACE_BAD, board.place(pyr1, 0, 0)); board.undo();
    }

    @Test
    public void testPlaceRowFilled(){
        Board board = new Board(5, 10);
        Piece stick = new Piece(Piece.STICK_STR);
        Piece pyramid = new Piece(Piece.PYRAMID_STR);
        assertEquals(Board.PLACE_OK, board.place(stick, 0 ,0)); board.commit();
        System.out.println(board.toString());
        assertEquals(Board.PLACE_OK, board.place(stick, 1 ,0)); board.commit();
        System.out.println(board.toString());
        assertEquals(Board.PLACE_OK, board.place(stick, 2 ,0)); board.commit();
        System.out.println(board.toString());
        assertEquals(Board.PLACE_OK, board.place(stick, 3,0)); board.commit();
        System.out.println(board.toString());
        assertEquals(Board.PLACE_OK, board.place(pyramid, 1,4)); board.commit();
        System.out.println(board.toString());
        int y  = board.dropHeight(pyramid, 0);
        assertEquals(Board.PLACE_OK, board.place(pyramid, 0, y)); board.commit();
        System.out.println(board.toString());
        assertEquals(Board.PLACE_ROW_FILLED, board.place(stick, 4,0)); board.commit();
        System.out.println(board.toString());
        board.clearRows(); board.commit();
        System.out.println(board.toString());

        assertEquals(Board.PLACE_OK, board.place(stick, 4,0)); board.commit();
        System.out.println(board.toString());

        assertEquals(Board.PLACE_ROW_FILLED, board.place(stick, 3,1)); board.commit();
        System.out.println(board.toString());

        board.clearRows(); board.commit();
        System.out.println(board.toString());

        assertEquals(Board.PLACE_ROW_FILLED, board.place(stick, 0,0)); board.commit();
        System.out.println(board.toString());

        board.clearRows(); board.commit();
        System.out.println(board.toString());
    }

    @Test
    public void testPlace1(){
        Board board = new Board(10, 10);
//        board.place()
    }

    @Test
    public void testClearEdge(){
        Board board = new Board(4, 10);
        Piece stick = new Piece(Piece.STICK_STR);
        board.place(stick, 0, 0); board.commit();
        board.place(stick, 1, 0); board.commit();
        board.place(stick, 2, 0); board.commit();
        board.place(stick, 3, 0); board.commit();

        board.place(pyr1, 0, 4); board.commit();
        System.out.println(board.toString());
        assertEquals(6, board.getMaxHeight());

        assertEquals(4, board.clearRows()); board.commit();
        assertEquals(2, board.getMaxHeight());
        System.out.println(board.toString());

    }

    @Test
    public void testUncommitedClearRows(){
        Board board = new Board(5, 5);
        board.place(pyr1, 1, 1);
        board.clearRows();
        System.out.println(board.toString());
        assertEquals(board.getMaxHeight(), 3);
    }

    @Test
    public void testUndoOnCommitted(){
        b.commit();
        b.undo();
    }

    @Test(expected = RuntimeException.class)
    public void testPlaceNotCommitted(){
        b.committed = false;
        b.place(pyr1, 0, 5);
    }

    @Test
    public void testOutOfBounds(){
        Board board = new Board(10, 10);
        assertTrue(board.getGrid(-1, 0));
        assertTrue(board.getGrid(0, -1));
        assertTrue(board.getGrid(-1, -1));
        assertTrue(board.getGrid(11, 0));
        assertTrue(board.getGrid(0, 11));
        assertTrue(board.getGrid(11, 11));
    }

}

