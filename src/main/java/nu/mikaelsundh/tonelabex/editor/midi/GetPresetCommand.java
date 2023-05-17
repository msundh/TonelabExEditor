package nu.mikaelsundh.tonelabex.editor.midi;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-05
 */
public class GetPresetCommand extends MidiCommand {

    public GetPresetCommand(int idx) {
        index = idx>99?0:idx;
        functionCode="1C";
        expectData=true;
        expectedReturnCode=0x4c;
    }
//
//
//    @Override
//    public void receive(byte[] data) throws MidiException {
//        logger.debug("byte 0: " + Integer.toHexString(data[0]) + ", byte 2: " + Integer.toHexString(data[1]));
//        logger.debug(HexUtil.toHexString(data));
//        super.receive(data);
//    }

}
