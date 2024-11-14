package Devices;

import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.NANOS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoffeeMachine extends Device {
    // State of device
    private boolean isOn;

    // Coffee brewing time information
    
    // Make time is the time set by user
    private LocalTime userMakeTime;
    // Actual time the coffee needs to begin brewing at to be ready for user selected make time
    private LocalTime actualMakeTime;
    // Amount of time left in current brew cycle
    private LocalTime brewTimeLeft;
    // Time of last check for reducing brew time
    private LocalTime timeOfLastCheck;

    // Coffee bean variables
    
    // User selected flavour
    private String selectedFlavour;
    // All potential coffee types/flavours
    private ArrayList<String> allFlavours;
    // How many beans it takes to brew the coffee
    private Map<String, Double> beanBrewCost;
    // How many beans are remaining for each coffee type
    private Map<String, Double> beansRemaining;
    // How long each coffee type takes to brew
    private Map<String, LocalTime> brewTimes;

    public CoffeeMachine(LocalTime makeTime, String flavour, String[] everyFlavour, double[] totalBeans) {
        // Simple allocations and sets
        this.isOn = false;
        
        // Setup maps
        this.beanBrewCost = new HashMap<>();
        this.brewTimes = new HashMap<>();
        this.beansRemaining = new HashMap<>();

        // Initialize flavours
        this.allFlavours = new ArrayList<>();
        for (int i = 0; i < everyFlavour.length; i++) {
            this.allFlavours.add(everyFlavour[i]);
        }

        // Will be moved to file later
        this.allFlavours.add("espresso");
        this.allFlavours.add("latte");
        this.allFlavours.add("cappuccino");
        this.allFlavours.add("mocha");
        this.allFlavours.add("americano");
        // Add flavours to object, all lowercase
        for (String flav : everyFlavour) {
            this.allFlavours.add(flav.toLowerCase());
        }

        // Check if selected flavour is valid
        boolean inFlavours = false;
        for (String flav : this.allFlavours) {
            if (flavour.equalsIgnoreCase(flav)) {
                inFlavours = true;
                this.selectedFlavour = flavour.toLowerCase();
                break;
            }
        }
        // Auto set flavour to first in flavours list if invalid type is entered
        if (!inFlavours) {
            this.selectedFlavour = this.allFlavours.get(0);
        }

        // Preparing coffee bean cost
        double[] totals = { 0.4, 1.0, 1.0, 0.7, 0.4 };
        for (int i = 0; i < this.allFlavours.size(); i++) {
            // Check that totals has a cost in that index
            if (i < totals.length) {
                this.beanBrewCost.put(this.allFlavours.get(i), totals[i]);
            }
            // Otherwise default cost of nothing
            else {
                this.beanBrewCost.put(this.allFlavours.get(i), 0.);
            }
        }

        // Preparing coffee brew times
        ArrayList<LocalTime> brew = new ArrayList<>();
        brew.add(LocalTime.parse("00:00:20"));
        brew.add(LocalTime.parse("00:03:00"));
        brew.add(LocalTime.parse("00:03:40"));
        brew.add(LocalTime.parse("00:02:30"));
        brew.add(LocalTime.parse("00:00:45"));
        for (int i = 0; i < this.allFlavours.size(); i++) {
            if (i < brew.size()) {
                this.brewTimes.put(this.allFlavours.get(i), brew.get(i));
            }
        }

        // Prepare bean counts
        for (int i = 0; i < this.allFlavours.size(); i++) {
            // Add bean count
            if (i < totalBeans.length) {
                this.beansRemaining.put(this.allFlavours.get(i), totalBeans[i]);
            }
            // Otherwise, assume no beans
            else {
                this.beansRemaining.put(this.allFlavours.get(i), 0.);
            }
        }

        // Finally, init actualmaketime based on chosen flavour and maketime from user inputted time

        // Remove nano seconds for future calculations
        makeTime = makeTime.minusNanos(makeTime.getNano());

        this.userMakeTime = makeTime;
        makeTime = makeTime.minusHours(this.brewTimes.get(this.selectedFlavour).getHour());
        makeTime = makeTime.minusMinutes(this.brewTimes.get(this.selectedFlavour).getMinute());
        this.actualMakeTime = makeTime.minusSeconds(this.brewTimes.get(this.selectedFlavour).getSecond());
    }

    // Inherited methods
    public STATES Check() {
        // Check if coffee machine should start
        LocalTime current = LocalTime.now();
        current = current.minusNanos(current.getNano());
        // Check make time to current time and that machine is not already on
        if (current.equals(this.actualMakeTime) && !this.isOn) {
            // If not on, turn on
            if (!this.isOn) {
                return this.Start();
            }
            
        }
        // Otherwise, check for decreasing brewing time or stopping machine
        else {
            // Machine must be on to reduce time
            if (this.isOn) {
                // Reduce time based on how much has passed since last check
                this.brewTimeLeft = this.brewTimeLeft.minusNanos(NANOS.between(this.timeOfLastCheck, current));
                this.timeOfLastCheck = current;

                // Finally, check if runtime is done by checking if the hour wrapped around
                if (this.brewTimeLeft.getHour() == 23) {
                    return this.Stop();
                }
            }
        }

        return STATES.GOOD;
    }
    
    public boolean Set(COMMAND_SET param, String value) {
        return true;
    }

    public String Get(COMMAND_GET param) {
        return "";
    }

    public String Call(COMMAND_CALL param, String args) {
        // Default good
        STATES state = STATES.GOOD;

        // Start machine on request
        if (param == COMMAND_CALL.START) {
            state = this.Start();
        }

        // Stop machine on request
        else if (param == COMMAND_CALL.STOP) {
            state = this.Stop();
        }

        // Command invalid
        else {
            state = STATES.ERROR_UNKNOWN;
        }

        return state.toString();
    }

    // CoffeeMachine specific functions
    
    // Start coffee machine with specified stats
    private STATES Start() {
        // Check for bean count
        double beansLeft = this.beansRemaining.get(this.selectedFlavour) - this.beanBrewCost.get(this.selectedFlavour);
        if (beansLeft >= 0) {
            // Remove beans for brewing
            this.beansRemaining.replace(this.selectedFlavour, beansLeft);
        }
        else {
            return STATES.ERROR_NO_BEANS;
        }

        // Finally, turn on coffee machine if everything is setup properly

        // Setup brewtime
        this.brewTimeLeft = this.brewTimes.get(this.selectedFlavour);
        // This allows for the reduction of brew time
        this.timeOfLastCheck = LocalTime.now();
        this.isOn = true;
        
        return STATES.GOOD;
    }

    // Turns off the coffee machine
    private STATES Stop() {
        this.isOn = false;

        return STATES.GOOD;
    }
}
