package nu.mikaelsundh.tonelabex.editor.gui.components;

import com.jidesoft.swing.RangeSlider;
import nu.mikaelsundh.tonelabex.editor.model.Constants;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Enumeration;

/**
 * User: mikael.sundh
 * Date: 2012-11-20
 */
public class ExFloatRangeSlider extends RangeSlider {
    private final static int PRECISION = 100;
    private float floatValue;
    private float floatMaxValue;
    private float floatMinValue;
    private float floatHighValue;
    private float floatLowValue;

    public ExFloatRangeSlider(float minValue, float maxValue, String frame) {
        super((int) (minValue * PRECISION), (int) (maxValue * PRECISION));
        this.floatMaxValue = maxValue;
        this.floatMinValue = minValue;
        this.floatHighValue = maxValue;
        this.floatLowValue = minValue;
        setOrientation(JSlider.VERTICAL);
        init(frame);
    }

    private void init(String frame) {
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor), frame);
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.sliderFrameLabelFont);
        setBorder(border);
        setPaintTicks(true);
        setPaintLabels(true);
        setMinorTickSpacing(100);
        setMajorTickSpacing(500);
        setBackground(Constants.backgroundColor);
        setForeground(Constants.textColor);
        setFont(Constants.sliderLabelFont);
        updateLabelTable();
        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (!getValueIsAdjusting()) {
                    setToolTipText(String.valueOf(getFloatLowValue() + " - " + getFloatHighValue()));
                }
            }
        });
    }

    public void setFloatLowValue(float lowValue) {
        this.floatLowValue = lowValue;
        super.setLowValue((int) lowValue * PRECISION);
    }

    public void setFloatHighValue(float highValue) {
        this.floatHighValue = highValue;
        super.setHighValue((int) highValue * PRECISION);
    }

    public float getFloatLowValue() {
        return super.getLowValue() / PRECISION;
    }

    public float getFloatHighValue() {
        return (getHighValue()) / PRECISION;
    }

    public void setFloatValue(float value) {
        this.floatValue = value;
        setValue((int) value * PRECISION);
    }

    public float getFloatValue() {
        return getValue() / PRECISION;
    }

    public float getFloatMinimum() {
        return getMinimum() / PRECISION;
    }

    public void setFloatMinimum(float minimum) {
        this.floatMinValue = minimum;
        setMinimum((int) minimum * PRECISION);
    }

    public float getFloatMaximum() {
        return getMaximum() / PRECISION;
    }

    public void setFloatMaximum(float maximum) {
        super.setMaximum((int) maximum * PRECISION);
    }

    public void setup(float min, float max) {
        setFloatMinimum(min);
        setFloatMaximum(max);
        setFloatHighValue(max);
        setFloatLowValue(min);
        setFloatValue(min);
        updateLabelTable();
    }

    public void updateValues(float low, float high) {
        setFloatHighValue(high);
        setFloatLowValue(low);
        setFloatValue(low);
        updateLabelTable();
    }

    private void updateLabelTable() {
        Enumeration e = getLabelTable().keys();
        while (e.hasMoreElements()) {
            Integer i = (Integer) e.nextElement();
            JLabel l = (JLabel) getLabelTable().get(i);
            if (i > 0) {
                l.setText(String.valueOf((float) (i / 100)));
//                System.out.println("ExFloatSlider.updateLabelTable label width: " + l.getWidth());
                l.setSize(40, l.getHeight());
            }
        }
    }
}
