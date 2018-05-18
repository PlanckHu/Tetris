package Tetris.v5.v4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public class Control implements ActionListener , Serializable {
    private Main frame;

    public Control(Main frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.btnClick();
    }
}
