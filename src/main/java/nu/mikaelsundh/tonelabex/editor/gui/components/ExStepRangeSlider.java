package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Hashtable;

/**
 * User: mikael.sundh
 * Date: 2012-11-21
 */
public class ExStepRangeSlider extends ExRangeSlider {
    private static Logger logger = LogManager.getLogger(ExStepRangeSlider.class);
    int mMinVal;
    int mMaxVal;

    public ExStepRangeSlider(int steps, String[] labels) {
        super(0, (steps - 1), false);
        mMinVal = 0;
        mMaxVal = steps - 1;

        init(labels);
    }

    private void init(String[] labels) {
        setup(mMinVal,mMaxVal);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor), "Range");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.sliderFrameLabelFont);
        setBorder(border);
        setPaintTicks(true);
        setPaintLabels(true);
        setMinorTickSpacing(1);
        setMajorTickSpacing(1);
        setSnapToTicks(true);
        setBackground(Constants.backgroundColor);
        setForeground(Constants.textColor);
        setFont(Constants.sliderLabelFont);
        setLabels(labels);
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

    public void setup(int min, int max) {
        setup(min,max,min,max);
    }
    public void setup(int min, int max, int low, int high) {
        mMinVal = min;
        mMaxVal = max;
        setMinimum(mMinVal);
        setMaximum(mMaxVal);
        setValue(mMinVal);
        setLowValue(low);
        setHighValue(high);
    }
    public void updateValues(int low, int high) {
        if (low <= mMaxVal && low >= mMinVal && high <= mMaxVal && high >= low) {
            setValue(low);
            setLowValue(low);
            setHighValue(high);
        } else {
            logger.error("Invalid Pedal Range values ("+low+"-"+high+")");
        }
    }

}
