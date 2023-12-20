package models;

import java.time.LocalDate;

public class Recommendation {
    private final int id;
    private final int userId;
    private String recommendationText;
    private LocalDate date;

    // Constructors
    public Recommendation(int id, int userId, String recommendationText, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.recommendationText = recommendationText;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getRecommendationText() {
        return recommendationText;
    }

    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", userId=" + userId +
                ", recommendationText='" + recommendationText + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
