package Managers;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import Devices.*;

// int POPUP_WIDTH = 500;
// int POPUP_HEIGHT = 150;

public class AlertManager extends JFrame implements ActionListener {

    private Alarm alarm;
    private String alert;

    private JButton okButton;
    private JLabel warningLabel;

    // default constructor for the AlertManager class (calling this will make a pop-up window)
    public AlertManager() {

        this.alarm = null;
        this.alert = "An error has occured";

        this.okButton = new JButton("ok");
        this.okButton.addActionListener(this);
        this.okButton.setVisible(true);

        this.warningLabel = new JLabel(alert, SwingConstants.CENTER);
        this.warningLabel.setVisible(true);

        ImageIcon img = new ImageIcon("src/Assets/AlertManagerIcon.png");
        //Image rescale = img.getImage();
        //rescale = rescale.getScaledInstance(80,80,80);
        //img = (ImageIcon)rescale;

        JLabel icon = new JLabel(img);

        // set the dimensions of the Alert Manager window which will be constant 
        setSize(500,250);
        setLayout(null);
        setResizable(false);
        setTitle("Alarm");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // place the GUI components on the screen
        this.okButton.setBounds(225,150,50,25);
        this.warningLabel.setBounds(100,75,300,50);
        icon.setBounds(20,50,80,80);

        this.add(okButton);
        this.add(warningLabel);
        this.add(icon);

    }

    // pararmetrized constuctor for the AlertManager class (calling this will make a pop-up window)
    public AlertManager(Alarm alarm, String alert) {

    }

    // Calls beep function from alarm
    public boolean Beep() {
        return this.alarm.Beep();
    }

    // closes the pop-up window and deletes the class 
    private void CloseWindow() {
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
