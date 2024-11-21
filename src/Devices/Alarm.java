package Devices;
import java.util.ArrayList;

import Devices.*;
import Managers.AlertManager;

public class Alarm extends Device {
    private boolean isBeeping;
    private int beepDelay;
    private Device sender;

    // constructors
    public Alarm() {

        // set safe blank default values for the object
        this.isBeeping = false;
        this.beepDelay = 1;
        this.type = DEVICE_TYPE.ALARM;
        this.sender = null;

    }

    public Alarm(boolean isBeeping, int beepDelay) {

        // set values to passed in parameters
        this.isBeeping = isBeeping;
        this.beepDelay = beepDelay;
        this.type = DEVICE_TYPE.ALARM;
        this.sender = null;

    }

    // Beeps every (beepDelay) runs

    // this requires functionality with WindowManager and will be implemented later
    public boolean Beep() {

        // alarm cant beep if isBeeping is false 

        // if sensor dismissed then stop the alarm 
        if (sender!=null) {
            if (sender.Get(COMMAND_GET.SENSOR_IS_DISMISSED).equals("true")) {
                StopAlarm();
            }
        }

        else if (!this.isBeeping) {
            return false;
        }

        // probably have a Thread.sleep(beepDelay*1000) either here or WindowManager;


        return true;
    }

    public void TriggerAlarm(String alert, Device sender) {
        if (!this.isBeeping) {
            this.isBeeping = true;
            this.sender = sender;
            new AlertManager(alert, this);
        }
    }

    public void StopAlarm() {
        this.isBeeping = false;
        this.sender = null;
    }

    public boolean IsSender(Device sender) {
        return (this.sender==sender);
    }

    // Attaches a Sensor to the alarm to check for isdismissed and untrigger the alarm 

    // file read
    public boolean readDataFromFile(String line) {

        // get values of device 
        int o_beepDelay = this.beepDelay;
        boolean o_isBeeping = this.isBeeping;

        // Split values from comma seperated values from line
        String[] values = line.split(",");

        // return false if incorrect amount of values are given 
        if (values.length!=2) {
            return false;
        }

        // set isBeeping value
        if (values[0].equals("0")) {
            this.isBeeping = false;
        }
        else if (values[0].equals("1")) {
            this.isBeeping = true;
        }
        else {

            // reset values
            this.beepDelay = o_beepDelay;
            this.isBeeping = o_isBeeping;

            return false;
        }

        // set beepDelay value
        try {
            int val = Integer.parseInt(values[1]);
            if (val>0) {
                this.beepDelay = val;
            } 
            else {

                // reset values
                this.beepDelay = o_beepDelay;
                this.isBeeping = o_isBeeping;

                return false;
            }
        }
        catch (Exception parseError) {

            // reset values
            this.beepDelay = o_beepDelay;
            this.isBeeping = o_isBeeping;
            
            return false;
        }


        return true;

    }

    // getters 
    public boolean GetIsBeeping() {
        return this.isBeeping;
    }
    public int GetBeepDelay() {
        return this.beepDelay;
    }

    // setters
    public void SetIsBeeping(boolean isBeeping) {
        this.isBeeping = isBeeping;
    }
    public void SetBeepDelay(int beepDelay) {
        this.beepDelay = beepDelay;
    }

    // inheritied methods 
    public boolean Set(COMMAND_SET param, String value) {

        // set value for beepDelay
        if (param==COMMAND_SET.ALARM_IS_BEEPING) {

            // set isbeeping to false if false passed in
            if (value.toLowerCase().equals("false") || value.equals("0")) {
                this.SetIsBeeping(false);
                return true;
            }

            // set value to true if true passed in
            else if (value.toLowerCase().equals("true") || value.equals("1")) {
                this.SetIsBeeping(true);
                return true;
            }
        }

        // set value for beepDelay
        if (param==COMMAND_SET.ALARM_BEEP_DELAY) {

            // parse number passed in and set value
            try {

                // parse
                int val = Integer.parseInt(value);

                // set beep delay if the value passed in was greater than 0
                if (val>1) {
                    SetBeepDelay(val);
                    return true;
                }
            }

            // value passed in was not a number
            catch (Exception parseError) {
                return false;
            }

        }

        // Set command failed, means invalid command or value was passed in
        return false;

    }

    public String Get(COMMAND_GET param) {

        // Get beepDelay
        if (param==COMMAND_GET.ALARM_BEEP_DELAY) {
            // turn beepDelay into a string
            return ("" + this.beepDelay);
        }

        // get isBeeping
        if (param==COMMAND_GET.ALARM_IS_BEEPING) {
            if (isBeeping) {
                return "true";
            }
            return "false";
        }

        return null;
    }

    public String Call(COMMAND_CALL param, String args) {

        if (param==COMMAND_CALL.START) {
            TriggerAlarm(args, null);
        }
        else if (param==COMMAND_CALL.STOP) {
            StopAlarm();
        }
        else if (param==COMMAND_CALL.BEEP) {
            Beep();
        }
        else if (param==COMMAND_CALL.READ_FROM_FILE) {
            readDataFromFile(args);
        }


        return null;

    }

    public String Check() {
        if (this==null) {
            return STATES.ERROR_NO_BEEP.toString();
        }
        return STATES.GOOD.toString();   
    }
}
