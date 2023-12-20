package services;

import models.HealthData;

import java.util.ArrayList;
import java.util.List;

public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;
    private static final double MIN_WEIGHT = 50.0;
    private static final double MAX_WEIGHT = 200.0;
    private static final double MIN_HEIGHT = 100.0;
    private static final double MAX_HEIGHT = 220.0;
    private static final int MIN_SLEEP_HOURS = 6;
    private static final int MAX_SLEEP_HOURS = 9;
    private static final int MIN_CALORIC_INTAKE = 2000;
    private static final int MAX_CALORIC_INTAKE = 3000;
    private static final int STRESS_LEVEL_THRESHOLD = 7;

    public static void generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

        int heartRate = healthData.getHeartRate();
        int steps = healthData.getSteps();
        double weight = healthData.getWeight();
        double height = healthData.getHeight();
        int sleepHours = healthData.getSleepHours();
        int caloricIntake = healthData.getCaloricIntake();
        int stressLevel = healthData.getStressLevel();

        // Analyze heart rate
        if (heartRate < MIN_HEART_RATE) {
            recommendations.add("Your heart rate is lower than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        }
        if (heartRate > MAX_HEART_RATE) {
            recommendations.add("Your heart rate is higher than the recommended range. " +
                    "Consider consulting with a healthcare professional to assess your cardiovascular health.");
        }

        // Analyze steps
        if (steps < MIN_STEPS) {
            recommendations.add("You're not reaching the recommended daily step count. " +
                    "Try to incorporate more walking or other physical activities into your daily routine.");
        }

        // Analyze weight
        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            recommendations.add("Your weight is outside the healthy range. Consider consulting with a nutritionist or healthcare professional.");
        }

        // Analyze height
        if (height < MIN_HEIGHT || height > MAX_HEIGHT) {
            recommendations.add("Your height is outside the typical range. Confirm the measurement and consult with a healthcare professional if needed.");
        }

        // Analyze sleep hours
        if (sleepHours < MIN_SLEEP_HOURS || sleepHours > MAX_SLEEP_HOURS) {
            recommendations.add("Your sleep hours are outside the recommended range. Ensure you are getting sufficient sleep for optimal health.");
        }

        // Analyze caloric intake
        if (caloricIntake < MIN_CALORIC_INTAKE || caloricIntake > MAX_CALORIC_INTAKE) {
            recommendations.add("Your caloric intake is outside the recommended range. Consider consulting with a nutritionist for personalized dietary advice.");
        }

        // Analyze stress level
        if (stressLevel >= STRESS_LEVEL_THRESHOLD) {
            recommendations.add("Your stress level is elevated. Consider practicing stress management techniques such as meditation or exercise.");
        }

        // Add more health data analysis and recommendations as needed

        if (!recommendations.isEmpty()) {
            for (String recommendation : recommendations) {
                RecommendationsManager.insertRecommendation(healthData.getUserId(), recommendation);
            }
        }
    }
}
