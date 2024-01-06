package nu.mikaelsundh.tonelabex.editor.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * User: Mikael Sundh
 * Date: 2012-11-09
 */
public class ModulationValue {
    private static final Logger logger = LogManager.getLogger(ModulationValue.class);
    private final boolean mOn;
    private final int mType;
    private final int mDepth;
    private final double mSpeed;
    private final int mResonance;
    private final int mAttack;
    private final int mBalance;
    private final int mPitch;
    private final int mSens;
    private final int mFiltronType;
    private final int mTalkType;


    public ModulationValue(boolean  on,int type,int depth ,double speed,int reso, int attack, int balance,int pitch,int sens,int filtronType, int talkType) {
        mOn=on;
        mType=type;
        mDepth =depth;
        if (speed > 15 && (type <5 || type == 6)) {
            mSpeed = 15;
        } else if (speed > 30 && type == 5) {
            mSpeed = 30;
        } else {
            mSpeed =speed;
        }
        mResonance=reso;
        mAttack = attack;
        mBalance = balance;
        mPitch = pitch;
        mSens = sens;
        mFiltronType = filtronType;
        mTalkType = talkType;
    }


    public boolean isOn() {
        return mOn;
    }
    public int getType() {
        return mType;
    }

    public int getDepth() {
        return mDepth;
    }

    public int getAttack() {
        return mAttack;
    }

    public int getBalance() {
        return mBalance;
    }


    public int getFiltronType() {
        return mFiltronType;
    }

    public int getPitch() {
        return mPitch;
    }

    public int getResonance() {
        return mResonance;
    }

    public int getSens() {
        return mSens;
    }

    public double getSpeed() {
        return mSpeed;
    }

    public int getTalkType() {
        return mTalkType;
    }
    public String toString() {
        if (mType == 9) {
            logger.debug("Filtron: " + mFiltronType);
            String x ="On: " +mOn +", Type ("+Constants.modNames[mType]+";";
                    x=x+") Depth("+mDepth+") Speed("+mSpeed+") Reso("+mResonance+") attack("+mAttack+") Balance(";
            x=x+mBalance+") Sens(" +mSens+") Filtron(";
            x=x+Constants.filtronNames[mFiltronType]+")";
        return "On: " +mOn +", Type ("+Constants.modNames[mType]+") Depth("+mDepth+") Speed("+mSpeed+") Reso("+mResonance+") attack("+mAttack+") Balance("+
                mBalance+") Sens(" +mSens+") Filtron("
                +Constants.filtronNames[mFiltronType]+")";
        } else if (mType == 8) {
            return "On: " +mOn +", Type ("+Constants.modNames[mType]+") Depth("+mDepth+") Speed("+mSpeed+") Reso("+mResonance+") attack("+mAttack+") Balance("+
                    mBalance+") Pitch("+Constants.pitchNames[mPitch]+") Sens(" +mSens+")";
        } else if (mType == 10) {
            return "On: " +mOn +", Type ("+Constants.modNames[mType]+") Depth("+mDepth+") Speed("+mSpeed+") Reso("+mResonance+") attack("+mAttack+") Balance("+
                    mBalance+") Sens(" +mSens+") Talk type("+Constants.talkModNames[mTalkType]+")";
        } else {
            return "On: " +mOn +", Type ("+Constants.modNames[mType]+") Depth("+mDepth+") Speed("+mSpeed+") Reso("+mResonance+") attack("+mAttack+") Balance("+
                    mBalance+") Sens(" +mSens+")";
        }
    }

}
