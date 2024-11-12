package Devices;

public class Thermostat extends Device {
    private char currentUnit;
    private double humidity, temperature, targetHumidity, targetTemperature;

    public Thermostat(char curUnit, double targetHumid, double targetTemp) {}

    // Inherited methods
    public STATES Check() { return STATES.GOOD; }
    
    // Setters
    public boolean SetUnit(char newUnit) { return true; }
    private void ChangeTemperature(char newUnit) {}
    public boolean SetTargetTemperature(double target) { return true; }
    public boolean SetTargetHumidity(double target) { return true; }
}
