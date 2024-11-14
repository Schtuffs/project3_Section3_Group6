// Dina Makhdoom :D

package Devices;

public class Thermostat extends Device {
    private static boolean celsius; // true == celsius, false == fahrenheit
    private double humidity, temperature, targetHumidity, targetTemperature;

    // Default constructor
    public Thermostat(boolean curUnit, double targetHumid, double targetTemp) {
        // Unit by default is in celsius 
        Thermostat.celsius = curUnit;
        // Humidity default is none
        this.targetHumidity = targetHumid;
        // Temperature default is 20
        this.targetTemperature = targetTemp;
    }

    // Inherited methods
    public STATES Check() { 
        return STATES.GOOD; 
    }
    
    // Change the unit of measurement for temperature
    public STATES SetUnit(boolean newUnit) { 
        // If the new unit is "false" and celsius wasn't false previously, then the unit changes to fahrenheit
        // and the temperature is converted using the celsius -> fahrenheit formula.
        if ( celsius == true && newUnit == false ) {
            // change to fahrenheit
            Thermostat.celsius = false;

            // calculate the new temperature
            ChangeTemperature(Thermostat.celsius);

            // return true to indicate that the unit change has been successfully executed.
            return STATES.GOOD;
        }

        // if the new unit is "true" and celsius was false to begin with (current unit is fahrenheit), the unit converts back to celisus
        // and changes it's state to reflect that
        if ( celsius == false && newUnit == true ) {
            // change back to celsius
            celsius = true;

            // calculate the new temperature
            ChangeTemperature(Thermostat.celsius);

            // return true to indicate that the unit change has been successfully executed.
            return STATES.GOOD;
        }

        // otherwise, the unit change has failed, return false
        return STATES.ERROR_UNKNOWN;
    }


    // Calculate the new temperature value based on the value of the unit of temperature
    private void ChangeTemperature(boolean newUnit) {
        // if the current unit is fahrenheit, convert the current temperature (assumed celsius) to fahrenheit
        if (!newUnit){
            this.temperature = ((9/5) * temperature + 32);
        }

        // if the current unit is celsius, convert the current temperature (assumed fahrenheit) to fahrenheit
        if (newUnit){
            this.temperature = ((5/9) * (temperature - 32));
        }
    }

    // Setters, but a little more fancy
    public STATES SetTargetTemperature(double target) { 

        // set the new temperature to the target
        // this can be implemented in a fancier way, but is out of scope for now.
        this.temperature = target;
        return STATES.GOOD; 
    }

    public STATES SetTargetHumidity(double target) { 
        this.humidity = target;
        return STATES.GOOD; 
    }
}
