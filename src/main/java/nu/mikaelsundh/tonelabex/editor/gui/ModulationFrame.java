package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.ExFloatSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ToggleButton;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.ModulationValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class ModulationFrame extends JPanel {
    private static final Logger logger = LogManager.getLogger(ModulationFrame.class);
    GuiListenerHandler guiListener;
    boolean initiating = false;
    ExStepSlider mType;
    ExSlider mEdit;
    ExSlider mTap;
    ExSlider mTapEdit;
    ExStepSlider mPitch;
    ExStepSlider mFiltron;
    ExStepSlider mTalk;
    ExFloatSlider mSpeed;
    ToggleButton mOn;


    public ModulationFrame(int x, int y) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        int w = 450;
        int h = 380;
        setLayout(null);
        setBounds(x, y, w, h);
        setOpaque(false);
        TitledBorder border = BorderFactory.createTitledBorder("Modulation");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        add(initPanel(w - 10, h - 25));
    }


    protected JPanel initPanel(int w, int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 20, w, h);
        mType = new ExStepSlider(Constants.modNames.length, Constants.modNames, "Type");
        mType.setBounds(10, 10, 120, h - 20);
        mType.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                    if (!mType.getValueIsAdjusting()) {
                        if (!mOn.isSelected()) {
                            if (!init)
                                initiating=true;
                            mOn.setSelected(true);
                            if (!init)
                                initiating=false;
                        }
                        updateTypeChange(mType.getValue());
                        fireGuiEvent();
                    }
            }
        });
        mType.setValue(0);
        mPitch = new ExStepSlider(Constants.pitchNames.length, Constants.pitchNames, "Pitch");
        addListeners(mPitch);
        mPitch.setBounds(210, 10, 120, h - 20);
        mPitch.setVisible(false);
        addListeners(mPitch);
        mFiltron = new ExStepSlider(Constants.filtronNames.length, Constants.filtronNames, "Type");
        addListeners(mFiltron);
        mFiltron.setBounds(340, 10, 100, h - 120);
        mFiltron.setVisible(false);
        mTalk = new ExStepSlider(Constants.talkModNames.length, Constants.talkModNames, "Type");
        mTalk.setBounds(340, 10, 100, h - 120);
        mTalk.setVisible(false);
        addListeners(mTalk);
        panel.add(mType);
        panel.add(mPitch);
        panel.add(mFiltron);
        panel.add(mTalk);
        mEdit = new ExSlider(0, 100, "Depth", true);
        mEdit.setBounds(140, 10, 70, h - 20);
        panel.add(mEdit);
        addListeners(mEdit);
        mTap = new ExSlider(1, 150, "Speed (10 Hz)", true);
        mTap.setBounds(210, 10, 130, h - 20);
        mTap.setVisible(false);
        panel.add(mTap);
        addListeners(mTap);
        mSpeed = new ExFloatSlider(0, 15, "Speed (Hz)");
        mSpeed.setBounds(210, 10, 130, h - 20);
        panel.add(mSpeed);
        addListeners(mSpeed);
        mTapEdit = new ExSlider(0, 100, "Resonance", true);
        mTapEdit.setBounds(340, 10, 80, h - 20);
        panel.add(mTapEdit);
        addListeners(mTapEdit);
        panel.setOpaque(false);
        mTapEdit.setVisible(false);
        mOn = new ToggleButton();
        mOn.setBounds(w - 30, h - 30, 20, 20);
        panel.add(mOn);
