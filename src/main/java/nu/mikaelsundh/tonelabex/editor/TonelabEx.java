package nu.mikaelsundh.tonelabex.editor;

import nu.mikaelsundh.tonelabex.editor.gui.MainFrame;
import nu.mikaelsundh.tonelabex.editor.midi.MidiController;
//import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-07
 */
public class TonelabEx {
    private static final Logger logger = LogManager.getLogger(TonelabEx.class);

    public static final boolean DEVELOPE_MIDI_ON=true;
    public static final boolean DEVELOPE_TEST_MENU_ON=false;
    public static final boolean DEVELOPE_SCROLL_ON=false;

    MidiController mMc;
    public static void main(String[] args) {
        TonelabEx main = new TonelabEx();
        main.start();
    }


    private void start() {
//        BasicConfigurator.configure();
//        Logger.getRootLogger().setLevel(Level.INFO);
//        PropertyConfigurator.configure("filename.properties");

//        Frame frame = new Frame("Tonelab EX Editor");
        mMc = new MidiController();
        if (!DEVELOPE_MIDI_ON || mMc.connect2Ex()) {
            logger.info("Successfully connected");
            MainFrame mainFrame = new MainFrame(mMc,DEVELOPE_SCROLL_ON);
            try {
                mainFrame.setIconImage(ImageIO.read(getClass().getResource("/vox_ex_logo3.png")));
            } catch (IOException e) {
                logger.warn("Failed to set application icon");
            }
//            mainFrame.initialize(DEVELOPE_SCROLL_ON);
            mainFrame.setVisible(true);
        } else {
            System.exit(1);
        }
    }
}