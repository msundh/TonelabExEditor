package nu.mikaelsundh.tonelabex.editor.midi;

import nu.mikaelsundh.tonelabex.editor.utils.MidiUtil;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-05
 */
public class MidiException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String ErrorCode;
    public MidiException(String message) {
        super(message);
        ErrorCode = "";
    }
    public MidiException(String message, String data) {
        super(message);
        ErrorCode = MidiUtil.getFunctionCode(data);
    }
    public MidiException(String message, byte[] data) {
        super(message);
        ErrorCode = String.valueOf(MidiUtil.getFunctionCode(data));
    }

    public String getErrorCode() {
        return ErrorCode;
    }
}
