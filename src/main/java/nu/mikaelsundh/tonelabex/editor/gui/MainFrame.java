package nu.mikaelsundh.tonelabex.editor.gui;

import com.jidesoft.dialog.BannerPanel;
import nu.mikaelsundh.tonelabex.editor.TonelabEx;
import nu.mikaelsundh.tonelabex.editor.gui.components.SliderFrame;
import nu.mikaelsundh.tonelabex.editor.midi.*;
import nu.mikaelsundh.tonelabex.editor.model.*;
import nu.mikaelsundh.tonelabex.editor.utils.FileManager;
import nu.mikaelsundh.tonelabex.editor.utils.HexUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-04
 */
public class MainFrame extends JFrame implements MenuContainer, Serializable, RootPaneContainer, WindowConstants, DeviceListener {
    private static Logger logger = LogManager.getLogger(MainFrame.class);
    private static final int MAIN_WINDOW_HEIGTH = 850;
    private static final int MAIN_WINDOW_WIDTH = 1445;
    MidiController deviceCtrl;
    GuiListenerHandler guiListener;
    long lastGuiUpdate = 0;
    AmpModeFrame mAmpFrame;
    CabinetFrame mCabFrame;
    SliderFrame mVolFrame;
    SliderFrame mGainFrame;
    SliderFrame mTrebleFrame;
    SliderFrame mMiddleFrame;
    SliderFrame mBassFrame;
    SliderFrame mPresenceFrame;
    SliderFrame mNoiseFrame;
    ReverbFrame mReverbFrame;
    Pedal1Frame mPedal1Frame;
    Pedal2Frame mPedal2Frame;
    DelayFrame mDelayFrame;
    ModulationFrame mModFrame;
    PedalAssignFrame mPedalAsgnFrame;
    JPanel jContentPane;
    JScrollPane jSContentPane;

    JLabel lblStatus;
    boolean receiveData = false;
    boolean mLoadedPreset = false;
    int mDefaultBank = 1;
    int mDdefaultNbr = 1;

    public MainFrame(MidiController ctrl, boolean use_scrollbars) {
        super();
        deviceCtrl = ctrl;
        guiListener = GuiListenerHandler.getInstance();
        initialize(use_scrollbars);
        guiListener.addDeviceListener(this);
    }

