package Test;

import Devices.*;

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
            String expected = "0.0"; // LocalTime format
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
        // Beans brew cost
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "0.4"; // LocalTime format
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_BREWCOST);
            Assert.AreEqual(expected, actual);
        }
        // Bean days
        {
            CoffeeMachine coffee = new CoffeeMachine();
            String expected = "Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday"; // LocalTime format
            String actual = coffee.Get(Device.COMMAND_GET.BEAN_DAYS);
            Assert.AreEqual(expected, actual);
        }
    }

    private static void TestSensor() {

    }

    private static void TestShower() {

    }

    private static void TestSmokeDetector() {

    }

    private static void TestThermostat() {

    }
}
