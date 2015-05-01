package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.midi.MessageBuilder;
import nu.mikaelsundh.tonelabex.editor.model.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Mikael Sundh
 * Date: 2012-11-06
 */
public class GetPresetDialog extends JDialog {
    int mPreset = -1;
    JButton mOk;
    JButton mCancel;
    JComboBox mBankBox;
    JComboBox mBankNbrBox;

    public GetPresetDialog(Frame owner, String title, int bank, int nbr) {
        super(owner);
        setModal(true);
        setTitle(title);
        setup(bank, nbr);
    }

    private void setup(int bank, int nbr) {
        JPanel panel = new JPanel(null);
        mBankBox = new JComboBox(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25});
        mBankBox.setToolTipText("Select Bank number");
        mBankNbrBox = new JComboBox(new Integer[]{1, 2, 3, 4});
        mBankNbrBox.setToolTipText("Select Preset number");
        JLabel lblBank = new JLabel("Bank");
        JLabel lblPreset = new JLabel("Preset");
        mBankBox.setSelectedItem(bank);
        mBankNbrBox.setSelectedItem(nbr);

        lblBank.setBounds(40, 10, 60, 20);
        mBankBox.setBounds(40, 40, 40, 20);
        lblPreset.setBounds(100, 10, 60, 20);
        mBankNbrBox.setBounds(100, 40, 40, 20);
        panel.add(lblBank);
        panel.add(lblPreset);
        panel.add(mBankBox);
        panel.add(mBankNbrBox);

        mCancel = new JButton("Cancel");
        mCancel.setBounds(15, 125, 85, 30);
        panel.add(mCancel);
        mOk = new JButton("Ok");
        mOk.setBounds(110, 125, 70, 30);
        panel.add(mOk);
        getContentPane().add(panel);

        mOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mPreset = MessageBuilder.getPreset((Integer) mBankBox.getSelectedItem(), (Integer) mBankNbrBox.getSelectedItem());
                setVisible(false);
            }
        });
        mCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                mPreset = -1;
                setVisible(false);
            }
        });
        getRootPane().setDefaultButton(mOk);
        setSize(200, 150);
        setBounds(300, 300, 200, 200);
        panel.setBackground(Constants.backgroundDialogColor);
        lblBank.setForeground(Constants.dialogTextColor);
        lblPreset.setForeground(Constants.dialogTextColor);
    }

    public int getPreset() {
        return mPreset;
    }

    public int getBank() {
        return (Integer) mBankBox.getSelectedItem();
    }

    public int getBankNbr() {
        return (Integer) mBankNbrBox.getSelectedItem();
    }

}
