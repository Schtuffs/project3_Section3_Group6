package Devices;
public abstract class Device {
    protected int type, Id;
    protected enum STATES {
        GOOD,
        // Smoke detector
        SMOKE_DETECTED,
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
    }

    public enum COMMAND_SET {
        // Coffee Machine, bean related
        BEAN_FLAVOUR, BEAN_NEW, BEAN_ADD, BEAN_BREWTIME, BEAN_DAYS,
        // Shower
        SHOWER_TEMPERATURE, SHOWER_HEADTYPE,
        // All
        UNKNOWN
    }

    public enum COMMAND_GET {
        // Coffee Machine, bean related
        BEAN_FLAVOUR, BEAN_LEFT, BEAN_BREWTIME, BEAN_BREWTIMELEFT, BEAN_BREWCOST, BEAN_DAYS,
        // Shower
        SHOWER_TEMPERATURE, SHOWER_HEADTYPE,
        // All
        UNKNOWN
    }

    public enum COMMAND_CALL {
        // All
        START, STOP
    }

    // Checks device is satisfied with current conditions
    // Ex. Windows are closed between close time and open time
    // Ex. Smoke detector detects no smoke
    abstract public STATES Check();

    // Sets value for object based on command param
    abstract public boolean Set(COMMAND_SET param, String value);

    // Gets variable value from device based on command param
    abstract public String Get(COMMAND_GET param);

    // Calls function from object based on command param, optional arguments to pass to function
    abstract public String Call(COMMAND_CALL param, String args);
}
