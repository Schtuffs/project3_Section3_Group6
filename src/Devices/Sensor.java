package Devices;

import java.time.LocalTime;

public class Sensor extends Device {
    private boolean isOpen, isDismissed;
    private LocalTime openTime, closeTime;

    public Sensor() {}

    // Inherited methods
    public STATES Check() { return STATES.GOOD; }
}
