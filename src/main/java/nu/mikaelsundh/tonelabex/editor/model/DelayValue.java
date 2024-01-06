package nu.mikaelsundh.tonelabex.editor.model;

import nu.mikaelsundh.tonelabex.editor.utils.PresetParser;

/**
 * User: Mikael Sundh
 * Date: 2012-11-10
 */
public class DelayValue {
    private final boolean mOn;
    private final int mType;
    private final int mLevel;
    private final int mTime;
    private final int mFeedback;

    public DelayValue(boolean on, int mTime, int mFeedback, int mLevel, int mType) {
        this.mOn=on;
        this.mTime = mTime;
        this.mFeedback = mFeedback;
        this.mLevel = mLevel;
        this.mType = mType;
    }
    public DelayValue(ExPreset preset) {
        this.mOn= PresetParser.isDelayEnabled(preset);
        this.mTime = PresetParser.getDelayTime(preset);
        this.mFeedback = PresetParser.getDelayFeedback(preset);
        this.mLevel = PresetParser.getDelayLevel(preset);
        this.mType = PresetParser.getDelayType(preset);
    }

    public boolean isOn() {
        return mOn;
    }
//    public void setOn(boolean on) {
//        mOn=on;
//    }
    public int getTime() {
        return mTime;
    }

//    public void setTime(int time) {
//        this.mTime = time;
//    }

    public int getFeedback() {
        return mFeedback;
    }

//    public void setFeedback(int mFeedback) {
//        this.mFeedback = mFeedback;
//    }

    public int getLevel() {
        return mLevel;
    }

//    public void setLevel(int mLevel) {
//        this.mLevel = mLevel;
//    }

    public int getType() {
        return mType;
    }

//    public void setType(int mType) {
//        this.mType = mType;
//    }
    public String toString() {
        return "On: " + isOn() +", Type: " + getType()+", level: " + getLevel()+", Feedback: " + getFeedback()+", Time: " + getTime();
    }
}
