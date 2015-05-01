package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.ExSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ToggleButton;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.Pedal2Value;
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
public class Pedal2Frame extends JPanel {
    Logger logger = Logger.getLogger(this.getClass().getName());
    GuiListenerHandler guiListener;
    boolean initiating = false;
    ExStepSlider pedalType;
    ExSlider pedalValue;
    ToggleButton mOn;

    public Pedal2Frame(int x, int y, int w, int h) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        setLayout(null);
        setBounds(x, y, w, h);
        setOpaque(false);
        TitledBorder border = BorderFactory.createTitledBorder("Pedal 2");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        add(initPanel(w - 10, h - 25));
    }


    protected JPanel initPanel(int w,int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 20, w, h);
        pedalType = new ExStepSlider(11,Constants.pedal2Names, "Type");
        pedalType.setBounds(10, 10, (w / 2) + 20, h - 40);
        panel.add(pedalType);
        pedalType.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                if (!pedalType.getValueIsAdjusting()) {
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
        pedalValue = new ExSlider(0,100, "Value",true);
        pedalValue.setBounds(w / 2 +40, 10, 70, h - 40);
        panel.add(pedalValue);
        pedalValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                if (!pedalValue.getValueIsAdjusting()) {
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
        mOn.setBounds(w-30,h-30,20,20);
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

    public JSlider getTypeSlider() {
        return pedalType;
    }

    private boolean isOn() {
        return mOn.isSelected();
    }

    private void setOn(boolean on) {
        mOn.setSelected(on);
    }

    private int getPedalType() {
        return pedalType.getValue();
    }

    private void setPedalType(int type) {
        pedalType.setValue(type);
    }

    private int getPedalValue() {
        return pedalValue.getValue();
    }

    private void setPedalValue(int val) {
        pedalValue.setValue(val);
    }

    public Pedal2Value getPedal2Values() {
        return new Pedal2Value(isOn(),getPedalType(),getPedalValue());
    }
    public void setPedal2Values(Pedal2Value pedal) {
        initiating=true;
        setPedalType(pedal.getType());
        setPedalValue(pedal.getValue());
        setOn(pedal.isOn());
        initiating=false;
    }

    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
