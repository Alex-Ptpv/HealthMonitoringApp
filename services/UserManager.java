package services;

import models.HealthData;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public boolean createUser(User user) {
        boolean success = false;
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        String query = "INSERT INTO users (id, first_name, last_name, email, password, is_doctor, medical_license_number, specialization) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, hashedPassword);
            statement.setBoolean(6, user.isDoctor());
            statement.setString(7, user.getMedicalLicenseNumber());
            statement.setString(8, user.getSpecialization());
            System.out.print(user.toString());

            int updatedRows = statement.executeUpdate();
            success = updatedRows > 0;
            System.out.println("User added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public static User getUserById(int id) {
        User user = null;

        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User getUserByEmail(String email) {
        User user = null;

        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user = extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean updateUser(User user) {
        boolean success = false;
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        String query = "UPDATE users " +
                "SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ? " +
                "WHERE id = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            statement.setInt(6, user.getId());

            int updatedRows = statement.executeUpdate();
            success = updatedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean deleteUser(int id) {
        boolean success = false;

        String query = "DELETE FROM users WHERE id = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);

            int rowsUpdated = statement.executeUpdate();
            success = rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean authenticateUser(String email, String password) {
        boolean success = false;
        String query = "SELECT password FROM users WHERE email = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                success = BCrypt.checkpw(password, hashedPassword);
            }
            if (success) {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    public static int getUserCount() {
        int userCount = 0;
        String query = "SELECT COUNT(*) FROM users";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                userCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userCount;
    }

    private static User extractUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        boolean isDoctor = rs.getBoolean("is_doctor");
        String medicalLicenseNumber = rs.getString("medical_license_number");
        String specialization = rs.getString("specialization");
        boolean isLoggedIn = rs.getBoolean("isloggedin");

        return new User(id, firstName, lastName, email, password, isDoctor, medicalLicenseNumber, specialization, isLoggedIn);
    }
    public static void setLoggedInStatus(int userId, boolean isLoggedIn) {
        // Prepare the SQL query
        String query = "UPDATE users SET isloggedin = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            // Set parameters
            statement.setBoolean(1, isLoggedIn);
            statement.setInt(2, userId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getPatientsList(User doctor) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE id IN (SELECT patient_id FROM doctor_patient WHERE doctor_id = ?)";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

                statement.setInt(1, doctor.getId());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User patient = new User(resultSet);

                        users.add(patient);
                    }
                    System.out.println("Here's list of your patients: ");
                    for (User patient : users) {
                        System.out.println("\nUser ID: " + patient.getId());
                        System.out.println("First Name: " + patient.getFirstName());
                        System.out.println("Last Name: " + patient.getLastName());

                        System.out.println(); // Add a blank line for better readability
                    }
                }
            } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }
    public static void getHealthDataByUserId(int userId) throws SQLException {
        List<HealthData> healthDataList = new ArrayList<>();
        String query = "SELECT * FROM health_data WHERE user_id = ?";
        User user = getUserById(userId);

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        HealthData healthData = new HealthData(resultSet);
                        healthDataList.add(healthData);
                    }
                    System.out.println("\nHere's health data for: " + user.getFirstName() + " " + user.getLastName());
                    for (HealthData healthData : healthDataList) {

                        System.out.println("\nDate: " + healthData.getDate());
                        System.out.println("Weight: " + healthData.getWeight());
                        System.out.println("Height: " + healthData.getHeight());
                        System.out.println("Steps: " + healthData.getSteps());
                        System.out.println("Heart Rate: " + healthData.getHeartRate());
                        System.out.println("Sleep Hours: " + healthData.getSleepHours());
                        System.out.println("Caloric Intake: " + healthData.getCaloricIntake());
                        System.out.println("Stress Level: " + healthData.getStressLevel());

                        System.out.println();
                    }

                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User checkLoggedInUsers() {
        // Prepare the SQL query
        String query = "SELECT * FROM users WHERE isloggedin = true";

        // Database logic to retrieve logged-in users using Prepared Statement
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Check if there are any rows in the result set
            if (resultSet.next()) {
                // Extract user information and return the User object
                return extractUserFromResultSet(resultSet);
            } else {
                // No logged-in users found
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
