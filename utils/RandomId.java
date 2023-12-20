package utils;

import java.util.Random;

public class RandomId {
    public static int generateRandomId() {
        // Generate a random integer ID (replace 100000 with the desired range)
        Random random = new Random();
        return random.nextInt(100000);
    }
}
