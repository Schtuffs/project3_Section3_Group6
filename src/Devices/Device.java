package Devices;
public abstract class Device {
    protected int type, Id;
    protected enum STATES {
        GOOD,
        // Alarm, Camera, CoffeeMachine, Shower
        ERROR_NO_START, ERROR_NO_STOP,
        // Alarm
        ERROR_NO_BEEP,
        // Blinds
        ERROR_NO_OPEN, ERROR_NO_CLOSE,
        // CoffeeMachine
        ERROR_NO_BREW, ERROR_NO_BEANS,
        // DeviceManager
        ERROR_READ, ERROR_WRITE,
        // Thermostat
        ERROR_INVALID_UNIT,
        // All
        ERROR_PARSE, ERROR_UNKNOWN
    };

    // Checks device is satisfied with current conditions
    // Ex. Windows are closed between close time and open time
    // Ex. Smoke detector detects no smoke
    abstract public STATES Check();
}
