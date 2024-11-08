package Devices;
public abstract class Device {
    protected int type, Id;

    // Checks device is satisfied with current conditions
    // Ex. Windows are closed between close time and open time
    // Ex. Smoke detector detects no smoke
    abstract public String Check();
}
