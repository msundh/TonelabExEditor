package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.*;
import nu.mikaelsundh.tonelabex.editor.midi.MidiException;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.PedalAssignValue;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class PedalAssignFrame extends JPanel {
    Logger logger = Logger.getLogger(this.getClass().getName());
    GuiListenerHandler guiListener;
    ToggleButton mInverted;
    boolean initiating = false;
    private ExStepSlider mTypeSlider;
    private ExRangeSlider mValueSlider;
    private ExFloatRangeSlider mFloatValueSlider;
    private ExStepRangeSlider mPitchSlider;
    private int mModType;

    public PedalAssignFrame(int x, int y, String label, int modType) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        mModType = modType;
        setLayout(null);
        int w = 290;
        int h = 380;
        setBounds(x, y, w, h);
        TitledBorder border = BorderFactory.createTitledBorder(label);
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        setOpaque(false);
        add(initSliders(w - 20, h));

    }

    private JPanel initSliders(int w, int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(10, 10, w, h);

        mTypeSlider = new ExStepSlider(Constants.pedalAssignNames.length, getLabels(), "Assign");
        mTypeSlider.setBounds(0, 20, 160, h - 40);
        panel.add(mTypeSlider);
        panel.setOpaque(false);
        mTypeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!mTypeSlider.getValueIsAdjusting()) {
                    if (Constants.pedalAssignNames[mTypeSlider.getValue()].equals("N/A")) {
                        mTypeSlider.setValue(0);
                    }
                    updateRange();
                    fireGuiEvent();
                }
            }
        });

        mValueSlider = new ExRangeSlider(0, 100, true);
        mValueSlider.setOrientation(JSlider.VERTICAL);
        mValueSlider.setBounds(160, 20, 100, h - 40);
        mValueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!mValueSlider.getValueIsAdjusting()) {
                    fireGuiEvent();
                }
            }
        });
        panel.add(mValueSlider);

        mPitchSlider = new ExStepRangeSlider(Constants.pitchNames.length, Constants.pitchNames);
        mPitchSlider.setOrientation(JSlider.VERTICAL);
        mPitchSlider.setBounds(160, 20, 100, h - 40);
        mPitchSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!mPitchSlider.getValueIsAdjusting()) {
                    fireGuiEvent();
                }
            }
        });
        mPitchSlider.setVisible(false);
        panel.add(mPitchSlider);

        mFloatValueSlider = new ExFloatRangeSlider(0, 100, "Range");
        mFloatValueSlider.setOrientation(JSlider.VERTICAL);
        mFloatValueSlider.setBounds(160, 20, 90, h - 40);
        mFloatValueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!mFloatValueSlider.getValueIsAdjusting()) {
                    fireGuiEvent();
                }
            }
        });
        mFloatValueSlider.setVisible(false);

        panel.add(mFloatValueSlider);
        updateRange();
        mInverted = new ToggleButton();
        mInverted.setSelected(false);
        mInverted.setBounds(140, 0, 120, 20);
        mInverted.setText("Inverted");
        mInverted.setFont(Constants.sliderLabelFont);
        mInverted.setForeground(Constants.textColor);
        mInverted.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                fireGuiEvent();
            }
        });
