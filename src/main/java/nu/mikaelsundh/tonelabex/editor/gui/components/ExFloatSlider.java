package nu.mikaelsundh.tonelabex.editor.gui.components;

import nu.mikaelsundh.tonelabex.editor.model.Constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

/**
 * User: Mikael Sundh
 * Date: 2012-11-12
 */
public class ExFloatSlider extends FloatJSlider {
    private static Logger logger = LogManager.getLogger(ExFloatSlider.class);
    float mMinVal;
    float mMaxVal;
    public ExFloatSlider(float min, float max) {
        super();
        mMinVal = min;
        mMaxVal = max;
        init(false,null);
    }
    public ExFloatSlider(float min, float max, String label) {
        super();
        mMinVal = min;
        mMaxVal = max;
        init(true,label);
    }
    private void init(boolean frame, String label) {
        setOrientation(JSlider.VERTICAL);
        if (frame) {
            TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor),label);
            border.setTitleColor(Constants.titleColor);
            border.setTitleFont(Constants.sliderFrameLabelFont);
            setBorder(border);
        }
        setFloatMinimum(mMinVal);
        setFloatMaximum(mMaxVal);
        setMajorTickSpacing(500);
        setMinorTickSpacing(100);
        setPaintTicks(true);
        setPaintLabels(true);
        setFloatValue(mMinVal);
        setBackground(Constants.backgroundColor);
        setForeground(Constants.textColor);
        setFont(Constants.sliderLabelFont);
        setOpaque(false);

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                if (!getValueIsAdjusting()) {
                    setToolTipText(String.valueOf(getFloatValue()));
                }
            }
        });
        updateLabelTable();
    }

    public void updateSlider(float min, float max, String label) {
        mMinVal = min;
        mMaxVal = max;
        TitledBorder border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.frameLineColor),label);
        border.setTitleColor(Constants.titleColor);
        border.setTitleFont(Constants.sliderFrameLabelFont);
        setBorder(border);
        setFloatMinimum(mMinVal);
        setFloatMaximum(mMaxVal);
        updateLabelTable();
    }
    private void updateLabelTable() {
        Enumeration e = getLabelTable().keys();
        while (e.hasMoreElements()) {
            Integer i = (Integer) e.nextElement();
            JLabel l = (JLabel) getLabelTable().get(i);
            if (i > 0) {
                l.setText(String.valueOf((float)(i / 100)));
            }
        }
    }


    // ***********************  TEST  **************************
//    Icon icon = new ImageIcon("C:\\Work\\WS\\test\\JavaTest\\src\\com\\cybercom\\test\\test.png");
//        defaults.put("Slider.horizontalThumbIcon", icon);
//        defaults.put("Slider.verticalThumbIcon", icon);

    private static class MySliderUI extends BasicSliderUI {

        private static float[] fracs = {0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f};
        private LinearGradientPaint p;

        public MySliderUI(JSlider slider) {
            super(slider);
        }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle t = trackRect;
            Point2D start = new Point2D.Float(t.x, t.y);
            Point2D end = new Point2D.Float(t.width, t.height);
            Color[] colors = {Color.red, Color.yellow, Color.green,
                    Color.green, Color.green, Color.green};
            p = new LinearGradientPaint(start, end, fracs, colors);
            g2d.setPaint(p);
            g2d.fillRect(t.x, t.y, t.width, t.height);
        }

        public void paintThumbSquare(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle t = thumbRect;
            g2d.setColor(Color.black);
            int tw2 = t.width / 2;
            g2d.drawLine(t.x, t.y, t.x + t.width - 1, t.y);
            g2d.drawLine(t.x, t.y, t.x + tw2, t.y + t.height);
            g2d.drawLine(t.x + t.width - 1, t.y, t.x + tw2, t.y + t.height);
        }

        public void paint( Graphics g, JComponent c ) {
            recalculateIfInsetsChanged();
            recalculateIfOrientationChanged();
            Rectangle clip = g.getClipBounds();

            if ( slider.getPaintTrack() && clip.intersects( trackRect ) ) {
                paintTrack( g );
            }
            if ( slider.getPaintTicks() && clip.intersects( tickRect ) ) {
                paintTicks( g );
            }
            if ( slider.getPaintLabels() && clip.intersects( labelRect ) ) {
                paintLabels( g );
            }
            if ( slider.hasFocus() && clip.intersects( focusRect ) ) {
                paintFocus( g );
            }
            if ( clip.intersects( thumbRect ) ) {
                Color savedColor = slider.getBackground();
                slider.setBackground(Color.MAGENTA);
                paintThumb( g );
                slider.setBackground(savedColor);
            }
        }
    }

    class coloredThumbSliderUI extends BasicSliderUI
    {

        Color thumbColor;
        coloredThumbSliderUI(JSlider s, Color tColor) {
            super(s);
            thumbColor=tColor;
        }

        public void paint( Graphics g, JComponent c ) {
            recalculateIfInsetsChanged();
            recalculateIfOrientationChanged();
            Rectangle clip = g.getClipBounds();

            if ( slider.getPaintTrack() && clip.intersects( trackRect ) ) {
                paintTrack( g );
            }
            if ( slider.getPaintTicks() && clip.intersects( tickRect ) ) {
                paintTicks( g );
            }
            if ( slider.getPaintLabels() && clip.intersects( labelRect ) ) {
                paintLabels( g );
            }
            if ( slider.hasFocus() && clip.intersects( focusRect ) ) {
                paintFocus( g );
            }
            if ( clip.intersects( thumbRect ) ) {
                Color savedColor = slider.getBackground();
                slider.setBackground(thumbColor);

                paintThumb(g);
                slider.setBackground(savedColor);
            }
        }
    }

    private class mySliderIconUI extends BasicSliderUI {

        Image knobImage;

        public mySliderIconUI( JSlider aSlider ) {

            super( aSlider );

            try {
                this.knobImage = ImageIO.read(new File("C:\\Work\\WS\\test\\JavaTest\\src\\com\\cybercom\\test\\test.png"));

            } catch ( IOException e ) {

                e.printStackTrace();
            }
        }
        public void paintThumb(Graphics g)  {

            g.drawImage( this.knobImage, thumbRect.x, thumbRect.y, 10, 10, null );

        }

    }
}
