package services;

import models.HealthData;
import models.User;
import utils.DatabaseConnection;
import utils.Validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static services.MedicineReminderManager.parseLocalDate;
import static utils.Validation.getStringInput;

public class HealthDataManager {
    public static void inputHealthData(User user) {

        System.out.println("Enter Health Data for " + user.getFirstName() + " " + user.getLastName());

        double weight = Validation.getDoubleInput("Enter weight: ");
        double height = Validation.getDoubleInput("Enter height: ");
        int steps = Validation.getIntInput("Enter steps: ");
        int heartRate = Validation.getIntInput("Enter heart rate: ");
        int sleepHours = Validation.getIntInput("Enter sleep hours: ");
        int caloricIntake = Validation.getIntInput("Enter caloric intake: ");
        int stressLevel = Validation.getIntInput("Enter stress level from 1 to 10: ");
        LocalDate date = parseLocalDate(getStringInput("Enter date (YYYY-MM-DD): "));

        // Create a new HealthData instance with the gathered data
        HealthData healthData = new HealthData(user.getId(), weight, height, steps, heartRate, sleepHours, caloricIntake, stressLevel, date);
        addHealthData(healthData);

    }
    public static void addHealthData(HealthData healthData) {
        boolean success = false;

        // Prepare the SQL query
        String query = "INSERT INTO health_data (id, user_id, weight, height, steps, heart_rate, sleep_hours, caloric_intake, stress_level, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Database logic to insert health data using Prepared Statement
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            java.sql.Date sqlDate = java.sql.Date.valueOf(healthData.getDate());
            statement.setInt(1, healthData.getId());
            statement.setInt(2, healthData.getUserId());
            statement.setDouble(3, healthData.getWeight());
            statement.setDouble(4, healthData.getHeight());
            statement.setInt(5, healthData.getSteps());
            statement.setInt(6, healthData.getHeartRate());
            statement.setInt(7, healthData.getSleepHours());
            statement.setInt(8, healthData.getCaloricIntake());
            statement.setInt(9, healthData.getStressLevel());
            statement.setDate(10, sqlDate);

            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                success = true;
                System.out.println("Data added successfully");
                RecommendationSystem.generateRecommendations(healthData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private static HealthData createHealthDataFromResultSet(ResultSet resultSet) throws SQLException {
        // Extract data from the ResultSet and create a HealthData object
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        double weight = resultSet.getDouble("weight");
        double height = resultSet.getDouble("height");
        int steps = resultSet.getInt("steps");
        int heartRate = resultSet.getInt("heart_rate");
        int sleepHours = resultSet.getInt("sleep_hours");
        int caloricIntake = resultSet.getInt("caloric_intake");
        int stressLevel = resultSet.getInt("stress_level");
        LocalDate date = resultSet.getDate("date").toLocalDate(); // Convert SQL Date to String

        return new HealthData(id, userId, weight, height, steps, heartRate, sleepHours, caloricIntake, stressLevel, date);
    }
    public static void printHealthData(HealthData healthData) {
        System.out.println("Health Data for: " + healthData.getDate());
        System.out.println("Health Data ID: " + healthData.getId());
        System.out.println("Weight: " + healthData.getWeight());
        System.out.println("Height: " + healthData.getHeight());
        System.out.println("Steps: " + healthData.getSteps());
        System.out.println("Heart Rate: " + healthData.getHeartRate());
        System.out.println("Sleep Hours: " + healthData.getSleepHours());
        System.out.println("Caloric Intake: " + healthData.getCaloricIntake());
        System.out.println("Stress Level: " + healthData.getStressLevel());
        System.out.println();
    }

    public static void getHealthDataByUserId(int userId) {
        // Prepare the SQL query
        String query = "SELECT * FROM health_data WHERE user_id = ?";

        // Database logic to retrieve health data using Prepared Statement
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.isBeforeFirst()) {
                    // No health data found for the given user ID
                    System.out.println("\nNo health data found for you");
                } else {
                    // Health data found, print details
                    while (resultSet.next()) {
                        printHealthData(createHealthDataFromResultSet(resultSet));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
