
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

    private String tracker;

    public Camera(){
        this.isOn=false;
        
    }
    public Camera(/*String location,*/String [] allLocations, String [] everyLocation) {

        this.isOn=false;

        // Initialize locations
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
            tracker = STATES.ERROR_READ.toString();
        }

        this.selectedLocation=this.allLocations.get(3);
        Camera.numLocation=3;

    }

    // Inherited methods
    public String Check() { 
        if (this.selectedLocation==""){
            return STATES.ERROR_UNKNOWN.toString();
        }

        if(this.tracker == STATES.ERROR_READ.toString()){
            return STATES.ERROR_READ.toString();
        }
        
        return STATES.GOOD.toString();
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
    public boolean ChangeCamera (String location){

        
        if (location.toLowerCase()=="backyard"){//If the user presses the backyard button, move to the appropriate camera location
            this.selectedLocation=this.allLocations.get(0);   
           // return Start();//Change the current camera image being displayed
        }

        if (location.toLowerCase()=="frontyard"){//If the user presses the frontyard button, move to the appropriate camera location
            this.selectedLocation=this.allLocations.get(1);   
           // return Start();//Change the current camera image being displayed
        }

        if (location.toLowerCase()=="pool"){//If the user presses the pool button, move to the appropriate camera location
            this.selectedLocation=this.allLocations.get(2);   
           // return Start();//Change the current camera image being displayed
        }

        if (location.toLowerCase()=="front door"){//If the user presses the front door button, move to the appropriate camera location
            this.selectedLocation=this.allLocations.get(3);   
          //  return Start();//Change the current camera image being displayed
        }

        if (location.toLowerCase()=="back door"){//If the user presses the back door button, move to the appropriate camera location
            this.selectedLocation=this.allLocations.get(4);   
          //  return Start();//Change the current camera image being displayed
        }
        
        return false;
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

    public void setCameraState(boolean state){
        this.isOn=state;
    }

    //Camera variables
   // CAMERA_LOCATION, CAMERA_IS_ON, CAMERA_IS_OFF, 

    public boolean Set(COMMAND_SET param, String value) {

        if (param==COMMAND_SET.CAMERA_STATE) {

            // set isbeeping to false if false passed in
            if (value.toLowerCase().equals("true") || value.equals("0")) {
                this.setCameraState(true);
                return true;
            }

            else if(value.toLowerCase().equals("false") || value.equals("1")){
                this.setCameraState(false);
                return false;
               // return this.Stop();
            }
        }
             if (param==COMMAND_SET.CAMERA_LOCATION) {
                return this.ChangeCamera(value);
        }


    return false;
 }

    public String Get(COMMAND_GET param) {

        String result = "";

        if (param == COMMAND_GET.CAMERA_STATE){
    
            if (this.getCameraState()){
                result = "true";
            }
            if (!this.getCameraState()){
                result = "false";
            }
        }

        if (param == COMMAND_GET.CAMERA_LOCATION) {
            result = this.selectedLocation;
        } 

        return result;
    }


    public String Call(COMMAND_CALL param, String args) {
       
        STATES result = STATES.GOOD;

        if (param == COMMAND_CALL.START){
            result = this.Start();
        }

        if( param == COMMAND_CALL.STOP){
            result = this.Stop();
        }

        return result.toString();
    }
}

