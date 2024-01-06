package nu.mikaelsundh.tonelabex.editor.midi;

import nu.mikaelsundh.tonelabex.editor.gui.GuiListenerHandler;
import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.ExPreset;
import nu.mikaelsundh.tonelabex.editor.utils.MidiUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.MidiMessage;
import java.util.LinkedList;


/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class MidiReceiver extends Thread {
    private static final Logger logger = LogManager.getLogger(MidiReceiver.class);
    boolean running=true;
    GuiListenerHandler guiListener = GuiListenerHandler.getInstance();
    LinkedList<MidiMessage> incomingData=new LinkedList<MidiMessage>();
    @Override
    public synchronized void run() {
        while(running){
            while(incomingData.size()>0){
                MidiMessage data=incomingData.pop();
                process(data);
            }
            try {
//                logger.debug("Waiting for incoming Commands");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            logger.debug("Woke up...");
        }
    }

    private void process(MidiMessage fullData)  {
//        logger.debug("MidiReceiver.process Incomming data: " + fullData);
        int functionCode= fullData.getMessage()[MidiConstants.SYSEX_START.length];
        if(functionCode == 0x24){
            logger.error("Got ERROR code 24 (DATA LOAD ERROR) !");
            guiListener.fireEvent(new DeviceEvent(this,DeviceEvent.ERROR,"ERROR code 24 (DATA LOAD ERROR) !"));
        }else if(functionCode == 0x26){
            logger.error("Got ERROR code 26 (DATA FORMAT ERROR) !");
            guiListener.fireEvent(new DeviceEvent(this,DeviceEvent.ERROR,"ERROR code 26 (DATA FORMAT ERROR) !"));
        }else if(functionCode == 0x22){
            logger.error("Got ERROR code 22 (WRITE ERROR) !");
            guiListener.fireEvent(new DeviceEvent(this,DeviceEvent.ERROR,"ERROR code 22 (WRITE ERROR) !"));
        }else if(functionCode == 0x23){
            logger.info("Data dump success");
            guiListener.fireEvent(new DeviceEvent(this,DeviceEvent.DATA_CHANGE_SUCCESS,"DATA Dump success"));
        }else if(functionCode == 0x4c){
            logger.info("Received data dump to process...");
            guiListener.fireEvent(new DeviceEvent(this,DeviceEvent.PRESET_LOAD,new ExPreset(fullData.getMessage())) );
//            PresetList.setPreset(new ExPreset(fullData.getMessage()));
        }else if(functionCode == 0x4e){
            logger.info("Received Preset change. No action.");

            guiListener.fireEvent(new DeviceEvent(this,DeviceEvent.PRESET_CHANGE, new byte[]{fullData.getMessage()[MidiConstants.SYSEX_START.length+1],fullData.getMessage()[MidiConstants.SYSEX_START.length+2]}));
        } else
            logger.warn("No command implementation for function code: "+Integer.toHexString(functionCode));
    }
//    public synchronized void processIncomingCommand(String fullData) {
////        System.out.println("MidiReceiver.processIncomingCommand: " + fullData);
//        if(!fullData.startsWith(MidiConstants.SYSEX_START_STR.toLowerCase()))  {
//            logger.error("unknown format! incoming command starts not as usual!");
//            return;
//        }
//        incomingData.push(fullData);
//        notify();
//    }

    public synchronized void processIncomingCommand(MidiMessage fullData) {
        if (!MidiUtil.isSysEx(fullData.getMessage())) {
            logger.error("unknown format! incoming command starts not as usual!");
            return;
        }
        incomingData.push(fullData);
        notify();
    }
}
