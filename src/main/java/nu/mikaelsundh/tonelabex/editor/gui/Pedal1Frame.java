package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.ExSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ToggleButton;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.Pedal1Value;

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
public class Pedal1Frame extends JPanel {
    GuiListenerHandler guiListener;
    boolean initiating = false;
    ExStepSlider pedalType;
    ExSlider pedalValue;
    ToggleButton mOn;

    public Pedal1Frame(int x, int y, int w, int h) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        setLayout(null);
        setBounds(x, y, w, h);
        TitledBorder border = BorderFactory.createTitledBorder("Pedal 1");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        add(initPanel(w - 10, h - 25));
        setOpaque(false);

    }


    protected JPanel initPanel(int w,int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 20, w, h);
        pedalType = new ExStepSlider(4,Constants.pedal1Names, "Type");
        pedalType.setBounds(10, 0, (w / 2), h - 25);
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
        pedalValue = new ExSlider(0,30, "Value",true);
        pedalValue.setBounds(w / 2 +20, 0, (w / 2) - 30, h - 25);
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
    public Pedal1Value getPedal1Values() {
        return new Pedal1Value(isOn(),getPedalType(),getPedalValue());
    }
    public void setPedal1Values(Pedal1Value pedal) {
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
