package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.CircleRadioButton;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ToggleButton;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
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
public class CabinetFrame extends JPanel {
    Logger logger = Logger.getLogger(this.getClass().getName());
    GuiListenerHandler guiListener;
    boolean initiating=false;
    ToggleButton mOn;
    ExStepSlider mSlider;
    public CabinetFrame(int x,int y,int w,int h) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        setLayout(null);
        setBounds(x, y, w, h);
        TitledBorder border = BorderFactory.createTitledBorder("Cabinets");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        add(initCabinetPanel(w-10,h-30));
        setOpaque(false);
    }


    protected JPanel initCabinetPanel(int w,int h) {
        JPanel cabPanel = new JPanel(null);
        mSlider = new ExStepSlider(11,Constants.cabNames);
        cabPanel.setBounds(5,15,w,h);
        mSlider.setBounds(0,10,w-20,h-10);
        cabPanel.add(mSlider);
        mOn = new ToggleButton();
        mOn.setBounds(w-20,h-20,20,20);
        mOn.setSelected(false);
        cabPanel.add(mOn);
        cabPanel.setOpaque(false);
        mSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                if(!mSlider.getValueIsAdjusting()) {
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
        mOn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                fireGuiEvent();
            }
        });
        return cabPanel;
    }

    public void setCabinet(int val) {
        initiating=true;
        mSlider.setValue(val);
        initiating=false;
    }

    public int getCabinet() {
        return mSlider.getValue();
    }
    public boolean isOn() {
        return mOn.isSelected();
    }
    public void setOn(boolean on) {
        initiating=true;
//        logger.debug("setOn: " + on);
        mOn.setSelected(on);
//        logger.debug("After set on/off: " + mOn.isSelected());
        initiating=false;
    }
    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
