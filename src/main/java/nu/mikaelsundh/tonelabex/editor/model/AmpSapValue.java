package nu.mikaelsundh.tonelabex.editor.model;

import nu.mikaelsundh.tonelabex.editor.utils.PresetParser;
import org.apache.log4j.Logger;

/**
 * User: Mikael Sundh
 * Date: 2012-11-10
 */
public class AmpSapValue {
    Logger logger = Logger.getLogger(this.getClass().getName());
    private static boolean mOn;
    private static int mType;
    private static int mModel;

    public AmpSapValue( boolean on, int value) {
        mOn = on;
        mType = (int)value/11;
        mModel = value-mType*11;
        logger.debug("on: " + mOn + ", Val: " + value +", type: " + mType + ", model " + mModel);
    }
    public AmpSapValue(boolean on, int type, int model) {
        mOn = on;
        mType = type;
        mModel = model;
        logger.debug("on: " + mOn + ", type: " + mType + ", model " + mModel);
    }
    public int getType() {
        return mType;
    }
    public int getModel() {
        return mModel;
    }
    public boolean isOn() {
        return mOn;
    }

}
