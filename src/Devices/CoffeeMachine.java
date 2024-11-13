package Devices;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoffeeMachine extends Device {
    // State of device
    private boolean isOn;

    // Coffee brewing time information
    
    // Make time is the time set by user
    private LocalTime userMakeTime;
    // Actual time the coffee needs to begin brewing at to be ready for user selected time
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
        // Simple allocations
        this.isOn = false;
        
        // Setup maps
        this.beanBrewCost = new HashMap<>();
        this.brewTimes = new HashMap<>();

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
        this.beansRemaining = new HashMap<>();
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
        STATES state = STATES.GOOD;

        // Check if coffee machine should start
        LocalTime current = LocalTime.now();
        if (current.equals(this.actualMakeTime) || !this.isOn) {
            // If not on, turn on
            if (!this.isOn) {
                state = this.Start();
                state = STATES.ERROR_NO_BEANS;
            }
            
        }
        // Otherwise, decrease brewing time
        else {
            LocalTime tmp = this.brewTimeLeft.minusNanos(current.getNano() - this.timeOfLastCheck.getNano());
            if (tmp.getNano() < 0) {
                System.out.println(tmp.getNano());
            }
            this.timeOfLastCheck = current;
            System.out.println(this.brewTimeLeft);
        }

        return state;
    }
    
    // Start coffee machine with specified stats
    private STATES Start() {
        STATES state = STATES.GOOD;

        // Finally, turn on coffee machine if everything is setup properly
        if (state == STATES.GOOD) {
            // Setup brewtime
            this.brewTimeLeft = this.brewTimes.get(this.selectedFlavour);
            // This allows for the reduction of brew time
            this.timeOfLastCheck = LocalTime.now();
            this.isOn = true;
        }
        
        return state;
    }

    // Turns off the coffee machine
    private STATES Stop() {
        STATES state = STATES.GOOD;
        return state;
    }
}
