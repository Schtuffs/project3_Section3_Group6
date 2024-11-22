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
    public String Check() { 

        LocalTime current = LocalTime.now();
        current = current.minusNanos(current.getNano());

        if (current.equals(Blinds.openTime) && !this.isOpen) {
            // If not open, open
            if (!this.isOpen) {
                return this.Open().toString();
            }
            
        }
        
        return STATES.GOOD.toString(); }
    
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

    @Override
    public boolean Set(COMMAND_SET param, String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Set'");
    }

    @Override
    public String Get(COMMAND_GET param) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Get'");
    }

    @Override
    public String Call(COMMAND_CALL param, String args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Call'");
    }
}
