package nu.mikaelsundh.tonelabex.editor.midi;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class SendChangePreset extends MidiCommand {



    public SendChangePreset(int idx) throws MidiException {
        if (idx < 0 || idx > 99) {
            throw new MidiException("Invalid preset number: " + idx,String.valueOf(idx));
        }
        index = idx;
        functionCode="4E";
        expectData=false;
        expectedReturnCode=0x23;
    }
    @Override
    public void run() {
        controller.send(MessageBuilder.buildChangePresetMsg(index));
    }


}
