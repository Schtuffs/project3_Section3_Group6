package Managers;

import java.util.ArrayList;
import Devices.*;

public class DeviceManager {
    private ArrayList<Device> devices;

    public DeviceManager(ArrayList<String> filenames) {}

    // Parses data and creates devices from it
    private void Parse(String data) {}

    // Checks each device for any erros
    public void Check() {}

    // Returns all devices of selected type
    public ArrayList<Device> GetDevices(int type) { return new ArrayList<Device>(); }

    // Calls write file for every device owned by manager
    public void Write() {}
}
