package com.company.main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // write your code here

        try {
            // Set System L&F
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
        Paint ui = new Paint();
        ui.run();
    }
}
