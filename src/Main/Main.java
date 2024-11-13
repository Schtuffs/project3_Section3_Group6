package Main;

import java.time.LocalTime;
import Devices.*;

public class Main {
    public static void main(String[] args) {
        String[] str = new String[0];
        double[] dbl = new double[0];
        CoffeeMachine coffee = new CoffeeMachine(LocalTime.parse("10:58:00"), "cappuccino", str, dbl);
        System.out.println("H");
        while (coffee.Check() == Device.STATES.ERROR_NO_BEANS) {}
    }
}
