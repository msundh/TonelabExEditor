package nu.mikaelsundh.tonelabex.editor.utils;

import nu.mikaelsundh.tonelabex.editor.midi.MidiConstants;
import nu.mikaelsundh.tonelabex.editor.model.*;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * User: Mikael Sundh
 * Date: 2012-11-11
 */
public class PresetParserTest {

    @Test
    public void testParseProgramDump1() {
        String data = "0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertFalse("Test Pedal 1 off", PresetParser.isPedal1Enabled(ex));
        assertTrue("Test Pedal 2 on", PresetParser.isPedal2Enabled(ex));
        assertTrue("Test Amp on", PresetParser.isAmpEnabled(ex));
        assertFalse("Test Cab off", PresetParser.isCabEnabled(ex));
        assertFalse("Test MOD off", PresetParser.isModEnabled(ex));
        assertFalse("Test Delay off", PresetParser.isDelayEnabled(ex));
        assertFalse("Test Reverb off", PresetParser.isReverbEnabled(ex));
        Pedal1Value ped1 = PresetParser.getPedal1Value(ex);
        assertEquals("Test Pedal 1 type", 2,ped1.getType()); // TONE
        assertEquals("Test Pedal 1 effect", 13, ped1.getValue());
        Pedal2Value ped2 = PresetParser.getPedal2Value(ex);
        assertEquals("Test Pedal 2 type", 8, ped2.getType()); // Gold Drive
        assertEquals("Test Pedal 2 effect", 18,ped2.getValue());
        AmpSapValue ampSapValue = PresetParser.getAmp(ex);
        assertEquals("Test AMP type", 0, ampSapValue.getType()); // STD
        assertEquals("Test AMP", 6, ampSapValue.getModel());   //UK ROCK      (hex 0 -2B, dec 0-43)  (8)
        assertEquals("Test Gain", 42, PresetParser.getGain(ex));   //(8)
        assertEquals("Test Volume", 78, PresetParser.getVolume(ex)); // (0)
        assertEquals("Test Treble", 8, PresetParser.getTreble(ex));   //(78)
        assertEquals("Test Middle",80,  PresetParser.getMiddle(ex));    //(8)
        assertEquals("Test Bass", 100, PresetParser.getBass(ex));        //(80)
        assertEquals("Test Presence",17,  PresetParser.getPresence(ex));  // (100)
        assertEquals("Test NR",60,  PresetParser.getNoiseReduction(ex));  //
        ModulationValue modVal = PresetParser.getModulation(ex);
        assertNotNull(modVal);
        assertEquals("Mod type",3,  modVal.getType());    //ORG PHASE
        assertEquals("Mod Depth(Par 1)", 68, modVal.getResonance()); //Depth
        assertEquals("Speed (Mod Par 3)", 3.85, modVal.getSpeed(), 0.0); // Speed   (3.85Hz)
        assertEquals("Delay type",1,PresetParser.getDelayType(ex));   // 8
        assertEquals("Delay Level",8,PresetParser.getDelayLevel(ex));     // 1
        assertEquals("Delay Feedback",38,PresetParser.getDelayFeedback(ex));  // 8
        assertEquals("Delay Time", 700, PresetParser.getDelayTime(ex));    // 9788   (0x263C)

        assertEquals("Expr Target", 5, PresetParser.getPedalAssignValue(ex).getAssign());
//        System.out.println("Expr Range min " + PresetParser.getPedalRangeMin(ex));
//        System.out.println("Expr Range max " + PresetParser.getPedalRangeMax(ex));
    }

