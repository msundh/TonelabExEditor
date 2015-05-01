package nu.mikaelsundh.tonelabex.editor.model;


import nu.mikaelsundh.tonelabex.editor.utils.PresetParser;

/**
 * User: Mikael Sundh
 * Date: 2012-11-10
 */
public class ExGuiPreset {
    private int bank;
    private int nbrInBank;

    private AmpSapValue ampSapValue;
    private int volumeValue;
    private int gainValue;
    private int trebleValue;
    private int middleValue;
    private int bassValue;
    private int presenceValue;
    private int noiseValue;
    private boolean cabOn;
    private int cabinetValue;
    private ReverbValue reverbValue;
    private Pedal1Value pedal1Value;
    private Pedal2Value pedal2Value;
    private ModulationValue modulationValue;
    private DelayValue delayValue;
    private PedalAssignValue pedalassignValue;

    public ExGuiPreset() {
        super();
    }

    public ExGuiPreset(ExPreset preset) {
        bank = preset.getBank();
        nbrInBank = preset.getNbrInBank();
        cabOn = PresetParser.isCabEnabled(preset);
        ampSapValue = PresetParser.getAmp(preset);
        volumeValue = PresetParser.getVolume(preset);
        gainValue = PresetParser.getGain(preset);
        trebleValue = PresetParser.getTreble(preset);
        middleValue = PresetParser.getMiddle(preset);
        bassValue = PresetParser.getBass(preset);
        presenceValue = PresetParser.getPresence(preset);
        noiseValue = PresetParser.getNoiseReduction(preset);
        cabinetValue = PresetParser.getCabinet(preset);
        reverbValue = PresetParser.getReverbValue(preset);
        pedal1Value = PresetParser.getPedal1Value(preset);
        pedal2Value = PresetParser.getPedal2Value(preset);
        modulationValue = PresetParser.getModulation(preset);
        delayValue = new DelayValue(preset);
        pedalassignValue = PresetParser.getPedalAssignValue(preset);
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public int getNbrInBank() {
        return nbrInBank;
    }

    public void setNbrInBank(int nbrInBank) {
        this.nbrInBank = nbrInBank;
    }

    public Pedal1Value getPedal1Value() {
        return pedal1Value;
    }

    public void setPedal1Value(Pedal1Value pedal1Value) {
        this.pedal1Value = pedal1Value;
    }

    public Pedal2Value getPedal2Value() {
        return pedal2Value;
    }

    public void setPedal2Value(Pedal2Value pedal2Value) {
        this.pedal2Value = pedal2Value;
    }

    public ReverbValue getReverbValue() {
        return reverbValue;
    }

    public void setReverbValue(ReverbValue reverbValue) {
        this.reverbValue = reverbValue;
    }

    public AmpSapValue getAmpSapValue() {
        return ampSapValue;
    }

    public void setAmpSapValue(AmpSapValue ampSapValue) {
        this.ampSapValue = ampSapValue;
    }

    public int getBassValue() {
        return bassValue;
    }

    public void setBassValue(int bassValue) {
        this.bassValue = bassValue;
    }

    public int getCabinetValue() {
        return cabinetValue;
    }

    public void setCabinetValue(int cabinetValue) {
        this.cabinetValue = cabinetValue;
    }

    public boolean isCabOn() {
        return cabOn;
    }

    public void setCabOn(boolean cabOn) {
        this.cabOn = cabOn;
    }

    public DelayValue getDelayValue() {
        return delayValue;
    }

    public void setDelayValue(DelayValue delayValue) {
        this.delayValue = delayValue;
    }

    public int getVolumeValue() {
        return volumeValue;
    }

    public void setVolumeValue(int volumeValue) {
        this.volumeValue = volumeValue;
    }

    public int getGainValue() {
        return gainValue;
    }

    public void setGainValue(int gainValue) {
        this.gainValue = gainValue;
    }

    public int getMiddleValue() {
        return middleValue;
    }

    public void setMiddleValue(int middleValue) {
        this.middleValue = middleValue;
    }

    public ModulationValue getModulationValue() {
        return modulationValue;
    }

    public void setModulationValue(ModulationValue modulationValue) {
        this.modulationValue = modulationValue;
    }

    public int getNoiseValue() {
        return noiseValue;
    }

    public void setNoiseValue(int noiseValue) {
        this.noiseValue = noiseValue;
    }

    public PedalAssignValue getPedalAssignValue() {
        return pedalassignValue;
    }

    public void setPedalAssignValue(PedalAssignValue pedalassign) {
        this.pedalassignValue = pedalassign;
    }

        public int getPresenceValue() {
        return presenceValue;
    }

    public void setPresenceValue(int presenceValue) {
        this.presenceValue = presenceValue;
    }

    public int getTrebleValue() {
        return trebleValue;
    }

    public void setTrebleValue(int trebleValue) {
        this.trebleValue = trebleValue;
    }

}
