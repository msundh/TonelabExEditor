package nu.mikaelsundh.tonelabex.editor.gui;

import com.jidesoft.dialog.BannerPanel;
import nu.mikaelsundh.tonelabex.editor.model.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class HelpDialog extends JDialog {
    public HelpDialog(Frame owner) {
        super(owner);
        setModal(true);
        setTitle("Help");
        setup();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);    //To change body of overridden methods use File | Settings | File Templates.
    }

    private void setup() {
        setBounds(300, 200, 500, 650);

        BannerPanel panel = new BannerPanel();
        panel.setLayout(null);
        StringBuilder helpTxt = new StringBuilder();
        helpTxt.append("<html><div style=\"color:#606dff\"><div style=\"text-align:center\"><b>Tonelab EX Editor Help</b></div><br>");
        helpTxt.append("<hr width=\"50%\"/><br>");
        helpTxt.append("<DIV style=\"margin-left:10px;margin-right:10px\">");
        helpTxt.append("With this editor you can load and save presets from/to file or device.<br>");
        helpTxt.append("Changes in the Editor will change the current program (no write) on the device (same as F5).<br><br>");
        helpTxt.append("At the moment the Expression Pedal assignment isn't complete.<br>");
        helpTxt.append("The Modulation speed is set to min/max regardless of the Editor settings.<br>");
        helpTxt.append("If a 'invalid range' error occurs, the range has been adjusted to the min and/or max value.<br>");
        helpTxt.append("The Editor is not changed when events initiated by the device is occurred");
        helpTxt.append(" i.e. if you change the preset on the device, the editor doesn't change. The status bar get updated in some cases.<br><br>");
        helpTxt.append("<b>File menu:</b><DL>");
        helpTxt.append("<DT>Load preset from file(Ctrl+O) <DD>Open a saved preset file from disk into the Editor");
        helpTxt.append("<DT>Save the preset to file (Ctrl+S) <DD>Save the Editor values to a file.</DL>");
        helpTxt.append("<b> MIDI Menu:</b>");
        helpTxt.append("<DL><DT>Load preset from device (Ctrl+L)<DD>Load a  preset from the device into the Editor");
        helpTxt.append("<DT>Write Editor values on the device (Ctrl+W)<DD>Write the Editor values to the device with option to change the preset number");
        helpTxt.append("<DT>Save Editor values on the device (F5)");
        helpTxt.append("<DD>Save the Editor values in the current program space on the device. <u>Note! No write</u>");
        helpTxt.append("</DL><hr width=\"50%\"/></div></div></html>");
        JTextPane txt = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Paint paint = new GradientPaint(0, 0, Constants.backgroundDialogLightColor,
                        getWidth(), 0, Constants.backgroundDialogColor, true);
                ((Graphics2D) g).setPaint(paint);
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }

        };
        txt.setContentType("text/html");
        txt.setText(helpTxt.toString());
        txt.setForeground(Constants.dialogTextColor);
        txt.setOpaque(false);
        txt.setEditable(false);
        txt.setBounds(0, 0, 500, 650);
        panel.setLocation(0, 0);
        panel.setPreferredSize(new Dimension(500, 650));
        panel.add(txt);
        panel.setBackground(Constants.backgroundDialogColor);

        getContentPane().setBackground(Constants.backgroundDialogColor);
        getContentPane().add(panel);
        txt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });
    }

}