    @Test
    public void testParseProgramDump2() {
        String str = "0057000A060B164B00414F2938640F060001140A35360100000114203C0010000000640000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertTrue("Test Pedal 1 on", PresetParser.isPedal1Enabled(data));
        assertTrue("Test Pedal 2 on", PresetParser.isPedal2Enabled(data));
        assertTrue("Test Amp on", PresetParser.isAmpEnabled(data));
        assertFalse("Test Cab off", PresetParser.isCabEnabled(data));
        assertTrue("Test MOD on", PresetParser.isModEnabled(data));
        assertFalse("Test Delay off", PresetParser.isDelayEnabled(data));
        assertTrue("Test Reverb on", PresetParser.isReverbEnabled(data));
        Pedal1Value ped1 = PresetParser.getPedal1Value(data);
        assertEquals("Test Pedal 1 type", 0, ped1.getType()); //
        assertEquals("Test Pedal 1 effect", 10, ped1.getValue());
        Pedal2Value ped2 = PresetParser.getPedal2Value(data);
        assertEquals("Test Pedal 2 type", 6, ped2.getType()); // tube od
        assertEquals("Test Pedal 2 effect", 11 ,ped2.getValue());
        AmpSapValue ampSapValue = PresetParser.getAmp(data);
        assertEquals("Test AMP type", 2, ampSapValue.getType()); // CST
        assertEquals("Test AMP", 0, ampSapValue.getModel());   //CLEAN      (hex 0 -2B, dec 0-43)  (8)
        assertEquals("Test Gain", 75, PresetParser.getGain(data));   //(8)
        assertEquals("Test Volume", 65, PresetParser.getVolume(data)); // (0)
        assertEquals("Test Treble", 79, PresetParser.getTreble(data));   //(78)
        assertEquals("Test Middle",41,  PresetParser.getMiddle(data));    //(8)
        assertEquals("Test Bass", 56, PresetParser.getBass(data));        //(80)
        assertEquals("Test Presence",100,  PresetParser.getPresence(data));
        assertEquals("Test NR",30,  PresetParser.getNoiseReduction(data));
        ModulationValue mod = PresetParser.getModulation(data);
        assertNotNull(mod);
        assertEquals("Mod type",10,  mod.getType());   // TALK MODE
        assertEquals("Mod Par 1", 53, mod.getSens());  //Depth
        assertEquals("Mod Par 2", 54, mod.getResonance()); // Reso
        assertEquals("Mod Par 3", Constants.talkModNames[1], Constants.talkModNames[mod.getTalkType()]); // Speed: TYPE2  (val:256)
//        ex.setExprTarget(PresetParser.getExpressionTarget(data));
        assertEquals("Expr Target", 5, PresetParser.getPedalAssignValue(data).getAssign());  // 0?
//        assertEquals("Expr Target", "Mod Depth", ex.getExprTargetName());
//        System.out.println("Expr Range min " + PresetParser.getExpressionRangeMin(data));
//        System.out.println("Expr Range max " + PresetParser.getExpressionRangeMax(data));
    }
    @Test
    public void testParseProgramDump3() {
        String str = "007D0300090907110036324450402D01200013000500500708000A276E0201000000640000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ModulationValue val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertTrue("Test MOD on", val.isOn());
        assertEquals("Test Mod type",0,  val.getType());   // CE CHORUS
        assertEquals("Test Mod Par 1",5, val.getDepth());  //Depth
        assertEquals("Test Mod Par 2",0.5,  val.getSpeed(),0.0); // Speed: 0.50Hz  (val:256)
        //assertEquals("Test Mod Par 2",0.5,  val.getSpeed()); // Speed: 0.50Hz  (val:256)

        str = "00 7D 02 06 05 46 07 38 00 3C 52 44 32 4E 0F 0A 00 02 17 07 49 46 00 00 00 00 18 34 26 02 14 00 00 00 64 00 00".replace(" ","").toLowerCase();
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertTrue("Test MOD on", val.isOn());
        assertEquals("Test Mod type",7,  val.getType());   // Slow attack
        assertEquals("Test Mod Par 1",73, val.getAttack());

        str = "00 77 02 12 04 00 00 27 00 52 00 64 00 00 0F 03 00 02 28 07 64 46 00 00 00 02 1E 39 22 01 08 00 00 00 14 00 00".replace(" ","").toLowerCase();
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertTrue("Test MOD on", val.isOn());
        assertEquals("Test Mod type",7,  val.getType());   // Slow attack
        assertEquals("Test Mod Par 1",100, val.getAttack());
    }
    @Test
    public void testParseProgramDump4() {
        String str = "0055031E04000C1700553D3649120F030000080925380000000213043C0008000000640000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ModulationValue val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertTrue("Test MOD on", val.isOn());
        assertEquals("Test Mod type",9,   val.getType());   // FILTRON,
        assertEquals("Test Mod Par 1",37,  val.getSens());  //Depth
        assertEquals("Test Mod Par 2",56,  val.getResonance()); // Reso
        assertEquals("Mod par 3",Constants.filtronNames[0],Constants.filtronNames[val.getFiltronType()]); // Speed: UP  (val:20487)
    }
    @Test
    public void testParseProgramDump5() {
        String str = "005D02110164191100593664351D1906000015081700190000000B3B72010C0A00001D0000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ModulationValue val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertTrue("Test MOD on", val.isOn());
        assertEquals("Test Mod type",8,   val.getType());   // PITCH,
        assertEquals("Test Mod Par 1",23,  val.getBalance());  //Balance
        assertEquals("Mod par 3","+12",Constants.pitchNames[val.getPitch()]);  // Speed: +12  (val:6400)
    }

