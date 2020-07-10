package com.company.main;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Setting extends JDialog {
    private static Color gridColor;
    private boolean isChoosing;

    private void refreshState() {
        a2DBoardRadioButton.setSelected(is2DBoard);
        a3DBoardRadioButton.setSelected(!is2DBoard);
        show2DAxisCheckBox.setEnabled(is2DBoard);
        show3DAxisCheckBox.setEnabled(!is2DBoard);
        show2DCoordinatesCheckBox.setEnabled(is2DBoard);
        show3DCoordinatesCheckBox.setEnabled(!is2DBoard);
    }

    private JCheckBox showGridCheckBox;

    public static Color getGridColor() {
        return gridColor;
    }

    private void onOK() {
        // add your code here
        isChoosing = true;
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean getState() {
        return is2DBoard;
    }

    public boolean getShow2DAxis() {
        return show2DAxisCheckBox.isSelected();
    }

    public boolean getShow2DCoord() {
        return show2DCoordinatesCheckBox.isSelected();
    }

    public boolean getShow3DAxis() {
        return show3DAxisCheckBox.isSelected();
    }

    public boolean getShow3DCoord() {
        return show3DCoordinatesCheckBox.isSelected();
    }

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
        isChoosing = false;
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
        showGridCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

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

        defaultColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gridColor = new Color(235, 235, 235);
            }
        });

        setColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color c = JColorChooser.showDialog(setColorButton, "Choose Color", gridColor);
                if (c != null)
                    gridColor = c;
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

    public boolean getOK() {
        return isChoosing;
    }

    public boolean getShowGrid() {
        return showGridCheckBox.isSelected();
    }


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JRadioButton a2DBoardRadioButton;
    private JRadioButton a3DBoardRadioButton;
    private JCheckBox show2DAxisCheckBox;
    private JCheckBox show2DCoordinatesCheckBox;
    private JCheckBox show3DAxisCheckBox;
    private JCheckBox show3DCoordinatesCheckBox;
    private JButton setColorButton;
    private JButton defaultColorButton;

    public void setChecker(boolean c2Axis, boolean c2Coord, boolean c3Axis, boolean c3Coord, boolean showGrid) {
        show2DAxisCheckBox.setSelected(c2Axis);
        show3DAxisCheckBox.setSelected(c3Axis);
        show2DCoordinatesCheckBox.setSelected(c2Coord);
        show3DCoordinatesCheckBox.setSelected(c3Coord);
        showGridCheckBox.setSelected(showGrid);
    }

    private boolean is2DBoard;

    public static void setGridColor(Color tmp) {
        gridColor = tmp;
    }

}
