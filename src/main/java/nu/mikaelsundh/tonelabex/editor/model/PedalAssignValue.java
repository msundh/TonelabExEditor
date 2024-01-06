package nu.mikaelsundh.tonelabex.editor.model;

/**
 * User: Mikael Sundh
 * Date: 2012-11-19
 */
public class PedalAssignValue {
    private final int mAssign;
    private final int mModulationType;
    double mFRangeMin;
    double mFRangeMax;

    public PedalAssignValue(int assign, double fRangeMin, double fRangeMax, int modulationType) {
        this.mAssign = assign;
        this.mFRangeMax = fRangeMax;
        this.mFRangeMin = fRangeMin;
        this.mModulationType = modulationType;
    }

    public int getAssign() {
        return mAssign;
    }

    public double getFRangeMax() {
        return mFRangeMax;
    }

    public double getFRangeMin() {
        return mFRangeMin;
    }

    public int getModulationType() {
        return mModulationType;
    }
    public String toString() {
        return "Assigned: " + Constants.pedalAssignNames[mAssign] + ", Min val: " + mFRangeMin + ", Max: " + mFRangeMax;
    }
}
