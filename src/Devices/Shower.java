package Devices;

import java.util.ArrayList;

public class Shower extends Device {
    private boolean isOn;
    private double temperature;
    private String headPattern;
    private ArrayList<String> allHeadPatterns;

    public Shower() {
        this.temperature = 15.;

        // Shower starts off
        this.isOn = false;

        // Add head patterns
        this.allHeadPatterns = new ArrayList<>();
        this.allHeadPatterns.add("high efficiency");
        this.allHeadPatterns.add("bubble");
        this.allHeadPatterns.add("rain");
        this.allHeadPatterns.add("massage");
        this.allHeadPatterns.add("soft drench");
        this.allHeadPatterns.add("jet");
        this.headPattern = this.allHeadPatterns.get(0);
    }

    // Inherited methods
    public String Check() {
        return STATES.GOOD.toString();
    }
    
    public boolean Set(COMMAND_SET param, String value) {
        // switch (param) {
        // case COMMAND_SET.SHOWER_TEMPERATURE:
        //     return this.SetTemperature(value);
        // case COMMAND_SET.SHOWER_HEADTYPE:
        //     return this.SetHeadPattern(value);
        // default:
        //     return false;
        // }
        if (param == COMMAND_SET.SHOWER_TEMPERATURE) {
            return this.SetTemperature(value);
        } else if (param == COMMAND_SET.SHOWER_HEADTYPE) {
            return this.SetHeadPattern(value);
        } else {
            return false;
        }
    }

    private boolean SetTemperature(String newTemp) {
        double temp;
        try {
            temp = Double.parseDouble(newTemp);
        }
        catch (NumberFormatException e) {
            return false;
        }

        if (10 <= temp && temp <= 30) {
            // Increase bean count by newly inputted value
            this.temperature = temp;
            return true;
        }
        return false;
    }

    private boolean SetHeadPattern(String newPattern) {
        for(String pat : this.allHeadPatterns) {
            if (newPattern.equalsIgnoreCase(pat)) {
                this.headPattern = newPattern.toLowerCase();
                return true;
            }
        }
        return false;
    }

    public boolean SetHeadPattern(int index) {
        try {
            this.headPattern = this.allHeadPatterns.get(index);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public String GetHeadPattern(int index) {
        try {
            return this.allHeadPatterns.get(index);
        }
        catch (Exception e) {
            return "";
        }
    }

    public int GetPatternCount() {
        return this.allHeadPatterns.size();
    }
    
    public String Get(COMMAND_GET param) {
        // switch (param) {
        // case COMMAND_GET.SHOWER_TEMPERATURE:
        //     return Double.toString(this.temperature);
        // case COMMAND_GET.SHOWER_HEADTYPE:
        //     return this.headPattern;
        // default:
        //     return COMMAND_GET.UNKNOWN.toString();
        // }
        if (param == COMMAND_GET.SHOWER_TEMPERATURE) {
            return Double.toString(this.temperature);
        } else if (param == COMMAND_GET.SHOWER_HEADTYPE) {
            return this.headPattern;
        } else {
            return COMMAND_GET.UNKNOWN.toString();
        }
    }

    public String Call(COMMAND_CALL param, String args) {
        // Default good
        STATES state = STATES.GOOD;

        // switch(param) {
        // case COMMAND_CALL.START:
        //     state = this.Start();
        //     break;
        // case COMMAND_CALL.STOP:
        //     state = this.Stop();
        //     break;
        // default:
        //     state = STATES.ERROR_UNKNOWN;
        //     break;
        // }


        if (param == COMMAND_CALL.START) {
            state = this.Start();
        } else if (param == COMMAND_CALL.STOP) {
            state = this.Stop();
        } else {
            state = STATES.ERROR_UNKNOWN;
        }

        return state.toString();
    }

    // Allows remote start/stop
    private STATES Start() {
        if (!this.isOn) {
            this.isOn = true;
            return STATES.GOOD;
        }
        return STATES.ERROR_NO_START;
    }

    private STATES Stop() {
        if (this.isOn) {
            this.isOn = false;
            return STATES.GOOD;
        }
        return STATES.ERROR_NO_STOP;
    }
}
