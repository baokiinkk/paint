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
    private JButton cube;
    private JButton globular;
    private JButton concical;
    private JButton circular;
    private JButton pyramid;
    private JButton tetrahedral;
    private JButton shapShower;
    private JTextField centerX;
    private JTextField centerY;
    private JTextField centerZ;
    private JTextField sizeHeight;
    private JTextField sizeWidth;
    private JTextField sizeDepth;

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
        shape = Button.CUBE;
        shapShower.setIcon(cube.getIcon());
        refreshState();
        //System.out.println(shape);
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

        cube.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == cube) {
                    shape = Button.CUBE;
                    shapShower.setIcon(cube.getIcon());
                    refreshState();
                }
            }
        });

        tetrahedral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == tetrahedral) {
                    shape = Button.TETRAHEDRON;
                    shapShower.setIcon(tetrahedral.getIcon());
                    refreshState();
                }
            }
        });

        circular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == circular) {
                    shape = Button.CIRCULAR;
                    shapShower.setIcon(circular.getIcon());
                    refreshState();
                }
            }
        });

        globular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == globular) {
                    shape = Button.GLOBULAR;
                    shapShower.setIcon(globular.getIcon());
                    refreshState();
                }
            }
        });

        concical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == concical) {
                    shape = Button.CONICAL;
                    shapShower.setIcon(concical.getIcon());
                    refreshState();
                }
            }
        });

        pyramid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == pyramid) {
                    shape = Button.PYRAMID;
                    shapShower.setIcon(pyramid.getIcon());
                    refreshState();
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
        if (vectorRadioButton.isSelected()) {
            start = new Point3D(Integer.valueOf(textField1.getText().trim()),
                    Integer.valueOf(textField2.getText().trim()), Integer.valueOf(textField3.getText().trim()));
            end = new Point3D(Integer.valueOf(textField4.getText().trim()),
                    Integer.valueOf(textField5.getText().trim()), Integer.valueOf(textField6.getText().trim()));
        } else {
            Point3D tmpStart = new Point3D(Integer.valueOf(centerX.getText().trim()),
                    Integer.valueOf(centerY.getText().trim()), Integer.valueOf(centerZ.getText().trim()));
            switch (shape) {
                case CUBE: {
                    Point3D tmpEnd = new Point3D(Integer.valueOf(sizeWidth.getText().trim()),
                            Integer.valueOf(sizeDepth.getText().trim()), Integer.valueOf(sizeHeight.getText().trim()));
                    start = new Point3D(tmpStart.X - Math.round(tmpEnd.X / 2),
                            tmpStart.Y - Math.round(tmpEnd.Y / 2), tmpStart.Z - Math.round(tmpEnd.Z / 2));
                    end = new Point3D(tmpStart.X + Math.round(tmpEnd.X / 2),
                            tmpStart.Y + Math.round(tmpEnd.Y / 2), tmpStart.Z + Math.round(tmpEnd.Z / 2));
                    break;
                }
                case GLOBULAR: {
                    start = new Point3D(tmpStart);
                    end = new Point3D(tmpStart.X + Integer.valueOf(sizeWidth.getText().trim()), tmpStart.Y, tmpStart.Z);
                    break;
                }
                case CONICAL: {
                    start = new Point3D(tmpStart.X, tmpStart.Y, tmpStart.Z + Integer.valueOf(sizeHeight.getText().trim()));
                    end = new Point3D(tmpStart.X + Integer.valueOf(sizeWidth.getText().trim()), tmpStart.Y, tmpStart.Z);
                    break;
                }
                case CIRCULAR: {
                    start = new Point3D(tmpStart.X, tmpStart.Y, tmpStart.Z + Integer.valueOf(sizeHeight.getText().trim()));
                    end = new Point3D(tmpStart.X + Integer.valueOf(sizeWidth.getText().trim()), tmpStart.Y, tmpStart.Z);
                    break;
                }
            }
        }


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

    public Button get3DShape() {
        return shape;
    }


    public void refreshState() {
        vectorRadioButton.setSelected(isVector);
        positionRadioButton.setSelected(!isVector);
        vectorPanel.setVisible(isVector);
        positionPanel.setVisible(!isVector);
        //System.out.println(shape);
        if (shape.equals(Button.GLOBULAR) || shape.equals(Button.CONICAL) || shape.equals(Button.CIRCULAR)) {
            depthPanel.setVisible(false);
            if (shape.equals(Button.GLOBULAR)) {
                heightPanel.setVisible(false);
            } else {
                heightPanel.setVisible(true);
            }
        } else {
            depthPanel.setVisible(true);
            heightPanel.setVisible(true);
        }
    }

    public boolean isOK() {
        return isChoosing;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
