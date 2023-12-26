package nu.mikaelsundh.tonelabex.editor.utils;

import nu.mikaelsundh.tonelabex.editor.model.*;

import java.math.BigInteger;

/**
 * User: Mikael Sundh
 * Date: 2012-11-10
 */
public class PresetParser {

    public static final int BIN_PEDAL1_ON = 0x01;
    public static final int BIN_PEDAL2_ON = 0x02;
    public static final int BIN_AMP_SA = 0x04;
    public static final int BIN_CABINET_ON = 0x08;

    public static final int BIN_MODULATION = 0x10;
    public static final int BIN_DELAY = 0x20;
    public static final int BIN_REVERB = 0x40;


    public static boolean isPedal1Enabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_PEDAL1_ON);
    }

    public static boolean isPedal2Enabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_PEDAL2_ON);
    }

    public static boolean isAmpEnabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_AMP_SA);
    }

    public static boolean isCabEnabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_CABINET_ON);
    }

    public static boolean isModEnabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_MODULATION);
    }

    public static boolean isDelayEnabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_DELAY);
    }

    public static boolean isReverbEnabled(ExPreset preset) {
        int hexByte = preset.getData()[1];
        return isEnabled(hexByte, BIN_REVERB);
    }

    public static Pedal1Value getPedal1Value(ExPreset preset) {
        return new Pedal1Value(isPedal1Enabled(preset), getPedal1Type(preset), getPedal1Effect(preset));
    }

    private static int getPedal1Type(ExPreset preset) {
        return preset.getData()[2];
    }

    private static int getPedal1Effect(ExPreset preset) {
        return preset.getData()[3];
    }

    public static byte[] getPedal1Values(ExGuiPreset preset, byte[] data) {
        data[2] = (byte) preset.getPedal1Value().getType();
        data[3] = (byte) preset.getPedal1Value().getValue();
        return data;
    }

    public static Pedal2Value getPedal2Value(ExPreset preset) {
        return new Pedal2Value(isPedal2Enabled(preset), getPedal2Type(preset), getPedal2Effect(preset));
    }

    private static int getPedal2Type(ExPreset preset) {
        return preset.getData()[4];
    }

    private static int getPedal2Effect(ExPreset preset) {
        return preset.getData()[5];
    }

    private static byte[] getPedal2Values(ExGuiPreset preset, byte[] data) {
        data[4] = (byte) preset.getPedal2Value().getType();
        data[5] = (byte) preset.getPedal2Value().getValue();
        return data;
    }

    public static AmpSapValue getAmp(ExPreset preset) {
        return new AmpSapValue(isAmpEnabled(preset), preset.getData()[6]);
    }

    private static byte[] getAmpValues(ExGuiPreset preset, byte[] data) {
        data[6] = (byte) ((preset.getAmpSapValue().getType() * 11) + preset.getAmpSapValue().getModel());
        return data;
    }

    public static int getGain(ExPreset preset) {
        return preset.getData()[7];
    }

    public static byte[] getGain(ExGuiPreset preset, byte[] data) {
        data[7] = (byte) preset.getGainValue();
        return data;
    }

    // Note: undocumented extra byte, error in MIDI spec 1.0
    public static int getVolume(ExPreset preset) {
        return preset.getData()[9];
    }

    public static byte[] getVolume(ExGuiPreset preset, byte[] data) {
        data[9] = (byte) preset.getVolumeValue();
        return data;
    }

    public static int getTreble(ExPreset preset) {
        return preset.getData()[10];
    }

    public static byte[] getTreble(ExGuiPreset preset, byte[] data) {
        data[10] = (byte) preset.getTrebleValue();
        return data;
    }

    public static int getMiddle(ExPreset preset) {
        return preset.getData()[11];
    }

    public static byte[] getMiddle(ExGuiPreset preset, byte[] data) {
        data[11] = (byte) preset.getMiddleValue();
        return data;
    }

    public static int getBass(ExPreset preset) {
        return preset.getData()[12];
    }

    public static byte[] getBass(ExGuiPreset preset, byte[] data) {
        data[12] = (byte) preset.getBassValue();
        return data;
    }

    public static int getPresence(ExPreset preset) {
        return preset.getData()[13];
    }

    public static byte[] getPresence(ExGuiPreset preset, byte[] data) {
        data[13] = (byte) preset.getPresenceValue();
        return data;
    }
    /*
    Get real value i.e. value * 2
     */
    public static int getNoiseReduction(ExPreset preset) {
//        logger.debug("NR: " + preset.getData()[14]);
        return preset.getData()[14]*2;
    }
    /*
    Get MIDI data i.e. real value/2
     */
    public static byte[] getNoiseReduction(ExGuiPreset preset, byte[] data) {
        data[14] = (byte) (preset.getNoiseValue()/2);
//        logger.debug("NR: " + preset.getNoiseValue());
        return data;
    }

    public static int getCabinet(ExPreset preset) {
        return preset.getData()[15];
    }

    public static byte[] getCabinet(ExGuiPreset preset, byte[] data) {
        data[15] = (byte) preset.getCabinetValue();
        return data;
    }


    // 32-34 Mod Offset. 32 + 128ms, 64 Fix 1,28s)
    public static int getModOffset1(ExPreset preset) {
        return preset.getData()[16];
    }

    public static ReverbValue getReverbValue(ExPreset preset) {
        return new ReverbValue(isReverbEnabled(preset), getReverbType(preset), getReverbEffect(preset));
    }

    private static int getReverbType(ExPreset preset) {
        return preset.getData()[17];
    }

    private static int getReverbEffect(ExPreset preset) {
        return preset.getData()[18];
    }

    public static byte[] getReverbValues(ExGuiPreset preset, byte[] data) {
        data[17] = (byte) preset.getReverbValue().getType();
        data[18] = (byte) preset.getReverbValue().getValue();
        return data;
    }

    public static ModulationValue getModulation(ExPreset preset) {
        switch (getModType(preset)) {
            case 0:
            case 1:
            case 4:
            case 5: //boolean  on,int type,int depth ,double speed,int reso, int attack, int balance,int pitch,int sens,int filtronType, int talkType
            case 6:
                return new ModulationValue(isModEnabled(preset), getModType(preset), getModEdit(preset), getModFrequency(preset), 0, 0, 0, 0, 0, 0, 0);
            case 2:
            case 3:
                return new ModulationValue(isModEnabled(preset), getModType(preset), 0, getModFrequency(preset), getModEdit(preset), 0, 0, 0, 0, 0, 0);
            case 7:
                return new ModulationValue(isModEnabled(preset), getModType(preset), 0, 0, 0, getModEdit(preset), 0, 0, 0, 0, 0); // Slow Attack
            case 8:
                return new ModulationValue(isModEnabled(preset), getModType(preset), 0, 0, 0, 0, getModEdit(preset), getPitchTalkModeValue(preset), 0, 0, 0); // Pitch
            case 9:
                return new ModulationValue(isModEnabled(preset), getModType(preset), 0, 0.0, getModTapEdit(preset), 0, 0, 0, getModEdit(preset), getPitchTalkModeValue(preset), 0); // Filtron
            case 10:
                return new ModulationValue(isModEnabled(preset), getModType(preset), 0, 0.0, getModTapEdit(preset), 0, 0, 0, getModEdit(preset), 0, getPitchTalkModeValue(preset)); // Talk Mode
            default:
                return null;
        }
    }

    private static int getModType(ExPreset preset) {  // Val 00 - 0A
        return preset.getData()[19];
    }

    //    private static int getModParameter1(ExPreset preset) {  // Val 00 - 64
