package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-07
 */
public class CircleRadioButton extends JRadioButton{
    public CircleRadioButton(String text,int size, Color color ) {
        super(text);
        setIcon(new CircleIcon(size-1,color));
        setFont(new Font(getFont().getName(),Font.BOLD,size -(size/3)));
        setOpaque(false);
        setForeground(Constants.textColor);
    }
    public CircleRadioButton(int size, Color color) {
        super(new CircleIcon(size-1, color));
        setOpaque(false);
    }

}
