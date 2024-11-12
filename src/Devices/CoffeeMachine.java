package Devices;

import java.time.LocalTime;

public class CoffeeMachine extends Device {
    private boolean isOn;
    private double amount;
    private LocalTime makeTime;
    private String flavour;

    public CoffeeMachine(double amount, LocalTime makeTime, String flavour) {}

    // Inherited methods
    public STATES Check() { return STATES.GOOD; }
    
    // Start coffee machine with specified stats
    public boolean Start() { return true; }

    // Turns off the coffee machine
    public boolean Stop() { return true; }
}
