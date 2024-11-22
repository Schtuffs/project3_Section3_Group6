package Managers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import Devices.*;
import Devices.Device.COMMAND_CALL;
import Devices.Device.COMMAND_GET;
import Devices.Device.COMMAND_SET;
import Devices.Device.STATES;

public class WindowManager {
    // Holds the window size
    private final int width, height;
    private final Dimension min_size;

    // Holds the device manager to access its variables later
    private DeviceManager devices;

    // Holds the current screen the window is displaying
    private JFrame window;
    // Also holds the main center display information in card deck
    private CardLayout cards;
    private JPanel center;

    //
    private final int FPS = 60;
    private final int MS_PER_SECOND = 1000;

    // This gonna be used to prevent stuff like blinds from checking their state every frame instead of every second
    private boolean check;
    private LocalTime prev_time;
    private Timer timer;
    

    private enum SCREENS {
        SCREEN_MAIN, SCREEN_ALARM, SCREEN_BLINDS, SCREEN_CAMERA, SCREEN_COFFEE, SCREEN_SENSOR, SCREEN_SHOWER, SCREEN_SMOKE, SCREEN_THERMO
    };

    private Alarm alarm;
    private Sensor sensor;
    private CoffeeMachine coffeeMachine;
    private Camera camera;
    private Shower shower;
    private SmokeDetector smokey;
    private Blinds blinds;
    private Thermostat thermostat;


    private SCREENS screen;

