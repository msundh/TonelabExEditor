package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.ExSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.gui.components.ToggleButton;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DelayValue;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

/**
 * User: Mikael Sundh
 * Date: 2012-11-08
 */
public class DelayFrame extends JPanel {
    private static Logger logger = LogManager.getLogger(DelayFrame.class);
    GuiListenerHandler guiListener;
    boolean initiating=false;
    ExStepSlider mType;
    ExSlider mLevel;
    ExSlider mTime;
    ExSlider mFBack;
    ToggleButton mOn;

    public DelayFrame(int x, int y) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        int w = 430;
        int h = 380;
        setLayout(null);
        setBounds(x, y, w, h);
        setOpaque(false);
        TitledBorder border = BorderFactory.createTitledBorder("Delay");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        add(initPanel(w - 10, h - 25));
    }


    protected JPanel initPanel(int w,int h) {
        JPanel panel = new JPanel(null);
        panel.setBounds(5, 20, w, h);
        mType = new ExStepSlider(Constants.delayNames.length,Constants.delayNames, "Type");
        mType.setBounds(10, 10, 120, h - 40);
        mType.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                boolean init=initiating;
                if (!mType.getValueIsAdjusting()) {
                    if (!mOn.isSelected()) {
                        if (!init)
                            initiating=true;
                        mOn.setSelected(true);
                        if(!init)
                            initiating=false;
                    }
                    fireGuiEvent();
                }
            }
        });
        panel.add(mType);
        mLevel = new ExSlider(0,30,"Level",true);
        mLevel.setBounds(140, 10, 70, h - 40);
        mLevel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                if (!mLevel.getValueIsAdjusting()) {
                    if(!init)
                        initiating=true;
                    if (!mOn.isSelected()) {
                        mOn.setSelected(true);
                    }
                    if(!init)
                        initiating=false;
                    fireGuiEvent();
                }
            }
        });
        panel.add(mLevel);
        mTime = new ExSlider(4,148, "Delay (ms)",false);
        updateTimeLabelTable();
        mTime.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                boolean init=initiating;
                if (!mTime.getValueIsAdjusting()) {
                    if (!init)
                        initiating=true;
                    if (!mOn.isSelected()) {
                        mOn.setSelected(true);
                    }
                    mTime.setToolTipText(String.valueOf(mTime.getValue() * 10));
                    if (!init)
                        initiating=false;
                    fireGuiEvent();
                }
            }
        });
        mTime.setMajorTickSpacing(20);
        mTime.setMinorTickSpacing(5);
        mTime.setBounds(210, 10, 130, h - 40);
        panel.add(mTime);
        mFBack = new ExSlider(0,100, "FBack",true);
        mFBack.setBounds(340, 10,70, h-40);
        mFBack.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean init=initiating;
                if (!mFBack.getValueIsAdjusting()) {
                    if (!init)
                        initiating=true;
                    if (!mOn.isSelected()) {
                        mOn.setSelected(true);
                    }
                    if (!init)
                        initiating=false;
                    fireGuiEvent();
                }
            }
        });
        panel.add(mFBack);
        mOn = new ToggleButton();
        mOn.setBounds(w-20,h-30,20,20);

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
        panel.add(mOn);
        panel.setOpaque(false);
        return panel;
    }
    private void updateTimeLabelTable() {
        Enumeration e = mTime.getLabelTable().keys();
        while (e.hasMoreElements()) {
            Integer i = (Integer) e.nextElement();
            JLabel l = (JLabel) mTime.getLabelTable().get(i);
            l.setSize(l.getWidth()+20,l.getHeight());
            if (i > 0) {
                l.setText(String.valueOf((i* 10)));
            }
        }
    }

    public DelayValue getDelayValue() {
//        logger.debug("Get Delay time: " + mTime.getValue()*10);
        return new DelayValue(mOn.isSelected(), mTime.getValue()*10,mFBack.getValue(),mLevel.getValue(),mType.getValue());
    }

    public void setDelayValues(DelayValue delay) {
        initiating=true;
        logger.debug("setDelayValues: " + delay.toString());
        mType.setValue(delay.getType());
        mLevel.setValue(delay.getLevel());
//        logger.debug("Set Delay time: " + delay.getTime()/10);
        mTime.setValue(delay.getTime()/10);
        mFBack.setValue(delay.getFeedback());
        mOn.setSelected(delay.isOn());
        initiating=false;
    }

    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }

}
