package nu.mikaelsundh.tonelabex.editor.midi;

import nu.mikaelsundh.tonelabex.editor.utils.HexUtil;
import nu.mikaelsundh.tonelabex.editor.utils.MidiUtil;
import org.apache.log4j.Logger;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public abstract class MidiCommand implements IMidiCommand {
    Logger logger = Logger.getLogger(this.getClass().getName());

    public enum State { NEW, INIT, EXECUTED, FINISHED, WAIT, ARRIVED };
    protected State state = State.NEW;
    int index = 0;
    protected String functionCode;
    protected boolean expectData=false;
    protected int expectedReturnCode;
    protected byte[] resultData=null;
    protected boolean ranSuccessfully=false;
    MidiController controller = MidiController.getInstance();

    @Override
    public void run() {
        controller.send(MessageBuilder.buildPresetMsg(index));
    }
    protected void receiveData(byte[] resultData) {
        this.resultData=resultData;
//        logger.debug("receiveData: " + resultData);
    }

    @Override
    public void receive(byte[] data) throws MidiException {
//        logger.debug("Receive: " + HexUtil.toHexString(data));
        ranSuccessfully=analyzeResult(data);
        arrived();
        if(!this.ranSuccessfully) throw new MidiException("unexpected midi return code!", HexUtil.toHexString(data));
//        logger.info("Midi command successful");
    }

    public byte[] getResultData() {
        return resultData;
    }

    public boolean ranSuccessfully() {
        return ranSuccessfully;
    }

    public synchronized void waitForResult() {
        while(isRunning()){
//            logger.debug("Waiting for answer of command: "+this);
            try {
                state=State.WAIT;
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean analyzeResult(byte[] data){
        MidiUtil.isSysEx(data);
        if(!MidiUtil.isSysEx(data)){
            logger.error("received message does not start with usual start data!");
            if(expectData) return false;
        }
        int resultCode=MidiUtil.getFunctionCode(data);
//        logger.debug("ResultCode: " + Integer.toHexString(resultCode));
        if(expectedReturnCode != resultCode){
            logger.error("expected return code: "+Integer.toHexString(expectedReturnCode)+" but got: "+Integer.toHexString(resultCode));
            return false;
        }
        if(expectData) {
//            logger.debug("received data: "+HexUtil.toHexString(resultData));
            if (resultCode == 0x4c) {
                receiveData(data); //ExPreset, keep full sysex
            } else {
                byte[] resultData=MidiUtil.extractData(data);
                receiveData(resultData);
            }
            return true;
        }
        return true;
    }


    public static String formatIncomingData(String fulldata){
        String ret="";
        int off=0;
        int len=fulldata.length();
        for(off=0;off<len;off+=8){
            int remain=len-off;
            if(remain<=8)
                ret+=fulldata.substring(off);
            else
                ret+=fulldata.substring(off,off+8)+" ";
        }
        return ret;
    }
    protected synchronized void arrived() {
        state=State.ARRIVED;
//        logger.debug("notify waiting thread");
        this.notify();
    }
    @Override
    public void finished() {
        state=State.FINISHED;
    }


    public synchronized boolean isRunning() {
        if(state.equals(State.ARRIVED))
            return false;
        return true;
    }




    @Override
    public void prepare() {
        state=State.INIT;
    }

}
