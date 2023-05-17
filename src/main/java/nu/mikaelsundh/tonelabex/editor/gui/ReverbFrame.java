package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.ExSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ToggleButton;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.ReverbValue;

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
public class ReverbFrame extends JPanel {
    GuiListenerHandler guiListener;
    boolean initiating = false;
    ExStepSlider reverbType;
    ExSlider reverbValue;
    ToggleButton mOn;

    public ReverbFrame(int x,int y,int w,int h) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        setLayout(null);
        setBounds(x, y, w, h);
        TitledBorder border = BorderFactory.createTitledBorder("Reverb");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        setOpaque(false);
        add(initReverbPanel(w-10,h-25));
    }


    protected JPanel initReverbPanel(int w,int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 20, w, h);
        reverbType = new ExStepSlider(3,Constants.reverbNames, "Type");
        reverbType.setBounds(10, 0, (w / 2), h - 25);
        panel.add(reverbType);
        reverbType.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init = initiating;
                if (!reverbType.getValueIsAdjusting()) {
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
        reverbValue = new ExSlider(0,40, "Value",true);
        reverbValue.setBounds(w / 2 +20, 0, (w / 2) - 30, h - 25);
        panel.add(reverbValue);
        reverbValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init = initiating;
                if (!reverbValue.getValueIsAdjusting()) {
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
        mOn = new ToggleButton();
        mOn.setBounds(w-20,h-20,20,20);
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
        panel.setOpaque(false);
        return panel;
    }

    private boolean isOn() {
        return mOn.isSelected();
    }

    private void setOn(boolean on) {
        mOn.setSelected(on);
    }

    private int getType() {
        return reverbType.getValue();
    }

    private void setType(int type) {
        reverbType.setValue(type);
    }

    private int getValue() {
        return reverbValue.getValue();
    }

    private void setValue(int val) {
        reverbValue.setValue(val);
    }
    public ReverbValue getReverbValues() {
        return new ReverbValue(isOn(),getType(),getValue());
    }
    public void setReverbValues(ReverbValue pedal) {
        initiating=true;
        setType(pedal.getType());
        setValue(pedal.getValue());
        setOn(pedal.isOn());
        initiating=false;
    }
    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