    @Test
    public void testParseProgramDump6() {
        String str = "001d0311050b113c004b204816001e01200000000c00290108010e2e1001080000000b0000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        AmpSapValue amp = PresetParser.getAmp(data);
        assertTrue("Test Amp on", amp.isOn());
        assertEquals("Test AMP type", 1,amp.getType()); // SPC
        assertEquals("Test AMP", 6, amp.getModel());   //CLEAN      17, UK Rock
        ModulationValue val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertTrue("Test MOD on", val.isOn());
        assertEquals("Test Mod type",0,  val.getType());   // CE CHORUS
        assertEquals("Test Mod Par 1",12, val.getDepth());  //Depth
        assertEquals("Test Mod Par 2",2.35,  val.getSpeed(), 0.0); // Speed:
    }

    @Test
    public void testPitch() {
        String str = "005C03140819083C00553C320B382300000116083200010008020B237A0001000000640000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ModulationValue val = PresetParser.getModulation(data);
        assertNotNull(val);
        assertEquals("Test Mod type",8,  val.getType());   // PITCH
        assertEquals("Test Mod Par 1",50,  val.getBalance());  //Balance
        assertEquals("Test Pitch name","-11",Constants.pitchNames[val.getPitch()]);

        str = "005C03140819083C00553C320B382300000116083200020008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","-10",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B382300000116083200060008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","-6",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B3823000001160832000A0008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","-2",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B3823000001160832000C0008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","0",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B3823000001160832000D0008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","detuned",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B382300000116083200100008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","+3",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B382300000116083200150008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","+8",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B382300000116083200170008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","+10",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);

        str = "005C03140819083C00553C320B382300000116083200190008020B237A0001000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Test Pitch name","+12",Constants.pitchNames[PresetParser.getModulation(data).getPitch()]);
    }

    @Test
    public void testModSpeedOrgPhase() {
        String data = "005C03140819083C00553C320B382300000116033F00102708020B237A0001000000640000";
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Test Mod par 3 0.10Hz: ",0.1, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data = "005C03140819083C00553C320B382300000116033F00420008020B237A0001000000640000";
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Test Mod par 3 15.0Hz: ",15.0, PresetParser.getModulation(ex).getSpeed(), 0.0);

    }

    @Test
    public void testDelay() {
        String str = "0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        DelayValue val = new DelayValue(data);
        assertEquals("Delay type ",1, val.getType());
        assertEquals("Delay level",8, val.getLevel());
        assertEquals("Delay FBack",38, val.getFeedback());
        assertEquals("Delay time(700ms)",700, val.getTime());
        str = "0057000A060B164B00414F2938640F060001140A35360100000114203C0010000000640000";
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Delay type(1)",1, val.getType());
        val = new DelayValue(data);
        assertEquals("Delay level",20, PresetParser.getDelayLevel(data));
        assertEquals("Delay FBack",32, val.getFeedback());
        assertEquals("Delay time",60, PresetParser.getDelayTime(data));

        str = "007E000F04001719004A2B4B3B24140A2000110300002C0308010B3D500212100027640000"; // OK
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        val = new DelayValue(data);
        assertEquals("Delay type) ",1, val.getType()); // Tape Echo
        assertEquals("Delay level",11, val.getLevel());
        assertEquals("Delay FBack",61, val.getFeedback()); //6.1
        assertEquals("Delay time",720, PresetParser.getDelayTime(data));
    }


    @Test
    public void testModSpeed() {
        // Preset 18-4 Mod type Multi chorus
        String data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 00 00 10 01 31 00 10 27 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace("","");
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 0.1, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 00 00 10 01 31 00 75 24 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 0.11, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 31 00 12 16 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 0.17, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 31 00 16 0C 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 0.31, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 23 00 02 06 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 0.60, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 23 00 5D 02 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 1.36, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 31 00 74 01 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 2.00, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 23 00 10 01 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 2.50, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 00 00 10 01 23 00 21 01 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 3.46, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 20 00 10 01 23 00 13 00 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 6.80, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 00 00 10 01 23 00 50 00 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 12.50, PresetParser.getModulation(ex).getSpeed(), 0.0);

        data="00 75 03 0E 02 32 2A 36 00 3C 44 37 46 1C 32 08 00 00 10 01 23 00 42 00 00 00 09 40 2C 01 0C 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertEquals("Mod speed", 15.00, PresetParser.getModulation(ex).getSpeed(), 0.0);
    }

    @Test
    public void testAmpAmpType() {

        String str = "005C03140819083C00553C320B382300000116083200010008020B237A0001000000640000";  //0x08
        ExPreset data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        AmpSapValue ampSapValue = PresetParser.getAmp(data);
        assertEquals("Amp type",0,  ampSapValue.getType());
        assertEquals("Amp Val",8,  ampSapValue.getModel());

        str = "005C031408190A3C00553C320B382300000116083200010008020B237A0001000000640000";  //0x0A   (10)
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ampSapValue = PresetParser.getAmp(data);
        assertEquals("Amp type",0,  ampSapValue.getType());
        assertEquals("Amp Val",10,  ampSapValue.getModel());

        str = "005C031408190B3C00553C320B382300000116083200010008020B237A0001000000640000";  //0x0B   (11)
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ampSapValue = PresetParser.getAmp(data);
        assertEquals("Amp type",1,  ampSapValue.getType());
        assertEquals("Amp Val",0,  ampSapValue.getModel());

        str = "005C03140819163C00553C320B382300000116083200010008020B237A0001000000640000";  //0x16   (22)
        data = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        ampSapValue = PresetParser.getAmp(data);
        assertEquals("Amp type",2,  ampSapValue.getType());
        assertEquals("Amp Val",0,  ampSapValue.getModel());
    }

    @Test
    public void testFrequencyCalc() {
        String data = "0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        ModulationValue modVal = PresetParser.getModulation(ex);
//        System.out.println("Mod type: " + modVal.getType());
        byte[] param3 =new byte[]{ex.getData()[22],ex.getData()[23]};
        byte offset = ex.getData()[16];
//        System.out.println("Param 3: " + Integer.toHexString(param3[0]) + Integer.toHexString(param3[1]));
//        System.out.println("Offset1: " + Integer.toHexString(offset));
        assertEquals("Speed (Mod Par 3)", 3.85, modVal.getSpeed(), 0.0); // Speed
        ExGuiPreset preset = new ExGuiPreset(ex);
        byte[] newData = new byte[ex.getData().length];
        newData = PresetParser.getModulation(preset,newData);
        byte[] newParam3 =new byte[]{newData[22],newData[23]};
        byte newOffset = newData[16];
//        System.out.println("Param 3: " + Integer.toHexString(newParam3[0]) + Integer.toHexString(newParam3[1]));
        assertEquals("Speed (Mod Par 3)", Integer.toHexString(param3[0]) + Integer.toHexString(param3[1]), Integer.toHexString(newParam3[0]) + Integer.toHexString(newParam3[1])); // Speed
        assertEquals("Offset", Integer.toHexString(offset),Integer.toHexString(newOffset));
    }