    public void initialize(boolean use_scrollbars) {
        this.setTitle("Tonelab EX Editor");
        this.setJMenuBar(getMenu());
        this.setBounds(50, 50, MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGTH);
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                logger.debug("Closing");
                quit();
            }
        });
        jContentPane = new JPanel();
        BannerPanel mainPanel = new BannerPanel();
        mainPanel.setGradientPaint(Constants.backgroundLightColor, Constants.backgroundColor, false);
        mainPanel.setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGTH));
        mainPanel.setLayout(null);

        mAmpFrame = new AmpModeFrame(10, 0, 270, 380);
        mainPanel.add(mAmpFrame);
        mCabFrame = new CabinetFrame(280, 0, 160, 380);
        mainPanel.add(mCabFrame);
        mGainFrame = new SliderFrame(440, 0, 100, 380, "Gain");
        mainPanel.add(mGainFrame);
        mTrebleFrame = new SliderFrame(540, 0, 100, 380, "Treble");
        mainPanel.add(mTrebleFrame);
        mMiddleFrame = new SliderFrame(640, 0, 100, 380, "Middle");
        mainPanel.add(mMiddleFrame);
        mBassFrame = new SliderFrame(740, 0, 100, 380, "Bass");
        mainPanel.add(mBassFrame);
        mPresenceFrame = new SliderFrame(840, 0, 100, 380, "Presence");
        mainPanel.add(mPresenceFrame);
        mNoiseFrame = new SliderFrame(940, 0, 100, 380, "Noise Red.");
        mainPanel.add(mNoiseFrame);
        mVolFrame = new SliderFrame(1040, 0, 100, 380, "Volume");
        mainPanel.add(mVolFrame);

        mPedalAsgnFrame = new PedalAssignFrame(1140, 0, "Pedal Assign", 0);
        mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        mainPanel.add(mPedalAsgnFrame);

        mReverbFrame = new ReverbFrame(10, 380, 230, 190);
        mainPanel.add(mReverbFrame);
        mPedal1Frame = new Pedal1Frame(10, 570, 230, 190);
        mPedal1Frame.getTypeSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JSlider) e.getSource()).getValue() == 1) {
                    mPedalAsgnFrame.setWhaWha(1);
                }
            }
        });
        mainPanel.add(mPedal1Frame);
        mPedal2Frame = new Pedal2Frame(245, 380, 290, 380);
        mPedal2Frame.getTypeSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (((JSlider) e.getSource()).getValue() == 0) {
                    mPedalAsgnFrame.setWhaWha(2);
                }
            }
        });
        mainPanel.add(mPedal2Frame);
        mModFrame = new ModulationFrame(545, 380);
        mModFrame.getTypeSlider().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!((JSlider) e.getSource()).getValueIsAdjusting()) {
                    mPedalAsgnFrame.changeModType(((JSlider) e.getSource()).getValue(), receiveData);
                }
            }
        });
        mainPanel.add(mModFrame);

        mDelayFrame = new DelayFrame(1000, 380);
        mainPanel.add(mDelayFrame);

        BannerPanel statusPanel = new BannerPanel();
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusPanel.setLayout(null);
        statusPanel.setPreferredSize(new Dimension(MAIN_WINDOW_WIDTH, 35));
        statusPanel.setGradientPaint(Color.WHITE, Constants.backgroundColor, false);
        JLabel lblTitle = new JLabel("Status");
        JSeparator jSeparator = new JSeparator(JSeparator.VERTICAL);
        lblTitle.setBounds(5, 5, 50, 20);
        lblStatus = new JLabel();
        lblStatus.setBounds(55, 5, 1000, 20);
        jSeparator.setBounds(1100, 5, 10, 20);
        statusPanel.add(lblTitle);
        statusPanel.add(jSeparator);
        statusPanel.add(lblStatus);

        jContentPane.setLayout(new BorderLayout(4, 0));
        jContentPane.add(mainPanel, BorderLayout.CENTER);
        jContentPane.add(statusPanel, BorderLayout.PAGE_END);
        if (use_scrollbars) {
            jSContentPane = new JScrollPane(jContentPane);
            jSContentPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            jSContentPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            this.setContentPane(jSContentPane);
        } else  {
            this.setContentPane(jContentPane);
        }
    }

    private void updateStatusBar(String msg, boolean error) {
        if (error) {
            lblStatus.setForeground(Color.RED);
        } else {
            lblStatus.setForeground(Color.GREEN);
        }
        logger.debug("updateStatusBar: new text" + msg);
        lblStatus.setText(msg);
    }

    private void updateTitle(String txt) {
        this.setTitle("Tonelab EX Editor - " + txt);
    }

    protected JMenuBar getMenu() {

        JMenuBar menu = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);
        JMenuItem loadItem = new JMenuItem("Load preset from file");
        loadItem.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_DOWN_MASK));
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                readPresetFromFile();
            }
        });
        fileMenu.add(loadItem);
        JMenuItem saveItem = new JMenuItem("Save preset to file");
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                writePresetToFile(getPresetFromGui());
            }
        });
        fileMenu.add(saveItem);
        fileMenu.addSeparator();


        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_W);
