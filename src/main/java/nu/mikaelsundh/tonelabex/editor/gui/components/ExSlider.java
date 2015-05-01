package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class ExSlider extends JSlider {
    Logger logger = Logger.getLogger(this.getClass().getName());
    int mMinVal;
    int mMaxVal;

    public ExSlider(int min, int max, boolean tooltipListener) {
        super();
        mMinVal = min;
        mMaxVal = max;
        init(false, null, tooltipListener);
    }

    public ExSlider(int min, int max, String label, boolean tooltipListener) {
        super();
        mMinVal = min;
        mMaxVal = max;
        init(true, label, tooltipListener);
    }

    private void init(boolean frame, String label, boolean tooltipListener) {
        setOrientation(JSlider.VERTICAL);
        if (frame) {
            TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor), label);
            border.setTitleColor(Constants.titleColor);
            border.setTitleFont(Constants.sliderFrameLabelFont);
            setBorder(border);
        }
        setMinimum(mMinVal);
        setMaximum(mMaxVal);
        setMajorTickSpacing(10);
        setMinorTickSpacing(1);
        setPaintTicks(true);
        setPaintLabels(true);
        setValue(mMinVal);
        setBackground(Constants.backgroundColor);
        setForeground(Constants.textColor);
        setFont(Constants.sliderLabelFont);
        setOpaque(false);
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

    public void updateSlider(int min, int max, String label) {
        mMinVal = min;
        mMaxVal = max;
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor), label);
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.sliderFrameLabelFont);

        setBorder(border);
        setMinimum(mMinVal);
        setMaximum(mMaxVal);

    }

}
