package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.gui.GuiListenerHandler;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExSlider;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class SliderFrame extends JPanel {
    GuiListenerHandler guiListener;
    boolean initiating = false;
    ExSlider mSlider;

    public SliderFrame(int x,int y,int w, int h, String label) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        setLayout(null);
        setBounds(x, y, w, h);
        TitledBorder border = BorderFactory.createTitledBorder(label);
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        setOpaque(false);
        add(initSlider(w-20,h-30));
    }

    private JPanel initSlider(int w, int h) {
        mSlider = new ExSlider(0,100,true);
        mSlider.setBounds(0,10,w,h-10);
        mSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!mSlider.getValueIsAdjusting()) {
                    fireGuiEvent();
                }
            }
        });
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 15, w, h);
        panel.add(mSlider);
        panel.setOpaque(false);
        return panel;
    }

    public void setValue(int val) {
        initiating=true;
        mSlider.setValue(val);
        initiating=false;
    }

    public int getValue() {
        return mSlider.getValue();
    }
    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
