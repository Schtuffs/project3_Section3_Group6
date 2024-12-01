package Devices;

public class SmokeDetector extends Device {
    private boolean isSmokey;

    public SmokeDetector() {
        // initializes with no smoke detected because god i'd really hope there isn't!
        this.isSmokey = false;
    }

    public void readDataFromFile(String line) {

        String[] values = line.split(",");

        if (values[0].equals("true")) {
            this.isSmokey = true;
        } else {this.isSmokey=false;}

    }

    // Inherited methods
    public String Check() { 
        
        // if smoke. return AHHH GET OUT GET OUT THERES SMOKE AHHHHH
        if (isSmokey) return STATES.SMOKE_DETECTED.toString();

        // otherwise no smoke
        return STATES.GOOD.toString(); 
    }

    public void SetIsSmokey(boolean isSmokey) {
        this.isSmokey = isSmokey;
    }

    public boolean GetIsSmokey() {
        return isSmokey;
    }

    @Override
    public boolean Set(COMMAND_SET param, String value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Set'");
    }

    @Override
    public String Get(COMMAND_GET param) {
        // Getting isSmokey
        if ( param == COMMAND_GET.SD_IS_SMOKEY ) {
            return String.valueOf(this.isSmokey);
        }

        return null;
    }

    @Override
    public String Call(COMMAND_CALL param, String args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Call'");
    }

    // likely there needs to be logic to randomly turn isSmokey true for "fires"
}
