package sudoku;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


public class SudokuFrame extends JFrame {

    /* private instance variables */
    JTextArea leftTextArea;
    JTextArea rightTextArea;
    JCheckBox autoCheck;
    JButton checkBtn;

    public SudokuFrame() {
        super("Sudoku Solver");

        BorderLayout bl = new BorderLayout(4, 4);
        setLayout(bl);

        leftTextArea = new JTextArea(15, 20);
        rightTextArea = new JTextArea(15, 20);

        leftTextArea.setBorder(new TitledBorder("Puzzle"));
        rightTextArea.setBorder(new TitledBorder("Solution"));

        add(leftTextArea, BorderLayout.CENTER);
        add(rightTextArea, BorderLayout.EAST);

        initSouthCanvas();

        // Could do this:
         setLocationByPlatform(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void initSouthCanvas(){
        initAutoCheck();
        initCheckButton();

        JComponent southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        southPanel.add(checkBtn);
        southPanel.add(autoCheck);

        add(southPanel, BorderLayout.SOUTH);
    }

    private void initAutoCheck(){
        autoCheck = new JCheckBox("Auto Check", true);

        leftTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                trySolveSudoku();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                trySolveSudoku();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                trySolveSudoku();
            }
        });
    }

    private void initCheckButton(){
        checkBtn = new JButton("Check");
        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });
    }

    private void trySolveSudoku(){
        if(autoCheck.isSelected())
            solveSudoku();
    }

    private void solveSudoku(){
        String sudokuText = leftTextArea.getText();
        try{
            Sudoku sudoku = new Sudoku(sudokuText);
            int numSolutions = sudoku.solve();
            rightTextArea.setText(sudoku.getSolutionText());
            rightTextArea.append("solutions:" + numSolutions + "\n");
            rightTextArea.append("elapsed:" + sudoku.getElapsed() + "ms" + "\n");
        } catch(Exception e){
            rightTextArea.setText("Parsing problem");
        }
    }



    public static void main(String[] args) {
        // GUI Look And Feel
        // Do this incantation at the start of main() to tell Swing
        // to use the GUI LookAndFeel of the native platform. It's ok
        // to ignore the exception.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }

        SudokuFrame frame = new SudokuFrame();
    }

}