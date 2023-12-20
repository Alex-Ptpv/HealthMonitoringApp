package services;

import models.Recommendation;
import utils.DatabaseConnection;
import utils.RandomId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecommendationsManager {

    public static void insertRecommendation(int userId, String recommendationText) {
        String query = "INSERT INTO recommendations (id, user_id, recommendation_text, date) VALUES (?, ?, ?, ?)";
        int id = RandomId.generateRandomId();

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.setInt(2, userId);
            statement.setString(3, recommendationText);
            statement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

            statement.executeUpdate();

            System.out.println("Recommendation added successfully.");

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    public static void printRecommendation(Recommendation recommendation) {
        String date = recommendation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Date: " + date + ", Recommendation: " + recommendation.getRecommendationText());
    }
    public static void getRecommendationsByUserId(int userId) {
        String query = "SELECT * FROM recommendations WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String recommendationText = resultSet.getString("recommendation_text");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    Recommendation recommendation = new Recommendation(id, userId, recommendationText, date);
                    printRecommendation(recommendation);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
