package nu.mikaelsundh.tonelabex.editor.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-15
 */
public class MidiUtilTest {

    @Test
    public void testMaskMidi() throws Exception {
//        byte[] b1 = new byte[]{0xfffffff0};
//       assertEquals("f0",Integer.toHexString(MidiUtil.maskInData(b1)[0]));
        short b1;
        byte in = 0xfffffff0;
        b1 = (short)(in &0xff);
        byte b2 = (byte)(in & 0xff);
        b2 = (byte)b1;
        short b3 = (short)0xfffffff0;
        System.out.println(" result: " + Integer.toHexString(b1) + ", " + Integer.toHexString(b2)+ ", " + Integer.toHexString(b3));
    }

    @Test
    public void textExtractData() throws Exception {
        byte[] data = HexUtil.hex2byte("f042300001104c204e0004001701642b1e0046484e5608280a000000083e0014000800113564020c000000310000f7");
        System.out.println(HexUtil.toHexString( MidiUtil.extractData(data)));
    }
}
