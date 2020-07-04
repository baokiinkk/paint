package com.company.main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //as
        try {
            // Set System L&F
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
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
