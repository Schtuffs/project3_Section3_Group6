package Managers;

import java.time.LocalTime;
import java.util.ArrayList;
import Devices.*;


public class DeviceManager {

    private ArrayList<Device> devices;

    //Could potentially 
    public DeviceManager(ArrayList<String> filenames) {
        
        ArrayList<Device> presentDevices = new ArrayList<Device>();
      //  presentDevices = Read(filenames);
        //Initialize a device manager class that creates a file to contain present devices

    }

    // Parses data and creates devices from it
    private void Parse(String data) {}

    // Checks each device for any erros
    public void Check() {}  

    // Returns all devices of selected type
    public ArrayList<Device> GetDevices(int type) {

        devices = new ArrayList<>();
        //Call read function, taking from the "device" file, adding each string matching type to a new array position
         return new ArrayList<Device>();
    }
    
    // Calls write file for every device owned by manager
    public void Write() {}

    //Needs to read from the file manager in order to search for devices present within the system
    public ArrayList<Device> Read(String filename) {

        // devices
        //ALARM, BLINDS, CAMERA, COFFEE_MACHINE, SENSOR, SHOWER, SMOKE_DETECTOR, THERMOSTAT
     
        //String allLocations[]; 
        //String everyLocation[];

        LocalTime otime =LocalTime.MIN;
        LocalTime ctime = LocalTime.MAX;

        boolean curUnit = true; 
        double targetHumid = 0.00; 
        double targetTemp = 21.0;

        devices = new ArrayList<Device>();

        ArrayList<String> tempDevices;
        tempDevices = FileManager.Read(filename);

        //Switch case wasn't working
        //could potentially do deleteCharAt(tempDevices(i).length()-1) if our devices are listed as Alarm1, Alarm2, etc. in file
        for (int i = 0; i<tempDevices.size(); tempDevices.size()){

            String currentDevice = (tempDevices.get(i)).toLowerCase();
            if(currentDevice == "blinds"){
                devices.add(new Blinds(otime, ctime));
            }
            if(currentDevice == "camera"){
                devices.add(new Camera());
            }
            if(currentDevice == "coffee machine"){
                devices.add(new CoffeeMachine());
            }
            if(currentDevice == "alarm"){
                devices.add(new Alarm());
            }
            if(currentDevice == "shower"){
                devices.add(new Shower());
            }
            if(currentDevice == "smoke detector"){
                devices.add(new SmokeDetector());
            }
            if(currentDevice == "thermostat"){
                devices.add(new Thermostat(curUnit, targetHumid, targetTemp));
            }   

        }
    return devices;

    }
}
