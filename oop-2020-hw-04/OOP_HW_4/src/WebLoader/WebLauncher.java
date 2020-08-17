package WebLoader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class WebLauncher extends Thread {

    private WebFrame frame;
    private DefaultTableModel tableModel;
    private int numWorkers;
    private WebWorker[] workers;

    public WebLauncher(WebFrame frame, DefaultTableModel tableModel, int numMaxThreads){
        this.frame = frame;
        this.tableModel = tableModel;
        this.numWorkers = Math.min(tableModel.getRowCount(), numMaxThreads);
    }

    @Override
    public void run() {
        final long startTime = System.currentTimeMillis();
        super.run();
        Semaphore threadsManager = new Semaphore(numWorkers, true);
        CountDownLatch latch = new CountDownLatch(tableModel.getRowCount());
        frame.incRunningThreadsCount();
        int rowCount = tableModel.getRowCount();
        workers = new WebWorker[rowCount];
        for(int i = 0; i < rowCount; i++){
            if(isInterrupted()){
                interruptedFinish();
                return;
            }

            workers[i] = new WebWorker(frame, tableModel, i, threadsManager, latch);
            workers[i].start();
        }
        finishLaunch(latch, startTime);
    }

    private void interruptedFinish(){
        if(workers == null) return;
        for(int i = 0; i < workers.length; i++){
            if(workers[i] != null)
                workers[i].interrupt();
            else
                break;
        }

    }

    private void finishLaunch(CountDownLatch latch, final long startTime) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            interruptedFinish();
        }
        frame.decRunningThreadsCount();
        setElapsedTime(startTime);
        frame.setReadyState();
    }

    private void setElapsedTime(final long startTime){
        final long finishTime = System.currentTimeMillis();
        final long elapsedTime = finishTime - startTime;
        frame.setElapsedTime((double)elapsedTime/1000);
    }
}
