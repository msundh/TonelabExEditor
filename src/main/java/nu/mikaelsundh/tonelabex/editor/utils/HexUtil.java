package nu.mikaelsundh.tonelabex.editor.utils;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class HexUtil {
    public static String toHexString(byte[] data) {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < data.length; ++i)  {
            ret.append(
                    Integer.toHexString(0x0100 + (data[i] & 0x00FF)).substring(1));
        }
        return ret.toString().toLowerCase();
    }
    public static byte[] hex2byte(String hex){
        String strMessage=hex.replaceAll(" ", "").toUpperCase();
        int	nLengthInBytes = strMessage.length() / 2;
        byte[]	abMessage = new byte[nLengthInBytes];
        for (int i = 0; i < nLengthInBytes; i++)
        {
            abMessage[i] = (byte) Integer.parseInt(strMessage.substring(i * 2, i * 2 + 2), 16);
        }
        return abMessage;
    }
}
