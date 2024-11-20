package Managers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import Devices.*;

public class AlertManager extends JFrame implements ActionListener {

    // Constants related to the class
    private String TITLE = "Alarm";
    private String CLOSE_TEXT = "ok";
    private String ICON_PATH = "Assets/AlertManager/AlarmIcon.png";
    private int SCREEN_HEIGHT = 250;
    private int SCREEN_WIDTH = 500;

    private Alarm alarm;
    private String alert;

    private JButton okButton;
    private JLabel warningLabel;

    public AlertManager() {

        this.alarm = null;
        this.alert = "An error has occured";

        //  arrange the screen to be displayed by the gui
        ArrangeGUI();


    }

    public AlertManager(String alert, Alarm alarm) {

        this.alarm = alarm;
        this.alert = alert;

        //  arrange the screen to be displayed by the gui
        ArrangeGUI();
    }

    // this function is an extension of the constructor just to reduce rewritten code
    public void ArrangeGUI() {

        this.okButton = new JButton(CLOSE_TEXT);
        this.okButton.addActionListener(this);
        this.okButton.setVisible(true);

        this.warningLabel = new JLabel("<html><p style=\"width:250px\">"+alert+"</p></html>", SwingConstants.CENTER);
        this.warningLabel.setVisible(true);

        ImageIcon img = new ImageIcon(ICON_PATH);

        // scales the image down to the size of the JLabel comp
        Image newImg = img.getImage();
        Image scaledImg = newImg.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        img = scaledIcon;

        JLabel icon = new JLabel(img);

        // set the dimensions of the Alert Manager window which will be constant 
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setLayout(null);
        setResizable(false);
        setTitle(TITLE);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // place the GUI components on the screen
        this.okButton.setBounds(225,150,50,25);
        this.warningLabel.setBounds(125,25,375,150);
        icon.setBounds(20,50,80,80);

        this.add(this.okButton);
        this.add(this.warningLabel);
        this.add(icon);

    }

    // Calls beep function from alarm
    public boolean Beep() {
        if (!(alarm==null)) {
            return alarm.Beep();
        }
        return false;
    }

    // closes the pop-up window and deletes the class 
    public void CloseWindow() {
        this.dispose();
    }

    public void SetAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void SetAlert(String alert) {
        this.alert = alert;
    }

    public Alarm GetAlarm() {
        return this.alarm;
    }

    public String GetAlert() {
        return this.alert;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
        // if the ok button on the screen is pressed then close the pop-up window 
        if (e.getSource()==(this.okButton)) {
            CloseWindow();
        }
    }
}
