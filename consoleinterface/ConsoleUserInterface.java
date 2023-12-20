package consoleinterface;

import services.UserManager;
import models.User;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleUserInterface {
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();

    public static void startMenu() throws SQLException {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Start Menu ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    exit = true;
                    System.out.print("Exit was successful");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void registerUser() {
        System.out.println("=== User Registration ===");

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Are you a doctor? (y/n): ");
        String doctorInput = scanner.next().trim().toLowerCase();
        boolean isDoctor = Objects.equals(doctorInput, "y");

        String medicalLicenseNumber = "";
        String specialization = "";

        if (isDoctor) {
            System.out.print("Enter your medical License Number: ");
            medicalLicenseNumber = scanner.nextLine();
            System.out.print("Enter your medical specialization: ");
            specialization = scanner.nextLine();
        }

        User newUser = new User(firstName, lastName, email, password, isDoctor, medicalLicenseNumber, specialization);
        userManager.createUser(newUser);

        System.out.println("Registration successful.");
    }

    public static void loginUser() throws SQLException {
        System.out.println("=== User Login ===");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        boolean isValid = userManager.authenticateUser(email, password);

        if (isValid) {
            System.out.println("\nWelcome");
            User user = UserManager.getUserByEmail(email);
            userManager.setLoggedInStatus(user.getId(), true);
            if (user.isDoctor()) {
                AuthenticatedUserMenu.doctorMenu(user);
            } else {
                AuthenticatedUserMenu.patientMenu(user);
            }
        } else {
            System.out.println("Invalid email or password. Please try again.");
        }
    }

}
