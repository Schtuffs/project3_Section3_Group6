package Devices;

public class Shower extends Device {
    private boolean isOn;
    private double temperature;
    private String headPattern;

    public Shower(double temperature, String headPattern) {}

    // Inherited methods
    public STATES Check() { return STATES.GOOD; }

    // Allows remote start/stop
    public boolean Start() { return true; }
    public boolean Stop() {return true; }
}
