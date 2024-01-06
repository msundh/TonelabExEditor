package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.gui.components.ExRadioButton;
import nu.mikaelsundh.tonelabex.editor.gui.components.ExStepSlider;
import nu.mikaelsundh.tonelabex.editor.model.AmpSapValue;
import nu.mikaelsundh.tonelabex.editor.model.Constants;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-07
 */
public class AmpModeFrame extends JPanel {
    private static final Logger logger = LogManager.getLogger(AmpModeFrame.class);
    GuiListenerHandler guiListener;
    boolean initiating = false;
    private static final String AMP_NORMAL = "Normal";
    private static final String AMP_SPECIAL = "Special";
    private static final String AMP_CUSTOM = "Custom";
    private static final String AMP_SAP = "S.A. Pedal";
    private static final String AMP_OFF = "Off";
    ExRadioButton rdNormal;
    ExRadioButton rdSpecial;
    ExRadioButton rdCustom;
    ExRadioButton rdSAP;
    ExRadioButton rdOff;
    JLabel lblAmpMode;
    ExStepSlider mSlider;

    public AmpModeFrame(int x, int y, int w, int h) throws HeadlessException {
        super();
        guiListener = GuiListenerHandler.getInstance();
        setLayout(null);
        setBounds(x, y, w, h);
        TitledBorder border = BorderFactory.createTitledBorder("AMP/S.A.Pedal");
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.frameLabelFont);
        setBorder(border);
        setOpaque(false);
        add(initAmpSapModePanel(w, h));
        add(initAmpTypePanel(w, h));
    }

    protected JPanel initAmpSapModePanel(int w, int h) {
        JPanel ampModePanel = new JPanel(null);
        ampModePanel.setBounds(10, 15, w - 20, 40);
        lblAmpMode = new JLabel(AMP_NORMAL);
        lblAmpMode.setOpaque(false);
        lblAmpMode.setForeground(Constants.textColor);
        lblAmpMode.setFont(Constants.frameLabelFont);
        lblAmpMode.setFocusable(false);
        try {
            rdNormal = new ExRadioButton(new ImageIcon(ImageIO.read(getClass().getResource("/ledyellow.png"))));
            rdSpecial = new ExRadioButton(new ImageIcon(ImageIO.read(getClass().getResource("/ledorange.png"))));
            rdCustom = new ExRadioButton(new ImageIcon(ImageIO.read(getClass().getResource("/ledred.png"))));
            rdSAP = new ExRadioButton(new ImageIcon(ImageIO.read(getClass().getResource("/ledblue.png"))));
            rdOff = new ExRadioButton(new ImageIcon(ImageIO.read(getClass().getResource("/ledpurple.png"))));
        } catch (IOException e) {
            logger.error("Failed to set icon");
        }
        rdNormal.setToolTipText("Normal");
        rdSpecial.setToolTipText("Special");
        rdCustom.setToolTipText("Custom");
        rdSAP.setToolTipText("Pedal");
        rdOff.setToolTipText("Off");
        rdNormal.setBounds(0, 5, 30, 30);
        rdSpecial.setBounds(25, 5, 30, 30);
        rdCustom.setBounds(50, 5, 30, 30);
        rdSAP.setBounds(75, 5, 30, 30);
        rdOff.setBounds(100, 5, 30, 30);
        lblAmpMode.setBounds(140, 10, 100, 20);
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rdNormal);
        btnGroup.add(rdSpecial);
        btnGroup.add(rdCustom);
        btnGroup.add(rdSAP);
        btnGroup.add(rdOff);
        rdNormal.setSelected(true);
        rdNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });
        rdSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });
        rdCustom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });
        rdSAP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });
        rdOff.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame();
            }
        });
        ampModePanel.add(lblAmpMode);
        ampModePanel.add(rdNormal);
        ampModePanel.add(rdSpecial);
        ampModePanel.add(rdCustom);
        ampModePanel.add(rdSAP);
        ampModePanel.add(rdOff);
        ampModePanel.setOpaque(false);
        ampModePanel.setFocusable(false);
        return ampModePanel;
    }

    protected JPanel initAmpTypePanel(int w, int h) {
        JPanel ampTypePanel = new JPanel(null);
        mSlider = new ExStepSlider(11, Constants.ampModelNormal);
        ampTypePanel.setBounds(5, 30, w - 20, h - 50);
        mSlider.setBounds(0, 30, w - 20, h - 80);
        ampTypePanel.add(mSlider);
        ampTypePanel.setOpaque(false);
        mSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!mSlider.getValueIsAdjusting()) {
//                    mSlider.setToolTipText(Constants.mSlider.getValue());
                    fireGuiEvent();
                }
            }
        });
        return ampTypePanel;
    }

    private void updateFrame() {
        logger.debug("updateFrame:" + rdNormal.isSelected() + "," + rdSpecial.isSelected() + "," + rdCustom.isSelected() + "," + rdSAP.isSelected() + "," + rdOff.isSelected());
        if (rdNormal.isSelected()) {
            mSlider.setLabels(Constants.ampModelNormal);
            lblAmpMode.setText(AMP_NORMAL);
            mSlider.setVisible(true);
        } else if (rdSpecial.isSelected()) {
            mSlider.setLabels(Constants.ampModelSpecial);
            lblAmpMode.setText(AMP_SPECIAL);
            mSlider.setVisible(true);
        } else if (rdCustom.isSelected()) {
            mSlider.setLabels(Constants.ampModelCustom);
            lblAmpMode.setText(AMP_CUSTOM);
            mSlider.setVisible(true);
        } else if (rdSAP.isSelected()) {
            mSlider.setLabels(Constants.ampModelSAP);
            lblAmpMode.setText(AMP_SAP);
            mSlider.setVisible(true);
        } else {
            mSlider.setLabels(Constants.ampModelSAP);
            lblAmpMode.setText(AMP_OFF);
            mSlider.setVisible(false);
        }
        fireGuiEvent();
    }

    public void setAmp(AmpSapValue val) {
        initiating = true;
        if (!val.isOn()) {
            rdOff.setSelected(true);
        } else {
            switch (val.getType()) {
                case 0:
                    rdNormal.setSelected(true);
                    break;
                case 1:
                    rdSpecial.setSelected(true);
                    break;
                case 2:
                    rdCustom.setSelected(true);
                    break;
                case 3:
                    rdSAP.setSelected(true);
                    break;
            }
        }
        mSlider.setValue(val.getModel());
        updateFrame();
        initiating = false;
        logger.debug("setAmp Done");
    }

    private int getType() {
        if (rdNormal.isSelected()) return 0;
        if (rdSpecial.isSelected()) return 1;
        if (rdCustom.isSelected()) return 2;
        if (rdSAP.isSelected()) return 3;
        return 0; // Bail out
    }

    public AmpSapValue getAmp() {
        logger.debug("Radiobuttons:" + rdNormal.isSelected() + "," + rdSpecial.isSelected() + "," + rdCustom.isSelected() + "," + rdSAP.isSelected() + "," + rdOff.isSelected());
        return new AmpSapValue(!rdOff.isSelected(), getType(), mSlider.getValue());
    }

    private void fireGuiEvent() {
        if (!initiating) {
            guiListener.fireEvent(new DeviceEvent(this, DeviceEvent.GUI_CHANGE));
        }
    }
}
