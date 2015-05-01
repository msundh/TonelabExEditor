package nu.mikaelsundh.tonelabex.editor.utils;

import nu.mikaelsundh.tonelabex.editor.midi.MidiConstants;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class MidiUtil {
    public static boolean isSysEx(byte[] msg) {
        boolean ok = true;
        for (int i=0;i< MidiConstants.SYSEX_START.length;i++) {
            if (msg[i] != MidiConstants.SYSEX_START[i]) {
                ok = false;
                break;
            }
        }
        return ok;
    }
    public static boolean isSysEx(String msg) {
        System.out.println("isSysEx: " + msg);
        boolean ok = true;
        char[] strMsg = msg.toCharArray();
        for (int i=0;i<MidiConstants.SYSEX_START_STR.length();i++) {
            System.out.println("In: " + strMsg[i] + ", expected: " + MidiConstants.SYSEX_START_STR.charAt(i));
            if (strMsg[i] != MidiConstants.SYSEX_START_STR.toLowerCase().charAt(i)) {
                ok = false;
                break;
            }
        }
        return ok;
    }
    public static String getFunctionCode(String data) {
        return data.substring(MidiConstants.SYSEX_START_STR.length(),MidiConstants.SYSEX_START_STR.length()+2);
    }
    public static int getFunctionCode(byte[] data) {
        return data[MidiConstants.SYSEX_START.length];
    }
    public static int getPreset(byte[] data) {
        return data[MidiConstants.SYSEX_START.length+2];
    }
    public static String extractData(String data) {
        return data.substring(MidiConstants.SYSEX_START_STR.length()+2-MidiConstants.SYSEX_END_STR.length());
    }
    public static byte[] extractData(byte[] data) {
        byte[] result = new byte[data.length - MidiConstants.SYSEX_START.length -3 -MidiConstants.SYSEX_END.length];
        for (int i=0;i<result.length;i++) {
            result[i] = data[i + (MidiConstants.SYSEX_START.length + 3)];
        }
        return result;
    }
    public static byte[] maskInData(byte[] data){
        byte[] result = new byte[data.length];
        for (int i=0;i<data.length;i++) {
//            result[i] = Byte.valueOf((byte)(data[i]&0xFF)).byteValue();
            result[i] = (byte)(0xff & data[i]);
        }
        return result;
    }
}
