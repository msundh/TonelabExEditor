package nu.mikaelsundh.tonelabex.editor.model;

import nu.mikaelsundh.tonelabex.editor.utils.PresetParser;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-13
 */
public class Pedal2Value {
    private boolean mOn;
    private int mType;
    private int mValue;

    public Pedal2Value(boolean on, int type, int value) {
        this.mOn = on;
        this.mType = type;
        this.mValue = value;
    }
    public Pedal2Value(ExPreset preset) {
        this.mOn = PresetParser.isPedal2Enabled(preset);
        this.mType = PresetParser.getPedal2Value(preset).getType();
        this.mValue = PresetParser.getPedal2Value(preset).getValue();
    }
    public boolean isOn() {
        return mOn;
    }

    public void setOn(boolean on) {
        this.mOn = on;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        this.mValue = value;
    }
}
