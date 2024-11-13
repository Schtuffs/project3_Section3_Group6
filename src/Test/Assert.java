package Test;

// Holds the results of all tests that used the asserts
// Results can be printed to check for total successes
public class Assert {
    // Holds test information
    private static int totalTests;
    private static int successfulTests;
    private static int failedTests;

    public static void AreEqual(Object o1, Object o2) {
        totalTests++;
        boolean result = o1.equals(o2);
        if (result) {
            successfulTests++;
        }
        else {
            failedTests++;
            System.out.println("Test " + totalTests + " failed: " + o1 + " != " + o2);
        }
    }

    public static void AreNotEqual(Object o1, Object o2) {
        totalTests++;
        boolean result = !(o1.equals(o2));
        if (result) {
            successfulTests++;
        }
        else {
            failedTests++;
            System.out.println("Test " + totalTests + " failed: " + o1 + " != " + o2);
        }
    }

    public static void IsTrue(boolean value) {
        totalTests++;
        if (value) {
            successfulTests++;
        }
        else {
            failedTests++;
            System.out.println("Test " + totalTests + " failed: " + value + " != " + true);
        }
    }

    public static void IsFalse(boolean value) {
        totalTests++;
        if (!value) {
            successfulTests++;
        }
        else {
            failedTests++;
            System.out.println("Test " + totalTests + " failed: " + value + " != " + false);
        }
    }

    public static void Results() {
        if (totalTests == 0) {
            System.out.println("No tests were performed.");
            return;
        }
        System.out.println("\nTotal Tests: " + totalTests);
        System.out.println("Successful Tests: " + successfulTests);
        System.out.println("Failed Tests: " + failedTests);
        System.out.println("Success Ratio: " + ((double)(successfulTests) / (double)(totalTests) * 100.) + "%");
    }
}
