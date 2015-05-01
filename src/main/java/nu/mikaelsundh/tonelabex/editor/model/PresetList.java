package nu.mikaelsundh.tonelabex.editor.model;

import nu.mikaelsundh.tonelabex.editor.midi.MessageBuilder;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class PresetList {
    private static SortedMap<Integer,ExPreset> presetMap = new TreeMap<Integer, ExPreset>();

    public static ExPreset getPreset(int nbr) {
        return presetMap.get(nbr);
    }
    public static ExPreset getPreset(int bank, int nbr) {
        return getPreset(MessageBuilder.getPreset(bank, nbr));
    }
    public static void setPreset(int nbr, ExPreset preset) {
        presetMap.put(nbr,preset) ;
    }
    public static void setPreset(int bank,int nbr, ExPreset preset) {
        setPreset(MessageBuilder.getPreset(bank, nbr), preset);
    }
    public static void setPreset( ExPreset preset) {
        setPreset(preset.getNbr(), preset);
    }
    public static SortedMap<Integer,ExPreset> getPresetMap() {
        return presetMap;
    }
}
