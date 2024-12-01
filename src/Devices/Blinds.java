package Devices;

import java.time.LocalTime;
//import static java.time.temporal.ChronoUnit.NANOS;
import java.time.format.DateTimeParseException;

public class Blinds extends Device {
    private boolean isOpen;
    private LocalTime openTime;
    private LocalTime closeTime;

    public Blinds(LocalTime oTime, LocalTime cTime) {
      
       // setCloseTime(cTime);
      //  setOpenTime(oTime);
      
        this.isOpen=false;
        this.openTime = oTime;
        this.closeTime = cTime;


    }

    
    // Inherited methods
    public String Check() { 

        LocalTime current = LocalTime.now();
        current = current.minusNanos(current.getNano());

        if (current.equals(this.openTime) && !this.isOpen) {
            // If not open, open
            if (!this.isOpen) {
                return this.Open().toString();
            }
            
        }
        return STATES.GOOD.toString();
     }
    
    public boolean setCloseTime(String cTime){
        try {
            LocalTime lt = LocalTime.parse(cTime);
            this.closeTime = lt;
        }
        catch (DateTimeParseException e) {
            return false;
        }

        return true;
     }

    public boolean setOpenTime(String oTime){
        try {
            LocalTime lt = LocalTime.parse(oTime);
            this.openTime = lt;
        }
        catch (DateTimeParseException e) {
            return false;
        }

        return true;
     }

    public LocalTime getOpenTime() {
        return this.openTime;
    }

    public LocalTime getCloseTime() {
        return this.closeTime;
    }

    public boolean getOpenStatus() {
        return this.isOpen;
    }

    public void setOpenStatus(boolean status){
        this.isOpen = status;
    }

    public void readDataFromFile(String line) {

        String[] values = line.split(",");

        if (values[0].equals("true")) {
            this.isOpen = true;
        } else {this.isOpen=false;}

        this.openTime = LocalTime.parse(values[1]);
        this.closeTime = LocalTime.parse(values[2]);


    }

    // Allows remote control of blinds
    public STATES Open() { 

        if(!this.isOpen){
            this.setOpenStatus(true);
            return STATES.GOOD; 
        }
        //void public animationFunctionOrSomething();
        return STATES.ERROR_NO_OPEN;
        }

    public STATES Close() { 

        if(this.isOpen){
            this.setOpenStatus(false);
            return STATES.GOOD;
        }
        //void public animationFunctionOrSomething();
        return STATES.ERROR_NO_OPEN; 
    }

    public boolean Set(COMMAND_SET param, String value) {
       
        if (param == COMMAND_SET.BLINDS_STATUS){

            if (value.toLowerCase()=="false"|| value.toLowerCase()=="0"){
                this.setOpenStatus(false);
                return false;
            }

            if (value.toLowerCase()=="true"|| value.toLowerCase()=="1"){
                this.setOpenStatus(true);
                return true;
            }
        }

        if (param == COMMAND_SET.BLINDS_CLOSE_TIME){
           return this.setCloseTime(value);
        }

        if (param == COMMAND_SET.BLINDS_OPEN_TIME){
           return this.setOpenTime(value);
        }

        return false;
    }

    public String Get(COMMAND_GET param) {
        
        String result = "";

        if(param == COMMAND_GET.BLINDS_STATUS){

            if(this.getOpenStatus()){
                result = "true";
            }

            if(!this.getOpenStatus()){
                result = "false";
            }
        }
        
        if(param == COMMAND_GET.BLINDS_CLOSE_TIME){

            result = this.getCloseTime().toString();
        }

        if(param == COMMAND_GET.BLINDS_OPEN_TIME){
            
            result = this.getOpenTime().toString();
        }

        return result;
    }

    @Override
    public String Call(COMMAND_CALL param, String args) {
        
        STATES result = STATES.GOOD;

        if(param == COMMAND_CALL.START){

            result = this.Open();
        }

        if(param == COMMAND_CALL.STOP){

            result = this.Close();
        }

        return result.toString();
    }
}
