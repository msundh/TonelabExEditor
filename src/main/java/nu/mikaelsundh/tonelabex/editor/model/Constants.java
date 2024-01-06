package nu.mikaelsundh.tonelabex.editor.model;

import java.awt.*;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-07
 */
public class Constants {
    public static final String[] ampModelNormal = {"Dumble Clean","Fender Brown Pro","Fender Bassman","Fender Twin Reverb","VOX AC15 (1960's)","VOX AC30TB","Marshall JTM45","Marshall JCM900","Bogner Ecstasy","Mesa Boogie Rectifier","Dumble Overdrive Special"};
//    public static final String[] ampModelNormal = {"CLEAN ","CALI CLEAN ","US BLUES ","US 2x12","VOX AC15","VOX AC30","UK ROCK","UK METAL","US HIGAIN","US METAL ","BTQ METAL"};
    public static final String[] ampModelSpecial = {"Roland JC120","Fender 57 Tweed Twin","Bruno Cowtipper","Valvetech Hayseed","1950's AC15","VOX AC30HH","Marshall JCM800","Marshall JVM410","Soldano Slo","VHT Pittbull","Diezel VH4"};
//    public static final String[] ampModelSpecial = {"CLEAN (Roland JC120)","CALI CLEAN (Fender 57 Tweed Twin)","US BLUES (Bruno Cowtipper)","US 2x12 (Valvetech Hayseed)","VOX AC15 (1950's AC15 (EF86))","VOX AC30 (AC30HH)","UK ROCK (Marshall JCM800)","UK METAL(Marshall JVM410)","US HIGAIN (Soldano Slo)","US METAL (VHT Pittbull)","BTQ METAL (Diezel VH4)"};
//    public static final String[] ampModelCustom = {"CLEAN (4Band EQ Pure Clean)","CALI CLEAN (Fender Blackface)","US BLUES (Trainwreck Express)","US 2x12 (Original Crunch)","VOX AC15 (Original BritishRockAMP)","VOX AC30 (AC30BM)","UK ROCK (Marshall 50W Plexi)","UK METAL (Marshall Vintage Modern 2466)","US HIGAIN (Original Hi Gain)","US METAL (Peavey 5150)","BTQ METAL (Original BOUTIQUE Metal)"};
    public static final String[] ampModelCustom = {"4Band EQ Pure Clean","Fender Blackface","Trainwreck Express","Original Crunch","Orig. BritishRockAMP","VOX AC30BM","Marshall 50W Plexi","Marshall Vintage 2466","Original Hi Gain","Peavey 5150","Orig. BOUTIQUE Metal"};
    public static final String[] ampModelSAP ={"Tone drive","Brit lead","Fat dist","Metal dist","Rock planet","Tube DX","Big fuzz","VOX Tone bender","Octa fuzz","Techno fuzz","Crusher"};
    public static final String [] cabNames={"TWEED 1x8","TWEED 1x12","TWEED 4x10", "BLACK 2x10","BLACK 2x12","VOX AC15", "VOX AC30","VOX AD120VTX","UK H30 4x12", "UK T75 4x12","US V30 4x12" };
    public final static String [] pedal2Names={"VOX V845","Brn Octave","Acoustic","Ring Mod","U-Vibe","MXR+","Tube OD","Blue driver","Gold drive","Org Dist","Germanium Fuzz"};
    public final static String[] modNames = {"CE Chorus","Multi chorus","Flanger","Org Phase","Twin Trem","Vibrato","G4 Rotary","Slow attack","Pitch","Filtron","Talk Mod"};
    public final static String[] delayNames = {"Analog delay","Tape echo","SDD Delay","Multi delay"};
    public final static String[] pitchNames = {"-12","-11","-10","-9","-8","-7","-6","-5","-4","-3","-2","-1","0","detuned","+1","+2","+3","+4","+5","+6","+7","+8","+9","+10","+11","+12"};
    public final static String[] expressionNames = {"Off","Volume","Pedal 1","Pedal 2","Gain","Mod Depth","Mod FBack","Mod Speed","Delay Input","Delay Feedback","Reverb Input"};
    public final static String[] filtronNames = {"Up","Down"};
    public final static String[] talkModNames = {"Type 1","Type 2"};
    public final static String [] reverbNames = {"Spring","Room","Hall"};
    public final static String[] pedal1Names = {"Comp","VOX V847","Tone","Energizer"};
    public final static String[] pedalAssignNames = {"Off","Volume","Pedal 1","Pedal 2","Gain","Mod Edit","Mod Tap","Mod Tap+Edit","Delay Input","Delay FeedBack","Reverb"};
    public final static String[] pedalAssignValNames = {"N/A","Volume","Pedal 1","Pedal 2","Gain","Mod Edit","Mod Tap","Mod Tap+Edit","Delay Input","Delay FeedBack","Reverb"};
    public final static Color backgroundColor = new Color(115, 115, 115);
    public final static Color backgroundLightColor = new Color(120, 147, 139);
//    public final static Color backgroundLightColor = new Color(148, 178, 169);
    public final static Color titleColor = new Color(255, 254, 11, 199);
    public final static Color textColor = new Color(255, 254, 35, 123);
    public final static Color frameLineColor = new Color(140, 255, 148, 123);
    public final static Color backgroundDialogColor = new Color(218, 227, 255);
    public final static Color backgroundDialogLightColor = new Color(248, 255, 224);
    public final static Color dialogTextColor = new Color(96, 109, 255);

    public final static Font sliderLabelFont = new Font("Verdana", Font.PLAIN, 12);
    public final static Font sliderLabelSmallFont = new Font("Verdana", Font.PLAIN, 8);
    public final static Font frameLabelFont = new Font("Verdana", Font.BOLD, 14);
    public final static Font sliderFrameLabelFont = new Font("Verdana", Font.PLAIN, 12);

    public static final byte[] pedalassignValues = {0x00,0x01,0x04,0x08,0x0c,0x10,0x11,0x12,0x14,0x15,0x18};
}
