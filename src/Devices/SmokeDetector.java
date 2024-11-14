package Devices;

public class SmokeDetector extends Device {
    private boolean isSmokey;

    public SmokeDetector() {
        // initializes with no smoke detected because god i'd really hope there isn't!
        this.isSmokey = false;
    }

    // Inherited methods
    public STATES Check() { 
        
        // if smoke. return AHHH GET OUT GET OUT THERES SMOKE AHHHHH
        if ( isSmokey) return STATES.SMOKE_DETECTED;

        // otherwise no smoke
        return STATES.GOOD; 
    }
}
