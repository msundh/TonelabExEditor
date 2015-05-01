package nu.mikaelsundh.tonelabex.editor.gui;

import com.jidesoft.dialog.BannerPanel;
import nu.mikaelsundh.tonelabex.editor.model.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class AboutDialog extends JDialog {
    public AboutDialog(Frame owner) {
        super(owner);
        setModal(true);
        setTitle("About");
        setup();
    }
    private void setup() {
        setBounds(300, 300, 300, 210);
        BannerPanel panel = new BannerPanel();
        panel.setLayout(null);
        panel.setBounds(0,0,300,210);
        panel.setPreferredSize(new Dimension(300,210));
        JLabel lblTitle = new JLabel("Tonelab EX Editor");
        JLabel lblAuthor = new JLabel("By Mikael Sundh");
        JLabel lblExtra = new JLabel("Thanks to Robert Grüber for ideas and MIDI");
        JLabel lblExtra2 = new JLabel("information found in his excellent STLab");
        JLabel lblRef = new JLabel("<html><a href =\"http://www.mikaelsundh.nu\">http://www.mikaelsundh.nu</a></html");
        lblRef.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.mikaelsundh.nu"));
                } catch (IOException e1) {
                } catch (URISyntaxException e1) {
                }
            }
        });
        lblTitle.setBounds(70, 10, 250, 20);
        lblAuthor.setBounds(70, 30, 250, 20);
        lblRef.setBounds(50, 50, 250, 20);
        lblExtra.setBounds(20, 80, 250, 20);
        lblExtra2.setBounds(20, 100, 250, 20);
        panel.add(lblTitle);
        panel.add(lblAuthor);
        panel.add(lblExtra);
        panel.add(lblExtra2);
        panel.add(lblRef);

//        JButton ok = new JButton("Ok");
//        ok.setBounds(110,135,55,30);
//        panel.add(ok);
        getContentPane().add(panel);

//        ok.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                setVisible(false);
//            }
//        });

//        setSize(250, 160);
        lblTitle.setForeground(Constants.dialogTextColor);
        lblAuthor.setForeground(Constants.dialogTextColor);
        lblRef.setForeground(Constants.dialogTextColor);
        lblExtra.setForeground(Constants.dialogTextColor);
        lblExtra2.setForeground(Constants.dialogTextColor);
//        panel.setBackground(Constants.backgroundDialogColor);
        panel.setGradientPaint(Constants.backgroundDialogLightColor,Constants.backgroundDialogColor,false);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
            }
        });
    }
}
