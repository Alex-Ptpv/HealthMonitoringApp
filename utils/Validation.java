package utils;

import java.util.Scanner;

public class Validation {
    private static Scanner scanner = new Scanner(System.in);
    public static double getDoubleInput(String prompt) {
        double value = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (input.matches("-?\\d+(\\.\\d+)?")) {
                    value = Double.parseDouble(input);
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        return value;
    }

    // Helper method to get integer input from the user
    public static int getIntInput(String prompt) {
        int value = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (input.matches("-?\\d+")) {
                    value = Integer.parseInt(input);
                    validInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return value;
    }

    public static String getStringInput(String prompt) {
        String userInput = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.print(prompt);
            userInput = scanner.nextLine();

            if (isValidString(userInput)) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter a valid string.");
            }
        }

        return userInput;
    }

    // Helper method to check if the input is a valid string
    private static boolean isValidString(String input) {
        try {
            String parsedString = String.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