//        return preset.getData()[20];
//    }
    private static int getModParameter2(ExPreset preset) {  // Val depend on type
        return preset.getData()[21];
    }

    public static int getModParameter3(ExPreset preset) {  // Val depend on type
        return byte2int(new byte[]{preset.getData()[22], preset.getData()[23]});  // Note 2 bytes
    }

    private static int getModEdit(ExPreset preset) {
        return preset.getData()[20]; // Mod Param 1
    }

    private static int getModTap(ExPreset preset) {
        switch (getModType(preset)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return (int) (getModFrequency(preset) * 100);
            case 7:
                return 0; // Slow Attack
            case 8:
                return getPitchTalkModeValue(preset); // Pitch
            case 9:
                return getModParameter3(preset); // Filtron
            case 10:
                return getPitchTalkModeValue(preset); // Talk Mode
            default:
                return 0;
        }
    }

    private static int getModTapEdit(ExPreset preset) {
        switch (getModType(preset)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return (int) (getModFrequency(preset) * 100);
            case 7:
                return 0; // Slow Attack
            case 8:
                return getPitchTalkModeValue(preset); // Pitch
            case 9:
                return getModParameter2(preset); // Filtron
            case 10:
                return getModParameter2(preset); // Talk Mode
            default:
                return 0;
        }
    }

    //    public String getModSpeedString() {
