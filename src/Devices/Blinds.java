package Devices;

import java.time.LocalTime;
//import static java.time.temporal.ChronoUnit.NANOS;

public class Blinds extends Device {
    private boolean isOpen;
    private static LocalTime openTime;
    private static LocalTime closeTime;

    public Blinds(LocalTime oTime, LocalTime cTime) {
      
        setCloseTime(cTime);
        setOpenTime(oTime);

    }
    
    // Inherited methods
    public STATES Check() { 

        LocalTime current = LocalTime.now();
        current = current.minusNanos(current.getNano());

        if (current.equals(Blinds.openTime) && !this.isOpen) {
            // If not open, open
            if (!this.isOpen) {
                return this.Open();
            }
            
        }
        
        return STATES.GOOD; }
    
        public void setCloseTime(LocalTime cTime){
            Blinds.closeTime=cTime;
        }

        public void setOpenTime(LocalTime oTime){
            Blinds.openTime=oTime;
        }

    // Allows remote control of blinds
    public STATES Open() { 
        this.isOpen = true;
        //void public animationFunctionOrSomething();
        return STATES.GOOD; }

    public STATES Close() { 
        this.isOpen = false;
        //void public animationFunctionOrSomething();
        return STATES.GOOD; }
}
