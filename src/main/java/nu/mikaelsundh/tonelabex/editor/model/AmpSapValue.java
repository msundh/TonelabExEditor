package nu.mikaelsundh.tonelabex.editor.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User: Mikael Sundh
 * Date: 2012-11-10
 */
public class AmpSapValue {
    private static Logger logger = LogManager.getLogger(AmpSapValue.class);
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
