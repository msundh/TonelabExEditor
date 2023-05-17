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
public class ToggleButton extends JToggleButton {
    private static Logger logger = LogManager.getLogger(ToggleButton.class);

    public ToggleButton() {
        super();
        setBackground(Constants.backgroundColor);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
        setUI(new myButtonUI());
        try {
            setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/ledblack.png"))));
            setSelectedIcon(new ImageIcon(ImageIO.read(getClass().getResource("/ledred.png"))));
//            mOn=false;
//            setSelected(false);
        } catch (IOException e) {
            logger.debug("Failed to set icon", e);
        }
//        setSelected(false);
//        repaint();
//        addMouseListener(this);

    }
//    public boolean isOn() {
//        return mOn;
//    }
//    private void setActive(boolean on) {
//        try {
//            if (on) {
//                setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/led_on.png"))));
//                mOn=true;
//            } else {
//                setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/led_off.png"))));
//                mOn=false;
//            }
//        } catch (IOException e) {
//            logger.debug("Failed to set icon",e);
//        }
//    }

//    @Override
//    public void mouseClicked(MouseEvent mouseEvent) {
//    }
//
//    @Override
//    public void mousePressed(MouseEvent mouseEvent) {
//    }
//    @Override
//    public void mouseReleased(MouseEvent e) {
////        mOn = !mOn;
//        setSelected(!isSelected());
//        setBackground(Constants.backgroundColor);
//        setOpaque(false);
//        repaint();
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent mouseEvent) {
//    }
//
//    @Override
//    public void mouseExited(MouseEvent mouseEvent) {
//    }

    //    public boolean isSelected() {
//        return mOn;
//    }
    public void setSelected(boolean on) {
        if ((isSelected() && !on) || (!isSelected() && on)) {
//            logger.debug("setSelected changed to " + on);
            super.setSelected(on);
            repaint();
        }
    }

    class myButtonUI extends MetalButtonUI {
        @Override
        public void paintButtonPressed(Graphics g, AbstractButton b) {
            g.setColor(Constants.backgroundColor);
            g.fillRect(0, 0, b.getSize().width, b.getSize().height);
        }
    }
}