//    @Test
//    public void testFrequencyCalc1() throws Exception {
//        String data = "001d0311050b113c004b204816001e01200000000c00290108010e2e1001080000000b0000";
//        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
//        int byte1 = ex.getData()[22];
//        int byte2 = ex.getData()[23];
//        int offset = ex.getData()[16];
//        double result;
//        if (offset == 0) {
//            System.out.println("Int value: " + (double) (byte2*256+byte1) + ", 0x" + Integer.toHexString(byte1) +  Integer.toHexString(byte2));
//            result = (Math.floor(100 * 1000 / (double) (byte2*256+byte1) + 0.5d)/100);
//            System.out.println("GUI Result: " + result) ;
//        } else {
//            System.out.println("Int value: " + (double) (byte2*256+byte1+128) + ", 0x" + Integer.toHexString(byte1) +  Integer.toHexString(byte2));
//            result = ( Math.floor(100 * 1000 / (double) (byte2*256+byte1+128) + 0.5d)/100);
//            System.out.println("GUI Result: " + result) ;
//        }
//        int newResult = (int)( Math.floor(1000 / result-0.5d));
//        System.out.println("Result 1: " + newResult + " (0x" + Integer.toHexString(newResult)+")") ;
//        byte1 = newResult%256;
//        byte2 = newResult/256;
//        offset = 0;
//        System.out.println("byte 1: " + byte1);
//        if (byte1 > 127) {
//            offset = 0x20;
//            byte1=byte1-128;
//        }
//        System.out.println("New MIDI Result. Offset: " + offset +", 0x" + Integer.toHexString(byte1) +  Integer.toHexString(byte2));
//    }

    @Test
    public void testExPresetGuiConvert() {
        testConvertExToGuiToEx( "0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000");
        testConvertExToGuiToEx( "0057000A060B164B00414F2938640F060001140A35360100000114203C0010000000640000".toLowerCase());
        testConvertExToGuiToEx("007D0300090907110036324450402D01200013000500500708000A276E0201000000640000".toLowerCase());
        testConvertExToGuiToEx( "0055031E04000C1700553D3649120F030000080925380000000213043C0008000000640000".toLowerCase());
//        testConvertExToGuiToEx( "005D02110164191100593664351D1906000015081700190000000B3B72010C0A00001D0000".toLowerCase()); //TODO VERIFY
        testConvertExToGuiToEx( "001d0311050b113c004b204816001e01200000000c00290108010e2e1001080000000b0000");
        testConvertExToGuiToEx( "005C03140819083C00553C320B382300000116083200010008020B237A0001000000640000".toLowerCase());
        testConvertExToGuiToEx( "005C03140819083C00553C320B382300000116033F00102708020B237A0001000000640000".toLowerCase());
        testConvertExToGuiToEx("0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000");

        testConvertExToGuiToEx("00 7D 02 06 05 46 07 38 00 3C 52 44 32 4E 0F 0A 00 02 17 07 49 46 00 00 00 00 18 34 26 02 14 00 00 00 64 00 00".replace(" ","").toLowerCase());
        testConvertExToGuiToEx("00 77 02 12 04 00 00 27 00 52 00 64 00 00 0F 03 00 02 28 07 64 46 00 00 00 02 1E 39 22 01 08 00 00 00 14 00 00".replace(" ","").toLowerCase());
    }
    private String testConvertExToGuiToEx(String data) {
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        ExGuiPreset gex = new ExGuiPreset(ex);
        ExPreset newEx = new ExPreset(gex);
        for (int i=0;i<newEx.getData().length;i++) {
            if (i==22) {
                assertTrue("Mod param3",(ex.getData()[i]-newEx.getData()[i])<2);// Calc of speed/Freq not exact
            } else {
                assertEquals("byte["+i+"]",ex.getData()[i],newEx.getData()[i]);
            }

        }
//        System.out.println("Mod type: " + gex.getModulationValue().getType());
        assertEquals("Test Pedal 1 off", gex.getPedal1Value().isOn(), PresetParser.isPedal1Enabled(newEx));
        assertEquals("Test Pedal 2 on",  gex.getPedal2Value().isOn(), PresetParser.isPedal2Enabled(newEx));
        assertEquals("Test Amp on", gex.getAmpSapValue().isOn(), PresetParser.isAmpEnabled(newEx));
        assertEquals("Test Cab off", gex.isCabOn(), PresetParser.isCabEnabled(newEx));
        assertEquals("Test MOD off", gex.getModulationValue().isOn(), PresetParser.isModEnabled(newEx));
        assertEquals("Test Delay off", gex.getDelayValue().isOn(), PresetParser.isDelayEnabled(newEx));
        assertEquals("Test Reverb off", gex.getReverbValue().isOn(), PresetParser.isReverbEnabled(newEx));
        Pedal1Value ped1 = PresetParser.getPedal1Value(newEx);
        assertEquals("Test Pedal 1 type",  gex.getPedal1Value().getType(),ped1.getType()); // TONE
        assertEquals("Test Pedal 1 effect",  gex.getPedal1Value().getValue(), ped1.getValue());
        Pedal2Value ped2 = PresetParser.getPedal2Value(newEx);
        assertEquals("Test Pedal 2 type", gex.getPedal2Value().getType(), ped2.getType()); // Gold Drive
        assertEquals("Test Pedal 2 effect", gex.getPedal2Value().getValue(),ped2.getValue());
        AmpSapValue ampSapValue = PresetParser.getAmp(newEx);
        assertEquals("Test AMP type", gex.getAmpSapValue().getType(), ampSapValue.getType()); // STD
        assertEquals("Test AMP", gex.getAmpSapValue().getModel(), ampSapValue.getModel());   //UK ROCK      (hex 0 -2B, dec 0-43)  (8)
        assertEquals("Test Gain", gex.getGainValue(), PresetParser.getGain(newEx));   //(8)
        assertEquals("Test Volume", gex.getVolumeValue(), PresetParser.getVolume(newEx)); // (0)
        assertEquals("Test Treble", gex.getTrebleValue(), PresetParser.getTreble(newEx));   //(78)
        assertEquals("Test Middle",gex.getMiddleValue(),  PresetParser.getMiddle(newEx));    //(8)
        assertEquals("Test Bass", gex.getBassValue(), PresetParser.getBass(newEx));        //(80)
        assertEquals("Test Presence",gex.getPresenceValue(),  PresetParser.getPresence(newEx));  // (100)
        assertEquals("Test NR",gex.getNoiseValue(),  PresetParser.getNoiseReduction(newEx));
        ModulationValue modVal = PresetParser.getModulation(newEx);
        assertEquals("Mod type",gex.getModulationValue().getType(),  modVal.getType(), 0.0);    //ORG PHASE
        assertEquals("Mod Res", gex.getModulationValue().getResonance(), modVal.getResonance(), 0.0);
        assertEquals("Mod speed", gex.getModulationValue().getSpeed(), modVal.getSpeed(), 0.0);
        assertEquals("Mod attack", gex.getModulationValue().getAttack(), modVal.getAttack());
        assertEquals("Mod Sens", gex.getModulationValue().getSens(), modVal.getSens());
        assertEquals("Mod Balance", gex.getModulationValue().getBalance(), modVal.getBalance());
        assertEquals("Mod Filtron", gex.getModulationValue().getFiltronType(), modVal.getFiltronType());
        assertEquals("Mod Pitch", gex.getModulationValue().getPitch(), modVal.getPitch());
        assertEquals("Mod Talk", gex.getModulationValue().getTalkType(), modVal.getTalkType());

        assertEquals("Delay type",gex.getDelayValue().getType(),PresetParser.getDelayType(newEx));   // 8
        assertEquals("Delay Level",gex.getDelayValue().getLevel(),PresetParser.getDelayLevel(newEx));     // 1
        assertEquals("Delay Feedback",gex.getDelayValue().getFeedback(),PresetParser.getDelayFeedback(newEx));  // 8
        assertEquals("Delay Time", gex.getDelayValue().getTime(), PresetParser.getDelayTime(newEx));    // 9788   (0x263C)
        assertEquals("Expr Target", gex.getPedalAssignValue().getAssign(), PresetParser.getPedalAssignValue(newEx).getAssign());
        assertEquals("Expr Min", gex.getPedalAssignValue().getFRangeMin(), PresetParser.getPedalAssignValue(newEx).getFRangeMin(), 0.0);
        assertEquals("Expr Max", gex.getPedalAssignValue().getFRangeMax(), PresetParser.getPedalAssignValue(newEx).getFRangeMax(), 0.0);
        return HexUtil.toHexString(newEx.getData());
    }
    //TODO: IMPLEMENT SPEED
    @Test
    public void testPedalAssign() {
        // preset 4 mod speed, G4 3.3, - , 0.8
//        String str = "00 5E 00 0F 04 00 0C 21 00 50 29 54 43 5C 1E 0A 20 00 05 06 21 00 62 04 40 02 0B 39 52 03 12 62 00 04 64 00 00".replace(" ","");
//        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
//        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
//        ModulationValue mod = PresetParser.getModulation(ex);
//        System.out.println("Mod Speed G4: Depth: "+mod.getDepth() +", speed: " + mod.getSpeed() + ", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
//        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
//        assertEquals("Expr Min", 25088, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 1124, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 5 Mod speed - Org phase 0, - , 3.46
//        str = "00 7D 00 0A 06 28 16 46 00 41 45 3E 19 55 19 04 00 01 0B 03 00 00 21 01 00 00 03 33 64 00 12 1D 00 09 21 01 00".replace(" ","");
//        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
//        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
//        mod = PresetParser.getModulation(ex);
//        System.out.println("Mod Speed: Org: Depth: "+mod.getDepth() +", speed: " + mod.getSpeed() + ", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
//        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
//        assertEquals("Expr Min", 7424,(int) PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 2337, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 9 Mod Depth - Twin 4.1, - , 8.7
        String str = "00 5D 00 09 04 00 00 39 00 32 48 41 53 64 18 0A 00 00 19 04 29 00 73 00 00 01 0D 27 04 01 10 00 00 00 64 00 00".replace(" ","");
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",5, PresetParser.getPedalAssignValue(ex).getAssign());
        ModulationValue mod = PresetParser.getModulation(ex);
        System.out.println("Mod Depth: Twin Depth: "+mod.getDepth() +", speed: " + mod.getSpeed() + ", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 100,(int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset14 Rev inp - Hall 11
        str = "00 4E 03 14 0A 26 06 61 00 42 1F 64 64 06 28 08 00 02 0B 02 0B 00 10 27 08 00 0D 37 66 00 18 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",10, PresetParser.getPedalAssignValue(ex).getAssign());
        ReverbValue rev = PresetParser.getReverbValue(ex);
        System.out.println("Reverb val: "+rev.getValue());
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 100, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 19 Volume - 75
        str = "00 4D 02 13 08 27 00 50 00 4B 3A 47 47 19 0F 04 00 00 0B 04 18 00 04 01 08 01 08 2B 3C 02 01 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",1, PresetParser.getPedalAssignValue(ex).getAssign());

        System.out.println("Vol: "+PresetParser.getVolume(ex));
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 100, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 37 Mod Talk - Talk 5.3,5.4,T2
        str = "00 57 00 0A 06 0B 16 4B 00 41 4F 29 38 64 0F 06 00 01 14 0A 35 36 01 00 00 01 14 20 3C 00 10 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",5, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod Talk Sens: "+mod.getSens() +", type: " + mod.getTalkType() + ", reso: " + mod.getResonance()+", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 100, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 39 P2 - Vox 0
        str = "00 4E 03 06 00 00 05 3A 00 3D 36 50 64 11 19 02 20 01 09 00 46 00 50 07 08 01 0D 2A 74 01 08 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",3, PresetParser.getPedalAssignValue(ex).getAssign());
        Pedal2Value p2 = PresetParser.getPedal2Value(ex);
        System.out.println("Pedal 2 val: "+p2.getValue());
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 100, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 95 Mod Pitch - Pitch 10,-,0
        str = "00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 08 64 00 0C 00 00 03 07 2E 1C 02 12 0C 00 00 19 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Pitch Bal: "+mod.getBalance() +", pitch: " + mod.getPitch() + ", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 12, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 25, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 98 Mod Balance - Pitch, 2.7,-,+7
        str = "00 56 00 10 04 00 00 2D 00 37 58 38 3A 37 19 02 00 02 1F 08 1B 00 14 00 00 02 0F 32 22 01 10 00 00 00 2D 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",5, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod Balance: Pitch Bal: "+mod.getBalance() +", pitch: " + mod.getPitch() + ", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 45, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 99 Mod Freq - Filtron, 7,4.4,up
        str = "00 77 00 0A 02 24 16 50 00 55 32 10 30 55 23 03 00 01 07 09 46 2C 00 00 00 02 08 2D 37 01 10 1D 00 00 5A 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",5, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod Freq: Filtron: Sens: "+mod.getSens() +", type: " + mod.getFiltronType() + ", reso: " + mod.getResonance()+", " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 29, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 90, (int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // preset 70 P1 - Pedal1 Vox 30
        str = "00 6D 01 1E 08 00 1B 4B 00 46 4A 2C 59 1C 2D 07 00 01 0A 08 32 00 12 00 00 01 06 00 5F 00 04 00 00 00 1E 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",2, PresetParser.getPedalAssignValue(ex).getAssign());
        Pedal1Value p1 = PresetParser.getPedal1Value(ex);
        System.out.println("Pedal 1 level: "+p1.getValue());
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 30,(int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        /*  Min/Max values

        Pedal 1:          F0 42 30 00 01 10 40 00 77 02 17 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 08 64 00 0C 00 00 03 07 2E 1C 02 04 00 00 00 1E 00 00 F7
        Pedal 2:          F0 42 30 00 01 10 40 00 77 02 17 07 4B 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 08 64 00 0C 00 00 03 07 2E 1C 02 08 00 00 00 64 00 00 F7

       Mod CE Speed:      F0 42 30 00 01 10 40 00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 00 2B 00 42 00 00 03 07 2E 1C 02 12 10 00 00 42 00 00 F7
       Mod Vibrato Speed :F0 42 30 00 01 10 40 00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 05 32 00 57 00 40 03 07 2E 1C 02 12 68 00 00 21 00 00 F7
       Mod G4 Speed:      F0 42 30 00 01 10 40 00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 06 46 00 00 01 40 03 07 2E 1C 02 12 62 00 00 42 00 00 F7
       Mod Pitch :        F0 42 30 00 01 10 40 00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 08 32 00 10 00 00 03 07 2E 1C 02 12 00 00 00 19 00 00 F7
       Mod Filtron:       F0 42 30 00 01 10 40 00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 09 32 43 00 00 00 03 07 2E 1C 02 11 00 00 00 64 00 00 F7
       Mod Talk:          F0 42 30 00 01 10 40 00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 0A 32 39 00 00 00 03 07 2E 1C 02 11 00 00 00 64 00 00 F7

         */

    }

    @Test
    public void testPedalAssign2() {
        String  str = "00 77 02 17 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 08 64 00 0C 00 00 03 07 2E 1C 02 04 00 00 00 1E 00 00".replace(" ","");
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",2, PresetParser.getPedalAssignValue(ex).getAssign());
        Pedal1Value p1 = PresetParser.getPedal1Value(ex);
        System.out.println("Pedal 1 level: "+p1.getValue());
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 30,(int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        str = "00 77 02 17 07 4B 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 08 64 00 0C 00 00 03 07 2E 1C 02 08 00 00 00 64 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",3, PresetParser.getPedalAssignValue(ex).getAssign());
        Pedal2Value p2 = PresetParser.getPedal2Value(ex);
        System.out.println("Pedal 1 level: "+p1.getValue());
        System.out.println("Min/Max : " + Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMin())+"/"+Integer.toHexString((int)PresetParser.getPedalAssignValue(ex).getFRangeMax()));
        assertEquals("Expr Min", 0, (int)PresetParser.getPedalAssignValue(ex).getFRangeMin());
        assertEquals("Expr Max", 100,(int)PresetParser.getPedalAssignValue(ex).getFRangeMax());

        // Mod CE Speed
        str = "00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 00 2B 00 42 00 00 03 07 2E 1C 02 12 10 00 00 42 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        ModulationValue mod = PresetParser.getModulation(ex);
        System.out.println("Mod CE Speed (0.1-15Hz): "+ Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());
        // Mod Vibrato Speed
        str = "00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 05 32 00 57 00 40 03 07 2E 1C 02 12 68 00 00 21 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod Vibrato Speed (1-30): " + Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());
        str = "00 77 00 14 03 47 26 13 00 32 32 3D 31 00 2E 03 00 01 0B 05 32 00 57 00 40 03 07 2E 1C 02 12 35 00 00 42 02 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod Vibrato Speed (1-30,75%/25%): " + "Mod speed: " + mod.getSpeed()+", " +Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());

        str = "00 5E 00 0F 06 45 02 2C 00 4C 3C 64 5A 0E 14 02 20 01 17 04 46 00 3D 00 48 00 0D 32 48 00 12 68 02 00 3D 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod TWIN Speed (1-15,0%/60%): " + "Mod speed(60%): " + mod.getSpeed()+", " +Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());

        str = "00 5E 00 0F 06 45 02 2C 00 4C 3C 64 5A 0E 14 02 00 01 17 04 46 00 6E 02 48 00 0D 32 48 00 12 68 02 00 3D 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod TWIN Speed (1-15,0%/60%): " + "Mod speed(25%): " + mod.getSpeed()+", " +Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());

        str = "00 5E 00 0F 06 45 02 2C 00 4C 3C 64 5A 0E 14 02 00 01 17 04 46 00 6E 02 48 00 0D 32 48 00 12 68 00 00 42 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod TWIN Speed (1-15,0%/100%): " + "Mod speed(25%): " + mod.getSpeed()+", " +Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());


        str = "00 5E 00 0F 06 45 02 2C 00 4C 3C 64 5A 0E 14 02 20 01 17 04 46 00 74 00 08 00 0D 32 48 00 12 4D 02 00 3D 00 00".replace(" ","");
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  str + "F7");
        assertEquals("Expr Target",7, PresetParser.getPedalAssignValue(ex).getAssign());
        mod = PresetParser.getModulation(ex);
        System.out.println("Mod TWIN Speed (1-15,40%/60%): " + "Mod speed(50%): " + mod.getSpeed()+", " +Integer.toHexString(PresetParser.getModParameter3(ex))+", offset1: " + Integer.toHexString(PresetParser.getModOffset1(ex))+", offset2: " + Integer.toHexString(PresetParser.getModOffset2(ex)));
        System.out.println("Min/Max : " + PresetParser.getPedalAssignValue(ex).getFRangeMin()+"/"+PresetParser.getPedalAssignValue(ex).getFRangeMax());