    public WindowManager() {
        // Setup private variables
        this.width = 1600;
        this.height = 900;
        this.min_size = new Dimension(1200,600);
        this.screen = SCREENS.SCREEN_MAIN;
        this.window = new JFrame();
        this.cards = new CardLayout();
        this.center = new JPanel();
        this.center.setLayout(this.cards);

        // for Timer purposes
        this.check = false;
        this.prev_time = LocalTime.now();

        // These are here for now to get the GUI running for usability testing
        alarm = new Alarm();
        blinds = new Blinds(LocalTime.parse("06:00:00"), LocalTime.parse("18:00:00"));
        shower = new Shower();
        sensor = new Sensor(false, false, LocalTime.parse("09:00:00"), LocalTime.parse("15:00:00"), alarm);
        thermostat = new Thermostat(true, 20, 20);
        smokey = new SmokeDetector();
        coffeeMachine = new CoffeeMachine();
        // camera = new Camera();

        // Create window
        this.window.getContentPane().setBackground(Color.DARK_GRAY);
        this.window.setTitle("Home Monitor");
        this.window.setLayout(new BorderLayout());
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setSize(this.width, this.height);
        // this.window.setMinimumSize(min_size);
        this.window.setVisible(true);

        // For some reason, this is required to get the window to display on start up without the user needing to resize the window before the components are displayed 
        this.window.setSize(new Dimension(this.width+1, this.height));

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
        alarmButton.setBackground(Color.white);
        alarmButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        alarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_ALARM.toString());
            }
        });
        
        JButton blindsButton = new JButton("Blinds");
        blindsButton.setBackground(Color.white);
        blindsButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        blindsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_BLINDS.toString());
            }
        });

        JButton cameraButton = new JButton("Camera");
        cameraButton.setBackground(Color.white);
        cameraButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        cameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_CAMERA.toString());
            }
        });

        JButton coffeeButton = new JButton("Coffee Machine");
        coffeeButton.setBackground(Color.white);
        coffeeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        coffeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_COFFEE.toString());            
            }
        });

        JButton sensorButton = new JButton("Sensors");
        sensorButton.setBackground(Color.white);
        sensorButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        sensorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SENSOR.toString());            
            }
        });

        JButton showerButton = new JButton("Shower");
        showerButton.setBackground(Color.white);
        showerButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        showerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SHOWER.toString());            
            }
        });

        JButton smokeButton  = new JButton("Smoke Detector");
        smokeButton.setBackground(Color.white);
        smokeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        smokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SMOKE.toString());            
            }
        });

        JButton thermoButton = new JButton("Thermostat");
        thermoButton.setBackground(Color.white);
        thermoButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        thermoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_THERMO.toString());            
            }
        });

        JButton homeButton   = new JButton("Home");
        homeButton.setBackground(Color.white);
        homeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_MAIN.toString());            
            }
        });


        // Close button as well
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(Color.white);
        closeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
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

        // for laying out panels
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 0;

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
        alarmPanel.setLayout(new GridBagLayout());

        // Sample picture to display the alarm 
        ImageIcon alarmPng = new ImageIcon("Assets/Devices/Alarm.png");
        JLabel alarmLabel = new JLabel(alarmPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        alarmPanel.add(alarmLabel, c);

        // Button to Stop the Alarm
        JButton stopAlarm = new JButton("STOP");

        stopAlarm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                alarm.StopAlarm();
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.ipady = 75;
        c.ipadx = 100;
        c.gridwidth = 1;
        c.insets = new Insets(100,200,50,200);
        alarmPanel.add(stopAlarm, c);




        //////////////////////////////////////////////////////////////////////////////
        c = new GridBagConstraints();

        // Blinds page
        JPanel blindPanel = new JPanel();
        blindPanel.setLayout(new GridBagLayout());

        // Sample picture to display the alarm 
        ImageIcon blindsPng = new ImageIcon("Assets/Devices/blinds.png");
        JLabel blindsLabel = new JLabel(blindsPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        blindPanel.add(blindsLabel, c);

        // Text to show Automatic Open Time
        JLabel openTime = new JLabel("<html><p style=\"width:250px\">Automatic Open Time</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 50;
        c.gridwidth = 1;
        blindPanel.add(openTime, c);

        // Button to increment hour
        JButton incrementHourOpen = new JButton("^");

        incrementHourOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime(blinds.getOpenTime().plusHours(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(25,160,0,160);
        blindPanel.add(incrementHourOpen, c);

        // Button to increment minute
        JButton incrementMinuteOpen = new JButton("^");

        incrementMinuteOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime(blinds.getOpenTime().plusMinutes(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        blindPanel.add(incrementMinuteOpen, c);

        // Button to increment second
        JButton incrementSecondOpen = new JButton("˄");

        incrementSecondOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime(blinds.getOpenTime().plusSeconds(1));

            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        blindPanel.add(incrementSecondOpen, c);

        // Display the Open Time 
        System.out.println(blinds.getOpenTime());
        JTextField openTimeDisplay = new JTextField(blinds.getOpenTime().toString());
        openTimeDisplay.setFont(null);
        openTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 50;
        blindPanel.add(openTimeDisplay, c);

        // Button to decrement hour
        JButton decrementHourOpen = new JButton("˅");

        decrementHourOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime(blinds.getOpenTime().minusHours(1));

            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        blindPanel.add(decrementHourOpen, c);

        // Button to decrement hour
        JButton decrementMinuteOpen = new JButton("˅");

        decrementMinuteOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime(blinds.getOpenTime().minusMinutes(1));

            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        blindPanel.add(decrementMinuteOpen, c);


        // Button to decrement second
        JButton decrementSecondOpen = new JButton("˅");

        decrementSecondOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime(blinds.getOpenTime().minusSeconds(1));

            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        blindPanel.add(decrementSecondOpen, c);

        // Text to show Automatic Open Time
        JLabel closeTime = new JLabel("<html><p style=\"width:250px\">Automatic Close Time</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 1;
        c.gridy = 5;
        c.ipady = 50;
        c.gridwidth = 1;
        blindPanel.add(closeTime, c);

        // Button to increment hour
        JButton incrementHourClose = new JButton("^");

        incrementHourClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime(blinds.getCloseTime().plusHours(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(25,160,0,160);
        blindPanel.add(incrementHourClose, c);

        // Button to increment minute
        JButton incrementMinuteClose = new JButton("^");

        incrementMinuteClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime(blinds.getCloseTime().plusMinutes(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        blindPanel.add(incrementMinuteClose, c);

        // Button to increment second
        JButton incrementSecondClose = new JButton("˄");

        incrementSecondClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime(blinds.getCloseTime().plusSeconds(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        blindPanel.add(incrementSecondClose, c);

        // Display the Open Time 
        JTextField closeTimeDisplay = new JTextField("00:00:00");
        closeTimeDisplay.setFont(null);
        closeTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        c.ipady = 50;
        blindPanel.add(closeTimeDisplay, c);

        // Button to decrement hour
        JButton decrementHourClose = new JButton("˅");

        decrementHourClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime(blinds.getCloseTime().minusHours(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        blindPanel.add(decrementHourClose, c);

        // Button to decrement hour
        JButton decrementMinuteClose = new JButton("˅");

        decrementMinuteClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime(blinds.getCloseTime().minusMinutes(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        blindPanel.add(decrementMinuteClose, c);


        // Button to decrement second
        JButton decrementSecondClose = new JButton("˅");

        decrementSecondClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime(blinds.getCloseTime().minusSeconds(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        blindPanel.add(decrementSecondClose, c);

        // Open or close Blinds
        JButton openClose = new JButton("Open");

        openClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (openClose.getText().equals("Open")) {
                    blinds.Open();
                    openClose.setText("Close");
                }
                else if (openClose.getText().equals("Close")) {
                    blinds.Close();
                    openClose.setText("Open");
                }
                
            }
            
        });

        c.insets = new Insets(50,200,0,200);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 3;
        c.ipady = 50;
        c.ipadx = 100;
        blindPanel.add(openClose, c);





        ////////////////////////////////////////////////////////////////////////
        c = new GridBagConstraints();
        
        // Camera page
        JPanel cameraPanel = new JPanel();
        cameraPanel.setLayout(new GridLayout());

        ImageIcon cameraPng = new ImageIcon("Assets/Devices/camera.png");
        JLabel cameraLabel = new JLabel(cameraPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        cameraPanel.add(cameraLabel, c);

        ////////////////////////////////////////////////////////////////
        
        // Coffee page
        JPanel coffeePanel = new JPanel();
        coffeePanel.setLayout(new GridLayout());

        ImageIcon coffeePng = new ImageIcon("Assets/Devices/coffeeMachine.png");
        JLabel coffeeLabel = new JLabel(coffeePng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        coffeePanel.add(coffeeLabel, c);
        
        ///////////////////////////////////////////////////////////////////////
        c = new GridBagConstraints();
        
        // Sensor page
        JPanel sensorPanel = new JPanel();
        sensorPanel.setLayout(new GridBagLayout());

        // Sample picture to display the alarm 
        ImageIcon sensorPng = new ImageIcon("Assets/Devices/sensor.png");
        JLabel sensorLabel = new JLabel(sensorPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        sensorPanel.add(sensorLabel, c);

        // Text to show Automatic Open Time
        JLabel sensorOpenTime = new JLabel("<html><p style=\"width:250px\">Open Time</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 50;
        c.gridwidth = 1;
        sensorPanel.add(sensorOpenTime, c);

        // Button to increment hour
        JButton s_incrementHourOpen = new JButton("^");

        s_incrementHourOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetOpenTime(sensor.GetOpenTime().plusHours(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(25,160,0,160);
        sensorPanel.add(s_incrementHourOpen, c);

        // Button to increment minute
        JButton s_incrementMinuteOpen = new JButton("^");

        s_incrementMinuteOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetOpenTime(sensor.GetOpenTime().plusMinutes(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        sensorPanel.add(s_incrementMinuteOpen, c);

        // Button to increment second
        JButton s_incrementSecondOpen = new JButton("˄");

        s_incrementSecondOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetOpenTime(sensor.GetOpenTime().plusSeconds(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        sensorPanel.add(s_incrementSecondOpen, c);

        // Display the Open Time 
        JTextField s_openTimeDisplay = new JTextField("00:00:00");
        s_openTimeDisplay.setFont(null);
        s_openTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 50;
        sensorPanel.add(s_openTimeDisplay, c);

        // Button to decrement hour
        JButton s_decrementHourOpen = new JButton("˅");

        s_decrementHourOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetOpenTime(sensor.GetOpenTime().minusHours(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        sensorPanel.add(s_decrementHourOpen, c);

        // Button to decrement min
        JButton s_decrementMinuteOpen = new JButton("˅");

        s_decrementMinuteOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetOpenTime(sensor.GetOpenTime().minusMinutes(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        sensorPanel.add(s_decrementMinuteOpen, c);


        // Button to decrement second
        JButton s_decrementSecondOpen = new JButton("˅");

        s_decrementSecondOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetOpenTime(sensor.GetOpenTime().minusSeconds(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        sensorPanel.add(s_decrementSecondOpen, c);

        // Text to show Automatic Open Time
        JLabel s_closeTime = new JLabel("<html><p style=\"width:250px\">Close Time</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 1;
        c.gridy = 5;
        c.ipady = 50;
        c.gridwidth = 1;
        sensorPanel.add(s_closeTime, c);

        // Button to increment hour
        JButton s_incrementHourClose = new JButton("^");

        s_incrementHourClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetCloseTime(sensor.GetCloseTime().plusHours(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(25,160,0,160);
        sensorPanel.add(s_incrementHourClose, c);

        // Button to increment minute
        JButton s_incrementMinuteClose = new JButton("^");

        s_incrementMinuteClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetCloseTime(sensor.GetCloseTime().plusMinutes(1));
                
            }
            
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        sensorPanel.add(s_incrementMinuteClose, c);

        // Button to increment second
        JButton s_incrementSecondClose = new JButton("˄");

        s_incrementSecondClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetCloseTime(sensor.GetCloseTime().plusSeconds(1));
                
            }
            
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        sensorPanel.add(s_incrementSecondClose, c);

        // Display the Open Time 
        JTextField s_closeTimeDisplay = new JTextField("00:00:00");
        s_closeTimeDisplay.setFont(null);
        s_closeTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        c.ipady = 50;
        sensorPanel.add(s_closeTimeDisplay, c);

        // Button to decrement hour
        JButton s_decrementHourClose = new JButton("˅");

        s_decrementHourClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetCloseTime(sensor.GetCloseTime().minusHours(1));
                
            }
            
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        sensorPanel.add(s_decrementHourClose, c);

        // Button to decrement hour
        JButton s_decrementMinuteClose = new JButton("˅");

        s_decrementMinuteClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetCloseTime(sensor.GetCloseTime().minusMinutes(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        sensorPanel.add(s_decrementMinuteClose, c);


        // Button to decrement second
        JButton s_decrementSecondClose = new JButton("˅");

        s_decrementSecondClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetCloseTime(sensor.GetCloseTime().minusSeconds(1));
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 8;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        sensorPanel.add(s_decrementSecondClose, c);

        // Dismiss Sensor
        JButton dismiss = new JButton("Dismiss");
        // only display when it has triggered an alarm
        dismiss.setVisible(false);
        c.insets = new Insets(50,200,0,200);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 3;
        c.ipady = 50;
        c.ipadx = 100;
        sensorPanel.add(dismiss, c);





        ///////////////////////////////////////////////
        c = new GridBagConstraints();
        
        
        // Shower page
        JPanel showerPanel = new JPanel();
        showerPanel.setLayout(new GridBagLayout());

        // Sample picture to display the shower 
        ImageIcon showerPng = new ImageIcon("Assets/Devices/shower.png");
        JLabel showerLabel = new JLabel(showerPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        showerPanel.add(showerLabel, c);

        // Text to show patterns
        JLabel temperText = new JLabel("<html><p style=\"width:100px\">Temperature C</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        showerPanel.add(temperText, c);

        // Text to show patterns
        JLabel patternsText = new JLabel("<html><p style=\"width:100px\">Head Pattern</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        showerPanel.add(patternsText, c);





        JComboBox<String> headPatterns = new JComboBox<String>();
        for (int i = 0;i<shower.GetPatternCount();i++) {
            headPatterns.addItem(shower.GetHeadPattern(i));
        }

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,50,0,50);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 50;
        c.ipady = 25;
        c.gridwidth = 1;
        showerPanel.add(headPatterns, c);

        // start or stop the shower
        JButton startStopShower = new JButton("Start");

        startStopShower.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (startStopShower.getText().equals("Start")) {
                    shower.Call(COMMAND_CALL.START, null);
                    startStopShower.setText("Stop");
                }
                else if (startStopShower.getText().equals("Stop")) {
                    shower.Call(COMMAND_CALL.STOP, null);
                    startStopShower.setText("Start");
                }
                
            }
            
        });

        

        c.insets = new Insets(50,200,0,200);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.ipady = 50;
        c.ipadx = 100;
        showerPanel.add(startStopShower, c);


        JSpinner temper = new JSpinner(new SpinnerNumberModel(0.0,-1000.0 ,1000.0,0.1));
        temper.setValue(Double.parseDouble(shower.Get(COMMAND_GET.SHOWER_TEMPERATURE)));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,100,0,100);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 20;
        c.ipady = 25;
        c.gridwidth = 1;
        showerPanel.add(temper, c);
        

        /////////////////////////////////////////////////////////////////
        c = new GridBagConstraints();
        
        // SmokeDetector page
        JPanel smokePanel = new JPanel();
        smokePanel.setLayout(new GridLayout());
        
        /// Simply show the smoke detector since it is uninteractable 
        // Sample picture to display the smokey detector 
        ImageIcon smokePng = new ImageIcon("Assets/Devices/smokeDetector.png");
        JLabel smokeLabel = new JLabel(smokePng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        smokePanel.add(smokeLabel, c);





        /////////////////////////////////////
        c = new GridBagConstraints();
        
        
        // Thermostat page
        JPanel thermoPanel = new JPanel();
        thermoPanel.setLayout(new GridBagLayout());

        ImageIcon thermPng = new ImageIcon("Assets/Devices/thermostat.png");
        JLabel thermLabel = new JLabel(thermPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        thermoPanel.add(thermLabel, c);

        // Text to display units 
        JLabel unitsText = new JLabel("<html><p style=\"width:100px\">Temperature Units</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(unitsText, c);

        // Text to Temperature 
        JLabel tTemperText = new JLabel("<html><p style=\"width:100px\">Target Temperature</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(tTemperText, c);

        // Text to humidity 
        JLabel tHumidText = new JLabel("<html><p style=\"width:100px\">Target Humidity</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(tHumidText, c);

        JComboBox<String> units = new JComboBox<String>();
        units.addItem("C");
        units.addItem("F");

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,50,0,50);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 50;
        c.ipady = 25;
        c.gridwidth = 1;
        thermoPanel.add(units, c);

        JSpinner thermTemper = new JSpinner(new SpinnerNumberModel(0.0,-1000.0 ,1000.0,0.1));
        thermTemper.setValue(thermostat.getTargetTemp());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,100,0,100);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 20;
        c.ipady = 25;
        c.gridwidth = 1;
        thermoPanel.add(thermTemper, c);

        JSpinner thermHumid = new JSpinner(new SpinnerNumberModel(0.0,-1000.0 ,1000.0,0.1));
        thermHumid.setValue(thermostat.getTargetHumid());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,100,0,100);
        c.gridx = 2;
        c.gridy = 2;
        c.ipadx = 20;
        c.ipady = 25;
        c.gridwidth = 1;
        thermoPanel.add(thermHumid, c);

        // Text to Current Temperature 
        JLabel cTemperText = new JLabel("<html><p style=\"width:100px\">Current Temperature</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cTemperText, c);

        // Text to humidity 
        JLabel cHumidText = new JLabel("<html><p style=\"width:100px\">Current Humidity</p></html>", SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cHumidText, c);

        // Display current temperature
        JTextField cTemp = new JTextField("1");
        cTemp.setFont(null);
        cTemp.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cTemp, c);

        // Display current humidity
        JTextField cHumid = new JTextField("1");
        cHumid.setFont(null);
        cHumid.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cHumid, c);
        
        
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


        // Creates a timer that runs FPS times every second
        //
        // Honestly, I dont think the FPS logic is right, Im not thinking rn, but it works
        timer = new Timer((int)(MS_PER_SECOND/FPS), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {






                // Camera Stuff





                // Blinds Stuff
                openTimeDisplay.setText(blinds.getOpenTime().toString());
                closeTimeDisplay.setText(blinds.getCloseTime().toString());


                if (LocalTime.now().getHour()==blinds.getOpenTime().getHour() &&
                    LocalTime.now().getMinute()==blinds.getOpenTime().getMinute() && 
                    LocalTime.now().getSecond()==blinds.getOpenTime().getSecond() &&
                    blinds.getIsOpen()==false) {
                    openClose.doClick();
                    // blinds.Open();
                    // openClose.setText("Close");
                }
                else if (LocalTime.now().getHour()==blinds.getCloseTime().getHour() &&
                    LocalTime.now().getMinute()==blinds.getCloseTime().getMinute() && 
                    LocalTime.now().getSecond()==blinds.getCloseTime().getSecond() &&
                    blinds.getIsOpen()) {
                    openClose.doClick();
                    // blinds.Close();
                    // openClose.setText("Open");
                }






                // Thermostat Stuff

                thermostat.SetTargetTemperature((Double)thermTemper.getValue());
                thermostat.SetTargetHumidity((Double)thermHumid.getValue());

                if (units.getSelectedItem()=="C") {
                    if (!thermostat.SetUnit(true).equals(STATES.ERROR_INVALID_UNIT)) {
                        cTemp.setText(""+thermostat.getTargetTemp());
                        thermTemper.setValue(thermostat.getTemp());
                    }
                }
                else if (units.getSelectedItem()=="F") {
                    if (!thermostat.SetUnit(false).equals(STATES.ERROR_INVALID_UNIT)) {
                        cTemp.setText(""+thermostat.getTargetTemp());
                        thermTemper.setValue(thermostat.getTemp());
                    }
                }

                // Randomly change values
               // System.out.println(thermostat.getTargetTemp()+"  "+thermostat.getTemp());
                if (thermostat.getTargetTemp()!=thermostat.getTemp()) {
                    int rand = ((int) (Math.random() * 100 + 1));
                    if (rand==1 && thermostat.getTargetTemp()>thermostat.getTemp()) {
                        thermostat.SetTemperature(thermostat.getTemp()+0.1);
                    }
                    else if (rand==1 && thermostat.getTargetTemp()<thermostat.getTemp()) {
                        thermostat.SetTemperature(thermostat.getTemp()-0.1);
                    }
                }
                if (thermostat.getTargetHumid()!=thermostat.getHumidity()) {
                    int rand = ((int) (Math.random() * 100 + 1));
                    if (rand==1 && thermostat.getTargetHumid()>thermostat.getHumidity()) {
                        thermostat.SetHumidity(thermostat.getHumidity()+0.1);
                    }
                    else if (rand==1 && thermostat.getTargetHumid()<thermostat.getHumidity()) {
                        thermostat.SetHumidity(thermostat.getHumidity()-0.1);
                    }
                }

                // display values

                cTemp.setText(""+thermostat.getTemp());
                cHumid.setText(""+thermostat.getHumidity());


                // need to do actual vs set calculations here





                // Smoke Detector Stuff
                int rand = ((int) (Math.random() * 1000 + 1));

                // probably change this to a function in smoke detector
                if (rand==1) {
                    smokey.SetIsSmokey(true);
                } 
                else {smokey.SetIsSmokey(false); }

                if (smokey.GetIsSmokey()) {
                    alarm.TriggerAlarm("Theres a fire in the house!",smokey);
                }





                // Shower Stuff
                shower.Set(COMMAND_SET.SHOWER_TEMPERATURE,(""+temper.getValue()));










                // Coffee Machine Stuff









                // Sensor stuff
                sensor.MonitorDevice(blinds);
                s_openTimeDisplay.setText(sensor.GetOpenTime().toString());
                s_closeTimeDisplay.setText(sensor.GetCloseTime().toString());


                // Alarm stuff
                if (alarm.GetIsBeeping()) {
                    stopAlarm.setVisible(true);
                } 
                else {stopAlarm.setVisible(false);}
            }

        } );
        timer.setRepeats(true);
        timer.start();

    }
}
