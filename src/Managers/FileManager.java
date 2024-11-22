package Managers;

import Devices.*;
import Devices.Device.COMMAND_GET;
import Devices.Device.DEVICE_TYPE;

import static java.time.temporal.ChronoUnit.valueOf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.lang.model.type.UnknownTypeException;

public class FileManager {
    // Read data from file to be turned into devices
    public static String Read(String filename) { 
        
        try {
            // File reading stuff here

        } catch (FileNotFoundException e ) {
            System.out.println("Error occurred: " + e);

        }
        
        


        return new String(); 
    }

    // Write a device to a file, return true/false if this file was able to be written to
    public static boolean Write(String filename, Device device) { 

        try {
            // Creating a new file to write device information to
            FileWriter f = new FileWriter("Saved" + filename);
            // Depending on the name of the file, since each device will have it's own file, write specific strings of data
            switch (filename) {
                case "Alarm":
                    // First, write the state of isBeeping
                    f.write(String.valueOf(((Alarm)device).Get(COMMAND_GET.ALARM_IS_BEEPING)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing value of beepDelay
                    f.write(String.valueOf(((Alarm)device).Get(COMMAND_GET.ALARM_BEEP_DELAY)));
                    break;

                case "Blinds":
                    // can't finish implementing yet, enums not written
                    // f.write(String.valueOf(((Blinds)device).Get(COMMAND_GET.BLINDS_IS_OPEN))));
                    break;\

                case "Camera":
                    // can't finish implementing yet, enums not written
                    // todo camera writing implementation
                    break;

                case "CoffeeMachine":
                    //  Brew cost
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_BREWCOST)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Brew time
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_BREWTIME)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Remaining Brew time
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_BREWTIMELEFT)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Bean days
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_DAYS)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Bean flavour
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_FLAVOUR)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Bean left
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_LEFT)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Bean Make time
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_MAKETIME)));
                    break;

                case "Shower":
                    // Writing shower head type
                    f.write(String.valueOf(((Shower)device).Get(COMMAND_GET.SHOWER_HEADTYPE)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing shower head type
                    f.write(String.valueOf(((Shower)device).Get(COMMAND_GET.SHOWER_TEMPERATURE)));
                    break;

                case "SmokeDetector":
                    // Status of smoke detection
                    f.write(String.valueOf(((SmokeDetector)device).Get(COMMAND_GET.SD_IS_SMOKEY)));
                    break;

                case "Thermostat":
                    // Celsius boolean
                    f.write(String.valueOf(((Thermostat)device).Get(COMMAND_GET.THERM_CELSIUS)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Current temp
                    f.write(String.valueOf(((Thermostat)device).Get(COMMAND_GET.THERM_TEMPERATURE)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Current humidity
                    f.write(String.valueOf(((Thermostat)device).Get(COMMAND_GET.THERM_HUMIDITY)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Target temp
                    f.write(String.valueOf(((Thermostat)device).Get(COMMAND_GET.THERM_TARGET_TEMP)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Target humidity
                    f.write(String.valueOf(((Thermostat)device).Get(COMMAND_GET.THERM_TARGET_HUMID)));
                    break;

                default:
                    // Otherwise, something brokey!
                    System.out.println("There was an error writing the file: Please double check that your filename syntax is correct!");
                   return false;   
            }

            // Close the file
            f.close();
            // The file was successfully written to, return true
            return true; 
        } catch (IOException e ) {
            // Print the error
            System.out.println("Error occurred: " + e);
            // Something went wrong; return false
            return false;
        }
    

    }
}
