package Count; // Count.JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JCount extends JPanel {

	private static final int NUM_SECTIONS = 4;
	private static final int TEXT_FIELD_LENGTH = 10;
	private static final int SEPARATION_HEIGHT = 40;

	private JTextField textField;
	private JLabel label;
	private WorkerThread workerThread;

	public JCount() {
		// Set the Count.JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		initTextField();
		initLabel();
		initStartButton();
		initStopButton();
		add(Box.createRigidArea(new Dimension(0,SEPARATION_HEIGHT)));
	}

	private void initTextField(){
		textField = new JTextField(TEXT_FIELD_LENGTH);
		add(textField);
	}

	private void initLabel(){
		label = new JLabel("0");
		add(label);
	}

	private void initStartButton(){
		JButton startButton = new JButton("Start");
		JCount counter = this;
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(workerThread != null)
					workerThread.interrupt();

				runNewCounter(counter);
			}
		});
		add(startButton);
	}

	private void runNewCounter(JCount counter){
		try{
			int numToReach = Integer.parseInt(textField.getText());
			workerThread = new WorkerThread(numToReach, counter);
			workerThread.start();
		} catch(Exception ex){
			System.out.println("Entered string is not number");
		}
	}

	private void initStopButton(){
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(workerThread != null)
					workerThread.interrupt();
			}
		});
		add(stopButton);
	}

	public void setLabelText(String text){
		label.setText(text);
	}

	static public void main(String[] args)  {
		// Creates a frame with NUM_SECTIONS JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		for(int i = 0; i < NUM_SECTIONS; i++)
			frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}