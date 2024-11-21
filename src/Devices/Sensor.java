package Devices;

import java.time.LocalTime;

public class Sensor extends Device {
    private boolean isOpen, isDismissed;
    private LocalTime openTime, closeTime;
    private Alarm alarm;

    public Sensor() {

        // initiate all variables to a safe empty state
        this.alarm = null;
        this.isOpen = false;
        this.isDismissed = false;
        this.openTime = LocalTime.parse("06:00:00");
        this.closeTime = LocalTime.parse("18:00:00");
        this.type = DEVICE_TYPE.SENSOR;

    }

    public Sensor (boolean isOpen, boolean isDismissed, LocalTime openTime, LocalTime closeTime, Alarm alarm) {

        // set values to the passed in parameters
        this.isOpen = isOpen;
        this.isDismissed = isDismissed;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.alarm = alarm;
        this.type = DEVICE_TYPE.SENSOR;

    }

    public boolean readDataFromFile(String line) {

        // get current object variable values
        LocalTime o_openTime = this.openTime;
        LocalTime o_closeTime = this.closeTime;
        boolean o_isOpen = this.isOpen;
        boolean o_isDismissed = this.isDismissed;

        // Split values from comma seperated values from line
        String[] values = line.split(",");

        // return false if incorrect amount of values are given 
        if (values.length!=4) {
            return false;
        }

        // set isOpen value
        if (values[0].equals("0")) {
            this.isOpen = false;
        }
        else if (values[0].equals("1")) {
            this.isOpen = true;
        }
        else {

            // reset variables
            this.openTime = o_openTime;
            this.closeTime = o_closeTime;
            this.isOpen = o_isOpen;
            this.isDismissed = o_isDismissed;

            return false;
        }

        // set isDismissed value
        if (values[1].equals("0")) {
            this.isDismissed = false;
        }
        else if (values[1].equals("1")) {
            this.isDismissed = true;
        }
        else {

            // reset variables
            this.openTime = o_openTime;
            this.closeTime = o_closeTime;
            this.isOpen = o_isOpen;
            this.isDismissed = o_isDismissed;

            return false;
        }

        // set open time value
        try {
            this.openTime = LocalTime.parse(values[2]);
        }
        catch (Exception parseError) {

            // reset variables
            this.openTime = o_openTime;
            this.closeTime = o_closeTime;
            this.isOpen = o_isOpen;
            this.isDismissed = o_isDismissed;

            return false;
        }

        // set close time value
        try {
            this.closeTime = LocalTime.parse(values[3]);
        }
        catch (Exception parseError) {

            // reset variables
            this.openTime = o_openTime;
            this.closeTime = o_closeTime;
            this.isOpen = o_isOpen;
            this.isDismissed = o_isDismissed;
            
            return false;
        }

        return true;
        

    }

    public boolean MonitorDevice(Blinds blinds) {

        /* Awaiting further implementation

        // check if blinds are open and time is between close time and open time
        if (blinds.getIsOpen ) {
            alarm.triggerAlarm();
        }
        
        // check if blinds are closed and time is between open time and close time
        else if (!blinds.getIsOpen ) {
            alarm.triggerAlarm();
        }
        */
        return false;
    }


     // getters for variables
     public boolean GetIsOpen() {
         return this.isOpen;
     }
     public boolean GetIsDismissed() {
         return this.isDismissed;
     }
     public LocalTime GetOpenTime() {
         return this.openTime;
     }
     public LocalTime GetCloseTime() {
         return this.closeTime;
     }

     // setters for variables
     public void SetIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
     }
     public void SetIsDismissed(boolean isDismissed) {
        this.isDismissed = isDismissed;
    }
    public void SetOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }
    public void SetCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }


     // Inherited methods

    public String Check() {
        if (this.openTime.equals(this.closeTime)) {
            return STATES.ERROR_INVALID_TIME.toString();
        }
        return STATES.GOOD.toString();
    }

    // Sets value for object based on command param
    // SENSOR_IS_OPEN, SENSOR_IS_DISMISSED, SENSOR_CLOSE_TIME, SENSOR__OPEN_TIME
    public boolean Set(COMMAND_SET param, String value) {

        if (param==COMMAND_SET.SENSOR_IS_OPEN) {

            // set isbeeping to false if false passed in
            if (value.toLowerCase().equals("false") || value.equals("0")) {
                this.SetIsOpen(false);
                return true;
            }

            // set value to true if true passed in
            else if (value.toLowerCase().equals("true") || value.equals("1")) {
                this.SetIsOpen(true);
                return true;
            }
        }

        else if (param==COMMAND_SET.SENSOR_IS_DISMISSED) {

            // set isbeeping to false if false passed in
            if (value.toLowerCase().equals("false") || value.equals("0")) {
                this.SetIsDismissed(false);
                return true;
            }

            // set value to true if true passed in
            else if (value.toLowerCase().equals("true") || value.equals("1")) {
                this.SetIsDismissed(true);
                return true;
            }
        }

        else if (param==COMMAND_SET.SENSOR_OPEN_TIME) {
            try {
                SetOpenTime(LocalTime.parse(value));
                return true;
            }
            catch (Exception parseError) {
                return false;
            }
        }

        else if (param==COMMAND_SET.SENSOR_CLOSE_TIME) {
            try {
                SetCloseTime(LocalTime.parse(value));
                return true;
            }
            catch (Exception parseError) {
                return false;
            }
        }

        return false;
    }

    // Gets variable value from device based on command param
    public String Get(COMMAND_GET param) {

        if (param==COMMAND_GET.SENSOR_IS_OPEN) {
            if (isOpen) {
                return "true";
            } return "false";
        }
        else if (param==COMMAND_GET.SENSOR_IS_DISMISSED) {
            if (isDismissed) {
                return "true";
            } return "false";
        }
        else if (param==COMMAND_GET.SENSOR_OPEN_TIME) {
            return openTime+"";
        }
        else if (param==COMMAND_GET.SENSOR_CLOSE_TIME) {
            return closeTime+"";
        }


        return null;

    }

    // Calls function from object based on command param, optional arguments to pass to function
    public String Call(COMMAND_CALL param, String args) {

        
        if (param==COMMAND_CALL.READ_FROM_FILE) {
            readDataFromFile(args);
        }

        return null;
    }

}
