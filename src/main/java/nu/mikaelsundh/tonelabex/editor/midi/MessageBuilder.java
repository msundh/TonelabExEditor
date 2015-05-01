package nu.mikaelsundh.tonelabex.editor.midi;


import nu.mikaelsundh.tonelabex.editor.model.ExPreset;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class MessageBuilder {
    public static byte[] buildPresetMsg(int bank, int preset) {
        return buildPresetMsg(getPreset(bank, preset));
    }

    public static byte[] buildPresetMsg(int nbr) {
        byte[] msg = new byte[MidiConstants.SYSEX_START.length + 5];
        int i = 0;
        for (; i < MidiConstants.SYSEX_START.length; i++) {
            msg[i] = MidiConstants.SYSEX_START[i];
        }
        msg[i++] = (byte) 0x1c;
        msg[i++] = (byte) 0x20;
        msg[i++] = (byte) nbr;
        msg[i] = (byte) 0xf7;
        return msg;
    }

    public static byte[] buildChangePresetMsg(int nbr) {
        byte[] msg = new byte[MidiConstants.SYSEX_START.length + 4];
        int i = 0;
        for (; i < MidiConstants.SYSEX_START.length; i++) {
            msg[i] = MidiConstants.SYSEX_START[i];
        }
        msg[i++] = (byte) 0x4e;
        msg[i++] = (byte) 0x00;
        msg[i++] = (byte) nbr;
        msg[i] = (byte) 0xf7;
        return msg;
    }

    public static byte[] buildSendPresetMsg(ExPreset preset) {
        byte[] msg = new byte[MidiConstants.SYSEX_START.length + 3 + preset.getData().length + 1];
        int i = 0;
        for (; i < MidiConstants.SYSEX_START.length; i++) {
            msg[i] = MidiConstants.SYSEX_START[i];
        }
        msg[i++] = (byte) 0x4c;
        msg[i++] = (byte) 0x20;
        msg[i++] = (byte) preset.getNbr();
        for (int ii = 0; ii < preset.getData().length; ii++) {
            msg[i++] = preset.getData()[ii];
        }
        msg[i] = (byte) 0xf7;
        return msg;
    }

    public static byte[] buildSendCurrentPresetMsg(ExPreset preset) {
        byte[] msg = new byte[MidiConstants.SYSEX_START.length + 1 + preset.getData().length + 1];
        int i = 0;
        for (; i < MidiConstants.SYSEX_START.length; i++) {
            msg[i] = MidiConstants.SYSEX_START[i];
        }
        msg[i++] = (byte) 0x40;
        for (int ii = 0; ii < preset.getData().length; ii++) {
            msg[i++] = preset.getData()[ii];
        }
        msg[i] = (byte) 0xf7;
        return msg;
    }

    public static int getPreset(int bank, int bankNbr) {
//        System.out.println("Bank(" + bank + ") nbr(" + bankNbr + ") :" + (((bank-1)*4) + bankNbr-1) );
        return ((bank - 1) * 4) + bankNbr - 1;
    }

    public static int[] getBankAndNbr(int preset) {
        int bank = ((preset + 4) / 4);
        int nbr = ((preset + 4) % 4) + 1;
        return new int[]{bank, nbr};
    }
}
