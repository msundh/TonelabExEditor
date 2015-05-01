package nu.mikaelsundh.tonelabex.editor.gui.components;

import javax.swing.*;

/**
 * <b>Programm:</b> WaveJNI<br>
 * <b>Copyright:</b> 2002 Andreas Gohr, Frank Schubert, Milan Altenburg<br>
 * <b>License:</b> GPL2 or higher<br>
 * <br>
 * <b>Info:</b> This JSlider uses floats for its values
 */
public class FloatJSlider extends JSlider {
    static final float FLOAT_MINIMUM = 0.0f;
    static final float FLOAT_MAXIMUM = 100.0f;
    static final float FLOAT_MIDDLE = 50.0f;
    static final int PRECISION_MULTIPLIER  = 100;
    /**
     * Constructor - initializes with 0.0,100.0,50.0
     */
    public FloatJSlider(){
        super();
        setFloatMinimum(FLOAT_MINIMUM);
        setFloatMaximum(FLOAT_MAXIMUM);
        setFloatValue(FLOAT_MIDDLE);
    }

    /**
     * Constructor
     */
    public FloatJSlider(float min, float max, float val){
        super();
        setFloatMinimum(min);
        setFloatMaximum(max);
        setFloatValue(val);
    }

    /**
     * returns Maximum in float precision
     */
    public float getFloatMaximum() {
        return( getMaximum()/FLOAT_MAXIMUM );
    }

    /**
     * returns Minimum in float precision
     */
    public float getFloatMinimum() {
        return( getMinimum()/FLOAT_MAXIMUM );
    }

    /**
     * returns Value in float precision
     */
    public float getFloatValue() {
        return( getValue()/FLOAT_MAXIMUM );
    }

    /**
     * sets Maximum in float precision
     */
    public void setFloatMaximum(float max) {
        setMaximum((int)(max*PRECISION_MULTIPLIER));
    }

    /**
     * sets Minimum in float precision
     */
    public void setFloatMinimum(float min) {
        setMinimum((int)(min*PRECISION_MULTIPLIER));
    }

    /**
     * sets Value in float precision
     */
    public void setFloatValue(float val) {
        setValue((int)(val*PRECISION_MULTIPLIER));
        setToolTipText(Float.toString(val));
    }
}
