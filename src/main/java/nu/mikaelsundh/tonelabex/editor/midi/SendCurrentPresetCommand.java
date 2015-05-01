package nu.mikaelsundh.tonelabex.editor.midi;

import nu.mikaelsundh.tonelabex.editor.model.ExPreset;
import org.apache.log4j.Logger;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class SendCurrentPresetCommand extends MidiCommand {
    Logger logger = Logger.getLogger(this.getClass().getName());
    ExPreset preset;
    public SendCurrentPresetCommand(ExPreset preset) throws MidiException {
        index = preset.getNbr();
        this.preset = preset;
        functionCode="40";
        expectData=false;
        expectedReturnCode=0x23;
    }
    @Override
    public void run() {
        controller.send(MessageBuilder.buildSendCurrentPresetMsg(preset));
    }


}
