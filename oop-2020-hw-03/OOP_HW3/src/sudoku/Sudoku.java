package sudoku;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
    // Provided grid data for main/testing
    // The instance variable strategy is up to you.

    private int[][] board;
    private List<Spot> spots;
    private String solutionStr;
    private boolean solutionFound;
    private long elapsedTime;

    // Provided easy 1 6 grid
    // (can paste this text into the GUI too)
    public static final int[][] easyGrid = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 9");


    // Provided medium 5 3 grid
    public static final int[][] mediumGrid = Sudoku.stringsToGrid(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079");

    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
    public static final int[][] hardGrid = Sudoku.stringsToGrid(
            "3 7 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");


    public static final int SIZE = 9;  // size of the whole 9x9 puzzle
    public static final int PART = 3;  // size of each 3x3 part
    public static final int MAX_SOLUTIONS = 100;

    // Provided various static utility methods to
    // convert data formats to int[][] grid.

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * The "..." is a Java 5 feature that essentially
     * makes "rows" a String[] array.
     * (provided utility)
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String... rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row<rows.length; row++) {
            result[row] = stringToInts(rows[row]);
        }
        return result;
    }


    /**
     * Given a single string containing 81 numbers, returns a 9x9 grid.
     * Skips all the non-numbers in the text.
     * (provided utility)
     * @param text string of 81 numbers
     * @return grid
     */
    public static int[][] textToGrid(String text) {
        int[] nums = stringToInts(text);
        if (nums.length != SIZE*SIZE) {
            throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
        }

        int[][] result = new int[SIZE][SIZE];
        int count = 0;
        for (int row = 0; row<SIZE; row++) {
            for (int col=0; col<SIZE; col++) {
                result[row][col] = nums[count];
                count++;
            }
        }
        return result;
    }


    /**
     * Given a string containing digits, like "1 23 4",
     * returns an int[] of those digits {1 2 3 4}.
     * (provided utility)
     * @param string string containing ints
     * @return array of ints
     */
    public static int[] stringToInts(String string) {
        int[] a = new int[string.length()];
        int found = 0;
        for (int i=0; i<string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                a[found] = Integer.parseInt(string.substring(i, i+1));
                found++;
            }
        }
        int[] result = new int[found];
        System.arraycopy(a, 0, result, 0, found);
        return result;
    }


    // Provided -- the deliverable main().
    // You can edit to do easier cases, but turn in
    // solving hardGrid.
    public static void main(String[] args) {
        Sudoku sudoku;
        sudoku = new Sudoku(hardGrid);

        System.out.println(sudoku); // print the raw problem
        int count = sudoku.solve();
        System.out.println("solutions:" + count);
        System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
        System.out.println(sudoku.getSolutionText());
    }

    public Sudoku(String lines){
        this(textToGrid(lines));
    }

    /**
     * Sets up based on the given ints.
     */
    public Sudoku(int[][] ints) {
        initBoard(ints);
        initSpots();
        solutionStr = "";
        solutionFound = false;
    }

    private void initBoard(int[][] ints) {
        board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(ints, 0, board, 0, SIZE);
        }
    }

    /**
     * Initialises list all spots, that has not exact and has some possible values
     * Spots are sorted.
     */
    private void initSpots(){
        spots = new ArrayList<>();
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                if(board[i][j] == 0)
                    spots.add(new Spot(i, j));
            }
        }
        Collections.sort(spots);
    }



    /**
     * Solves the puzzle, invoking the underlying recursive search.
     */
    public int solve() {
        long timeOnStart = System.currentTimeMillis();
        int numSolutions = recSolve(0);
        elapsedTime = System.currentTimeMillis() - timeOnStart;
        return numSolutions;
    }

    /**
     * @param position, index in list of this spot
     * @return numer of solutions from this recursive call
     */
    private int recSolve(int position){
        if(position >= spots.size()) {
            if(!solutionFound) {
                solutionStr = toString();
                solutionFound = true;
            }
            return 1;
        }

        int numSolutions = 0;
        Spot currSpot = spots.get(position);
        int prevValue = currSpot.getValue();

        currSpot.update();
        Iterator<Integer> it = currSpot.getIterator();
        List<Integer> possibleValues = new ArrayList<>();
        while(it.hasNext())
            possibleValues.add(it.next());

        for(Integer value : possibleValues) {
            currSpot.setValue(value);
            numSolutions += recSolve(position + 1);
            if (numSolutions >= MAX_SOLUTIONS)
                return numSolutions;
        }

        currSpot.setValue(prevValue);

        return numSolutions;
    }

    public String getSolutionText() {
        return solutionStr;
    }

    public long getElapsed() {
        return elapsedTime;
    }

    @Override
    public String toString() {
        String s = "";
        for(int row = 0; row < SIZE; row++){
            for(int col = 0; col < SIZE; col++){
                s += board[row][col];
                if(col != SIZE-1)
                    s += " ";
            } s += "\n";
        }
        return s;
    }

    public class Spot implements Comparable<Spot> {

        private final int columnID;
        private final int rowID;
        private int value;
        private Set<Integer> posValues;

        public Spot(int i, int j){
            columnID = j;
            rowID = i;
            value = 0; // default value
            initPossibleValues();
        }

        /**
         * Collects all possible values, that are not mentioned
         *  in current row, or current column or current part
         */
        private void initPossibleValues(){
            posValues = new HashSet<>();
            update();
        }

        /* Updates possible values for this Spot */
        public void update(){
            posValues.clear();

            boolean[] isPossible = new boolean[SIZE];
            Arrays.fill(isPossible, true);

            falsizeNotPossibleValues(isPossible);

            fillPossibleValues(isPossible);
        }

        private void falsizeNotPossibleValues(boolean[] isPossible){
            falsizeRowValues(isPossible);
            falsizeColumnValues(isPossible);
            falsizeFamilyValues(isPossible);
        }

        /* Initially sets that all values are possible */
        private void fillPossibleValues(boolean[] isPossible){
            for(int i = 0; i < SIZE; i++){
                if(isPossible[i])
                    posValues.add(i+1);
            }
        }

        /* Makes false all nonpossible values in same 3x3 grid */
        private void falsizeFamilyValues(boolean[] isPossible){
            int familyStartRow = rowID - rowID%PART;
            int familyStartCol = columnID - columnID%PART;

            for(int row = familyStartRow; row < familyStartRow+PART; row++){
                for(int col = familyStartCol; col < familyStartCol+PART; col++){
                    int value = board[row][col];
                    if(value != 0)
                        isPossible[value-1] = false;
                }
            }
        }

        /* Makes false all nonpossible values in same row */
        private void falsizeRowValues(boolean[] isPossible){
            for(int col = 0; col < SIZE; col++){
                int value = board[rowID][col];
                if(value != 0)
                    isPossible[value-1] = false;
            }
        }

        /* Makes false all nonpossible values in column */
        private void falsizeColumnValues(boolean[] isPossible){
            for(int row = 0; row < SIZE; row++){
                int value = board[row][columnID];
                if(value != 0)
                    isPossible[value-1] = false;
            }
        }

        public int getValue(){
            return board[rowID][columnID];
        }

        public void setValue(int value){
            board[rowID][columnID] = value;
        }

        public int getSize(){
            return posValues.size();
        }

        public Iterator getIterator(){
            return posValues.iterator();
        }

        @Override
        public int compareTo(Spot s) {
            return this.getSize() - s.getSize();
        }
    }

}

