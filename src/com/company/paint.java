package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paint  extends JFrame {
    private JPanel activity_main;
    private JPanel tabLayout;
    private JButton button1;
    private JTable table1;
    private JButton button2;

    public paint() {
        setContentPane(activity_main);
        setVisible(true);
        setSize(500,700);
        activity_main.setSize(500,500);
        DefaultTableModel tableModel = new DefaultTableModel(1000,1000);
        table1.setModel(tableModel);
        table1.setRowHeight(10);
        table1.setGridColor(Color.white);
        for(int i=0 ;i<1000;i++)
        {
            TableColumn column = table1.getColumnModel().getColumn(i);
            column.setMinWidth(10);
            column.setMaxWidth(10);
            column.setPreferredWidth(10);
        }
        table1.setSelectionBackground(Color.RED);
        table1.setCellSelectionEnabled(true);
        table1.setValueAt(Color.BLACK,10,10);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"aaaaa");
            }
        });
    }


}
