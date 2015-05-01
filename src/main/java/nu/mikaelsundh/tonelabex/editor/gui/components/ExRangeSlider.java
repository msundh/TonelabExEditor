package nu.mikaelsundh.tonelabex.editor.gui.components;

//import nu.mikaelsundh.tonelabex.editor.gui.components.rangeslider.RangeSlider;
import com.jidesoft.swing.RangeSlider;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * User: Mikael Sundh
 * Date: 2012-11-19
 */
public class ExRangeSlider extends RangeSlider {
    Logger logger = Logger.getLogger(this.getClass().getName());
    int mMinVal;
    int mMaxVal;

    public ExRangeSlider(int min, int max,boolean tooltipListener) {
        super(min,max);
        mMinVal = min;
        mMaxVal = max;

        init(tooltipListener);
    }
    private void init(boolean tooltipListener) {
        setup(mMinVal,mMaxVal);
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor), "Range");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.sliderFrameLabelFont);
        setBorder(border);
        setPaintTicks(true);
        setPaintLabels(true);
        setMinorTickSpacing(1);
        setMajorTickSpacing(10);
        setBackground(Constants.backgroundColor);
        setForeground(Constants.textColor);
        setFont(Constants.sliderLabelFont);
        if (tooltipListener) {
            addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent changeEvent) {
                    if (!getValueIsAdjusting()) {
                        setToolTipText(String.valueOf(getValue()));
                    }
                }
            });
        }
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
        if (low <= mMaxVal && low >= mMinVal && high <= mMaxVal && high >= mMinVal) {
        setValue(low);
        setLowValue(low);
        setHighValue(high);
        } else {
            logger.error("Invalid Pedal Range values ("+low+"-"+high+")");
        }
    }

}
