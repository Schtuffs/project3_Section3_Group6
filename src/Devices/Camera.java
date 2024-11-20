
package Devices;

import java.util.ArrayList;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Camera extends Device {
   
    private boolean isOn;

    private String selectedLocation;

    private ArrayList<String> allLocations;

    private static int numLocation;

    public Camera(/*String location,*/String [] allLocations, String [] everyLocation) {

        this.isOn=false;

        // Initialize flavours
        this.allLocations = new ArrayList<>();
        for (int i = 0; i < allLocations.length; i++) {
        this.allLocations.add(everyLocation[i]);
        }

        //Read from a file with many camera locations
        try {
            File cameras = new File("CameraLocations.txt");
            Scanner systemCams = new Scanner(cameras);
            while (systemCams.hasNextLine()) {
              
                String location = systemCams.nextLine();
                this.allLocations.add(location);

                //Print out every location as it's added for debugging
              //  System.out.println(location);
               
            }

            systemCams.close();

            } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        this.selectedLocation=this.allLocations.get(3);
        Camera.numLocation=3;

    }

    // Inherited methods
    public STATES Check() { 
        // Camera current;
        // if (current.isOn==true){
        //     return STATES.GOOD;
        // }
        return STATES.GOOD;
    }
    
    
    // Allows activation and deactivation of camera
    public STATES Start() {

        String currentLocation = this.selectedLocation;

        if (currentLocation.equalsIgnoreCase("Backyard")){
            //present image or call other function for presenting images\
            //if fail, return BAD boolean
        }

        if (currentLocation.equalsIgnoreCase("Frontyard")){
             //present image or call other function for presenting images
             //if fail, return BAD boolean
        }
        
        if (currentLocation.equalsIgnoreCase("Pool")){
             //present image or call other function for presenting images
             //if fail, return BAD boolean
        }

        if (currentLocation.equalsIgnoreCase("Front Door")){
            //present image or call other function for presenting images
            //if fail, return BAD boolean
        }

        if (currentLocation.equalsIgnoreCase("Back Door")){
            //present image or call other function for presenting images
            //if fail, return BAD boolean
        }


        return STATES.GOOD;
    }

    //Concepts of left and right camera location changing for initial implementation
    public STATES ChangeCamera (boolean right){

        if (right){//If the user presses the right button, move to the next camera location
            this.selectedLocation=this.allLocations.get(Camera.numLocation++);   
        }

        else {
            this.selectedLocation=this.allLocations.get(Camera.numLocation--);
        }
        
        return STATES.GOOD;
    }

    public STATES Stop() { 
        
        this.isOn=false;
        //Code to change image to a blank screen/redirect to a homescreen
        //if fail, return BAD boolean
        return STATES.GOOD;

     }

    public  boolean getCameraState(){

        return this.isOn;
    }
}

