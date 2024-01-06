package nu.mikaelsundh.tonelabex.editor.midi;

import nu.mikaelsundh.tonelabex.editor.utils.HexUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsresources.midi.MidiCommon;

import javax.sound.midi.*;
import javax.sound.midi.MidiDevice.Info;
import java.util.Stack;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class MidiController implements Receiver {
    private static final Logger logger = LogManager.getLogger(MidiController.class);
    private static MidiController instance;
    MidiDevice outDevice = null;
    MidiDevice input = null;
    Receiver receiver = null;
    MidiReceiver mMidiReciever;
    final Stack<IMidiCommand> commandStack = new Stack<IMidiCommand>();


    public MidiController() {
        mMidiReciever = new MidiReceiver();
        mMidiReciever.start();
        instance = this;
    }
    public static MidiController getInstance() {
        return instance;
    }

    public void send(byte[] data) {
        SysexMessage onMessage = new SysexMessage();
        try {
            onMessage.setMessage(data,data.length);
            receiver.send(onMessage, -1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void send(MidiMessage message, long timeStamp) {
        midiInput(message);
    }

    synchronized void midiInput(MidiMessage msg) {
//        logger.debug("Incoming midi data: {1}", HexConvertionUtil.formatHexData(data));
        synchronized (commandStack) {
            try {
                if(commandStack.size()>0){
                    commandStack.pop().receive(msg.getMessage());
                    return;
                }

            } catch (MidiException e) {
                e.printStackTrace();
            }
        }
        processIncomming(msg);
    }
    public boolean connect2Ex() {
        boolean success = false;
        int idxIn = -1;
        int idxOut = -1;
        String[] inDeviceNames = MidiCommon.listInputDevices();
        String [] outDeviceNames = MidiCommon.listOutputDevices();
        for (int i=0;i<inDeviceNames.length;i++) {
            if (inDeviceNames[i].startsWith("ToneLab EX")) {
                idxIn = i;
                break;
            }
        }
        for (int i=0;i<outDeviceNames.length;i++) {
            if (outDeviceNames[i].startsWith("ToneLab EX")) {
                idxOut = i;
                break;
            }
        }
        if (idxIn != -1 && idxOut != -1) {
            Info[] inDevices = MidiCommon.getInputDevices();
            Info[] outDevices = MidiCommon.getOutputDevices();
            if (idxIn <= (inDevices.length -1)   && idxOut <= (outDevices.length -1) ) {
                try {
                    input = MidiSystem.getMidiDevice(inDevices[idxIn]);
                    outDevice = MidiSystem.getMidiDevice(outDevices[idxOut]);
                    input.open();
                    outDevice.open();
                    receiver = outDevice.getReceiver();
                    Transmitter	t = input.getTransmitter();
                    t.setReceiver(this);
                    success = true;
                } catch (MidiUnavailableException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        } else {
            System.out.println("No device found");
        }
        return success;
    }

    public void requestProgram(int bank, int bankNbr) {
        byte[] data =  MessageBuilder.buildPresetMsg(bank,bankNbr);
        SysexMessage msg = new SysexMessage();
        try {
            msg.setMessage(data,data.length);
            System.out.println("SEnding: " + HexUtil.toHexString(data));
            receiver.send(msg,-1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void processIncomming(MidiMessage msg) {
        mMidiReciever.processIncomingCommand(msg);
    }

    /**
     * sends a command and queue to get te answer
     * @param cmd the Command of type IMidiComand
     */
    public synchronized void executeCommand(IMidiCommand cmd){
//        logger.debug("Executing command of type "+cmd.getClass().getName());
        synchronized (commandStack) {
            commandStack.push(cmd);
        }
        cmd.prepare();
        cmd.run();
        cmd.finished();
    }
    public byte[] runCommandBlocking(IMidiCommand cmd){
        executeCommand(cmd);
        //log.debug("Waiting for answer...");
        cmd.waitForResult();
//        logger.debug("Command finished "+(cmd.ranSuccessfully()?"successfully.":"with error!"));
        if(cmd.ranSuccessfully()){
            return cmd.getResultData();
        }
        return null;
    }


    @Override
    public void close() {
        logger.info("Closing devices..");
        if (input != null)
            input.close();
        if (receiver != null)
            receiver.close();
    }
}
