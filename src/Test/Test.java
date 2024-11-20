package Test;

import java.time.LocalTime;

import Devices.*;
import Devices.Device.COMMAND_GET;
import Devices.Device.COMMAND_SET;

public class Test {
    public static void main(String[] args) {
        TestAlarm();
        TestBlinds();
        TestCamera();
        TestCoffeeMachine();
        TestSensor();
        TestShower();
        TestSmokeDetector();
        TestThermostat();

        Assert.Results();
    }

    private static void TestAlarm() {
        
    }

    private static void TestBlinds() {
        
    }

    private static void TestCamera() {

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
    }

    private static void TestSensor() {

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
    }

    private static void TestSmokeDetector() {

    }

    private static void TestThermostat() {

    }
}
