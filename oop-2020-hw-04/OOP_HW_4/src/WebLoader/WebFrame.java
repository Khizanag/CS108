package WebLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WebFrame extends JFrame {

    /* constants */
    private static final int APPLICATION_WIDTH = 600;
    private static final int APPLICATION_HEIGHT = 500;
    private static final int TABLE_WIDTH = 500;
    private static final int TABLE_HEIGHT = 300;
    private static final int TEXT_FIELD_MAX_WIDTH = 50;
    private static final int TEXT_FIELD_MAX_HEIGHT = 30;

    /* private instance variables */
    private DefaultTableModel tableModel;
    private JTextField textField;
    private JIntegerLabel runningIL, completedIL;
    private JLabel elapsedJL;
    private JButton singleThreadBT, concurrentBT, stopBT;
    private JProgressBar progressBar;
    private WebLauncher launcher;
    private Integer numRunningThreads;

    /* constructor */
    public WebFrame(String filename){
        super("Web Loader");
        initDefaultProperties();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        initTable(filename);
        initSingleThreadFetchButton();
        initConcurrentFetchButton();
        initTextBox();
        initLabels();
        initProgressBar();
        initStopButton();

        setReadyState();
    }

    private void initDefaultProperties() {
        setMinimumSize(new Dimension(APPLICATION_WIDTH, APPLICATION_HEIGHT));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initTable(String filename) {
        String[] columnTitles = {"url", "status"};
        tableModel = new DefaultTableModel(columnTitles, 0);
        loadTable(filename);
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        add(scrollPane);
    }

    private void loadTable(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while(true){
                String line = br.readLine();
                if(line == null)
                    break;
                Object[] row = {line, null};
                tableModel.addRow(row);
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        }
        catch (IOException e) { }
    }

    private void initSingleThreadFetchButton() {
        singleThreadBT = new JButton("Single Thread Fetch");
        singleThreadBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runWebLauncher(1); // single thread
            }
        });
        add(singleThreadBT);
    }

    private void initConcurrentFetchButton() {
        concurrentBT = new JButton("Concurrent Fetch");
        concurrentBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int numMaxThreads = Integer.parseInt(textField.getText());
                    runWebLauncher(numMaxThreads);
                } catch(NumberFormatException nfe){
                    System.out.println("You should enter number in Text Field");
                }
            }
        });
        add(concurrentBT);
    }

    private void runWebLauncher(int numMaxThreads){
        setRunningState();
        if(launcher != null)
            launcher.interrupt();
        numRunningThreads = 0;
        launcher = new WebLauncher(WebFrame.this, tableModel, numMaxThreads);
        launcher.start();
    }

    public void incRunningThreadsCount(){
        synchronized (numRunningThreads){
            numRunningThreads += 1;
        }
        runningIL.updateIntValue(numRunningThreads);
    }

    public void decRunningThreadsCount(){
        synchronized (numRunningThreads){
            numRunningThreads -= 1;
        }
        runningIL.updateIntValue(numRunningThreads);
    }

    public void setElapsedTime(final double time){
        synchronized (elapsedJL){
            elapsedJL.setText("elapsed:" + time);
        }
    }

    public void incCompletedTasks(){
        synchronized (completedIL){
            completedIL.updateIntValue(completedIL.getIntVal() + 1);
        }
        synchronized (progressBar){
            progressBar.setValue(progressBar.getValue()+1);
        }
    }

    private void initTextBox() {
        textField = new JTextField();
        textField.setMaximumSize(new Dimension(TEXT_FIELD_MAX_WIDTH, TEXT_FIELD_MAX_HEIGHT));
        add(textField);
    }

    private void initLabels() {
        initRunningLabel();
        initCompletedLabel();
        initElapsedLabel();
    }

    private void initRunningLabel() {
        runningIL = new JIntegerLabel("Running:", 0);
        add(runningIL);
    }

    private void initCompletedLabel() {
        completedIL = new JIntegerLabel("Completed:",  0);
        add(completedIL);
    }

    private void initElapsedLabel() {
        elapsedJL = new JLabel("Elapsed:");
        add(elapsedJL);
    }

    private void initProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setValue(0);
        add(progressBar);
    }

    private void initStopButton() {
        stopBT = new JButton("Stop");
        stopBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launcher != null)
                    launcher.interrupt();
                setReadyState();
            }
        });
        add(stopBT);
    }

    private void setRunningState(){
        updateButtonEnables();
        resetStatusLabels();
        resetDownloadStatuses();
        int numURLs = tableModel.getRowCount();
        progressBar.setMaximum(numURLs);
        progressBar.setValue(0);
    }

    private void resetDownloadStatuses(){
        for(int i = 0; i < tableModel.getRowCount(); i++){
            tableModel.setValueAt("", i, 1);
        }
    }

    private void updateButtonEnables() {
        singleThreadBT.setEnabled(false);
        concurrentBT.setEnabled(false);
        stopBT.setEnabled(true);
    }

    private void resetStatusLabels() {
        runningIL.reset();
        completedIL.reset();
        elapsedJL.setText("elapsed:");
    }

    public void setReadyState(){
        singleThreadBT.setEnabled(true);
        concurrentBT.setEnabled(true);
        stopBT.setEnabled(false);
//        progressBar.setValue(0);
    }

}

