package nu.mikaelsundh.tonelabex.editor.midi;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-05
 */
public interface IMidiCommand {
    public void run();
    public void receive(byte[] data) throws MidiException;
    public void prepare();
    public void finished();
    public void waitForResult();
    public boolean ranSuccessfully();
    public byte[] getResultData();
}
