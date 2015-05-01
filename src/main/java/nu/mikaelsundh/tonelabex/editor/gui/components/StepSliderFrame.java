package nu.mikaelsundh.tonelabex.editor.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class StepSliderFrame extends JPanel {
    ExStepSlider mSlider;
    int mSteps;
    String[] mLabels;
    public StepSliderFrame(int x, int y, int w, int h,int steps, String label,String[] labels) throws HeadlessException {
        super();
        setLayout(null);
        setBounds(x, y, w, h);
        setBorder(BorderFactory.createTitledBorder(label));
        mSteps = steps;
        mLabels = labels;
        add(initSlider());
    }

    private JPanel initSlider() {
        mSlider = new ExStepSlider(mSteps, mLabels);
        mSlider.setBounds(0,10,70,170);
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 15, 80, 180);
        panel.add(mSlider);
        return panel;
    }
    public void setmLabels(String[] labels) {
        mSlider.setLabels(labels);
    }
}
