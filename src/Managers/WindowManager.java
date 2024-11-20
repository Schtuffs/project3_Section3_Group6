package Managers;

import javax.swing.*;

import java.util.ArrayList;
import Devices.*;

public class WindowManager {
    // Holds the window size
    private final int width, height;

    // Holds the device manager to access its variables later
    private DeviceManager devices;

    // Holds the current screen the window is displaying
    private JFrame window;

    public enum SCREENS {
        SCREEN_MAIN, SCREEN_BLINDS, SCREEN_CAMERA, SCREEN_COFFEE, SCREEN_SHOWER, SCREEN_SMOKE, SCREEN_THERMO
    };

    private SCREENS screen;
    
    public WindowManager() {
        // Setup private variables
        this.width = 1600;
        this.height = 900;
        this.screen = SCREENS.SCREEN_MAIN;
        this.window = new JFrame();

        // Create window
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setSize(this.width, this.height);
        this.window.setResizable(false);
        this.window.setVisible(true);
    }

    // Displays a selected screen
    public void Display() {
        
    }

    // Selects a screen
    // Returns if screen is valid option
    public boolean SelectScreen(SCREENS newScreen) {
        try {
            this.screen = newScreen;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
