package Devices;

public class SmokeDetector extends Device {
    private boolean isSmokey;

    public SmokeDetector() {}

    // Inherited methods
    public STATES Check() { return STATES.GOOD; }
}