//            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, MENU_MASK));
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        fileMenu.add(exitMenuItem);

        // MIDI Menu
        JMenu midiMenu = new JMenu("MIDI");
        menu.add(midiMenu);
        JMenuItem loadMenuItem = new JMenuItem("Load preset from device");
        loadMenuItem.setAccelerator(KeyStroke.getKeyStroke('L', CTRL_DOWN_MASK));
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoadPresetDialog();
            }
        });
        midiMenu.add(loadMenuItem);
        JMenuItem writePresetItem = new JMenuItem("Save preset on device");
        writePresetItem.setAccelerator(KeyStroke.getKeyStroke('W', CTRL_DOWN_MASK));
        writePresetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showWritePresetDialog();
            }
        });
        midiMenu.add(writePresetItem);

        JMenuItem updatePresetItem = new JMenuItem("Save gui changes on device");
        updatePresetItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        updatePresetItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.debug("F5 pressed");
                if (mLoadedPreset) {
                    sendPresetToDevice(getPresetFromGui());
                }
            }
        });
        midiMenu.add(updatePresetItem);

        if (TonelabEx.DEVELOPE_TEST_MENU_ON) {
            menu.add(getTestMenu());
        }

        // HELP Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHelp();
            }
        });
        helpMenu.add(helpItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout();
            }
        });
        helpMenu.add(aboutItem);
        menu.add(helpMenu);
        return menu;
    }

    private JMenu getTestMenu() {
        JMenu testMenu = new JMenu("Test");
//        JMenuItem test = new JMenuItem("Test");
//        test.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mModFrame.setValues();
//                logger.debug("Modulation values: "
//                        + "On: " + ModulationValue.isOn()
//                        + ", Type: " + Constants.modNames[ModulationValue.getType()] + ", Par1: "
//                        + ModulationValue.getPar1()
//                        + ", Par 2: " + ModulationValue.getPar2()
//                        + ", Par 3: " + ModulationValue.getPar3());
//            }
//        });
        // "0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000"
//        testMenu.add(test);

        JMenuItem testPreset1 = new JMenuItem("Test testParseProgramDump2");
        testPreset1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExPreset p = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2001" + "0057000A060B164B00414F2938640F060001140A35360100000114203C0010000000640000" + "F7");
                inMessage(new DeviceEvent(this, DeviceEvent.PRESET_LOAD, p));
                logger.debug("Cab on/off: " + mCabFrame.isOn());
            }
        });
        testMenu.add(testPreset1);
        JMenuItem testPreset2 = new JMenuItem("Test testParseProgramDump1");
        testPreset2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExPreset p = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2002" + "0006020d0812062a004e085064111e000000000344000401080108263c0210000000440000" + "F7");
                logger.debug("Test preset data len: " + p.getData().length);
                inMessage(new DeviceEvent(this, DeviceEvent.PRESET_LOAD, p));
            }
        });
        testMenu.add(testPreset2);
        JMenuItem testPreset3 = new JMenuItem("Test testParseProgramDump4(p2 inverted");
        testPreset3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExPreset p = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2003" + "0055031E04000C1700553D3649120F030000080925380000000213043C0008640000140000" + "F7");
                logger.debug("Test preset data len: " + p.getData().length);
                inMessage(new DeviceEvent(this, DeviceEvent.PRESET_LOAD, p));
            }
        });
        testMenu.add(testPreset3);
        JMenuItem testPreset4 = new JMenuItem("Test testParseProgramDump5");
        testPreset4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExPreset p = new ExPreset(MidiConstants.SYSEX_START_STR + "4C2004" + "005D02110164191100593664351D1906000015081700190000000B3B72010C0A00001D0000" + "F7");
                logger.debug("Test preset data len: " + p.getData().length);
                inMessage(new DeviceEvent(this, DeviceEvent.PRESET_LOAD, p));
            }
        });
        testMenu.add(testPreset4);
        JMenuItem testPreset5 = new JMenuItem("Test Amp Off");
        testPreset5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExPreset p = new ExPreset("f042300001104c2005005102110164191100593664351d19060000150817001900003b250b00000c000000000000f7");
                inMessage(new DeviceEvent(this, DeviceEvent.PRESET_LOAD, p));
            }
        });
        testMenu.add(testPreset5);
        JMenuItem testPreset6 = new JMenuItem("Test Pitch Expression");
        testPreset6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExPreset p = new ExPreset("f042300001104c200600770014034726130032323D31002E0300010B0864000C000003072E1C02120C0000190000f7");
                inMessage(new DeviceEvent(this, DeviceEvent.PRESET_LOAD, p));
            }
        });
        testMenu.add(testPreset6);
        return testMenu;
    }


    public void updateGuiFromPreset(ExPreset ep) {
        receiveData = true;
        mLoadedPreset = true;
        mDefaultBank = ep.getBank();
        mDdefaultNbr = ep.getNbrInBank();
        logger.debug("updateGuiFromPreset: " + HexUtil.toHexString(MessageBuilder.buildSendPresetMsg(ep)));
        ExGuiPreset preset = new ExGuiPreset(ep);
//        logger.debug("updateGuiFromPreset1 Cab on/off: " + preset.isCabOn() + ", " + mCabFrame.isOn());
        mAmpFrame.setAmp(preset.getAmpSapValue());
        mCabFrame.setCabinet(preset.getCabinetValue());
        mCabFrame.setOn(preset.isCabOn());
        mVolFrame.setValue(preset.getVolumeValue());
        mGainFrame.setValue(preset.getGainValue());
        mTrebleFrame.setValue(preset.getTrebleValue());
        mMiddleFrame.setValue(preset.getMiddleValue());
        mBassFrame.setValue(preset.getBassValue());
        mPresenceFrame.setValue(preset.getPresenceValue());
        mNoiseFrame.setValue(preset.getNoiseValue());
        mReverbFrame.setReverbValues(preset.getReverbValue());
        mPedal1Frame.setPedal1Values(preset.getPedal1Value());
        mPedal2Frame.setPedal2Values(preset.getPedal2Value());
        mModFrame.setModulation(preset.getModulationValue());
        mDelayFrame.setDelayValues(preset.getDelayValue());
        mPedalAsgnFrame.setPedalAssignValues(preset.getPedalAssignValue());
        updateTitle("Preset " + mDefaultBank + "-" + mDdefaultNbr);
//        logger.debug("updateGuiFromPreset2 Cab on/off: " + preset.isCabOn() + ", " + mCabFrame.isOn());
//        updateStatusBar("Preset " + mDefaultBank + "-" + mDdefaultNbr + " loaded");
        receiveData = false;
    }

    public ExPreset getPresetFromGui() {
        ExGuiPreset preset = new ExGuiPreset();
        preset.setBank(mDefaultBank);
        preset.setNbrInBank(mDdefaultNbr);
        preset.setAmpSapValue(mAmpFrame.getAmp());
        preset.setCabOn(mCabFrame.isOn());
        preset.setCabinetValue(mCabFrame.getCabinet());
        preset.setVolumeValue(mVolFrame.getValue());
        preset.setGainValue(mGainFrame.getValue());
        preset.setTrebleValue(mTrebleFrame.getValue());
        preset.setMiddleValue(mMiddleFrame.getValue());
        preset.setBassValue(mBassFrame.getValue());
        preset.setPresenceValue(mPresenceFrame.getValue());
        preset.setNoiseValue(mNoiseFrame.getValue());
        preset.setReverbValue(mReverbFrame.getReverbValues());
        preset.setPedal1Value(mPedal1Frame.getPedal1Values());
        preset.setPedal2Value(mPedal2Frame.getPedal2Values());
        preset.setModulationValue(mModFrame.getModulation());
        preset.setDelayValue(mDelayFrame.getDelayValue());
        preset.setPedalAssignValue(mPedalAsgnFrame.getPedalAssignValues()); // TODO: IMPLEMENT
        return new ExPreset(preset);
    }


    protected void loadPresetFromDevice(int bank, int nbr) {
        if (TonelabEx.DEVELOPE_MIDI_ON) {
            try {
                updateStatusBar("", false);
                PresetList.setPreset(bank, nbr, null);
                deviceCtrl.runCommandBlocking(new SendChangePreset(MessageBuilder.getPreset(bank, nbr)));
                waitABit(100);
                for (int count = 0; count < 2; count++) {
                    byte[] data = deviceCtrl.runCommandBlocking(new GetPresetCommand(MessageBuilder.getPreset(bank, nbr)));
//                logger.debug(HexUtil.toHexString(data));
                    ExPreset preset = new ExPreset(data);
//                logger.debug("Got preset: " + preset);
                    if (preset != null) {
                        count = 10; //Break the for loop
                        updateGuiFromPreset(preset);
                    } else {
                        waitABit(100);
                    }
                }
//            if (PresetList.getPreset(bank, nbr) == null) {
//                updateStatusBar("Failed to load preset (Bank:" + bank + ", Nbr: " + nbr +")");
//            }
            } catch (MidiException e) {
                e.printStackTrace();
                logger.error("Failed to load preset (Bank:" + bank + ", Nbr: " + nbr);
                updateStatusBar("Failed to load preset (Bank:" + bank + ", Nbr: " + nbr + ") " + e.getMessage(), true);
            }
        }
    }

    private void sendPresetToDevice(ExPreset preset) {
        if (TonelabEx.DEVELOPE_MIDI_ON) {
            try {
                deviceCtrl.executeCommand(new SendCurrentPresetCommand(preset));
//                deviceCtrl.runCommandBlocking(new SendCurrentPresetCommand(preset));
            } catch (MidiException e) {
                e.printStackTrace();
                logger.error("Failed to send preset: " + e.getMessage());
            }
        }
    }

    private void writePresetToDevice(ExPreset preset) {
        if (TonelabEx.DEVELOPE_MIDI_ON) {
            try {
                deviceCtrl.runCommandBlocking(new SendWritePresetCommand(preset));
                waitABit(100);
                updateTitle("Preset " + preset.getBank() + "-" + preset.getNbrInBank());
                deviceCtrl.runCommandBlocking(new SendChangePreset(preset.getNbr()));
            } catch (MidiException e) {
                e.printStackTrace();
                logger.error("Failed to send preset: " + e.getMessage());
                updateStatusBar("Failed to write preset", true);
            }
        }
    }

    private void showWritePresetDialog() {
        if (mLoadedPreset) {
            ExPreset preset = getPresetFromGui();
            GetPresetDialog dlg = new GetPresetDialog(this, "Write Preset", preset.getBank(), preset.getNbrInBank());
            dlg.setVisible(true);
            logger.debug("Selected preset: " + dlg.getPreset());
            if (dlg.getPreset() != -1) {
                preset.setPresetNbr(dlg.getBank(), dlg.getBankNbr());
                writePresetToDevice(preset);
            }
        }
    }

    private void writePresetToFile(ExPreset preset) {
        logger.debug("Write: data len: " + preset.getData().length + ", data: " + HexUtil.toHexString(MessageBuilder.buildSendPresetMsg(preset)));
        FileManager.writePresetToFile(this, preset);
    }

    private void readPresetFromFile() {
        ExPreset preset = FileManager.readPresetFromFile(this);
        if (preset != null) {
            logger.debug("Read: " + HexUtil.toHexString(MessageBuilder.buildSendPresetMsg(preset)));
            updateGuiFromPreset(preset);
        }
    }

    private void showAbout() {
        AboutDialog dlg = new AboutDialog(this);
        dlg.setVisible(true);
    }

    private void showHelp() {
        HelpDialog dlg = new HelpDialog(this);
        dlg.setVisible(true);
    }

    private void showLoadPresetDialog() {
        GetPresetDialog dlg = new GetPresetDialog(this, "Load Preset", mDefaultBank, mDdefaultNbr);
        dlg.setVisible(true);
        logger.debug("Selected preset: " + dlg.getPreset());
        if (dlg.getPreset() != -1) {
            loadPresetFromDevice(dlg.getBank(), dlg.getBankNbr());
        }
    }


    private void waitABit(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
        }
    }

    public void quit() {
        deviceCtrl.close();
        System.exit(0);
    }


    @Override
    public void inMessage(DeviceEvent e) {
        switch (e.getType()) {
            case DeviceEvent.ERROR:
                updateStatusBar((String) e.getData(), true);
                break;
            case DeviceEvent.PRESET_CHANGE:
                int preset = (((byte[]) e.getData())[0]) * 25 + ((byte[]) e.getData())[1];
                updateStatusBar("Preset changed on device:" + MessageBuilder.getBankAndNbr(preset)[0] + "-" + MessageBuilder.getBankAndNbr(preset)[1], false);
//                System.out.println("Preset:  user/preset: " + ((byte[]) e.getData())[0] + ", Preset: " + ((byte[]) e.getData())[1]);
                break;
            case DeviceEvent.PRESET_LOAD:

                updateStatusBar("", false);
                updateGuiFromPreset((ExPreset) e.getData());

                break;
            case DeviceEvent.GUI_CHANGE:
                if (!receiveData) {
                    logger.info("Gui Change from: " + e.getSource().getClass().getName());
                    if ((System.currentTimeMillis() - lastGuiUpdate) > 800) {
                        logger.debug("Send change to device");
                        sendPresetToDevice(getPresetFromGui());
                    }
                    lastGuiUpdate = System.currentTimeMillis();
                }
                break;
            case DeviceEvent.DATA_CHANGE_SUCCESS:
                updateStatusBar((String) e.getData(), false);
            default:
                logger.error("Invalid type (" + e.getType() + ") in DeviceEvent");
        }
    }

}