//        mOn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                fireGuiEvent();
//            }
//        });
        mOn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                fireGuiEvent();
            }
        });
        return panel;
    }

    private void addListeners(final JSlider slider) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                if (!slider.getValueIsAdjusting()) {
                    if (!mOn.isSelected()) {
                        if (!init)
                            initiating=true;
                        mOn.setSelected(true);
                        if (!init)
                            initiating=false;
                    }
                    fireGuiEvent();
                }
            }
        });
    }

    private void updateTypeChange(int val) {
//        logger.debug(Constants.modNames[val] + " selected");
        switch (val) {
            case 0:
            case 1:
            case 4:
                mEdit.updateSlider(0, 100, "Depth");
                mSpeed.updateSlider(0.0f, 15.0f, "Speed (Hz)"); //0.1 - 15.0
                mSpeed.setVisible(true);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(false);
                mTalk.setVisible(false);
                mTap.setVisible(false);
                break;
            case 2:
            case 3:
                mEdit.updateSlider(0, 100, "Resonance");
                mSpeed.updateSlider(0.0f, 15.0f, "Speed (Hz)");//0.1 - 15.0
                mSpeed.setVisible(true);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(false);
                mTalk.setVisible(false);
                mTap.setVisible(false);
                break;
            case 5: //Vibrator
                mEdit.updateSlider(0, 100, "Depth");
                mSpeed.updateSlider(1.0f, 30.0f, "Speed (Hz)"); // 1.0 - 30.0
                mSpeed.setVisible(true);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(false);
                mTalk.setVisible(false);
                mTap.setVisible(false);
                break;
            case 6: //G4 Rotary
                mEdit.updateSlider(0, 100, "Depth");
                mSpeed.updateSlider(0.0f, 15.0f, "Speed (Hz)");//0.8 - 15.0
                mSpeed.setVisible(true);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(false);
                mTalk.setVisible(false);
                mTap.setVisible(false);
                break;
            case 7: //Slow Attack
                mEdit.updateSlider(0, 100, "Attack");
                mTap.setVisible(false);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(false);
                mTalk.setVisible(false);
                mSpeed.setVisible(false);
                break;
            case 8: //Pitch
                mEdit.updateSlider(0, 100, "Balance");
                mTap.setVisible(false);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(true);
                mTalk.setVisible(false);
                mSpeed.setVisible(false);
                break;
            case 9: //Filtron
                mEdit.updateSlider(0, 100, "Sense");
                mTap.updateSlider(0, 100, "Resonance");
                mTap.setVisible(true);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(true);
                mPitch.setVisible(false);
                mSpeed.setVisible(false);
                mTalk.setVisible(false);
                break;
            case 10: // Talk Mode
                mEdit.updateSlider(0, 100, "Sense");
                mTap.updateSlider(0, 100, "Resonance");
                mTap.setVisible(true);
                mTapEdit.setVisible(false);
                mFiltron.setVisible(false);
                mPitch.setVisible(false);
                mTalk.setVisible(true);
                mSpeed.setVisible(false);
                break;
        }
    }


    public void setModulation(ModulationValue val) {
        initiating = true;
//        logger.debug(val.toString());
        mType.setValue(val.getType());
        switch (val.getType()) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
                mEdit.setValue(val.getDepth());
                mSpeed.setFloatValue((float) val.getSpeed());
                break;
            case 2:
            case 3:
                mEdit.setValue(val.getResonance());
                mSpeed.setFloatValue((float) val.getSpeed());
                break;
            case 7:
                mEdit.setValue(val.getAttack());
                break;
            case 8:
                mEdit.setValue(val.getBalance());
                mPitch.setValue(val.getPitch());
                break;
            case 9:
                mEdit.setValue(val.getSens());
                mFiltron.setValue(val.getFiltronType());
                mTap.setValue(val.getResonance());
                break;
            case 10:
                mEdit.setValue(val.getSens());
                mTalk.setValue(val.getTalkType());
                mTap.setValue(val.getResonance());
                break;
        }

        updateTypeChange(val.getType());
        mOn.setSelected(val.isOn());
        initiating = false;
    }


    public ModulationValue getModulation() {
        //boolean  on,int type,int depth ,double speed,int reso, int attack, int balance,int pitch,int sens,int filtronType, int talkType
        switch (mType.getValue()) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
                return new ModulationValue(mOn.isSelected(), mType.getValue(), mEdit.getValue(), getSpeedValue(), 0, 0, 0, 0, 0, 0, 0);
            case 2:
            case 3:
                return new ModulationValue(mOn.isSelected(), mType.getValue(), 0, getSpeedValue(), mEdit.getValue(), 0, 0, 0, 0, 0, 0);
            case 7:
                return new ModulationValue(mOn.isSelected(), mType.getValue(), 0, 0, 0, mEdit.getValue(), 0, 0, 0, 0, 0);  // Slow Attack
            case 8:
                return new ModulationValue(mOn.isSelected(), mType.getValue(), 0, 0, 0, 0, mEdit.getValue(), mPitch.getValue(), 0, 0, 0);  // Pitch
            case 9:
                return new ModulationValue(mOn.isSelected(), mType.getValue(), 0, 0.0, mTap.getValue(), 0, 0, 0, mEdit.getValue(), mFiltron.getValue(), 0);  // Filtron
            case 10:
                return new ModulationValue(mOn.isSelected(), mType.getValue(), 0, 0.0, mTap.getValue(), 0, 0, 0, mEdit.getValue(), 0, mTalk.getValue());  // Talk Mode
        }
        return null;
    }

    public ExStepSlider getTypeSlider() {
        return mType;
    }

    private double getSpeedValue() {
        switch (mType.getValue()) {
            case 0:
            case 1:
            case 2:
            case 3:
                if (mSpeed.getFloatValue() < 0.1) return 0.1;
                else if (mSpeed.getFloatValue() >15.0) return 15.0;
                else return mSpeed.getFloatValue();
            case 4:
                if (mSpeed.getFloatValue() < 1.0) return 1.0;
                else if (mSpeed.getFloatValue() >15.0) return 15.0;
                else return mSpeed.getFloatValue();
            case 5:
                if (mSpeed.getFloatValue() < 1.0) return 1.0;
                else if (mSpeed.getFloatValue() >30.0) return 30.0;
                else return mSpeed.getFloatValue();
            case 6:
                if (mSpeed.getFloatValue() < 0.8) return 0.8;
                else if (mSpeed.getFloatValue() >15.0) return 15.0;
                else return mSpeed.getFloatValue();
                default:
                    logger.warn("Return speed value at mod type: " + mType.getValue());
                    return mSpeed.getFloatValue();
        }
    }
    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