//        mInverted.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                setInverted(!mInverted.isSelected());
//            }
//        });
        panel.add(mInverted);
        return panel;
    }


    public void changeModType(int mod, boolean setInit) {
        if (setInit) initiating=true;
        mModType = mod;
        mTypeSlider.setLabels(getLabels());
        if (Constants.pedalAssignNames[mTypeSlider.getValue()].equals("N/A")) {
            mTypeSlider.setValue(0);
        }
        updateRange();
        if (setInit) initiating=false;
    }

    public void setWhaWha(int pedal) {
        if (pedal == 1) {
            mTypeSlider.setValue(pedal + 1); // Pedal 1=2, pedal 2= 3
        }
    }

    private String[] getLabels() {
        String[] labels = Constants.pedalAssignNames;
        if (mModType == 2 || mModType == 3) {
            labels[5] = "Mod Resonance";
            labels[6] = "Mod Speed";
            labels[7] = "Mod Speed";
        } else if (mModType == 7) {
            labels[5] = "Mod Attack";
            labels[6] = "N/A";
            labels[7] = "N/A";
        } else if (mModType == 8) {
            labels[5] = "Mod Balance";
            labels[6] = "N/A";
            labels[7] = "Mod Pitch";
        } else if (mModType == 9 || mModType == 10) {
            labels[5] = "Mod Sens";
            labels[6] = "Mod Type";
            labels[7] = "Mod Resonance";
        } else {
            labels[5] = "Mod Depth";
            labels[6] = "Mod Speed";
            labels[7] = "Mod Speed";
        }
        return labels;
    }

    private void updateRange() {
        if (mTypeSlider.getValue() == 0) {
            mValueSlider.setVisible(false);
            mFloatValueSlider.setVisible(false);
            mPitchSlider.setVisible(false);
        } else { // Delay and Reverb cannot be changed
            if (mTypeSlider.getValue() == 8 || mTypeSlider.getValue() == 10) {
                mValueSlider.setEnabled(false);
            } else {
                mValueSlider.setEnabled(true);
            }
        }
        switch (mTypeSlider.getValue()) {
            case 1:     // Volume
                mValueSlider.setup(0, 100);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 2:   // Pedal 1
                mValueSlider.setup(0, 30);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 3:   // Pedal 2
                mValueSlider.setup(0, 100);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 4:   // Gain
                mValueSlider.setup(0, 100);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 8:  // Delay input
                mValueSlider.setup(0, 100);   // 0-100 or 0-30? input == level?
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 9:  // Delay Feedback
                mValueSlider.setup(0, 100);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 10:  // Reverb
                mValueSlider.setup(0, 100);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 5:  //Mod Depth, Reso, Send, Attack, Balance
                mValueSlider.setup(0, 100);
                mValueSlider.setVisible(true);
                mFloatValueSlider.setVisible(false);
                mPitchSlider.setVisible(false);
                break;
            case 6:
            case 7:
                if (mModType < 7) {
                    mValueSlider.setVisible(false);
                    mFloatValueSlider.setVisible(true);
                    mPitchSlider.setVisible(false);
                    if (mModType == 4) {   // Twin
                        mFloatValueSlider.setup(1f, 15f);
                    } else if (mModType == 5) { // Vibrato
                        mFloatValueSlider.setup(1f, 30f);
                    } else {
                        mFloatValueSlider.setup(0f, 15f);
                    }
                } else if (mModType == 8) {
                    mValueSlider.setVisible(false);
                    mFloatValueSlider.setVisible(false);
                    mPitchSlider.setVisible(true);
                } else {
                    mValueSlider.setVisible(true);
                    mFloatValueSlider.setVisible(false);
                    mPitchSlider.setVisible(false);
                    mValueSlider.setup(0, 100);
                }

        }
    }

    private void setInverted(boolean inverted) {
        mValueSlider.setInverted(inverted);
        mFloatValueSlider.setInverted(inverted);
        mPitchSlider.setInverted(inverted);
        mInverted.setSelected(inverted);
    }

    public void setPedalAssignValues(PedalAssignValue values) {
        logger.debug("setPedalAssignValues: " + values);
        initiating = true;
        changeModType(values.getModulationType(), false);
        mTypeSlider.setValue(values.getAssign());
        try {
            updateRange(values.getFRangeMin(), values.getFRangeMax());
        } catch (MidiException e) {
            logger.error("failed to set received Pedal range values." + e.getMessage());
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.ERROR, e.getMessage()));
        }
        logger.debug("setPedalAssignValues2: " + mValueSlider.getLowValue() + "/" + mValueSlider.getHighValue() + " - " + mFloatValueSlider.getFloatLowValue() + "/" + mFloatValueSlider.getFloatHighValue());
        initiating = false;
    }

    public PedalAssignValue getPedalAssignValues() {  // TODO: Verify when Speed
        if (mTypeSlider.getValue() == 6 || mTypeSlider.getValue() == 7) {
            if (mModType == 8) { // Pitch
                if (mInverted.isSelected())
                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mPitchSlider.getHighValue(), (double) mPitchSlider.getLowValue(), mModType);
                else
                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mPitchSlider.getLowValue(), (double) mPitchSlider.getHighValue(), mModType);
            } else if (mModType == 9 || mModType == 10) { // Filtron, Talk
                if (mInverted.isSelected())
                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mValueSlider.getHighValue(), (double) mValueSlider.getLowValue(), mModType);
                else
                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mValueSlider.getLowValue(), (double) mValueSlider.getHighValue(), mModType);
            } else { // Mod type < 7 Speed TODO: IMPLEMENT
//                if (mInverted.isSelected()) {
//                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mFloatValueSlider.getFloatHighValue(), (double) mFloatValueSlider.getFloatLowValue(), mModType);
//                } else {
//                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mFloatValueSlider.getFloatLowValue(), (double) mFloatValueSlider.getFloatHighValue(), mModType);
//                }
                // SEt to min/max until we implement speed calculation
                if (mInverted.isSelected())
                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mFloatValueSlider.getFloatMaximum(), (double) mFloatValueSlider.getFloatMinimum(), mModType);
                else
                    return new PedalAssignValue(mTypeSlider.getValue(), (double) mFloatValueSlider.getFloatMinimum(), (double) mFloatValueSlider.getFloatMaximum(), mModType);
            }

        } else {
            if (mInverted.isSelected())
                return new PedalAssignValue(mTypeSlider.getValue(), (double) mValueSlider.getHighValue(), (double) mValueSlider.getLowValue(), mModType);
            else
                return new PedalAssignValue(mTypeSlider.getValue(), (double) mValueSlider.getLowValue(), (double) mValueSlider.getHighValue(), mModType);
        }

    }

    // TODO: VERIFY when Speed
    private void updateRange(double low, double high) throws MidiException {
        double min = 0;
        double max = 0;
        boolean inverted = (low > high);
        setInverted(inverted);

        if (mValueSlider.isVisible()) {
            min = mValueSlider.getMinimum();
            max = mValueSlider.getMaximum();
        } else if (mFloatValueSlider.isVisible()) {
            min = mFloatValueSlider.getFloatMinimum();
            max = mFloatValueSlider.getFloatMaximum();
        }  else {
            min = mPitchSlider.getMinimum();
            max = mPitchSlider.getMaximum();
        }
        logger.debug("updateRange: min/max: " + min + "/" + max);
        if (low >= min && high <= max) {
            setRangeValues(inverted ? high : low, inverted ? low : high);
        } else {
            if (inverted)
                setRangeValues(low > max ? max : low, high < min ? min : high);
            else
                setRangeValues(low < min ? min : low, high > max ? max : high);
            throw new MidiException("Invalid range values: (" + low + "/" + high + ") other values have set instead");
        }

    }

    private void setRangeValues(double low, double high) {
        if (mTypeSlider.getValue() == 6 || mTypeSlider.getValue() == 7) {
            if (mModType == 8) { // Pitch
                mPitchSlider.updateValues((int) low, (int) high);
            } else if (mModType == 9 || mModType == 10) {
                mValueSlider.updateValues((int) low, (int) high);
            } else { // Mod type < 7 Speed TODO: IMPLEMENT
                mFloatValueSlider.updateValues((float) low, (float) high);
            }
        } else {
            mValueSlider.updateValues((int) low, (int) high);
        }

    }

    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
