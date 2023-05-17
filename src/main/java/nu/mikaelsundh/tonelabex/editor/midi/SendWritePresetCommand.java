package nu.mikaelsundh.tonelabex.editor.midi;

import nu.mikaelsundh.tonelabex.editor.model.ExPreset;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class SendWritePresetCommand extends MidiCommand {
    ExPreset preset;
    public SendWritePresetCommand(ExPreset preset) throws MidiException {
        int idx = preset.getNbr();
        if (idx < 0 || idx > 99) {
            throw new MidiException("Invalid preset number: " + idx, preset.getData());
        }
        index = idx;
        this.preset = preset;
        functionCode="4C";
        expectData=false;
        expectedReturnCode=0x23;
    }
    @Override
    public void run() {
        controller.send(MessageBuilder.buildSendPresetMsg(preset));
    }


}
