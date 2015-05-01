package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-07
 */
public class CircleIcon implements Icon {
    private int mSize;
    private Color mColor;
    public CircleIcon(int size, Color color) {
        mSize = size;
        mColor = color;

    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Constants.backgroundColor);
        g.fillRect(0,0,mSize+10,mSize+10);
        g.setColor(mColor);
        g.fillRoundRect(0,0,mSize,mSize,mSize,mSize);
        g.setColor(Color.BLACK);

        g.drawRoundRect(0,0,mSize,mSize,mSize,mSize);
    }

    @Override
    public int getIconWidth() {
        return mSize;
    }

    @Override
    public int getIconHeight() {
        return mSize;
    }
}
