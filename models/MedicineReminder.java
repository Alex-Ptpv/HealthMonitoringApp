package models;

import utils.RandomId;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MedicineReminder {
    private int id;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private LocalDate startDate;
    private LocalDate endDate;

    public MedicineReminder(int id, int userId, String medicineName, String dosage, String schedule, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public MedicineReminder(int userId, String medicineName, String dosage, String schedule, LocalDate startDate, LocalDate endDate) {
        this.id = RandomId.generateRandomId();
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule = schedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getSchedule() {
        return schedule;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setStartDate(String startDate) {
        // Define a DateTimeFormatter for the specified format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the date string into a LocalDate object
        this.startDate = LocalDate.parse(startDate, formatter);
    }

    public void setEndDate(String endDate) {
        // Define a DateTimeFormatter for the specified format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the date string into a LocalDate object
        this.endDate = LocalDate.parse(endDate, formatter);
    }

    public LocalDateTime getDueDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(startDate + " " + schedule, formatter);
    }

    public void notifyUser() {
        LocalDateTime dueDateTime = getDueDateTime();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(dueDateTime)) {
            // The reminder is due; calculate and display the time left
            Duration duration = Duration.between(dueDateTime, now);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();

            System.out.println("Reminder: It's time to take your medicine - " + medicineName);
            System.out.println("Time left: " + hours + " hours and " + minutes + " minutes.");
        } else {
            // The reminder is not due yet
            Duration duration = Duration.between(now, dueDateTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();

            System.out.println("Reminder: It's not yet time to take your medicine - " + medicineName);
            System.out.println("Time left: " + hours + " hours and " + minutes + " minutes.");
        }
    }
}
