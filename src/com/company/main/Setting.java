package com.company.main;

import javax.swing.*;
import java.awt.event.*;

public class Setting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton a2DBoardRadioButton;
    private JRadioButton a3DBoardRadioButton;
    private JCheckBox show2DAxisCheckBox;
    private JCheckBox show2DCoordinatesCheckBox;
    private JCheckBox show3DAxisCheckBox;
    private JCheckBox show3DCoordinatesCheckBox;
    private boolean is2DBoard;

    public Setting(boolean boardState) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setResizable(false);
        this.setTitle("Setting");
        ButtonGroup myButtonGroup = new ButtonGroup();
        myButtonGroup.add(a2DBoardRadioButton);
        myButtonGroup.add(a3DBoardRadioButton);
        this.is2DBoard = boardState;
        refreshState();

        a2DBoardRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getSource() == a2DBoardRadioButton) {
                    if (a2DBoardRadioButton.isSelected()) {
                        if (!is2DBoard) {
                            is2DBoard = true;
                            refreshState();
                            //System.out.println(is2DBoard);
                        }
                    }
                }
            }
        });

        a3DBoardRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getSource() == a3DBoardRadioButton) {
                    if (a3DBoardRadioButton.isSelected()) {
                        if (is2DBoard) {
                            is2DBoard = false;
                            refreshState();
                            //System.out.println(is2DBoard);
                        }
                    }
                }
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void refreshState() {
        a2DBoardRadioButton.setSelected(is2DBoard);
        a3DBoardRadioButton.setSelected(!is2DBoard);
        show2DAxisCheckBox.setEnabled(is2DBoard);
        show3DAxisCheckBox.setEnabled(!is2DBoard);
        show2DCoordinatesCheckBox.setEnabled(is2DBoard);
        show3DCoordinatesCheckBox.setEnabled(!is2DBoard);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean getState() {
        return is2DBoard;
    }
}
