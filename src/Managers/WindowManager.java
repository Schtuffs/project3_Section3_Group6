package Managers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
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
    private boolean passed = false;
    

    private enum SCREENS {
        SCREEN_MAIN, SCREEN_ALARM, SCREEN_BLINDS, SCREEN_CAMERA, SCREEN_COFFEE, SCREEN_SENSOR, SCREEN_SHOWER, SCREEN_SMOKE, SCREEN_THERMO
    };


    // references to the current devices the screen needs to display
    private Alarm alarm;
    private Sensor sensor;
    private CoffeeMachine coffeeMachine;
    private Camera camera;
    private Shower shower;
    private SmokeDetector smokey;
    private Blinds blinds;
    private Thermostat thermostat;

    //different screens to display the individual devices
    private JPanel thermoPanel;
    private JPanel smokePanel;
    private JPanel showerPanel;
    private JPanel sensorPanel;
    private JPanel coffeePanel;
    private JPanel cameraPanel;
    private JPanel blindPanel;
    private JPanel alarmPanel;

    private int FramesPassed;
    private int BeepSeconds;
    private String selected;

    GridBagConstraints c;

    FileManager fm;



    private SCREENS screen;

    public WindowManager() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        // Setup private variables

        
        this.width = 1600;
        this.height = 900; 
        this.min_size = new Dimension(1200,600);
        this.screen = SCREENS.SCREEN_MAIN;
        this.window = new JFrame();
        this.cards = new CardLayout();
        this.center = new JPanel();
        this.center.setLayout(this.cards);
        this.FramesPassed = 0;
        this.BeepSeconds = 0;
        this.fm = new FileManager();

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
        camera = new Camera();

        alarm.readDataFromFile(fm.Read("Alarm"));
        blinds.readDataFromFile(fm.Read("Blinds"));
        shower.readDataFromFile(fm.Read("Shower"));
        sensor.readDataFromFile(fm.Read("Sensor"));
        thermostat.readDataFromFile(fm.Read("Thermostat"));
        smokey.readDataFromFile(fm.Read("SmokeDetector"));
        coffeeMachine.readDataFromFile(fm.Read("CoffeeMachine"));
        camera.readDataFromFile(fm.Read("Camera"));

        // Create window
        this.window.getContentPane().setBackground(Color.DARK_GRAY);
        this.window.setTitle("Home Monitor");
        this.window.setLayout(new BorderLayout());
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setSize(this.width, this.height);
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        // this.window.setMinimumSize(min_size);
        this.window.setVisible(true);

        // For some reason, this is required to get the window to display on start up without the user needing to resize the window before the components are displayed 
        // this.window.setSize(new Dimension(this.width+1, this.height));

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
        // alarmButton.setBackground(Color.white);
        alarmButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        alarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_ALARM.toString());
            }
        });
        
        JButton blindsButton = new JButton("Blinds");
        // blindsButton.setBackground(Color.white);
        blindsButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        blindsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_BLINDS.toString());
            }
        });

        JButton cameraButton = new JButton("Camera");
        // cameraButton.setBackground(Color.white);
        cameraButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        cameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_CAMERA.toString());
            }
        });

        JButton coffeeButton = new JButton("Coffee Machine");
        // coffeeButton.setBackground(Color.white);
        coffeeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        coffeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_COFFEE.toString());            
            }
        });

        JButton sensorButton = new JButton("Sensor");
        // sensorButton.setBackground(Color.white);
        sensorButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        sensorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SENSOR.toString());            
            }
        });

        JButton showerButton = new JButton("Shower");
        // showerButton.setBackground(Color.white);
        showerButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        showerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SHOWER.toString());            
            }
        });

        JButton smokeButton  = new JButton("Smoke Detector");
        // smokeButton.setBackground(Color.white);
        smokeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        smokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_SMOKE.toString());            
            }
        });

        JButton thermoButton = new JButton("Thermostat");
        // thermoButton.setBackground(Color.white);
        thermoButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        thermoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_THERMO.toString());            
            }
        });

        JButton homeButton   = new JButton("Home");
        // homeButton.setBackground(Color.white);
        homeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WindowManager.this.cards.show(WindowManager.this.center, SCREENS.SCREEN_MAIN.toString());            
            }
        });


        // Close button as well
        JButton closeButton = new JButton("Close");
        // closeButton.setBackground(Color.white);
        closeButton.setFont(new Font("Silkscreen",Font.PLAIN,24));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
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


        // Add screens independently to the screen

        AlarmScreen();
        BlindsScreen();
        SensorScreen();
        CameraScreen();
        CoffeeMachineScreen();
        SmokeDetectorScreen();
        ShowerScreen();
        ThermostatScreen();

        
        
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
        timer = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                FramesPassed++;

                // Reset Frames and write Devices to file
                if (FramesPassed==FPS) {
                    FramesPassed = 0;

                    fm.Write("Alarm", alarm);
                    fm.Write("Blinds", blinds);
                    fm.Write("Camera", camera);
                    fm.Write("Shower", shower);
                    fm.Write("Sensor", sensor);
                    fm.Write("Thermostat", thermostat);
                    fm.Write("CoffeeMachine", coffeeMachine);
                    fm.Write("SmokeDetector", smokey);
                }
            }


        } );
        timer.setRepeats(true);
        timer.start();


    }

    private void AlarmScreen() {

        // Alarm page
        c = new GridBagConstraints();

        // this will be used by the timer to determine if a second has passed 


        
        alarmPanel = new JPanel();
        alarmPanel.setLayout(new GridBagLayout());

        // Sample picture to display the alarm 
        final ImageIcon alarmOffPng = new ImageIcon("Assets/Devices/AlarmOff.png");
        final ImageIcon alarmOnPng = new ImageIcon("Assets/Devices/AlarmOn.png");
        JLabel alarmLabel = new JLabel(alarmOffPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        alarmPanel.add(alarmLabel, c);

        // Button to Stop the Alarm
        JButton stopAlarm = new JButton("STOP");
        stopAlarm.setFont(new Font("Silkscreen",Font.PLAIN,24));

        stopAlarm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                alarm.StopAlarm();
                alarmLabel.setIcon(alarmOffPng);
                smokey.SetIsSmokey(false);
                
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

        Timer time = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Alarm stuff
                if (alarm.GetIsBeeping()) {
                    stopAlarm.setVisible(true);
                } 
                else {stopAlarm.setVisible(false);}

                if (alarm.GetIsBeeping() && FramesPassed==0) {
                    BeepSeconds++;
                }

                if (!alarm.GetIsBeeping() || !alarmPanel.isVisible()) {
                    BeepSeconds = 0;
                    alarmLabel.setIcon(alarmOffPng);
                }

                // Check alarm if it should beep
                else if (alarm.GetIsBeeping() && FramesPassed==0 && BeepSeconds==alarm.GetBeepDelay() && alarmPanel.isVisible()) {
                    alarmLabel.setIcon(alarmOnPng);
                    BeepSeconds = 0;
                }
                else if ((FramesPassed==((int)(FPS/4)) && BeepSeconds==0 && alarmPanel.isVisible()) || alarm.GetIsBeeping()==false) {
                    alarmLabel.setIcon(alarmOffPng);
                }
            }

        } );
        time.setRepeats(true);
        time.start();

    }

    private void BlindsScreen() {

        c = new GridBagConstraints();

        // Blinds page
        blindPanel = new JPanel();
        blindPanel.setLayout(new GridBagLayout());

        // Sample picture to display the alarm 
        ImageIcon blindsOpen = new ImageIcon("Assets/Devices/BlindsOpen.png");
        ImageIcon blindsClose = new ImageIcon("Assets/Devices/BlindsClosed.png");
        JLabel blindsLabel = new JLabel(blindsClose);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        blindPanel.add(blindsLabel, c);

        // Text to show Automatic Open Time
        JLabel openTime = new JLabel("Automatic Open Time", SwingConstants.CENTER);
        openTime.setFont(new Font("Silkscreen",Font.PLAIN,24));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 0;
        c.gridwidth = 1;
        blindPanel.add(openTime, c);

        // Button to increment hour
        JButton incrementHourOpen = new JButton("^");

        incrementHourOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime((blinds.getOpenTime().plusHours(1)).toString());
                
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

                blinds.setOpenTime((blinds.getOpenTime().plusMinutes(1)).toString());
                
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

                blinds.setOpenTime((blinds.getOpenTime().plusSeconds(1)).toString());

            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        blindPanel.add(incrementSecondOpen, c);

        // Display the Open Time 
        JTextField openTimeDisplay = new JTextField(blinds.getOpenTime().toString(), SwingConstants.CENTER);
        openTimeDisplay.setFont(new Font("Silkscreen",Font.PLAIN,24));
        openTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 30;
        blindPanel.add(openTimeDisplay, c);

        // Button to decrement hour
        JButton decrementHourOpen = new JButton("˅");

        decrementHourOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setOpenTime((blinds.getOpenTime().minusHours(1)).toString());

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

                blinds.setOpenTime((blinds.getOpenTime().minusMinutes(1)).toString());

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

                blinds.setOpenTime((blinds.getOpenTime().minusSeconds(1)).toString());

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
        JLabel closeTime = new JLabel("Automatic Close Time", SwingConstants.CENTER);
        closeTime.setFont(new Font("Silkscreen", Font.PLAIN,24));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 1;
        c.gridy = 5;
        c.ipady = 0;
        c.gridwidth = 1;
        blindPanel.add(closeTime, c);

        // Button to increment hour
        JButton incrementHourClose = new JButton("^");

        incrementHourClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime((blinds.getCloseTime().plusHours(1)).toString());
                
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

                blinds.setCloseTime((blinds.getCloseTime().plusMinutes(1)).toString());
                
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

                blinds.setCloseTime((blinds.getCloseTime().plusSeconds(1)).toString());
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        c.ipady = 15;
        blindPanel.add(incrementSecondClose, c);

        // Display the Open Time 
        JTextField closeTimeDisplay = new JTextField("00:00:00", SwingConstants.CENTER);
        closeTimeDisplay.setFont(new Font("Silkscreen",Font.PLAIN,24));
        closeTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        c.ipady = 30;
        blindPanel.add(closeTimeDisplay, c);

        // Button to decrement hour
        JButton decrementHourClose = new JButton("˅");

        decrementHourClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                blinds.setCloseTime((blinds.getCloseTime().minusHours(1)).toString());
                
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

                blinds.setCloseTime((blinds.getCloseTime().minusMinutes(1)).toString());
                
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

                blinds.setCloseTime((blinds.getCloseTime().minusSeconds(1)).toString());
                
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
        openClose.setFont(new Font("Silkscreen",Font.PLAIN,24));

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

        // Creates a timer that runs FPS times every second
        timer = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                // Blinds Stuff
                openTimeDisplay.setText(blinds.getOpenTime().toString());
                closeTimeDisplay.setText(blinds.getCloseTime().toString());


                if (LocalTime.now().getHour()==blinds.getOpenTime().getHour() &&
                    LocalTime.now().getMinute()==blinds.getOpenTime().getMinute() && 
                    LocalTime.now().getSecond()==blinds.getOpenTime().getSecond() &&
                    blinds.getOpenStatus()==false) {
                    openClose.doClick();
                }
                else if (LocalTime.now().getHour()==blinds.getCloseTime().getHour() &&
                    LocalTime.now().getMinute()==blinds.getCloseTime().getMinute() && 
                    LocalTime.now().getSecond()==blinds.getCloseTime().getSecond() &&
                    blinds.getOpenStatus()) {
                    openClose.doClick();
                }

                // blinds display
                if (blinds.getOpenStatus() && blindPanel.isVisible()) {
                    blindsLabel.setIcon(blindsOpen);

                }
                else if (!blinds.getOpenStatus() && blindPanel.isVisible()) {
                    blindsLabel.setIcon(blindsClose);
                }

                if (blinds.getOpenStatus()) {
                    openClose.setText("Close");
                    
                }
                else if (!blinds.getOpenStatus()) {
                    openClose.setText("Open");
                }


            }

        } );
        timer.setRepeats(true);
        timer.start();


    }

    private void CameraScreen() {
        
        // Camera page
        cameraPanel = new JPanel();
        cameraPanel.setLayout(new BorderLayout());

        JPanel CameraViews = new JPanel(new GridLayout(15,1));

        // hardcoded for now

        ArrayList<String> locations = camera.GetAllLocations();
        ArrayList<JButton> locationButtons = new ArrayList();

        ActionListener LocationListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (int i = 0;i<locationButtons.size();i++) {
                    if (e.getSource()==locationButtons.get(i)) {
                        selected = locationButtons.get(i).getText();
                    }
                }
                
            }

        };

        for (int i = 0;i<locations.size();i++) {
            locationButtons.add(new JButton(locations.get(i)));
            selected = locations.get(i);
            locationButtons.get(i).setFont(new Font("Silkscreen",Font.PLAIN,24));
            locationButtons.get(i).addActionListener(LocationListener);
            CameraViews.add(locationButtons.get(i));

        }

        ImageIcon cameraPng = new ImageIcon("Assets/Devices/camera.png");
        JLabel cameraLabel = new JLabel(cameraPng);
        cameraPanel.add(cameraLabel, BorderLayout.EAST);

        JScrollPane cameraSelect = new JScrollPane(CameraViews,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cameraPanel.add(cameraSelect, BorderLayout.WEST);
        cameraSelect.setPreferredSize(new Dimension(200, 2));


        Timer time = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (FramesPassed!=0 && cameraPanel.isVisible()) {
                    // set image to be displayed
                    ImageIcon png = camera.ChangeCamera(selected);

                    // scale the image to the visible panel being displayed 
                    Image img = png.getImage();
                    Image resizeImg = img.getScaledInstance((int)cameraPanel.getBounds().getWidth()-200, (int)cameraPanel.getBounds().getHeight(), java.awt.Image.SCALE_SMOOTH);
                    ImageIcon rescaledImg = new ImageIcon(resizeImg);
                    png = rescaledImg;

                    // change the image
                    cameraLabel.setIcon(png);
                }


            }

        } );
        
        time.setRepeats(true);
        time.start();

    }

    private void SensorScreen() {

        c = new GridBagConstraints();
        
        // Sensor page
        sensorPanel = new JPanel();
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
        JLabel sensorOpenTime = new JLabel("Open Time", SwingConstants.CENTER);
        sensorOpenTime.setFont(new Font("Silkscreen",Font.PLAIN,24));
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
        JTextField s_openTimeDisplay = new JTextField("00:00:00", SwingConstants.CENTER);
        s_openTimeDisplay.setFont(new Font("Silkscreen",Font.PLAIN,24));
        s_openTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 30;
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
        JLabel s_closeTime = new JLabel("Close Time", SwingConstants.CENTER);
        s_closeTime.setFont(new Font("Silkscreen",Font.PLAIN,24));
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
        JTextField s_closeTimeDisplay = new JTextField("00:00:00", SwingConstants.CENTER);
        s_closeTimeDisplay.setFont(new Font("Silkscreen",Font.PLAIN,24));
        s_closeTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        c.ipady = 30;
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
        dismiss.setFont(new Font("Silkscreen",Font.PLAIN,24));

        dismiss.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                sensor.SetIsDismissed(true);
                
            }
            
        });

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

        // Creates a timer that runs FPS times every second
        timer = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {         

                // Sensor stuff
                if (sensor.MonitorDevice(blinds)) {
                    // dismiss.setVisible(true);
                }
                s_openTimeDisplay.setText(sensor.GetOpenTime().toString());
                s_closeTimeDisplay.setText(sensor.GetCloseTime().toString());

                if (alarm.GetIsBeeping()==false) {
                    dismiss.setVisible(false);
                    sensor.SetIsDismissed(false);
                }


            }

        });
        
        timer.setRepeats(true);
        timer.start();
    
    

    }

    private void SmokeDetectorScreen() {

        c = new GridBagConstraints();
        
        // SmokeDetector page
        smokePanel = new JPanel();
        smokePanel.setLayout(new GridLayout());
        
        /// Simply show the smoke detector since it is uninteractable 
        // Sample picture to display the smokey detector 
        ImageIcon smokePngOn = new ImageIcon("Assets/Devices/SmokeyYes.png");
        ImageIcon smokePngOff = new ImageIcon("Assets/Devices/SmokeyNo.png");
        JLabel smokeLabel = new JLabel(smokePngOff);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        smokePanel.add(smokeLabel, c);

        // Creates a timer that runs FPS times every second
        timer = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Smoke Detector Stuff
                int rand = ((int) (Math.random() * 1000 + 1));

                // probably change this to a function in smoke detector
                if (rand==1) {
                    smokey.SetIsSmokey(true);
                } 
                if (smokey.GetIsSmokey()) {
                    alarm.TriggerAlarm("Theres a fire in the house!",smokey);
                    smokeLabel.setIcon(smokePngOn);
                }
                else {
                    smokeLabel.setIcon(smokePngOff);
                }



            }

        } );
        timer.setRepeats(true);
        timer.start();

    }

    private void CoffeeMachineScreen() {

        c = new GridBagConstraints();

        int rand = ((int) (Math.random() * 20 + 10));
        coffeeMachine.Set(COMMAND_SET.BEAN_ADD, "" + rand);

        coffeePanel = new JPanel();
        coffeePanel.setLayout(new GridBagLayout());

        ImageIcon coffeePng = new ImageIcon("Assets/Devices/coffeeMachine.png");
        ImageIcon coffeePng1 = new ImageIcon("Assets/Devices/CoffeeMachine1.png");
        ImageIcon coffeePng2 = new ImageIcon("Assets/Devices/CoffeeMachine2.png");
        ImageIcon coffeePng3 = new ImageIcon("Assets/Devices/CoffeeMachine3.png");
        JLabel coffeeLabel = new JLabel(coffeePng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        coffeePanel.add(coffeeLabel, c);

        // Text to show Automatic Open Time
        JLabel brewTime = new JLabel("Brew Time", SwingConstants.CENTER);
        brewTime.setFont(new Font("Silkscreen",Font.PLAIN,24));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 50;
        c.gridwidth = 3;
        coffeePanel.add(brewTime, c);

        // Button to increment hour
        JButton brewIncHour = new JButton("^");

        brewIncHour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                coffeeMachine.Set(COMMAND_SET.BEAN_MAKETIME, LocalTime.parse(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME)).plusHours(1).toString());
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(25,160,0,160);
        coffeePanel.add(brewIncHour, c);

        // Button to increment minute
        JButton brewIncMinute = new JButton("^");

        brewIncMinute.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                coffeeMachine.Set(COMMAND_SET.BEAN_MAKETIME, LocalTime.parse(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME)).plusMinutes(1).toString());
                
            }
            
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        coffeePanel.add(brewIncMinute, c);

        // Button to increment second
        JButton brewIncSec = new JButton("˄");

        brewIncSec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                coffeeMachine.Set(COMMAND_SET.BEAN_MAKETIME, LocalTime.parse(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME)).plusSeconds(1).toString());
                
            }
            
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipady = 15;
        coffeePanel.add(brewIncSec, c);

        // Display the Open Time 
        JTextField brewTimeDisplay = new JTextField("00:00:00", SwingConstants.CENTER);
        brewTimeDisplay.setFont(new Font("Silkscreen",Font.PLAIN,24));
        brewTimeDisplay.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,160,0,160);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 30;
        coffeePanel.add(brewTimeDisplay, c);

        // Button to decrement hour
        JButton brewDecHour = new JButton("˅");

        brewDecHour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                coffeeMachine.Set(COMMAND_SET.BEAN_MAKETIME, LocalTime.parse(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME)).minusHours(1).toString());
                
            }
            
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        coffeePanel.add(brewDecHour, c);

        // Button to decrement hour
        JButton brewDecMinute = new JButton("˅");

        brewDecMinute.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                coffeeMachine.Set(COMMAND_SET.BEAN_MAKETIME, LocalTime.parse(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME)).minusMinutes(1).toString());
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        coffeePanel.add(brewDecMinute, c);


        // Button to decrement second
        JButton brewDecSec = new JButton("˅");

        brewDecSec.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                coffeeMachine.Set(COMMAND_SET.BEAN_MAKETIME, LocalTime.parse(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME)).minusSeconds(1).toString());
                
            }
            
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,160);
        coffeePanel.add(brewDecSec, c);

        JPanel BrewDayContainer = new JPanel(new GridLayout(8,1));

        JLabel days = new JLabel("Brew Days:");
        days.setFont(new Font("Silkscreen",Font.BOLD,24));
        JCheckBox sunday = new JCheckBox("Sunday");
        sunday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Sunday")>=0) {
            sunday.setSelected(true);
        }
        sunday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Sunday");
            }

        });

        JCheckBox monday = new JCheckBox("Monday");
        monday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Monday")>=0) {
            monday.setSelected(true);
        }
        monday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Monday");
            }

        });

        JCheckBox tuesday = new JCheckBox("Tuesday");
        tuesday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Tuesday")>=0) {
            tuesday.setSelected(true);
        }
        tuesday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Tuesday");
            }

        });

        JCheckBox wednesday = new JCheckBox("Wednesday");
        wednesday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Wednesday")>=0) {
            wednesday.setSelected(true);
        }
        wednesday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Wednesday");
            }

        });

        JCheckBox thursday = new JCheckBox("Thursday");
        thursday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Thursday")>=0) {
            thursday.setSelected(true);
        }
        thursday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Thursday");
            }

        });

        JCheckBox friday = new JCheckBox("Friday");
        friday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Friday")>=0) {
            friday.setSelected(true);
        }
        friday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Friday");
            }

        });

        JCheckBox saturday = new JCheckBox("Saturday");
        saturday.setFont(new Font("Silkscreen",Font.PLAIN,18));
        if (coffeeMachine.GetBrewDays().indexOf("Saturday")>=0) {
            saturday.setSelected(true);
        }
        saturday.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                coffeeMachine.Set(COMMAND_SET.BEAN_DAYS, "Saturday");
            }

        });

        BrewDayContainer.add(days);
        BrewDayContainer.add(sunday);
        BrewDayContainer.add(monday);
        BrewDayContainer.add(tuesday);
        BrewDayContainer.add(wednesday);
        BrewDayContainer.add(thursday);
        BrewDayContainer.add(friday);
        BrewDayContainer.add(saturday);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,160,25,0);
        coffeePanel.add(BrewDayContainer, c);



        JComboBox<String> beanTypes = new JComboBox<String>();
        beanTypes.setFont(new Font("Silkscreen",Font.PLAIN,24));

        ArrayList<String> AllBeanTypes = coffeeMachine.GetFlavours();
        for (int i = 0;i<AllBeanTypes.size();i++) {
            beanTypes.addItem(AllBeanTypes.get(i));
        }
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.ipady = 15;
        c.insets = new Insets(0,80,25,80);
        coffeePanel.add(beanTypes, c);

        JPanel remainingBrewTimeDisplay = new JPanel(new GridLayout(2,1));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 5;
        c.gridwidth = 1;
        c.ipady = 30;
        c.insets = new Insets(0,0,25,160);

        JLabel remBrew = new JLabel("Remaining Brew Time");
        remBrew.setFont(new Font("Silkscreen",Font.BOLD,20));
        remainingBrewTimeDisplay.add(remBrew);

        JTextField remaining = new JTextField("00:00:00", SwingConstants.CENTER);
        remaining.setEditable(false);
        remaining.setFont(new Font("Silkscreen",Font.PLAIN,24));
        remainingBrewTimeDisplay.add(remaining);

        coffeePanel.add(remainingBrewTimeDisplay, c);



        Timer time = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (coffeeMachine.GetBrewDays().toLowerCase().indexOf(LocalDate.now().getDayOfWeek().toString().toLowerCase())>=0) {
                    coffeeMachine.Check();
                }

                brewTimeDisplay.setText(coffeeMachine.Get(COMMAND_GET.BEAN_MAKETIME).toString());
                remaining.setText(coffeeMachine.Get(COMMAND_GET.BEAN_BREWTIMELEFT));

                if (coffeeMachine.Get(COMMAND_GET.BEAN_BREWTIMELEFT).charAt(0)=='2') {
                    remaining.setText("00:00:00");
                }
                if (coffeeMachine.GetIsOn() && (FramesPassed >= (FPS*(2.0/3.0))) && coffeePanel.isVisible()) {
                    coffeeLabel.setIcon(coffeePng1);
                }
                else if (coffeeMachine.GetIsOn() && (FramesPassed >= (FPS*(1.0/3.0))) && coffeePanel.isVisible()) {
                    coffeeLabel.setIcon(coffeePng2);
                }
                else if (coffeeMachine.GetIsOn() && coffeePanel.isVisible()) {
                    coffeeLabel.setIcon(coffeePng3);
                }
                else if (coffeePanel.isVisible()) {
                    coffeeLabel.setIcon(coffeePng);
                }
            }

        } );
        time.setRepeats(true);
        time.start();

    }

    private void ShowerScreen() {

        c = new GridBagConstraints();
        
        
        // Shower page
        showerPanel = new JPanel();
        showerPanel.setLayout(new GridBagLayout());

        // Sample picture to display the shower 
        ImageIcon showerPng = new ImageIcon("Assets/Devices/ShowerOff.png");
        ImageIcon showerOn1Png = new ImageIcon("Assets/Devices/ShowerOn1.png");
        ImageIcon showerOn2Png = new ImageIcon("Assets/Devices/ShowerOn2.png");
        ImageIcon showerOn3Png = new ImageIcon("Assets/Devices/ShowerOn3.png");
        JLabel showerLabel = new JLabel(showerPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        showerPanel.add(showerLabel, c);

        // Text to show patterns
        JLabel temperText = new JLabel("Temperature C", SwingConstants.CENTER);
        temperText.setFont(new Font("Silkscreen",Font.PLAIN,24));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        showerPanel.add(temperText, c);

        // Text to show patterns
        JLabel patternsText = new JLabel("Head Pattern", SwingConstants.CENTER);
        patternsText.setFont(new Font("Silkscreen",Font.PLAIN,24));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        showerPanel.add(patternsText, c);


        JComboBox<String> headPatterns = new JComboBox<String>();
        headPatterns.setFont(new Font("Silkscreen",Font.PLAIN,24));
        for (int i = 0;i<shower.GetPatternCount();i++) {
            headPatterns.addItem(shower.GetHeadPattern(i));
            if (shower.Get(COMMAND_GET.SHOWER_HEADTYPE).equals(shower.GetHeadPattern(i))) {
                headPatterns.setSelectedItem(i);
            }
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
        startStopShower.setFont(new Font("Silkscreen",Font.PLAIN,24));

        if (shower.Get(COMMAND_GET.SHOWER_STATE).equals("true")) {
            startStopShower.setText("Stop");
        }

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


        JSpinner temper = new JSpinner(new SpinnerNumberModel(Double.parseDouble(shower.Get(COMMAND_GET.SHOWER_TEMPERATURE)),10.0 ,30.0,0.1));
        temper.setFont(new Font("Silkscreen",Font.PLAIN,24));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,100,0,100);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 20;
        c.ipady = 25;
        c.gridwidth = 1;
        showerPanel.add(temper, c);

        // Creates a timer that runs FPS times every second
        timer = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
 
                // Shower Stuff
                shower.Set(COMMAND_SET.SHOWER_TEMPERATURE,(temper.getValue().toString()));
                temper.setValue(Double.parseDouble(shower.Get(COMMAND_GET.SHOWER_TEMPERATURE)));

                shower.Set(COMMAND_SET.SHOWER_HEADTYPE, headPatterns.getSelectedItem()+"");

                // animate shower
                if (shower.Get(COMMAND_GET.SHOWER_STATE).equals("true") && (FramesPassed >= (FPS*(2.0/3.0))) && showerPanel.isVisible()) {
                    showerLabel.setIcon(showerOn3Png);
                }
                else if (shower.Get(COMMAND_GET.SHOWER_STATE).equals("true") && (FramesPassed >= (FPS*(1.0/3.0))) && showerPanel.isVisible()) {
                    showerLabel.setIcon(showerOn2Png);
                }
                else if (shower.Get(COMMAND_GET.SHOWER_STATE).equals("true") && showerPanel.isVisible()) {
                    showerLabel.setIcon(showerOn1Png);
                }
                else if (showerPanel.isVisible()) {
                    showerLabel.setIcon(showerPng);
                }


            }

        } );
        timer.setRepeats(true);
        timer.start();

    }

    private void ThermostatScreen() {

        c = new GridBagConstraints();
        
        
        // Thermostat page
        thermoPanel = new JPanel();
        thermoPanel.setLayout(new GridBagLayout());

        // image to display the thermostat
        ImageIcon thermPng = new ImageIcon("Assets/Devices/thermostat.png");
        JLabel thermLabel = new JLabel(thermPng);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        thermoPanel.add(thermLabel, c);

        // Text to display units 
        JLabel unitsText = new JLabel("Temperature Units", SwingConstants.CENTER);
        unitsText.setFont(new Font("Silkscreen",Font.PLAIN,18));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(unitsText, c);

        // Text to Temperature 
        JLabel tTemperText = new JLabel("Target Temperature", SwingConstants.CENTER);
        tTemperText.setFont(new Font("Silkscreen",Font.PLAIN,18));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(tTemperText, c);

        // Text to humidity 
        JLabel tHumidText = new JLabel("Target Humidity", SwingConstants.CENTER);
        tHumidText.setFont(new Font("Silkscreen",Font.PLAIN,18));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(tHumidText, c);

        // drop down menu where user can change the temperature unit
        JComboBox<String> units = new JComboBox<String>();
        units.setFont(new Font("Silkscreen",Font.PLAIN,24));
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

        // spinner component to allow the user to change the target temperature
        JSpinner thermTemper = new JSpinner(new SpinnerNumberModel(0.0,-20.0 ,150.0,0.1));
        thermTemper.setFont(new Font("Silkscreen",Font.PLAIN,24));
        thermTemper.setValue(thermostat.getTargetTemp());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,100,0,100);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 20;
        c.ipady = 25;
        c.gridwidth = 1;
        thermoPanel.add(thermTemper, c);

        // spinner component to allow user to change the humidity
        JSpinner thermHumid = new JSpinner(new SpinnerNumberModel(0.0,-50 ,50.0,0.1));
        thermHumid.setFont(new Font("Silkscreen",Font.PLAIN,24));
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
        JLabel cTemperText = new JLabel("Current Temperature", SwingConstants.CENTER);
        cTemperText.setFont(new Font("Silkscreen",Font.PLAIN,18));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 3;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cTemperText, c);

        // Text to humidity 
        JLabel cHumidText = new JLabel("Current Humidity", SwingConstants.CENTER);
        cHumidText.setFont(new Font("Silkscreen",Font.PLAIN,18));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 3;
        c.ipady = 50;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cHumidText, c);

        // Display current temperature
        JTextField cTemp = new JTextField("1", SwingConstants.CENTER);
        cTemp.setFont(new Font("Silkscreen",Font.PLAIN,24));
        cTemp.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 4;
        c.ipady = 30;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cTemp, c);

        // Display current humidity
        JTextField cHumid = new JTextField("1", SwingConstants.CENTER);
        cHumid.setFont(new Font("Silkscreen",Font.PLAIN,24));
        cHumid.setEditable(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 4;
        c.ipady = 30;
        c.ipadx = 100;
        c.gridwidth = 1;
        thermoPanel.add(cHumid, c);

        // Creates a timer that runs FPS times every second
        timer = new Timer(MS_PER_SECOND/FPS, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                DecimalFormat df = new DecimalFormat("#.#");

                // Thermostat Stuff
                thermostat.SetTargetTemperature((Double)thermTemper.getValue());
                thermostat.SetTargetHumidity((Double)thermHumid.getValue());

                if (units.getSelectedItem()=="C") {
                    if (!thermostat.SetUnit(true).equals(STATES.ERROR_INVALID_UNIT)) {
                        cTemp.setText(""+thermostat.getTemp());
                        thermTemper.setValue(thermostat.getTargetTemp());
                    }
                }
                else if (units.getSelectedItem()=="F") {
                    if (!thermostat.SetUnit(false).equals(STATES.ERROR_INVALID_UNIT)) {
                        cTemp.setText(""+thermostat.getTemp());
                        thermTemper.setValue(thermostat.getTargetTemp());
                    }
                }

                
                // Randomly change values
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

                thermHumid.setValue(Double.parseDouble(df.format(thermHumid.getValue())));
                thermTemper.setValue(Double.parseDouble(df.format(thermTemper.getValue())));

                cTemp.setText(String.format("%.1f", +thermostat.getTemp()));
                cHumid.setText(String.format("%.1f", +thermostat.getHumidity()));

                if ((double)thermHumid.getValue()<(-50)) {
                    thermHumid.setValue(-50);
                }
                if ((double)thermHumid.getValue()>(50)) {
                    thermHumid.setValue(50);
                }
                if ((double)thermTemper.getValue()<(-20)) {
                    thermTemper.setValue(-20);
                }
                if ((double)thermTemper.getValue()>(150)) {
                    thermTemper.setValue(150);
                }


                // need to do actual vs set calculations here



            }

        } );
        timer.setRepeats(true);
        timer.start();

    }




}
