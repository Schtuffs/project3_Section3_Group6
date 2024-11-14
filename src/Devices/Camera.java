
package Devices;

import java.util.ArrayList;

public class Camera extends Device {
   
    private boolean isOn;

    private String selectedLocation;

    private ArrayList<String> allLocations;

    private static int numLocation;

    public Camera(/*String location,*/String [] allLocations, String [] everyLocation) {

        boolean isLocation=false;

        

        this.isOn=false;

        // this.allLocations=new ArrayList<>();

        // Initialize flavours
        this.allLocations = new ArrayList<>();
        for (int i = 0; i < allLocations.length; i++) {
        this.allLocations.add(everyLocation[i]);
        }

        this.allLocations.add("Backyard");
        this.allLocations.add("Frontyard");
        this.allLocations.add("Pool");
        this.allLocations.add("Front Door");
        this.allLocations.add("Back Door");

        for (String loc : everyLocation) {
            this.allLocations.add(loc.toLowerCase());
        }

        //Possible code to search for a location (for later implementation)
        // for (String loc : this.allLocations){
        //     if(location.equalsIgnoreCase(loc)){
        //         isLocation=true;
        //         this.selectedLocation=location.toLowerCase();
        //         break;
        //     }
        // this.numLocation ++;
        // }

        if (!isLocation) {
            this.selectedLocation = this.allLocations.get(3);
            Camera.numLocation = 3;
        }

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

        String currentLocations = this.selectedLocation;

        if (currentLocations.equalsIgnoreCase("Backyard")){
            //present image or call other function for presenting images\
            //if fail, return BAD boolean
        }

        if (currentLocations.equalsIgnoreCase("Frontyard")){
             //present image or call other function for presenting images
             //if fail, return BAD boolean
        }
        
        if (currentLocations.equalsIgnoreCase("Pool")){
             //present image or call other function for presenting images
             //if fail, return BAD boolean
        }

        if (currentLocations.equalsIgnoreCase("Front Door")){
            //present image or call other function for presenting images
            //if fail, return BAD boolean
        }

        if (currentLocations.equalsIgnoreCase("Back Door")){
            //present image or call other function for presenting images
            //if fail, return BAD boolean
        }


        return STATES.GOOD;
    }

    //Concepts of left and right camera location changing for initial implementation
    public STATES ChangeCamera (boolean right){

        if (right){
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

