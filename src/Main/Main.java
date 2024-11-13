package Main;

import java.time.LocalTime;
import Devices.*;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffee = new CoffeeMachine(LocalTime.parse("08:45:00"), "espresso", new String[0], new double[0]);
    }
}
