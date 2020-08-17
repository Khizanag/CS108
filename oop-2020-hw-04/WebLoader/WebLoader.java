package WebLoader;

import Count.JCount;

import javax.swing.*;

public class WebLoader {

    private static final String FILENAME = "/Users/gigakhizanishvili/Programming/GitHub/oop-2020-hw-04/OOP_HW_4/src/WebLoader/links2.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new WebFrame(FILENAME);
            }
        });
    }
}
