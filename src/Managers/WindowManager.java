package Managers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import Devices.*;

public class WindowManager {
    // Holds the window size
    private final int width, height;

    // Holds the device manager to access its variables later
    private DeviceManager devices;

    // Holds the current screen the window is displaying
    private JFrame window;
    // Also holds the main center display information in card deck
    private CardLayout cards;
    private JPanel center;

    private enum SCREENS {
        SCREEN_MAIN, SCREEN_ALARM, SCREEN_BLINDS, SCREEN_CAMERA, SCREEN_COFFEE, SCREEN_SENSOR, SCREEN_SHOWER, SCREEN_SMOKE, SCREEN_THERMO
    };

    private SCREENS screen;
    
    public WindowManager() {
        // Setup private variables
        this.width = 1600;
        this.height = 900;
        this.screen = SCREENS.SCREEN_MAIN;
        this.window = new JFrame();
        this.cards = new CardLayout();
        this.center = new JPanel();
        this.center.setLayout(this.cards);

        // Create window
        this.window.getContentPane().setBackground(Color.DARK_GRAY);
        this.window.setTitle("Home Monitor");
        this.window.setLayout(new BorderLayout());
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setSize(this.width, this.height);
        this.window.setVisible(true);

        // Add items to the panel
        this.SetupTop();
        this.SetupLeft();
        this.SetupRight();
        this.SetupBottom();
        this.SetupCenter();
    }

    // Displays a selected screen
    public void Display() {
        // Outside directional information areas
        

        // Center (Main) display area
        switch (this.screen) {
        case SCREEN_MAIN:
            // this.ShowMain();
            break;
        default:
            // this.ShowMain();
        }
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

    private void SetupTop() {
        
    }

    private void SetupLeft() {
        // Sets the left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5, 1));
    }

    private void SetupRight() {
        // Sets the right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 2));

        // JButtons for each class
        JButton alarmButton  = new JButton("Alarm");
        alarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_ALARM.toString());
            }
        });
        
        JButton blindsButton = new JButton("Blinds");
        blindsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_BLINDS.toString());
            }
        });

        JButton cameraButton = new JButton("Camera");
        cameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_CAMERA.toString());
            }
        });

        JButton coffeeButton = new JButton("Coffee Machine");
        coffeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_COFFEE.toString());            
            }
        });

        JButton sensorButton = new JButton("Sensors");
        sensorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SENSOR.toString());            
            }
        });

        JButton showerButton = new JButton("Shower");
        showerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SHOWER.toString());            
            }
        });

        JButton smokeButton  = new JButton("Smoke Detector");
        smokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SMOKE.toString());            
            }
        });

        JButton thermoButton = new JButton("Thermostat");
        thermoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_THERMO.toString());            
            }
        });

        JButton homeButton   = new JButton("Home");
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_MAIN.toString());            
            }
        });


        // Close button as well
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.window.dispose();
            }
        });

        // Add the buttons to the screen
        rightPanel.add(alarmButton);
        rightPanel.add(blindsButton);
        rightPanel.add(cameraButton);
        rightPanel.add(coffeeButton);
        rightPanel.add(sensorButton);
        rightPanel.add(sensorButton);
        rightPanel.add(showerButton);
        rightPanel.add(smokeButton);
        rightPanel.add(thermoButton);
        rightPanel.add(homeButton);
        rightPanel.add(closeButton);
        this.window.add(rightPanel, BorderLayout.EAST);
        rightPanel.setPreferredSize(new Dimension(500, 2));
    }

    private void SetupBottom() {

    }

    private void SetupCenter() {
        // Main layout is card, each subpanel can be anything, but can also have same generic structure

        // Home page
        JPanel home = new JPanel();
        home.setLayout(new GridLayout());

        JButton but = new JButton("Button of Errors");
        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlertManager am = new AlertManager();
                am.Beep();
            }
        });
        home.add(but);

        // Alarm page
        JPanel alarmPanel = new JPanel();
        alarmPanel.setLayout(new GridLayout());
        JLabel alarmLabel = new JLabel("Alarm");
        alarmPanel.add(alarmLabel);

        // Blinds page
        JPanel blindPanel = new JPanel();
        blindPanel.setLayout(new GridLayout());
        JLabel blindLabel = new JLabel("Blind");
        blindPanel.add(blindLabel);
        
        // Camera page
        JPanel cameraPanel = new JPanel();
        cameraPanel.setLayout(new GridLayout());
        JLabel cameraLabel = new JLabel("Camera");
        cameraPanel.add(cameraLabel);
        
        // Coffee page
        JPanel coffeePanel = new JPanel();
        coffeePanel.setLayout(new GridLayout());
        JLabel coffeeLabel = new JLabel("Coffee");
        coffeePanel.add(coffeeLabel);
        
        // Sensor page
        JPanel sensorPanel = new JPanel();
        sensorPanel.setLayout(new GridLayout());
        JLabel sensorLabel = new JLabel("Sensor");
        sensorPanel.add(sensorLabel);
        
        // Shower page
        JPanel showerPanel = new JPanel();
        showerPanel.setLayout(new GridLayout());
        JLabel showerLabel = new JLabel("Shower");
        showerPanel.add(showerLabel);
        
        // SmokeDetector page
        JPanel smokePanel = new JPanel();
        smokePanel.setLayout(new GridLayout());
        JLabel smokeLabel = new JLabel("Smoke");
        smokePanel.add(smokeLabel);
        
        // Thermostat page
        JPanel thermoPanel = new JPanel();
        thermoPanel.setLayout(new GridLayout());
        JLabel thermoLabel = new JLabel("Thermostat");
        thermoPanel.add(thermoLabel);
        
        
        // Add the different screens to the card container
        this.center.add(home, SCREENS.SCREEN_MAIN.toString());
        this.center.add(alarmPanel, SCREENS.SCREEN_ALARM.toString());
        this.center.add(blindPanel, SCREENS.SCREEN_BLINDS.toString());
        this.center.add(cameraPanel, SCREENS.SCREEN_CAMERA.toString());
        this.center.add(coffeePanel, SCREENS.SCREEN_COFFEE.toString());
        this.center.add(sensorPanel, SCREENS.SCREEN_SENSOR.toString());
        this.center.add(showerPanel, SCREENS.SCREEN_SHOWER.toString());
        this.center.add(smokePanel, SCREENS.SCREEN_SMOKE.toString());
        this.center.add(thermoPanel, SCREENS.SCREEN_THERMO.toString());

        // Set this center panel to the window
        this.window.add(this.center, BorderLayout.CENTER);
    }
}
