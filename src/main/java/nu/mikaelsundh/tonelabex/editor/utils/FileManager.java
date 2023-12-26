package nu.mikaelsundh.tonelabex.editor.utils;

import nu.mikaelsundh.tonelabex.editor.midi.MessageBuilder;
import nu.mikaelsundh.tonelabex.editor.model.ExPreset;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class FileManager {
    private static Logger logger = LogManager.getLogger(FileManager.class);

    public static void writePresetToFile(Component parent, ExPreset preset) {
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setDialogTitle("Choose File to save Preset ( Bank "+preset.getBank() + "-" +preset.getNbrInBank()+")");

        int result = jfc.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            if (file != null) {
                String fileName = file.getAbsolutePath();
                logger.debug("Chosen file: " + fileName);
                writePreset(preset,fileName);
            }
        }
    }

    public static ExPreset readPresetFromFile(Component parent) {
        ExPreset preset = null;
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.setDialogTitle("Choose File to read");

        int result = jfc.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            if (file != null) {
                String fileName = file.getAbsolutePath();
                logger.debug("Chosen file: " + fileName);
                preset = readPreset(fileName);
            }
        }
        return preset;
    }
    public static void writePreset(ExPreset preset, String fileName) {
        write(MessageBuilder.buildSendPresetMsg(preset),fileName);
    }
    public static ExPreset readPreset(String fileName) {
        byte[] data = readFile(fileName);
        return new ExPreset(data);
    }
    private static void write(byte[] data, String aOutputFileName){
        logger.debug("write: data len: " + data.length);
        try {
            OutputStream output = null;
            try {
                output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
                output.write(data);
            }
            finally {
                output.close();
            }
        }
        catch(FileNotFoundException e){
            logger.error("File not found.");
        }
        catch(IOException e){
            logger.error("Failed to write to file", e);
        }
    }



    private static byte[] readFile(String fileName){
        logger.debug("Reading in binary file named : " + fileName);
        File file = new File(fileName);
        logger.debug("File size: " + file.length());
        byte[] result = new byte[(int)file.length()];
        try {
            InputStream input = null;
            try {
                int totalBytesRead = 0;
                input = new BufferedInputStream(new FileInputStream(file));
                while(totalBytesRead < result.length){
                    int bytesRemaining = result.length - totalBytesRead;
                    int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
                    if (bytesRead > 0){
                        totalBytesRead = totalBytesRead + bytesRead;
                    }
                }

                logger.debug("Num bytes read: " + totalBytesRead);
            }
            finally {
                logger.debug("Closing input stream.");
                input.close();
            }
        }
        catch (FileNotFoundException ex) {
            logger.error("File not found.");
        }
        catch (IOException ex) {
            logger.error(ex);
        }
        return result;
    }
}
