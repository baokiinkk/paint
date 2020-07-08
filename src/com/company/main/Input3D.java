package com.company.main;

import com.company.Button;
import com.company.xuli.xuliduongve.Point3D;

import javax.swing.*;
import java.awt.event.*;

public class Input3D extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JRadioButton vectorRadioButton;
    private JRadioButton positionRadioButton;
    private JPanel positionPanel;
    private JPanel vectorPanel;
    private JPanel depthPanel;
    private JPanel heightPanel;

    private Point3D start;
    private Point3D end;
    private Point3D vec;

    private int OX;
    private int OY;
    private boolean isVector, isChoosing;
    private Button shape;

    public Input3D(int Width, int Height, Button Choose) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setResizable(false);
        this.setTitle("Setting");
        ButtonGroup myButtonGroup = new ButtonGroup();
        myButtonGroup.add(vectorRadioButton);
        myButtonGroup.add(positionRadioButton);
        OX = Width / 2;
        OY = Height / 2;
        isVector = true;
        isChoosing = false;
        shape = Choose;
        refreshState();
        System.out.println(shape);
        if (shape.equals(Button.GLOBULAR) || shape.equals(Button.CONICAL) || shape.equals(Button.CIRCULAR)) {
            depthPanel.setVisible(false);
            if (shape.equals(Button.GLOBULAR)) {
                heightPanel.setVisible(false);
            }
        }
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        positionRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == positionRadioButton) {
                    if (positionRadioButton.isSelected()) {
                        isVector = false;
                        refreshState();
                    }
                }
            }
        });

        vectorRadioButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getSource() == vectorRadioButton) {
                    if (vectorRadioButton.isSelected()) {
                        isVector = true;
                        refreshState();
                    }
                }
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
            public void windowClosing (WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK () {
        // add your code here
        start = new Point3D(Integer.valueOf(textField1.getText().trim()), Integer.valueOf(textField2.getText().trim()), Integer.valueOf(textField3.getText().trim()));
        end = new Point3D(Integer.valueOf(textField4.getText().trim()), Integer.valueOf(textField5.getText().trim()), Integer.valueOf(textField6.getText().trim()));
        System.out.println(start.X + "/" + start.Y + "+++" + end.X + "/" + end.Y);
        //vec = new Point3D(end.X-start.X, end.Y-start.Y, end.Z-start.Z);
        //start.X += OX;
        //start.Y += OX;
        //start.Z = OY - start.Z;
        //end.X += OX;
        //end.Y += OX;
        //end.Y += OY - start.Z;
        isChoosing = true;
        dispose();
    }

    private void onCancel () {
        // add your code here if necessary
        dispose();
    }

//    public static void main (String[] args) {
//        Input3D dialog = new Input3D();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
//    }

    public Point3D getStart3D() {
        return start;
    }

    public Point3D getEnd3D() {
        return end;
    }

    public void refreshState() {
        vectorRadioButton.setSelected(isVector);
        positionRadioButton.setSelected(!isVector);
        vectorPanel.setVisible(isVector);
        positionPanel.setVisible(!isVector);
    }

    public boolean isOK() {
        return isChoosing;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
