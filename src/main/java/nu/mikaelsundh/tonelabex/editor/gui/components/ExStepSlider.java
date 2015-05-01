package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Hashtable;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class ExStepSlider extends JSlider {
    Logger logger = Logger.getLogger(this.getClass().getName());
    int mMinVal;
    int mMaxVal;


    public ExStepSlider(int steps, String[] labels) {
        super();
        mMinVal = 0;
        mMaxVal = steps - 1;

        init(labels, false, null);
    }

    public ExStepSlider(int steps, String[] labels, String frame) {
        super();
        mMinVal = 0;
        mMaxVal = steps - 1;

        init(labels, true, frame);
    }

    private void init(String[] labels, boolean frame, String frameLabel) {
        setOrientation(JSlider.VERTICAL);
        setAlignmentX(JSlider.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(getPreferredSize().width, mMaxVal + 1));
        if (frame) {
            TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor), frameLabel);
            border.setTitleColor(Constants.titleColor);
            border.setTitleFont(Constants.sliderFrameLabelFont);
            setBorder(border);
        }
        setMinimum(mMinVal);
        setMaximum(mMaxVal);
        setMajorTickSpacing(1);
        setMinorTickSpacing(1);
        setSnapToTicks(true);
        setPaintTicks(true);
        setPaintLabels(true);
        setLabels(labels);
        setValue(mMinVal);
        setBackground(Constants.backgroundColor);
        setForeground(Constants.textColor);
        setFont(Constants.sliderLabelFont);
        setOpaque(false);
//        addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent changeEvent) {
//                if (!getValueIsAdjusting()) {
//                }
//            }
//        });
    }

    public void setLabels(String[] labels) {
        Hashtable labelTable = new Hashtable();
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(Constants.textColor);
            label.setOpaque(false);
            label.setFont(Constants.sliderLabelFont);
            labelTable.put(i, label);
        }
        setLabelTable(labelTable);
    }

}
