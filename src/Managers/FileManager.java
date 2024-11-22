package Managers;

import Devices.*;
import Devices.Device.DEVICE_TYPE;

import static java.time.temporal.ChronoUnit.valueOf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
                    f.write(String.valueOf(((Alarm)device).GetIsBeeping()));
                    // Comma seperator for Split(",")
                    f.write(",");
                    // Writing value of beepDelay
                    f.write(String.valueOf(((Alarm)device).GetBeepDelay()));
                case "Blinds":
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
