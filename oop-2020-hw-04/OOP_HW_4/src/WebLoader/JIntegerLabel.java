package WebLoader;

import javax.swing.*;

public class JIntegerLabel extends JLabel {

    private static final int DEFAULT_INT_VALUE = 0;

    private String defaultText;
    private int intValue;

    public JIntegerLabel(String defaultText, int intValue){
        super(defaultText + intValue);
        this.defaultText = defaultText;
        this.intValue = intValue;
    }

    public int getIntVal(){
        return intValue;
    }

    public void updateIntValue(int value){
        this.intValue = value;
        update();
    }

    private void update(){
        setText(defaultText + intValue);
    }

    public void reset(){
        intValue = DEFAULT_INT_VALUE;
        update();
    }
}
