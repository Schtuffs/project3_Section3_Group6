package Devices;

import java.time.LocalTime;

public class Blinds extends Device {
    private boolean isOpen;
    private LocalTime openTime, closeTime;

    public Blinds(LocalTime oTime, LocalTime cTime) {}
    
    // Inherited methods
    public String Check() { return "good"; }
    
    // Allows remote control of blinds
    public boolean Open() { return true; }
    public boolean Close() { return true; }
}
