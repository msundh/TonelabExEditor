package nu.mikaelsundh.tonelabex.editor.model;

import nu.mikaelsundh.tonelabex.editor.midi.MessageBuilder;
import nu.mikaelsundh.tonelabex.editor.midi.MidiConstants;
import nu.mikaelsundh.tonelabex.editor.utils.HexUtil;
import nu.mikaelsundh.tonelabex.editor.utils.MidiUtil;
import nu.mikaelsundh.tonelabex.editor.utils.PresetParser;
import org.apache.log4j.Logger;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class ExPreset {
    Logger logger  = Logger.getLogger(this.getClass().getName());
    private int nbr;
    private int bank;
    private int nbrInBank;
    private int code;
    private byte[] data;

    public ExPreset(ExGuiPreset preset) {
        bank = preset.getBank();
        nbrInBank = preset.getNbrInBank();
        nbr = MessageBuilder.getPreset(bank,nbrInBank);
        data = PresetParser.encodeToMidi(preset);
    }
    public ExPreset(ExPreset preset) {
        nbr = preset.getNbr();
        code = preset.getCode();
        bank = MessageBuilder.getBankAndNbr(nbr)[0];
        nbrInBank = MessageBuilder.getBankAndNbr(nbr)[1];
        data = preset.getData().clone();
    }
    public ExPreset(byte[] data) {
        extractData(data);
    }
    public ExPreset(String data) {
        extractData(HexUtil.hex2byte(data));
    }
    public byte[] getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public int getNbr() {
        return nbr;
    }
    public int getBank() {
        return bank;
    }
//    public void setBank(int bank) {
//        this.bank = bank;
//    }
    public int getNbrInBank() {
        return nbrInBank;
    }
    public void setPresetNbr(int bank, int nbr) {
        this.bank = bank;
        this.nbrInBank=nbr;
        this.nbr = MessageBuilder.getPreset(bank,nbr);
    }


    private void extractData(byte[] inData) {
        if ((short)(inData[0]&0xff) == (short)0xf0) {
            int offset = MidiConstants.SYSEX_START.length + 3;
            byte[] outData = new byte[inData.length - offset -1];
            System.arraycopy(inData,offset,outData,0,outData.length);

            nbr = MidiUtil.getPreset(inData);
            bank = MessageBuilder.getBankAndNbr(nbr)[0];
            nbrInBank = MessageBuilder.getBankAndNbr(nbr)[1];
            code = inData[MidiConstants.SYSEX_START.length];
            this.data = outData;
        } else {
            logger.error("Tried to create an ExPreset with none sysexclusive data");
        }
    }
//    public void setPreset(int preset) {
//        nbr = preset;
//        bank = MessageBuilder.getBankAndNbr(nbr)[0];
//        nbrInBank = MessageBuilder.getBankAndNbr(nbr)[1];
//    }
    public String toString() {
        return "Preset " + nbr + " (Bank " + bank + " - " + nbrInBank+")";
    }
}
