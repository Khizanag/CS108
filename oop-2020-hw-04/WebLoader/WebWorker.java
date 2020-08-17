package WebLoader;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class WebWorker extends Thread {

    private WebFrame frame;
    private DefaultTableModel tableModel;
    private int row;
    private Semaphore threadsManager;
    private CountDownLatch latch;

    public WebWorker(WebFrame frame, DefaultTableModel tableModel, int row,
                     Semaphore threadsManager,  CountDownLatch latch){
        this.frame = frame;
        this.tableModel = tableModel;
        this.row = row;
        this.threadsManager = threadsManager;
        this.latch = latch;
    }

    @Override
    public void run(){
        try {
            threadsManager.acquire();
        } catch (InterruptedException e) {
            return;
        }

        frame.incRunningThreadsCount();
        String urlString = (String)tableModel.getValueAt(row, 0);
        download(urlString);

        frame.decRunningThreadsCount();
        latch.countDown();
        threadsManager.release();
    }


    /* This is the core web/download i/o code... */
 	public void download(String urlString) {
        InputStream input = null;
        StringBuilder contents = null;
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            // Set connect() to throw an IOException
            // if connection does not succeed in this many msecs.
            connection.setConnectTimeout(5000);

            try{
                connection.connect();
            } catch(IOException ioe){
                if(isInterrupted())  throw new InterruptedException();
//                return; // because timeout result is written slowly and is looking like mistake
            }
            if(isInterrupted())  throw new InterruptedException();
            input = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            final long startTime = System.currentTimeMillis();
            char[] array = new char[1000];
            int len;
            int numBytes = 0;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                if(isInterrupted())  throw new InterruptedException();
                numBytes += len;
                contents.append(array, 0, len);
                Thread.sleep(100);
                if(isInterrupted())  throw new InterruptedException();
            }
            if(isInterrupted())  throw new InterruptedException();
            // Successful download if we get here
            final long endTime = System.currentTimeMillis();
            final long elapsedTime = endTime - startTime;
            String dateStr = new SimpleDateFormat("HH:MM:SS").format(new Date(startTime));
            String status =  dateStr + " " + elapsedTime + "ms " + numBytes + " bytes";
            tableModel.setValueAt(status, row, 1);
        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            tableModel.setValueAt("err", row, 1);
        } catch (InterruptedException exception) {
            // deal with interruption
            tableModel.setValueAt("interrupted", row, 1);
        } catch (IOException ignored) {
            tableModel.setValueAt("err", row, 1);
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {}
            frame.incCompletedTasks();
        }
    }
}
