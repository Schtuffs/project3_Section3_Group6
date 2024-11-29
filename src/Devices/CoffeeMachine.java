package Devices;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import static java.time.temporal.ChronoUnit.NANOS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// The Bean Machine
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
    // Days to automatically brew coffee
    private boolean[] makeDays;

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

    public CoffeeMachine() {
        // Simple allocations and sets
        this.isOn = false;
        this.brewTimeLeft = LocalTime.parse("00:00:00");
        this.makeDays = new boolean[7];
        for (int i = 0; i < makeDays.length; i++) {
            makeDays[i] = true;
        }
        
        // Setup maps
        this.beanBrewCost = new HashMap<>();
        this.brewTimes = new HashMap<>();
        this.beansRemaining = new HashMap<>();

        // Initialize flavours
        this.allFlavours = new ArrayList<>();

        // Will be moved to file later
        this.allFlavours.add("espresso");
        this.allFlavours.add("latte");
        this.allFlavours.add("cappuccino");
        this.allFlavours.add("mocha");
        this.allFlavours.add("americano");
        
        this.selectedFlavour = this.allFlavours.get(0);

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
            // Assume no beans
            this.beansRemaining.put(this.allFlavours.get(i), 0.);
        }

        // Finally, init actualmaketime based on chosen flavour and maketime from user inputted time

        // Remove nano seconds for future calculations
        this.userMakeTime = LocalTime.parse("08:00:00");
        LocalTime makeTime = this.userMakeTime.minusHours(this.brewTimes.get(this.selectedFlavour).getHour());
        makeTime = makeTime.minusMinutes(this.brewTimes.get(this.selectedFlavour).getMinute());
        this.actualMakeTime = makeTime.minusSeconds(this.brewTimes.get(this.selectedFlavour).getSecond());
    }

    // Inherited methods
    public String Check() {
        // Check if coffee machine should start
        LocalTime current = LocalTime.now();
        current = current.minusNanos(current.getNano());
        // Check make time to current time and that machine is not already on
        if (current.equals(this.actualMakeTime) && !this.isOn) {
            // If not on, turn on
            if (!this.isOn) {
                return this.Start().toString();
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
                    return this.Stop().toString();
                }
            }
            // See if machine should be running right now
            else {
                boolean inTime[] = new boolean[] {false, false, false};
                for (int i = 0; i < 3; i++) {
                    int actual = 0, user = 0, cur = 0;
                    switch (i) {
                    case 0:
                        actual = this.actualMakeTime.getHour();
                        user = this.userMakeTime.getHour();
                        cur = current.getHour();
                        break;
                    case 1:
                        actual = this.actualMakeTime.getMinute();
                        user = this.userMakeTime.getMinute();
                        cur = current.getMinute();
                        break;
                    case 2:
                        actual = this.actualMakeTime.getSecond();
                        user = this.userMakeTime.getSecond();
                        cur = current.getSecond();
                        break;
                    }

                    if (actual > user) {
                        if (actual <= cur || cur <= user) {
                            inTime[i] = true;
                        }
                    }
                    else {
                        if (actual <= cur && cur <= user) {
                            inTime[i] = true;
                        }
                    }
                }
                
                if (inTime[0]  && inTime[1] && inTime[2]) {
                    // Machine should be running but it is not
                    return STATES.ERROR_NO_START.toString();
                }
            }
        }
        return STATES.GOOD.toString();
    }
    
    public boolean Set(COMMAND_SET param, String value) {
        
        switch (param) {
        case BEAN_FLAVOUR:
            return this.SetBeanFlavour(value);
        case BEAN_ADD:
            return this.SetBeansLeft(value);
        case BEAN_NEW:
            return this.SetNewBean(value);
        case BEAN_MAKETIME:
            return this.SetBeanMakeTime(value);
        case BEAN_DAYS:
            return this.SetNewDays(value);
        default:
            return false;
        }
        
        /* 
        if (param==COMMAND_SET.BEAN_FLAVOUR) {
            return this.SetBeanFlavour(value);
        }
        else if (param==COMMAND_SET.BEAN_ADD) {
            return this.SetBeansLeft(value);
        }
        else if (param==COMMAND_SET.BEAN_NEW) {
            return this.SetNewBean(value);
        }
        else if (param==COMMAND_SET.BEAN_MAKETIME) {
            return this.SetBeanMakeTime(value);
        }
        else if (param==COMMAND_SET.BEAN_DAYS) {
            return this.SetNewDays(value);
        }
        else { return false; }
        */
    }

    private boolean SetBeanFlavour(String newFlavour) {
        for(String flav : this.allFlavours) {
            if (newFlavour.equalsIgnoreCase(flav)) {
                this.selectedFlavour = newFlavour.toLowerCase();
                return true;
            }
        }
        return false;
    }

    private boolean SetBeansLeft(String addBeanCount) {
        double beans;
        try {
            beans = Double.parseDouble(addBeanCount);
        }
        catch (NumberFormatException e) {
            return false;
        }

        if (beans > 0) {
            // Increase bean count by newly inputted value
            this.beansRemaining.replace(this.selectedFlavour, this.beansRemaining.get(this.selectedFlavour) + beans);
            return true;
        }
        return false;
    }
    
    private boolean SetNewBean(String value) {
        return this.allFlavours.add(value);
    }

    private boolean SetBeanMakeTime(String time) {
        // Try to change the user maketime
        try {
            LocalTime lt = LocalTime.parse(time);
            lt = lt.minusNanos(lt.getNano());
            this.userMakeTime = lt;
        }
        catch (DateTimeParseException e) {
            return false;
        }
        // Change the actual make time as well
        LocalTime makeTime = this.userMakeTime.minusHours(this.brewTimes.get(this.selectedFlavour).getHour());
        makeTime = makeTime.minusMinutes(this.brewTimes.get(this.selectedFlavour).getMinute());
        this.actualMakeTime = makeTime.minusSeconds(this.brewTimes.get(this.selectedFlavour).getSecond());
        return true;
    }
    
    private boolean SetNewDays(String days) {
        // Setup map for days
        Map<String, Integer> dMap = new HashMap<>();
        dMap.put("Sunday", 0);
        dMap.put("Monday", 1);
        dMap.put("Tuesday", 2);
        dMap.put("Wednesday", 3);
        dMap.put("Thursday", 4);
        dMap.put("Friday", 5);
        dMap.put("Saturday", 6);
        
        // Split user string into separate days
        String[] daysSplit = days.split(", ");
        for (String day : daysSplit) {
            if (dMap.containsKey(day)) {
                int index = dMap.get(day);
                this.makeDays[index] = !this.makeDays[index];
            }
        }
        return true;
    }
    
    public String Get(COMMAND_GET param) {
        
        String result;
        switch (param) {
        case BEAN_FLAVOUR:
            result = this.selectedFlavour;
            break;
        case BEAN_LEFT:
            result = this.beansRemaining.get(this.selectedFlavour).toString();
            break;
        case BEAN_BREWTIME:
            result = this.brewTimes.get(this.selectedFlavour).toString();
            break;
        case BEAN_BREWTIMELEFT:
            result = this.brewTimeLeft.toString();
            break;
        case BEAN_BREWCOST:
            result = this.beanBrewCost.get(this.selectedFlavour).toString();
            break;
        case BEAN_MAKETIME:
            result = this.userMakeTime.toString();
            break;
        case BEAN_DAYS:
            return this.GetBrewDays();
        default:
            result = STATES.ERROR_UNKNOWN.toString();
        }
        return result;
        
    }

    public ArrayList<String> GetFlavours() {
        return this.allFlavours;
    }

    private String GetBrewDays() {
        String[] days = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
        String toReturn = "";
        // Adds days to string based on true
        for (int i = 0; i < days.length; i++) {
            if (this.makeDays[i]) {
                if (toReturn.length() != 0) {
                    toReturn += ", ";
                }
                toReturn += days[i];
            }
        }
        return toReturn;
    }

    public String Call(COMMAND_CALL param, String args) {
        // Default good
        STATES state = STATES.GOOD;

        /* 
        switch(param) {
        case COMMAND_CALL.START:
            state = this.Start();
            break;
        case COMMAND_CALL.STOP:
            state = this.Stop();
            break;
        default:
            state = STATES.ERROR_UNKNOWN;
            break;
        }
            */
        if (param==COMMAND_CALL.START) {
            state = this.Start();
        }
        else if (param==COMMAND_CALL.STOP) {
            state = this.Stop();
            
        }
        else {
            state = STATES.ERROR_UNKNOWN;
        }


        return state.toString();
    }

    // CoffeeMachine specific functions
    
    // Start coffee machine with specified stats
    private STATES Start() {
        // If function is called by user after already being started
        if (this.isOn) {
            return STATES.ERROR_NO_START;
        }

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
        if (this.isOn) {
            this.isOn = false;
            return STATES.GOOD;
        }
        return STATES.ERROR_NO_STOP;
    }
}
