package nu.mikaelsundh.tonelabex.editor.midi;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04   F0 42 30 00 01 10
 */
public class MidiConstants {
    public static final byte[] SYSEX_START = {(byte)0xF0,(byte)0x42,(byte)0x30,(byte)0x00,(byte)0x01,(byte)0x10};
    public static final String SYSEX_START_STR = "F04230000110";
    public static final byte[] SYSEX_END = {(byte)0xF7};
    public static final String SYSEX_END_STR = "F7";
}