//        if(modIsFrequency()) return getModFrequencyString()+" Hz";
//        if(modIsFiltron()) return filtronNames[getModSpeed()>filtronNames.length?0:getModSpeed()];
//        if(modIsPitch()) return getPitchName();
//        if (modIsTalkMod()) return getTalkModName();
//        return getModSpeed()+" ms";
//    }
    private static int getPitchTalkModeValue(ExPreset preset) {
        return getModParameter3(preset) >> 8;  //Only 1st byte is used

    }

    private static double getModFrequency(ExPreset preset) {
        byte[] bytes = int2bytes(getModParameter3(preset));
//        logger.debug("Bytes from int value, len: " + bytes.length);
        int byte1 = bytes[0];
        int byte2 = bytes[1];
        double result;
        if (getModOffset1(preset) == 0) {
            result = (Math.floor(100 * 1000 / (double) (byte2 * 256 + byte1) + 0.5d) / 100);
        } else {
            result = (Math.floor(100 * 1000 / (double) (byte2 * 256 + byte1 + 128) + 0.5d) / 100);
        }
        return result;
    }

    private static byte[] getModFrequency(ExGuiPreset preset, byte[] data) {
        int speed = (int) (Math.floor(1000 / preset.getModulationValue().getSpeed() + 0.5d));
        int byte1 = speed % 256;
        int byte2 = speed / 256;
        byte offset = 0;
        if (byte1 > 127) {
            offset = 0x20;
            byte1 = byte1 - 128;
        }
        data[16] = offset;
        data[22] = (byte) byte1;
        data[23] = (byte) byte2;
        return data;
    }

    private static double getRangeModFrequency(ExPreset preset,boolean min) {
        byte[] bytes;
        if (min) {
            bytes = new byte[]{preset.getData()[31],preset.getData()[32]};
        }else {
            bytes = new byte[]{preset.getData()[33],preset.getData()[34]};
        }
        int offset = 0;//getModOffset1(preset);//preset.getData()[35];
//        logger.debug("Bytes from int value, len: " + bytes.length);
        int byte1 = bytes[0];
        int byte2 = bytes[1];
        double result;
        if (offset == 0) {
            result = (Math.floor(100 * 1000 / (double) (byte2 * 256 + byte1) + 0.5d) / 100);
        } else {
            result = (Math.floor(100 * 1000 / (double) (byte2 * 256 + byte1 + 128) + 0.5d) / 100);
        }
        return result;
    }

    private static byte[] getRangeModFrequency(ExGuiPreset preset, byte[] data) {
        int speed = (int) (Math.floor(1000 / preset.getModulationValue().getSpeed() + 0.5d));
        int byte1 = speed % 256;
        int byte2 = speed / 256;
        byte offset = 0;
        if (byte1 > 127) {
            offset = 0x20;
            byte1 = byte1 - 128;
        }
        data[16] = offset;
        data[22] = (byte) byte1;
        data[23] = (byte) byte2;
        return data;
    }


    // 48-50: Offset Modulation (8: +128)
    public static int getModOffset2(ExPreset preset) {
        return preset.getData()[24];
    }

    public static byte[] getModulation(ExGuiPreset preset, byte[] data) {
        data[19] = (byte) preset.getModulationValue().getType();

        switch (preset.getModulationValue().getType()) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 6:
                data[20] = (byte) preset.getModulationValue().getDepth();
                data[21] = 0x00; //Parameter 2
                data = getModFrequency(preset, data);
                break;
            case 2:
            case 3:
                data[20] = (byte) preset.getModulationValue().getResonance();//Parameter 1
                data[21] = 0x00; //Parameter 2
                data = getModFrequency(preset, data);
                break;
            case 7: // Slow Attack
                data[20] = (byte)preset.getModulationValue().getAttack();
                data[21] = 0x46; // TODO verify
                data[22] = 0x00; // Param 3
                data[23] = 0x00; // Para, 3
//                data[20] = 0x00; //Parameter 1
//                data[21] = 0x00; //Parameter 2
//                data = getModAttack(preset.getModulationValue().getAttack(), data); //Parameter 3
                break;
            case 8:
                data[20] = (byte) preset.getModulationValue().getBalance(); //Parameter 1
                data[21] = 0x00; //Parameter 2
                data[23] = 0x00;
                data[22] = (byte) preset.getModulationValue().getPitch();
                break;
            case 9:
                data[20] = (byte) preset.getModulationValue().getSens(); //Parameter 1
                data[21] = (byte) preset.getModulationValue().getResonance(); //Parameter 2
                data[23] = 0x00;
                data[22] = (byte) preset.getModulationValue().getFiltronType();
                break;
            case 10:
                data[20] = (byte) preset.getModulationValue().getSens(); //Parameter 1
                data[21] = (byte) preset.getModulationValue().getResonance(); //Parameter 2
                data[23] = 0x00;
                data[22] = (byte) preset.getModulationValue().getTalkType();
                break;
        }
        return data;
    }

    private static byte[] getModAttack(int ms, byte[] data) {
        int byte1 = ms % 256;
        int byte2 = ms / 256;
        byte offset = 0;
        if (byte1 > 127) {
            offset = 0x20;
            byte1 = byte1 - 128;
        }
        data[16] = offset;
        data[22] = (byte) byte1;
        data[23] = (byte) byte2;
        return data;
    }

    public static int getDelayType(ExPreset preset) {
        return preset.getData()[25];   // val 0 -3
    }

    public static int getDelayLevel(ExPreset preset) {
        return preset.getData()[26];   // val 0 -30
    }

    public static int getDelayFeedback(ExPreset preset) {
        return preset.getData()[27];   // val 0 -100
    }

    private static int getDelayTimeVal(ExPreset preset) {
//        System.out.println("getDelayTime, input: " + HexUtil.toHexString(new byte[]{preset.getData()[28],preset.getData()[29]}));
        int s1 = preset.getData()[28];
        int s2 = preset.getData()[29];
        int offset = getModOffset2(preset) == 8 ? 128 : 0;
//        System.out.println("Delay S1: " + s1 + ", s2: " + s2 + ", offset2: " + offset + ", DelayVal: " + (s1 +(s2*256)));
        return offset + s1 + (s2 * 256);
//        return hex2int(data.substring(54, 58));   // Note 2 bytes. val 40 - 1480 (ms)
    }

    public static int getDelayTime(ExPreset preset) {
        // returns Hz rounded to two digits
        return getDelayTimeVal(preset);
//        return (int)Math.floor(100*1000/(double)getDelayTimeVal(preset)+0.5d)/100;
    }


    public static byte[] getDelayValues(ExGuiPreset preset, byte[] data) {
        data[25] = (byte) preset.getDelayValue().getType();
        data[26] = (byte) preset.getDelayValue().getLevel();
        data[27] = (byte) preset.getDelayValue().getFeedback();
        data = getDelayTime(preset.getDelayValue().getTime(), data);
        return data;
    }

    private static byte[] getDelayTime(int ms, byte[] data) {
        int byte1 = ms % 256;
        int byte2 = ms / 256;
        byte offset = 0;
        if (byte1 > 127) {
            offset = 0x08;
            byte1 = byte1 - 128;
        }
        data[24] = offset;
        data[28] = (byte) byte1;
        data[29] = (byte) byte2;
        return data;
    }
