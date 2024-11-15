package Devices;

public class Alarm {
    private boolean isBeeping;
    private int beepDelay;

    // constructors
    public Alarm() {

        // set safe blank default values for the object
        this.isBeeping = false;
        this.beepDelay = 1;

    }

    public Alarm(boolean isBeeping, int beepDelay) {

        // set values to passed in parameters
        this.isBeeping = isBeeping;
        this.beepDelay = beepDelay;

    }

    // Beeps every (beepDelay) runs

    // this requires functionality with WindowManager and will be implemented later
    public void Beep() {
        // probably have a Thread.sleep(beepDelay*1000) either here or WindowManager;
    }

    public void TriggerAlarm(String alert) {
        if (!this.isBeeping) {
            this.isBeeping = true;
            new AlertManager(this, alert);
        }
    }

    public void StopAlarm() {
        this.isBeeping = false;
    }

    // getters 
    public boolean GetIsBeeping() {
        return this.isBeeping;
    }
    public int GetBeepDelay() {
        return this.beepDelay;
    }

    // setters
    public void SetIsBeeping(boolean isBeeping) {
        this.isBeeping = isBeeping;
    }
    public void SetBeepDelay(int beepDelay) {
        this.beepDelay = beepDelay;
    }
}
