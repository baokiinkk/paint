package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paint  extends JFrame {
    private JPanel activity_main;
    private JPanel leftPanel;
    private JPanel topPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JPanel mainArea;
    private JPanel drawArea;
    private JTable table1;

    public paint() {
        setContentPane(activity_main);
        setVisible(true);
        setSize(500,700);
        activity_main.setSize(500,500);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