//        assertEquals("Expr Min", 0, PresetParser.getPedalAssignValue(ex).getFRangeMin());
//        assertEquals("Expr Max", 45, PresetParser.getPedalAssignValue(ex).getFRangeMax());
    }

    @Test
    public void testOnOff() {
        String data = "007f020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ExPreset ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertTrue("Pedal1",PresetParser.isPedal1Enabled(ex));
        assertTrue("Pedal2",PresetParser.isPedal2Enabled(ex));
        assertTrue("AMP",PresetParser.isAmpEnabled(ex));
        assertTrue("Cab",PresetParser.isCabEnabled(ex));
        assertTrue("Mod",PresetParser.isModEnabled(ex));
        assertTrue("Dela",PresetParser.isDelayEnabled(ex));
        assertTrue("Reverb",PresetParser.isReverbEnabled(ex));

        data = "0035020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertTrue("Pedal1",PresetParser.isPedal1Enabled(ex));
        assertFalse("Pedal2", PresetParser.isPedal2Enabled(ex));
        assertTrue("AMP",PresetParser.isAmpEnabled(ex));
        assertFalse("Cab",PresetParser.isCabEnabled(ex));
        assertTrue("Mod",PresetParser.isModEnabled(ex));
        assertTrue("Dela",PresetParser.isDelayEnabled(ex));
        assertFalse("Reverb",PresetParser.isReverbEnabled(ex));

        data = "0029020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertTrue("Pedal1",PresetParser.isPedal1Enabled(ex));
        assertFalse("Pedal2", PresetParser.isPedal2Enabled(ex));
        assertFalse("AMP",PresetParser.isAmpEnabled(ex));
        assertTrue("Cab",PresetParser.isCabEnabled(ex));
        assertFalse("Mod",PresetParser.isModEnabled(ex));
        assertTrue("Dela",PresetParser.isDelayEnabled(ex));
        assertFalse("Reverb",PresetParser.isReverbEnabled(ex));

        data = "0015020d0812062a004e085064111e000000000344000401080108263c0210000000440000";
        ex = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2000" +  data + "F7");
        assertTrue("Pedal1",PresetParser.isPedal1Enabled(ex));
        assertFalse("Pedal2", PresetParser.isPedal2Enabled(ex));
        assertTrue("AMP",PresetParser.isAmpEnabled(ex));
        assertFalse("Cab",PresetParser.isCabEnabled(ex));
        assertTrue("Mod",PresetParser.isModEnabled(ex));
        assertFalse("Dela",PresetParser.isDelayEnabled(ex));
        assertFalse("Reverb",PresetParser.isReverbEnabled(ex));
    }


}
