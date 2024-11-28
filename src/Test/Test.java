package Test;

import java.time.LocalTime;

import Devices.*;
import Managers.*;
import Devices.Device.COMMAND_GET;
import Devices.Device.COMMAND_SET;
import Devices.Device.COMMAND_CALL;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        // Commented out so a bunch of pop-up windows are not created for others
        // TestAlarm();
        System.out.println("Alarm tests completed");
        TestBlinds();
        System.out.println("Blinds tests completed");
        TestCamera();
        System.out.println("Camera tests completed");
        TestCoffeeMachine();
        System.out.println("Coffee tests completed");
        // TestSensor();
        System.out.println("Sensor tests completed");
        TestShower();
        System.out.println("Shower tests completed");
        TestSmokeDetector();
        System.out.println("Smoke Detector tests completed");
        TestThermostat();
        System.out.println("Thermostat tests completed");
        TestWindowManager();
        System.out.println("WindowManager tests completed");
        // TestAlertManager();
        System.out.println("AlertManager tests completed");
        TestFileManager();
        System.out.println("FileManager tests completed");
        TestDeviceManager();
        System.out.println("DeviceManager tests completed");

        Assert.Results();
    }
   
    private static void TestAlarm() throws InterruptedException {
        // 1
        // TEST_UNIT_ALARM_01 part 1
        {

            // Arrange
            Alarm alarm = new Alarm();
            alarm.TriggerAlarm("Test Alarm", null);

            // make actual and expected values 
            boolean actual = alarm.GetIsBeeping();
            boolean expected = true;

            // Assert
            Assert.AreEqual(expected, actual);
        }

        // 2
        // TEST_UNIT_ALARM_01 part 2
        {

            // Arrange
            Alarm alarm = new Alarm();
            alarm.TriggerAlarm("Test Alarm", null);
            alarm.StopAlarm();

            // make actual and expected values 
            boolean actual = alarm.GetIsBeeping();
            boolean expected = false;

            // Assert
            Assert.AreEqual(expected, actual);
        }


        // 3
        // TEST_UNIT_ALARM_02 part 1
        {

            // Arrange
            Alarm alarm = new Alarm(true, 3);

            // make actual and expected
            int actual = alarm.GetBeepDelay();
            int expected = 3;

            // Assert

            Assert.AreEqual(expected, actual);

        }

        // 4
        // TEST_UNIT_ALARM_02 part 2
        {

            // Arrange
            Alarm alarm = new Alarm(true, 3);

            // make actual and expected
            boolean actual = alarm.GetIsBeeping();
            boolean expected = true;

            // Assert

            Assert.AreEqual(expected, actual);

        }


        // 5
        // TEST_UNIT_ALARM_03 part 1
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            alarm.readDataFromFile("1,2");
            boolean actual = alarm.GetIsBeeping();
            boolean expected = true;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 5
        // TEST_UNIT_ALARM_03 part 2
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            alarm.readDataFromFile("1,2");
            int actual = alarm.GetBeepDelay();
            int expected = 2;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 6
        // TEST_UNIT_ALARM_04
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            boolean actual = alarm.readDataFromFile("1,2,3");
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 7
        // TEST_UNIT_ALARM_05
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            boolean actual = alarm.readDataFromFile("2,2");
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 8
        // TEST_UNIT_ALARM_06
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            boolean actual = alarm.readDataFromFile("1,-1");
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 9
        // TEST_UNIT_ALARM_07 p1
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            boolean actual = alarm.GetIsBeeping();
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 10
        // TEST_UNIT_ALARM_07 p2
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            int actual = alarm.GetBeepDelay();
            int expected = 1;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 11
        // TEST_UNIT_ALARM_08 p1
        {
            // Arrange
            Alarm alarm = new Alarm(true, 5);

            // Act, get actual and expected
            boolean actual = alarm.GetIsBeeping();
            boolean expected = true;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 12
        // TEST_UNIT_ALARM_08 p2
        {
            // Arrange
            Alarm alarm = new Alarm(true, 5);

            // Act, get actual and expected
            int actual = alarm.GetBeepDelay();
            int expected = 5;

            // Assert
            Assert.AreEqual(actual, expected);
        }
        
        // 13
        // TEST_UNIT_ALARM_09
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            boolean actual = alarm.Beep();
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        // 14
        // TEST_UNIT_ALARM_10
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            alarm.SetIsBeeping(true);
            boolean actual = alarm.Beep();
            boolean expected = true;

            // Assert
            Assert.AreEqual(actual, expected);
        }

        /* STATES no public cannot test 
        // 15
        // TEST_UNIT_ALARM_11 p1
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Act, get actual and expected
            STATES actual = alarm.Check();

            // Assert
            Assert.AreEqual(actual, expected);
        }
            */


        // Alarm Integration tests 

        // TEST_INTEGRATION_ALARM_01
        {
            // Arrange
            Alarm alarm = new Alarm();

            // Trigger Alarm 
            alarm.TriggerAlarm("Test Alert", null);

        }

        // Waiting for WindowManager implementation
        /* 
        // TEST_INTEGRATION_ALARM_02
        {
            // Arrange
            Alarm alarm = new Alarm();
            alarm.TriggerAlarm("Test Alert");

        }
            */

        // TEST_INTEGRATION_ALARM_03
        {

            // Arrange
            Alarm alarm = new Alarm();

            // Trigger Alarm, check to see if only one created 
            alarm.TriggerAlarm("Test", null);
            Thread.sleep(2000);
            alarm.TriggerAlarm("Test", null);

        }
        

        
    }

    private static void TestBlinds() {
        
    }

    private static void TestCamera() {
        //What
        //The
        //Frick
        //Frack
    }

    private static void TestCoffeeMachine() {
        // Testing constructor creates good object
        {
            CoffeeMachine coffee = new CoffeeMachine();
            Assert.AreEqual(coffee.Check(), "GOOD");
        }

        // Test getters

        // Flavour
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "espresso";
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_FLAVOUR);
            Assert.AreEqual(expected, actual);
        }
        // Beans remaining
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "0.0";
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_LEFT);
            Assert.AreEqual(expected, actual);
        }
        // Brew time
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "00:00:20"; // LocalTime format
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_BREWTIME);
            Assert.AreEqual(expected, actual);
        }
        // Make time
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "08:00"; // LocalTime format, seconds autocutoff
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_MAKETIME);
            Assert.AreEqual(expected, actual);
        }
        // Brew time remaining in current cycle, should be none when not running
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "00:00"; // LocalTime format, seconds autocutoff
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_BREWTIMELEFT);
            Assert.AreEqual(expected, actual);
        }
        // Beans brew cost
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "0.4";
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_BREWCOST);
            Assert.AreEqual(expected, actual);
        }
        // Bean days
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday";
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_DAYS);
            Assert.AreEqual(expected, actual);
        }

        // Test setters, requires functioning getters

        // Change flavour, uses previously tested get command
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "latte";
            String actual;

            coffee.Set(Device.COMMAND_SET.BEAN_FLAVOUR, "laTTe");
            actual = coffee.Get(Device.COMMAND_GET.BEAN_FLAVOUR);
            
            Assert.AreEqual(expected, actual);
        }
        // New bean, uses previously tested set command, previously tested get command
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "coffee";
            String actual;

            // Add the bean
            coffee.Set(Device.COMMAND_SET.BEAN_NEW, "coffee");
            // Set the bean
            coffee.Set(Device.COMMAND_SET.BEAN_FLAVOUR, "coffee");
            // Get the bean
            actual = coffee.Get(Device.COMMAND_GET.BEAN_FLAVOUR);

            Assert.AreEqual(expected, actual);
            
        }
        // Add bean, uses previously tested set command, previously tested get command
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "2.0";
            String actual;

            // Set the bean
            coffee.Set(Device.COMMAND_SET.BEAN_ADD, "2.0");
            // Get the bean
            actual = coffee.Get(Device.COMMAND_GET.BEAN_LEFT);

            Assert.AreEqual(expected, actual);
            
        }
        // Change bean brewtime, uses previously tested get command
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "10:45:23";
            String actual;

            // Add the beans
            coffee.Set(Device.COMMAND_SET.BEAN_MAKETIME, "10:45:23");
            // Get the bean
            actual = coffee.Get(Device.COMMAND_GET.BEAN_MAKETIME);

            Assert.AreEqual(expected, actual);
            
        }
        // Change bean brewtime, checks that machine should be ERROR_NO_START, uses previously tested set command
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "ERROR_NO_START";
            String actual;

            // Get the time
            LocalTime lt = LocalTime.now();
            lt.plusSeconds(5);
            coffee.Set(Device.COMMAND_SET.BEAN_MAKETIME, lt.toString());
            // Get the bean
            actual = coffee.Check();

            Assert.AreEqual(expected, actual);
        }
        // Changes bean brewdays, uses previously tested get command
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "Monday, Tuesday, Wednesday, Thursday, Friday";
            String actual;

            // Change the bean days
            coffee.Set(Device.COMMAND_SET.BEAN_DAYS, "Sunday, Saturday, Sunday, Sunday");
            // Get the bean days
            actual = coffee.Get(Device.COMMAND_GET.BEAN_DAYS);

            Assert.AreEqual(expected, actual);
        }

        // Test calling
        
        // Start and stop machine
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "GOOD";
            String actual;

            // Change state
            coffee.Call(COMMAND_CALL.START, "");
            coffee.Call(COMMAND_CALL.STOP, "");

            actual = coffee.Check();

            Assert.AreEqual(expected, actual);
        }
    }

    private static void TestSensor() {

        // 1
        // TEST_UNIT_SENSOR_01 p1
        {
            // Arrange
            Sensor sensor = new Sensor();

            // Get Vals
            boolean actual = sensor.GetIsDismissed();
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual , expected);
        }

        // 2
        // TEST_UNIT_SENSOR_01 p2
        {
            // Arrange
            Sensor sensor = new Sensor();

            // Get Vals
            boolean actual = sensor.GetIsOpen();
            boolean expected = false;

            // Assert
            Assert.AreEqual(actual , expected);
        }

        // 3
        // TEST_UNIT_SENSOR_01 p3
        {
            // Arrange
            Sensor sensor = new Sensor();

            // Get Vals
            LocalTime actual = sensor.GetOpenTime();
            LocalTime expected = LocalTime.parse("06:00:00");

            // Assert
            Assert.AreEqual(actual , expected);
        }

        // 4
        // TEST_UNIT_SENSOR_01 p4
        {
            // Arrange
            Sensor sensor = new Sensor();

            // Get Vals
            LocalTime actual = sensor.GetCloseTime();
            LocalTime expected = LocalTime.parse("18:00:00");

            // Assert
            Assert.AreEqual(actual , expected);
        }

        // 5
        // TEST_UNIT_SENSOR_02
        {
            // Arrange
            LocalTime ot = LocalTime.parse("11:00:00");
            LocalTime ct = LocalTime.parse("20:00:00");
            Sensor sensor = new Sensor(true, true, ot, ct, null);

            // Get Vals
            boolean actual = true;
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==true) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 6
        // TEST_UNIT_SENSOR_03
        {
            // Arrange
            LocalTime ot = LocalTime.parse("13:35:00");
            LocalTime ct = LocalTime.parse("23:14:00");
            Sensor sensor = new Sensor(false, true, ot, ct, null);

            // Get Vals
            boolean actual = true;
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==false) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 7
        // TEST_UNIT_SENSOR_04
        {
            // Arrange
            LocalTime ot = LocalTime.parse("03:20:00");
            LocalTime ct = LocalTime.parse("14:00:00");
            Sensor sensor = new Sensor();

            // set vals
            sensor.SetOpenTime(ot);
            sensor.SetOpenTime(ct);
            sensor.SetIsDismissed(true);
            sensor.SetIsOpen(true);

            // Get Vals
            boolean actual = true;
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==true) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 8
        // TEST_UNIT_SENSOR_05
        {
            // Arrange
            LocalTime ot = LocalTime.parse("04:00:00");
            LocalTime ct = LocalTime.parse("19:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.readDataFromFile("04:00:00,19:00:00,1,1");

            // Get Vals
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==true) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 9
        // TEST_UNIT_SENSOR_06
        {
            // Arrange
            LocalTime ot = LocalTime.parse("04:00:00");
            LocalTime ct = LocalTime.parse("19:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.readDataFromFile("04:00:00,19:00:00,1");

            // Get Vals
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==true) {actual=false;}

            // Assert
            Assert.IsFalse(actual);
        }

        // 10
        // TEST_UNIT_SENSOR_07
        {
            // Arrange
            LocalTime ot = LocalTime.parse("06:00:00");
            LocalTime ct = LocalTime.parse("13:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.readDataFromFile("25:00:00,13:00:00,1,0");

            // Get Vals
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==false) {actual=false;}

            // Assert
            Assert.IsFalse(actual);
        }

        // 11
        // TEST_UNIT_SENSOR_08
        {
            // Arrange
            LocalTime ot = LocalTime.parse("15:00:00");
            LocalTime ct = LocalTime.parse("18:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.readDataFromFile("15:00:00,hi,1,0");

            // Get Vals
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==false) {actual=false;}

            // Assert
            Assert.IsFalse(actual);
        }

        // 12
        // TEST_UNIT_SENSOR_09
        {
            // Arrange
            LocalTime ot = LocalTime.parse("11:00:00");
            LocalTime ct = LocalTime.parse("17:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.readDataFromFile("11:00:00,17:00:00,-1,0");

            // Get Vals
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==true) {actual=false;}
            else if (!sensor.GetIsOpen()==false) {actual=false;}

            // Assert
            Assert.IsFalse(actual);
        }

        // 13
        // TEST_UNIT_SENSOR_10
        {
            // Arrange
            LocalTime ot = LocalTime.parse("02:00:00");
            LocalTime ct = LocalTime.parse("23:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.readDataFromFile("02:00:00,23:00:00,0,3");

            // Get Vals
            if (!sensor.GetOpenTime().equals(ot)) {actual=false;}
            else if (!sensor.GetCloseTime().equals(ct)) {actual=false;}
            else if (!sensor.GetIsDismissed()==false) {actual=false;}
            else if (!sensor.GetIsOpen()==true) {actual=false;}

            // Assert
            Assert.IsFalse(actual);
        }

        /* STATES is private cannot be tested
        // 14
        // TEST_UNIT_SENSOR_10
        {
            // Arrange
            LocalTime time = LocalTime.parse("00:00:00");
            Sensor sensor = new Sensor();

            // set vals
            boolean actual = sensor.Check().equals(STATES.GOOD);
            sensor.SetOpenTime(time);
            sensor.SetCloseTime(time);


            // Get Vals
            if (!sensor.Check().equals(STATES.ERROR_INVALID_TIME)) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }
            */
        


        // 15
        /* Awaiting Implementation 
        // #
        // TEST_INTEGRATION_SENSOR_01
        {

            // Arrange
            Sensor sensor = new Sensor();
            Blinds blinds = new Blinds();
        }

        // 16
        // TEST_INTEGRATION_SENSOR_02
        {

            // Arrange
            Sensor sensor = new Sensor();
        }


        
        */
        // 17
        // TEST_INTEGRATION_SENSOR_03
        {

            // Arrange
            Sensor sensor = new Sensor();
            Alarm alarm = new Alarm();
            alarm.SetIsBeeping(true);
            sensor.SetIsDismissed(true);
            Assert.IsTrue(alarm.Beep());
        }

    }

    private static void TestShower() {
        // Test constructor
        {
            Shower shower = new Shower();
            String expected = "GOOD";
            String actual;

            actual = shower.Check();

            Assert.AreEqual(expected, actual);
        }

        // Test getters

        // Get temperature
        {
            Shower shower = new Shower();
            String expected = "15.0";
            String actual;

            actual = shower.Get(COMMAND_GET.SHOWER_TEMPERATURE);

            Assert.AreEqual(expected, actual);
        }
        // Get headtype
        {
            Shower shower = new Shower();
            String expected = "high efficiency";
            String actual;

            actual = shower.Get(COMMAND_GET.SHOWER_HEADTYPE);

            Assert.AreEqual(expected, actual);
        }

        // Test setters

        // Set temperature
        {
            Shower shower = new Shower();
            String expected = "20.3";
            String actual;

            shower.Set(COMMAND_SET.SHOWER_TEMPERATURE, "20.3");
            actual = shower.Get(COMMAND_GET.SHOWER_TEMPERATURE);

            Assert.AreEqual(expected, actual);
        }
        // Set headtype
        {
            Shower shower = new Shower();
            String expected = "rain";
            String actual;

            shower.Set(COMMAND_SET.SHOWER_HEADTYPE, "RaiN");
            actual = shower.Get(COMMAND_GET.SHOWER_HEADTYPE);

            Assert.AreEqual(expected, actual);
        }

        // Test calls

        // Start
        {
            Shower shower = new Shower();
            String expected = "GOOD";
            String actual;

            actual = shower.Call(COMMAND_CALL.START, "");

            Assert.AreEqual(expected, actual);
        }
        // Stop
        {
            Shower shower = new Shower();
            String expected = "GOOD";
            String actual;
            
            shower.Call(COMMAND_CALL.START, "");
            actual = shower.Call(COMMAND_CALL.STOP, "");

            Assert.AreEqual(expected, actual);
        }
    }

    private static void TestSmokeDetector() {

    }

    private static void TestThermostat() {

    }

    private static void TestWindowManager() {

    }

    private static void TestAlertManager() throws InterruptedException {


        // 1
        // TEST_UNIT_ALERTMANAGER_01
        {
            // Arrange
            AlertManager am = new AlertManager();

            // set vals
            boolean actual = true;

            // Get Vals
            if (!(am.GetAlarm()==null)) {actual=false;}
            else if (!am.GetAlert().equals("An error has occured")) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 2
        // TEST_UNIT_ALERTMANAGER_02
        {
            // Arrange
            Alarm alarm = new Alarm();
            AlertManager am = new AlertManager("Error 404", alarm);

            // set vals
            boolean actual = true;

            // Get Vals
            if (!(am.GetAlarm().equals(alarm))) {actual=false;}
            else if (!am.GetAlert().equals("Error 404")) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 3
        // TEST_UNIT_ALERTMANAGER_03
        {
            // Arrange
            AlertManager am = new AlertManager();

            // set vals
            boolean actual = am.Beep();

            // Assert
            Assert.IsFalse(actual);
        }

        // 4
        // TEST_UNIT_ALERTMANAGER_04
        {
            // Arrange
            Alarm alarm = new Alarm();
            AlertManager am = new AlertManager("", alarm);

            // set vals
            alarm.SetIsBeeping(true);
            boolean actual = am.Beep();

            // Assert
            Assert.IsTrue(actual);
        }

        // 5
        // TEST_UNIT_ALERTMANAGER_05
        {
            // Arrange
            AlertManager am = new AlertManager();

            // set vals
            Thread.sleep(2000);
            am.CloseWindow();
        }

        // 6
        // TEST_UNIT_ALERTMANAGER_06
        {
            // Arrange
            Alarm alarm = new Alarm();
            AlertManager am = new AlertManager("Alarm Triggered", alarm);

            // set vals
            boolean actual = true;

            // Get Vals
            if (!(am.GetAlarm().equals(alarm))) {actual=false;}
            else if (!am.GetAlert().equals("Test Alert")) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 7
        // TEST_UNIT_ALERTMANAGER_07
        {
            // Arrange
            Alarm alarm = new Alarm();
            AlertManager am = new AlertManager();

            // set vals
            am.SetAlarm(alarm);
            am.SetAlert("Test Alert");
            boolean actual = true;

            // Get Vals
            if (!(am.GetAlarm().equals(alarm))) {actual=false;}
            else if (!am.GetAlert().equals("Test Alert")) {actual=false;}

            // Assert
            Assert.IsTrue(actual);
        }

        // 8
        // TEST_INTEGRATION_ALERTMANAGER_01
        {
            // Arrange
            Alarm alarm = new Alarm();
            
            // Creates a AlertManager
            alarm.TriggerAlarm("Alarm Test Error", null);

            // View variables on the pop-up window
        }

        /* awaiting window manager
        // 1
        // TEST_INTEGRATION_ALERTMANAGER_02
        {
            // Arrange
            Alarm alarm = new Alarm();
        }
            */

        


    }

    private static void TestFileManager() {

    }

    private static void TestDeviceManager() {

    }
}
