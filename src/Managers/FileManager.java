package Managers;

import Devices.*;
import Devices.Device.COMMAND_GET;
import static java.time.temporal.ChronoUnit.valueOf;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import javax.smartcardio.CardException;

public class FileManager {
    // Read data from file to be turned into devices
    public String Read(String filename) { 

        // ArrayList buffer
        String buf = "";
        
        try {
            // File to be read
            File f = new File("Saved/" + filename + ".txt");
            // Scanner to read files
            Scanner s = new Scanner(f);
            // Read line from file
            buf = s.nextLine();
            // Closing the file
            s.close();

        } catch (IOException e ) {
            System.out.println("Error occurred: " + e);
        }     
        // Return contents of the file
        return buf; 
    }

    // Write a device to a file, return true/false if this file was able to be written to
    public boolean Write(String filename, Device device) { 

        try {
            // Creating a new file to write device information to
            // Second param is false for no appending; this means the file is wiped every time Write() is called.
            // This is important because there should only be one string in the file no matter how many times it is updated.
            FileWriter f = new FileWriter("Saved/" + filename + ".txt", false);
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
                    // Writing the blinds open state to file
                    f.write(String.valueOf(((Blinds)device).Get(COMMAND_GET.BLINDS_STATUS)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing the blinds open time
                    f.write(String.valueOf(((Blinds)device).Get(COMMAND_GET.BLINDS_OPEN_TIME)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing the blinds open time
                    f.write(String.valueOf(((Blinds)device).Get(COMMAND_GET.BLINDS_CLOSE_TIME)));
                    break;

                case "Camera":
                    // Writing the blinds open state to file
                    f.write(String.valueOf(((Camera)device).Get(COMMAND_GET.CAMERA_LOCATION)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing the blinds open time
                    f.write(String.valueOf(((Camera)device).Get(COMMAND_GET.CAMERA_STATE)));
                    for (int i = 0;i<((Camera)device).GetAllLocations().size();i++) {
                        // Comma seperator for Split(",")
                        f.write(",");
                        f.write(String.valueOf(((Camera)device).GetAllLocations().get(i)));
                    }
                    break;

                case "CoffeeMachine":
                    //  Brew cost
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_BREWCOST)));
                    // Comma seperator for Split(",")
                    f.write("&");
                    // Brew time
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_BREWTIME)));
                    // Comma seperator for Split(",")
                    f.write("&");
                    // Bean flavour
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_FLAVOUR)));
                    // Comma seperator for Split(",")
                    f.write("&");
                    // Bean left
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_LEFT)));
                    // Comma seperator for Split(",")
                    f.write("&");
                    // Bean Make time
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_MAKETIME)));
                    // Comma seperator for Split(",")
                    f.write("&");
                    // Bean days
                    f.write(String.valueOf(((CoffeeMachine)device).Get(COMMAND_GET.BEAN_DAYS)));
                    break;

                case "Shower":
                    // Writing shower on status
                    f.write(String.valueOf(((Shower)device).Get(COMMAND_GET.SHOWER_STATE)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing shower head type
                    f.write(String.valueOf(((Shower)device).Get(COMMAND_GET.SHOWER_HEADTYPE)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing shower head type
                    f.write(String.valueOf(((Shower)device).Get(COMMAND_GET.SHOWER_TEMPERATURE)));
                    for (int i = 0;i<((Shower)device).GetPatternCount();i++) {
                        // Comma seperator for Split(",")
                        f.write(",");
                        f.write(String.valueOf(((Shower)device).GetHeadPattern(i)));
                    }
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
                case "Sensor":
                    // isOpen
                    f.write(String.valueOf(((Sensor)device).Get(COMMAND_GET.SENSOR_IS_OPEN)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // isDismissed
                    f.write(String.valueOf(((Sensor)device).Get(COMMAND_GET.SENSOR_IS_DISMISSED)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing opentime
                    f.write(String.valueOf(((Sensor)device).Get(COMMAND_GET.SENSOR_OPEN_TIME)));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing closetime
                    f.write(String.valueOf(((Sensor)device).Get(COMMAND_GET.SENSOR_CLOSE_TIME)));
                    break;


                default:
                    // Otherwise, something brokey!
                    f.close();
                    System.out.println("There was an error writing the file: Please double check that your filename syntax is correct! File: "+ filename);
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