//    private double calcRangeValues(ExPreset preset) {
//        int modType = getModType(preset);
//        int pedalAsn = getPedal1Type(preset);
//        switch (pedalAsn) {
//
//        }
//    }

    public static PedalAssignValue getPedalAssignValue(ExPreset preset) {
        int modType = getModType(preset);
        int pedalAsn = getPedalAssign(preset);
        switch (pedalAsn) {
            case 0: return new PedalAssignValue(pedalAsn,0,0,modType); //Off
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 9:
            case 10:return new PedalAssignValue(pedalAsn,getPedalRangeMin(preset),getPedalRangeMax(preset),modType);
            case 6:
            case 7:
                   if (modType < 7) { // Speed(freq) values
                       return new PedalAssignValue(pedalAsn,getRangeModFrequency(preset,true),getRangeModFrequency(preset,false),modType);
                   } else { // Attack, Pitch, Filtron, Talk
                       return new PedalAssignValue(pedalAsn,getPedalRangeMin(preset),getPedalRangeMax(preset),modType);
                   }
        }
            return new PedalAssignValue(getPedalAssign(preset),(double)getPedalRangeMin(preset), (double)getPedalRangeMax(preset),getModType(preset));
    }
    private static int getPedalAssign(ExPreset preset) {
        for (int i = 0; i < Constants.pedalassignValues.length; i++) {
            if (preset.getData()[30] == Constants.pedalassignValues[i]) return i;
        }
        return 0;

    }
    public static byte[] getPedalAssignValues(ExGuiPreset preset, byte[] data) {
        data[30] = Constants.pedalassignValues[preset.getPedalAssignValue().getAssign()];
        byte[] bytes = int2bytes((int)preset.getPedalAssignValue().getFRangeMin());
        data[31] = bytes[0];
        data[32] = bytes[1];
        bytes = int2bytes((int)preset.getPedalAssignValue().getFRangeMax());
        data[33] = bytes[0];
        data[34] = bytes[1];
        return data;
    }
    private static byte[] getPedalAssign(ExGuiPreset preset, byte[] data) {
        data[30] = Constants.pedalassignValues[preset.getPedalAssignValue().getAssign()];
        return data;
    }

    private static int getPedalRangeMin(ExPreset preset) {
//        return byte2int(new byte[]{preset.getData()[31], preset.getData()[32]});        // Note 2 bytes
        return preset.getData()[31];
    }

    private static int getPedalRangeMax(ExPreset preset) {
//        return byte2int(new byte[]{preset.getData()[33], preset.getData()[34]});         // Note 2 bytes
        return preset.getData()[34];
    }

    private static byte[] getPedalRangeValues(ExGuiPreset preset, byte[] data) {
        byte[] bytes = int2bytes((int)preset.getPedalAssignValue().getFRangeMin());
        data[31] = bytes[1];
        data[32] = bytes[0];
        bytes = int2bytes((int)preset.getPedalAssignValue().getFRangeMax());
        data[33] = bytes[0];
        data[34] = bytes[1];
        return data;
    }


    public static byte[] encodeToMidi(ExGuiPreset preset) {

        byte[] data = new byte[37];
        data[0] = 0x00;
        short b1 = 0x00;
        if (preset.getPedal1Value().isOn()) b1 |= BIN_PEDAL1_ON;
        if (preset.getPedal2Value().isOn()) b1 |= BIN_PEDAL2_ON;
        if (preset.getAmpSapValue().isOn()) b1 |= BIN_AMP_SA;
        if (preset.isCabOn()) b1 |= BIN_CABINET_ON;
        if (preset.getModulationValue().isOn()) b1 |= BIN_MODULATION;
        if (preset.getDelayValue().isOn()) b1 |= BIN_DELAY;
        if (preset.getReverbValue().isOn()) b1 |= BIN_REVERB;
        data[1] = (byte) b1;
//        logger.debug("Bit mask on/off: " + Integer.toHexString(b1) + ", " + Integer.toHexString(data[1]) + ", HJexStr: " + HexUtil.toHexString(data));
        data = getPedal1Values(preset, data);
        data = getPedal2Values(preset, data);
        data = getAmpValues(preset, data);
        data = getGain(preset, data);
        data[8] = 0; //Undocumented
        data = getVolume(preset, data);
        data = getTreble(preset, data);
        data = getMiddle(preset, data);
        data = getBass(preset, data);
        data = getPresence(preset, data);
        data = getNoiseReduction(preset, data);
        data = getCabinet(preset, data);
        data = getReverbValues(preset, data);
        data = getModulation(preset, data);
        data = getDelayValues(preset, data);
        data = getPedalAssign(preset, data);
        data = getPedalRangeValues(preset, data);
        data[35] = 0x00;
        data[36] = 0x00;
        return data;
    }


    private static boolean isEnabled(int value, int binFeature) {
        return (value & binFeature) > 0;
    }

    public static int byte2int(byte[] bytes) {
        int result = 0;
        if (bytes.length == 2) {
            result = bytes[0] * 256 + bytes[1];
        }
        return result;
    }

    private static byte[] int2bytes(int val) {
        byte[] bytes = BigInteger.valueOf(val).toByteArray();
        if (bytes.length > 2) return new byte[]{bytes[bytes.length - 2], bytes[bytes.length - 1]};
        if (bytes.length < 2) return new byte[]{(byte) 0, bytes[0]};
        else return bytes;
    }

    private static byte[] int2Shiftedbytes(int val) {
        byte[] bytes = BigInteger.valueOf(val).toByteArray();
        if (bytes.length > 2) return new byte[]{bytes[bytes.length - 1], bytes[bytes.length - 2]};
        if (bytes.length < 2) return new byte[]{(byte) 0, bytes[0]};
        else return bytes;
    }
}

