package models;

import utils.RandomId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HealthData {
    private final int id;
    private final int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private int sleepHours;
    private int caloricIntake;
    private int stressLevel;
    private LocalDate date;

    // Constructor
    public HealthData(int userId, double weight, double height, int steps, int heartRate, int sleepHours, int caloricIntake, int stressLevel, LocalDate date) {

        this.id = RandomId.generateRandomId();
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.sleepHours = sleepHours;
        this.caloricIntake = caloricIntake;
        this.stressLevel = stressLevel;
        this.date = date;
    }
    public HealthData(int id, int userId, double weight, double height, int steps, int heartRate, int sleepHours, int caloricIntake, int stressLevel, LocalDate date) {

        this.id = RandomId.generateRandomId();
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.sleepHours = sleepHours;
        this.caloricIntake = caloricIntake;
        this.stressLevel = stressLevel;
        this.date = date;
    }
    public HealthData(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.userId = resultSet.getInt("user_id");
        this.weight = resultSet.getDouble("weight");
        this.height = resultSet.getDouble("height");
        this.steps = resultSet.getInt("steps");
        this.heartRate = resultSet.getInt("heart_rate");
        this.sleepHours = resultSet.getInt("sleep_hours");
        this.caloricIntake = resultSet.getInt("caloric_intake");
        this.stressLevel = resultSet.getInt("stress_level");
        this.date = resultSet.getDate("date").toLocalDate();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getSleepHours() {
        return sleepHours;
    }

    public void setSleepHours(int sleepHours) {
        this.sleepHours = sleepHours;
    }

    public int getCaloricIntake() {
        return caloricIntake;
    }

    public void setCaloricIntake(int caloricIntake) {
        this.caloricIntake = caloricIntake;
    }

    public int getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(int stressLevel) {
        this.stressLevel = stressLevel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public static LocalDate convertStringToDate(String dateString) {
        // Define the format of your date string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the string to LocalDate
        return LocalDate.parse(dateString, formatter);
    }
}
