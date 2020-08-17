package Count;

import javax.swing.*;
import java.awt.event.ActionListener;

public class WorkerThread extends Thread{

    private static final int STEP_VALUE = 10000;
    private static final int SLEEP_TIME = 100;

    private int numToReach;
    private JCount counter;

    public WorkerThread(int numToReach, JCount counter){
        this.numToReach = numToReach;
        this.counter = counter;
    }

    @Override
    public void run(){
        int numCycles = numToReach / STEP_VALUE;
        for(int i = 1; i <= numCycles; i++){
            if(!countOneCycle(i))
                return;
        }
        int leftedCount = numToReach % STEP_VALUE;
        for(int i = 0; i < leftedCount; i++){
            if(isInterrupted())
                return;
        }
        counter.setLabelText("" + numToReach); // if want to let unchanged final just comment this line
    }

    private boolean countOneCycle(int iCycle){
        for(int i = 0; i < STEP_VALUE; i++){}

        if(isInterrupted())
            return false;

        try {
            sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            return false;
        }

       updateCounterLabel(iCycle * STEP_VALUE);
        return true;
    }

    private void updateCounterLabel(int num){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                counter.setLabelText("" + num);
            }
        });
    }
}
