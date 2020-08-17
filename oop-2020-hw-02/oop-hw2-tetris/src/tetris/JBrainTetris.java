package tetris;

import javax.swing.*;
import java.awt.*;

public class JBrainTetris extends JTetris{

    private static final String PRIMARY_MOVE_STATUS = "ok";
    private static final String ADVERSARY_MOVE_STATUS = "*ok*";

    /* instance variables */
    private JCheckBox brainMode;
    private JSlider adversary;
    protected Brain brain; // TODO change to private
    private JLabel status;
    private Brain.Move move;

    public JBrainTetris(int pixels){
        super(pixels);
        brain = new DefaultBrain();
        move = new Brain.Move();
    }

   /**
    Creates the panel of UI controls -- controls wired
    up to call methods on the JBrainTetris. This code is very repetitive.
     */
   @Override
    public JComponent createControlPanel() {
        JComponent panel = super.createControlPanel();

        // code from handouts
       panel.add(new JLabel("Brain:"));
       brainMode = new JCheckBox("Brain active");
       panel.add(brainMode);

       JPanel little = getInitedLittePanel();
       panel.add(little);

       status = new JLabel(PRIMARY_MOVE_STATUS);
       panel.add(status);

       return panel;
    }

    private JPanel getInitedLittePanel(){
        // make a little panel, put a JSlider in it. JSlider responds to getValue() little = new JPanel();
        JPanel little = new JPanel();
        little.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0); // min, max, current
        adversary.setPreferredSize(new Dimension(100,15));
        little.add(adversary);
        return little;
    }

    @Override
    public Piece pickNextPiece(){
       if(shouldMakeAdversaryMove()){
           status.setText(ADVERSARY_MOVE_STATUS);
            return getAdversaryPiece();
       } else {
           status.setText(PRIMARY_MOVE_STATUS);
           return super.pickNextPiece();
       }
    }

    private boolean shouldMakeAdversaryMove(){
           int n = random.nextInt(99);  // [0, 98]
            return (n+1) < adversary.getValue();
    }

    private Piece getAdversaryPiece(){
       Brain.Move worstMove = null;
       for(Piece piece : pieces){
           Brain.Move tmpMove = new Brain.Move();
           tmpMove = brain.bestMove(board, piece, board.getHeight(), tmpMove);
           if(worstMove == null || (tmpMove != null && tmpMove.score < worstMove.score)){
               worstMove = tmpMove;
           }
       }

       return (worstMove == null) ? null : worstMove.piece;
    }

    @Override
    public void tick(int verb) {
       if(brainMode.isSelected() && verb == DOWN){
           board.undo();

           move = brain.bestMove(board, currentPiece, board.getHeight(), move);
           if(move != null) {
               makeBrainMove();
                return;
           }
       }
       super.tick(verb);
    }

    private void makeBrainMove(){
       if(!currentPiece.equals(move.piece))
           super.tick(ROTATE);
       if(move.x < currentX)
           super.tick(LEFT);
       else if(move.x > currentX)
           super.tick(RIGHT);

       super.tick(DOWN);
    }


    /**
      * Creates a frame with a JBrainTetris.
      * main from JTetris class
     */
    public static void main(String[] args) {
        // Set GUI Look And Feel Boilerplate.
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        JBrainTetris tetris = new JBrainTetris(16);
        JFrame frame = JTetris.createFrame(tetris);
        frame.setVisible(true);
    }
}
