package services;

import services.UserManager;
import models.MedicineReminder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import models.User;
import utils.DatabaseConnection;

import static utils.Validation.getStringInput;

public class MedicineReminderManager {

    public static void addMedicineReminder(int userId) {
        System.out.println("Add a Medicine Reminder:");
        System.out.println();
        String medicineName = getStringInput("Enter Medicine Name: ");
        String dosage = getStringInput("Enter Dosage: ");
        String schedule = getStringInput("Enter Schedule: ");
        LocalDate startDate = parseLocalDate(getStringInput("Enter Start Date (YYYY-MM-DD): "));
        LocalDate endDate = parseLocalDate(getStringInput("Enter End Date (YYYY-MM-DD): "));

        MedicineReminder reminder = new MedicineReminder(userId, medicineName, dosage, schedule, startDate, endDate);

        try (Connection connection = DatabaseConnection.getCon()) {

            // SQL query to insert a new medicine reminder
            String query = "INSERT INTO medicine_reminders (id, user_id, medicine_name, dosage, schedule, start_date, end_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(reminder.getStartDate());
            java.sql.Date sqlEndDate = java.sql.Date.valueOf(reminder.getEndDate());

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, reminder.getId());
                statement.setInt(2, reminder.getUserId());
                statement.setString(3, reminder.getMedicineName());
                statement.setString(4, reminder.getDosage());
                statement.setString(5, reminder.getSchedule());
                statement.setDate(6, sqlStartDate);
                statement.setDate(7, sqlEndDate);

                // Execute the query
                statement.executeUpdate();
                System.out.println("Reminder added successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    public static LocalDate parseLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
    public static void getRemindersForUser(int userId) {
        ArrayList<MedicineReminder> reminders = new ArrayList<>();

        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?";
        User user = UserManager.getUserById(userId);

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    MedicineReminder reminder = new MedicineReminder(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("medicine_name"),
                            resultSet.getString("dosage"),
                            resultSet.getString("schedule"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate()
                    );

                    reminders.add(reminder);
                }
                for (MedicineReminder reminder: reminders) {
                    printReminderDetails(reminder);
                }
                if (reminders.isEmpty()) {
                    System.out.println("\nThere's no Medicine Reminders for patient " + user.getFirstName() + " " + user.getLastName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public static void getDueReminders(int userId) {
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ? AND end_date >= ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, userId);

            // Get the current date
            LocalDate currentDate = LocalDate.now();
            java.sql.Date sqlCurrentDate = java.sql.Date.valueOf(currentDate);

            // Set the parameters for the query
            statement.setDate(2, sqlCurrentDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    MedicineReminder reminder = createMedicineReminderFromResultSet(resultSet);
                    printReminderDetails(reminder);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private static void printReminderDetails(MedicineReminder reminder) {
        User user = UserManager.getUserById(reminder.getUserId());

        System.out.println();
        System.out.println("Reminder for patient " + user.getFirstName() + " " + user.getLastName() + ":");
        System.out.println("Reminder ID: " + reminder.getId());
        System.out.println("User ID: " + reminder.getUserId());
        System.out.println("Medicine Name: " + reminder.getMedicineName());
        System.out.println("Dosage: " + reminder.getDosage());
        System.out.println("Schedule: " + reminder.getSchedule());
        System.out.println("Start Date: " + reminder.getStartDate());
        System.out.println("End Date: " + reminder.getEndDate());
        System.out.println();
    }


        public static MedicineReminder getMedicineReminderById(int reminderId) {
            String query = "SELECT * FROM medicine_reminders WHERE id = ?";

            try (Connection con = DatabaseConnection.getCon();
                 PreparedStatement statement = con.prepareStatement(query)) {

                statement.setInt(1, reminderId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return createMedicineReminderFromResultSet(resultSet);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            return null; // Return null if no reminder is found
        }
    // Helper method to create MedicineReminder from ResultSet
    private static MedicineReminder createMedicineReminderFromResultSet(ResultSet resultSet) throws SQLException {

        return new MedicineReminder(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("medicine_name"),
                resultSet.getString("dosage"),
                resultSet.getString("schedule"),
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDate("end_date").toLocalDate()
        );
    }
    public static void updateMedicineReminder(int reminderId) {

        String startDate;
        String endDate;

        System.out.println("Update Medicine Reminder:");
        System.out.println();

        // Assuming you have a method to get the existing reminder by ID
        MedicineReminder existingReminder = getMedicineReminderById(reminderId);

        if (existingReminder == null) {
            System.out.println("No such reminder found.");
            return;
        }
        User user = UserManager.getUserById(existingReminder.getUserId());
        System.out.println();
        System.out.println("Update Reminder for user: " + user.getFirstName() + " " + user.getLastName() + ":");
        System.out.println("Reminder ID: " + existingReminder.getId());
        System.out.println("User ID: " + existingReminder.getUserId());
        System.out.println("Medicine Name: " + existingReminder.getMedicineName());
        System.out.println("Dosage: " + existingReminder.getDosage());
        System.out.println("Schedule: " + existingReminder.getSchedule());
        System.out.println("Start Date: " + existingReminder.getStartDate());
        System.out.println("End Date: " + existingReminder.getEndDate());
        System.out.println();
        // Collect updated information
        String medicineName = getStringInput("Enter updated Medicine Name (press Enter to keep the existing value): ");
        String dosage = getStringInput("Enter updated Dosage (press Enter to keep the existing value): ");
        String schedule = getStringInput("Enter updated Schedule (press Enter to keep the existing value): ");
        startDate = getStringInput("Enter updated Start Date (YYYY-MM-DD) (press Enter to keep the existing value): ");
        endDate = getStringInput("Enter updated End Date (YYYY-MM-DD) (press Enter to keep the existing value): ");

        // Update the existing reminder with new information
        if (!medicineName.isEmpty()) {
            existingReminder.setMedicineName(medicineName);
        }

        if (!dosage.isEmpty()) {
            existingReminder.setDosage(dosage);
        }

        if (!schedule.isEmpty()) {
            existingReminder.setSchedule(schedule);
        }

        if (!startDate.isEmpty()) {
            existingReminder.setStartDate(startDate);
        }

        if (!endDate.isEmpty()) {
            existingReminder.setEndDate(endDate);
        }

        try (Connection connection = DatabaseConnection.getCon()) {
            // SQL query to update the medicine reminder
            String query = "UPDATE medicine_reminders SET medicine_name=?, dosage=?, schedule=?, start_date=?, end_date=? WHERE id=?";
            java.sql.Date sqlStartDate = java.sql.Date.valueOf(existingReminder.getStartDate());
            java.sql.Date sqlEndDate = java.sql.Date.valueOf(existingReminder.getEndDate());

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, existingReminder.getMedicineName());
                statement.setString(2, existingReminder.getDosage());
                statement.setString(3, existingReminder.getSchedule());
                statement.setDate(4, sqlStartDate);
                statement.setDate(5, sqlEndDate);
                statement.setInt(6, existingReminder.getId());

                // Execute the query
                statement.executeUpdate();
                System.out.println("Reminder updated successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    public static void deleteMedicineReminder(int reminderId) {
        System.out.println("Delete Medicine Reminder:");
        System.out.println();

        try (Connection connection = DatabaseConnection.getCon()) {
            // SQL query to delete the medicine reminder by ID
            String query = "DELETE FROM medicine_reminders WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, reminderId);

                // Execute the query
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Reminder deleted successfully");
                } else {
                    System.out.println("No such reminder found with ID: " + reminderId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
