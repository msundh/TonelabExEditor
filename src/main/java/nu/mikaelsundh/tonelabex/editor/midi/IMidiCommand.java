package nu.mikaelsundh.tonelabex.editor.midi;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-05
 */
public interface IMidiCommand {
    void run();
    void receive(byte[] data) throws MidiException;
    void prepare();
    void finished();
    void waitForResult();
    boolean ranSuccessfully();
    byte[] getResultData();
}
