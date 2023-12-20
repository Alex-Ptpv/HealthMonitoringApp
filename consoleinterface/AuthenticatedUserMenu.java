package consoleinterface;

import models.User;
import services.HealthDataManager;
import services.MedicineReminderManager;
import services.RecommendationsManager;
import services.UserManager;

import java.sql.SQLException;
import java.util.Scanner;

import static consoleinterface.ConsoleUserInterface.startMenu;

public class AuthenticatedUserMenu {

    public static void patientMenu(User user) throws SQLException {
        boolean exit = false;
        System.out.println("\nWelcome to the Patients Portal " + user.getFirstName() + " " + user.getLastName());

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. View My Health Data");
            System.out.println("2. Add Health Data");
            System.out.println("3. View recommendations");
            System.out.println("4. View medicine reminders");
            System.out.println("5. Get due reminders");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            int userId = 0;
            int reminderId = 0;
            switch (choice) {
                case 1:
                    HealthDataManager.getHealthDataByUserId(user.getId());
                    break;
                case 2:
                    HealthDataManager.inputHealthData(user);
                    break;
                case 3:
                    RecommendationsManager.getRecommendationsByUserId(user.getId());
                    break;
                case 4:
                    MedicineReminderManager.getRemindersForUser(user.getId());
                    break;
                case 5:
                    MedicineReminderManager.getDueReminders(user.getId());
                    break;
                case 6:
                    exit = true;
                    UserManager.setLoggedInStatus(user.getId(), false);
                    System.out.print("\nExit was successful");
                    startMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void doctorMenu (User user) throws SQLException {
        boolean exit = false;
        while (!exit) {

            System.out.println("\nWelcome to the Doctor Portal dr." + user.getFirstName() + " " + user.getLastName());
            System.out.println("\nMenu:");
            System.out.println("1. View My Patients List");
            System.out.println("2. View My Patient Health Data");
            System.out.println("3. View My Patient Medicine Reminders");
            System.out.println("4. Add Medicine Reminder");
            System.out.println("5. Update Medicine Reminder");
            System.out.println("6. Delete Medicine Reminder");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            int userId = 0;
            int reminderId = 0;
            switch (choice) {
                case 1:
                    UserManager.getPatientsList(user);
                    break;
                case 2:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextInt();
                    UserManager.getHealthDataByUserId(userId);
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextInt();
                    MedicineReminderManager.getRemindersForUser(userId);
                    break;
                case 4:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextInt();
                    MedicineReminderManager.addMedicineReminder(userId);
                    break;
                case 5:
                    System.out.print("Enter Reminder ID: ");
                    reminderId = scanner.nextInt();
                    MedicineReminderManager.updateMedicineReminder(reminderId);
                    break;
                case 6:
                    System.out.print("Enter Reminder ID: ");
                    reminderId = scanner.nextInt();
                    MedicineReminderManager.deleteMedicineReminder(reminderId);
                    break;
                case 7:
                    UserManager.setLoggedInStatus(user.getId(), false);
                    exit = true;
                    System.out.print("\nExit was successful\n");
                    startMenu();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

    }
}
