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

    }

    public Sensor (boolean isOpen, boolean isDismissed, LocalTime openTime, LocalTime closeTime, Alarm alarm) {

        // set values to the passed in parameters
        this.isOpen = isOpen;
        this.isDismissed = isDismissed;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.alarm = alarm;

    }

    public boolean readDataFromFile(String line) {

        // Split values from comma seperated values from line
        String[] values = line.Split(",");

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
            return false;
        }

        // set open time value
        this.openTime = LocalTime.parse(values[2]);

        // set close time value
        this.closeTime = LocalTime.parse(values[3]);

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
    }

     // Inherited methods
     public STATES Check() { return STATES.GOOD; }


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
         return this.class;
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


}
