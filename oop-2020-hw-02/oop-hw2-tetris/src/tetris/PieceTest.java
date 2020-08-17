package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
    // You can create data to be used in the your
    // test cases like this. For each run of a test method,
    // a new PieceTest object is created and setUp() is called
    // automatically by JUnit.
    // For example, the code below sets up some
    // pyramid and s pieces in instance variables
    // that can be used in tests.
    private Piece pyr1, pyr2, pyr3, pyr4;
    private Piece s, sRotated;

    private static final int NUM_TESTING_ROTATIONS = 25;

    @Before
    public void setUp() throws Exception {

        pyr1 = new Piece(Piece.PYRAMID_STR);
        pyr2 = pyr1.computeNextRotation();
        pyr3 = pyr2.computeNextRotation();
        pyr4 = pyr3.computeNextRotation();

        s = new Piece(Piece.S1_STR);
        sRotated = s.computeNextRotation();
    }

    // Here are some sample tests to get you started

    @Test
    public void testSampleSize() {
        // Check size of pyr piece
        assertEquals(3, pyr1.getWidth());
        assertEquals(2, pyr1.getHeight());

        // Now try after rotation
        // Effectively we're testing size and rotation code here
        assertEquals(2, pyr2.getWidth());
        assertEquals(3, pyr2.getHeight());

        // Now try with some other piece, made a different way
        Piece l = new Piece(Piece.STICK_STR);
        assertEquals(1, l.getWidth());
        assertEquals(4, l.getHeight());
    }


    // Test the skirt returned by a few pieces
    @Test
    public void testSampleSkirt() {
        // Note must use assertTrue(Arrays.equals(... as plain .equals does not work
        // right for arrays.
        assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
        assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));

        assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
        assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
    }

    @Test
    public void testSkirt(){
        Piece st = new Piece(Piece.STICK_STR);
        assertTrue(Arrays.equals(new int[]{0}, st.getSkirt()));

        Piece l1 = new Piece(Piece.L1_STR);
        assertTrue(Arrays.equals(new int[]{0, 0}, l1.getSkirt()));

        Piece l2 = new Piece(Piece.L2_STR);
        assertTrue(Arrays.equals(new int[]{0, 0}, l2.getSkirt()));

        Piece s1 = new Piece(Piece.S1_STR);
        assertTrue(Arrays.equals(new int[]{0, 0, 1}, s1.getSkirt()));

        Piece s2 = new Piece(Piece.S2_STR);
        assertTrue(Arrays.equals(new int[]{1, 0, 0}, s2.getSkirt()));

        Piece sqr = new Piece(Piece.SQUARE_STR);
        assertTrue(Arrays.equals(new int[]{0, 0}, sqr.getSkirt()));

        Piece p = new Piece(Piece.PYRAMID_STR);
        assertTrue(Arrays.equals(new int[]{0, 0, 0}, p.getSkirt()));
    }

    @Test
    public void testBodyLength(){
        Piece st = new Piece(Piece.STICK_STR);
        assertEquals(4, st.getBody().length);

        Piece l1 = new Piece(Piece.L1_STR);
        assertEquals(4, l1.getBody().length);

        Piece l2 = new Piece(Piece.L2_STR);
        assertEquals(4, l2.getBody().length);

        Piece s1 = new Piece(Piece.S1_STR);
        assertEquals(4, s1.getBody().length);

        Piece s2 = new Piece(Piece.S2_STR);
        assertEquals(4, s2.getBody().length);

        Piece sqr = new Piece(Piece.SQUARE_STR);
        assertEquals(4, sqr.getBody().length);

        Piece p = new Piece(Piece.PYRAMID_STR);
        assertEquals(4, p.getBody().length);
    }

    @Test
    public void testComputeNextRotationStick(){
        Piece st = new Piece(Piece.STICK_STR);
        assertTrue(st.equals(st.computeNextRotation().computeNextRotation()));
        assertFalse(st.equals(st.computeNextRotation()));
        assertTrue(st.equals(st));
        assertFalse(st.equals(null));
        testComputeNextRotation(st, 2);
    }

    @Test
    public void testComputeNextRotationL1(){
        Piece l1 = new Piece(Piece.L1_STR);
        testComputeNextRotation(l1, 4);
    }

    @Test
    public void testComputeNextRotationL2(){
        Piece l2 = new Piece(Piece.L2_STR);
        testComputeNextRotation(l2, 4);
    }

    @Test
    public void testComputeNextRotationS1(){
        Piece s1 = new Piece(Piece.S1_STR);
        testComputeNextRotation(s1, 2);
    }

    @Test
    public void testComputeNextRotationS2(){
        Piece s2 = new Piece(Piece.S2_STR);
        testComputeNextRotation(s2, 2);
    }

    @Test
    public void testComputeNextRotationSquare(){
        Piece square = new Piece(Piece.SQUARE_STR);
        testComputeNextRotation(square, 1);
    }

    @Test
    public void testComputeNextRotationPyramid(){
        Piece pyramid = new Piece(Piece.PYRAMID_STR);
        testComputeNextRotation(pyramid, 4);
    }

    private void testComputeNextRotation(Piece piece, int n){
        Piece curr = new Piece(piece.getBody());
        assertTrue(piece.equals(piece));
        assertFalse(piece.equals(null));
        for(int i = 1; i < NUM_TESTING_ROTATIONS; i++){
            curr = curr.computeNextRotation();
            boolean equals = piece.equals(curr);
            if(i%n == 0)
                assertTrue(equals);
            else
                assertFalse(equals);
        }
    }

    /* Test runtime exceptions during parsing */

    @Test (expected = RuntimeException.class)
    public void testWrongPiece1(){ Piece tmp = new Piece("1"); }

    @Test (expected = RuntimeException.class)
    public void testWrongPiece2(){ Piece tmp = new Piece("1 1 9 0 0"); }

    @Test (expected = RuntimeException.class)
    public void testWrongPiece3(){ Piece tmp = new Piece("-1 -3"); }

    @Test (expected = RuntimeException.class)
    public void testWrongPiece4(){ Piece tmp = new Piece("1 1 3"); }

    @Test (expected = RuntimeException.class)
    public void testWrongPiece5(){ Piece tmp = new Piece("exception"); }

    @Test
    public void testStaticPieces(){
        Piece[] pieces = Piece.getPieces();
        for(Piece p : pieces){
            assertEquals(p.fastRotation(), p.computeNextRotation());
        }
        Piece[] pieces2 = Piece.getPieces();
    }

}
