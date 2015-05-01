package nu.mikaelsundh.tonelabex.editor.gui;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-05
 */
public class ProgressDialog extends JDialog {
    Logger logger = Logger.getLogger(this.getClass().getName());
    JProgressBar progress;

    public ProgressDialog(Frame owner) {
        super(owner);
        init();
    }

    protected void init() {
        setTitle("Progress Bar");
        setModal(true);
        JPanel pane = new JPanel(null);
        progress = new JProgressBar(0,100);
        progress.setStringPainted(true);
        progress.setBorder(BorderFactory.createTitledBorder("Reading..."));
        progress.setValue(10);
        progress.setVisible(true);
        pane.setLayout(null);
        pane.add(progress);


        setBounds(150, 100, 400, 100);
        pane.setBounds(0, 0, 400, 100);
        pane.setVisible(true);
        progress.setBounds(20, 0, 340, 60);
        setContentPane(pane);
    }
    public void setValue(int val) {
//        logger.debug("Setting value: " + val);
        progress.setValue(val);
    }
}
