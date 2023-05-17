package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.io.IOException;


/**
 * User: Mikael Sundh
 * Date: 2012-11-09
 */
public class ExRadioButton extends JRadioButton {
    private static Logger logger = LogManager.getLogger(ExRadioButton.class);

    public ExRadioButton(Icon icon) {
        super();
        setBackground(Constants.backgroundColor);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
        setUI(new myButtonUI());
        try {
            setSelectedIcon(icon);
            setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/ledblack.png"))));
        } catch (IOException e) {
            logger.debug("Failed to set icon",e);
        }
        setSelected(false);
        repaint();
    }


    class myButtonUI extends MetalButtonUI {
        @Override
        public void paintButtonPressed(Graphics g, AbstractButton b) {
            logger.debug("paintButtonPressed");
            g.setColor(Constants.backgroundColor);
            g.fillRect(0, 0, b.getSize().width, b.getSize().height);
        }
    }
}
