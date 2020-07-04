package com.company.main;

import com.company.xuli.xuliduongve.Point2D;
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

    private Point3D start;
    private Point3D end;
    private Point3D vec;

    private int OX;
    private int OY;

    public Input3D (int Width, int Height) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        OX = Width/2;
        OY = Height/2;
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
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
        start = new Point3D(Integer.valueOf(textField1.getText().trim().toString()), Integer.valueOf(textField2.getText().trim().toString()), Integer.valueOf(textField3.getText().trim().toString()));
        end = new Point3D(Integer.valueOf(textField4.getText().trim().toString()), Integer.valueOf(textField5.getText().trim().toString()), Integer.valueOf(textField6.getText().trim().toString()));
        vec = new Point3D(end.X-start.X, end.Y-start.Y, end.Z-start.Z);
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

    public Point2D getStart2D()
    {
        int tmpX = (int) (start.X - start.Y/(2*Math.sqrt(Math.abs(start.X))));
        int tmpY = (int) (start.Z - start.Y/(2*Math.sqrt(Math.abs(start.X))));
        //System.out.println(tmpX + "--" + tmpY);
        return new Point2D(OX+tmpX, OY-tmpY);
    }
    public Point2D getEnd2D()
    {
        int tmpX = (int) (end.X - end.Y/(2*Math.sqrt(Math.abs(end.X))));
        int tmpY = (int) (end.Z - end.Y/(2*Math.sqrt(Math.abs(end.X))));
        return new Point2D(OX+tmpX, OY-tmpY);
    }


}
