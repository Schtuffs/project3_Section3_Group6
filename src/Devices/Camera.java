package Devices;

public class Camera extends Device {
    private boolean isOn;

    public Camera(boolean state) {}

    // Inherited methods
    public STATES Check() { return STATES.GOOD; }
    
    // Allows activation and deactivation of camera
    public boolean Start() { return true; }
    public boolean Stop() { return true; }
}
