package models;
import services.UserManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isDoctor;
    private String medicalLicenseNumber;
    private String specialization;
    private boolean isLoggedIn;

    // Constructor without id (id will be generated)
    public User(String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        this.id = UserManager.getUserCount() + 1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
        this.medicalLicenseNumber = isDoctor ? "" : null;
        this.specialization = isDoctor ? "" : null;
        this.isLoggedIn = false;
    }

    // Constructor with id
    public User(Integer id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
        this.medicalLicenseNumber = isDoctor ? medicalLicenseNumber : null;
        this.specialization = isDoctor ? specialization : null;
        this.isLoggedIn = false;
    }
    public User(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.firstName = resultSet.getString("first_name");
        this.lastName = resultSet.getString("last_name");
        this.email = resultSet.getString("email");
        this.password = resultSet.getString("password");
        this.isDoctor = resultSet.getBoolean("is_doctor");
        this.medicalLicenseNumber = isDoctor ? "" : null;
        this.specialization = isDoctor ? "" : null;
        this.isLoggedIn = false;
    }

    public User(int id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization, boolean isLoggedIn) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
        this.medicalLicenseNumber = isDoctor ? medicalLicenseNumber : null;
        this.specialization = isDoctor ? specialization : null;
        this.isLoggedIn = isLoggedIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDoctor() {
        return isDoctor;
    }

    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }

    public String getMedicalLicenseNumber() {
        return medicalLicenseNumber;
    }

    public void setMedicalLicenseNumber(String medicalLicenseNumber) {
        this.medicalLicenseNumber = medicalLicenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isDoctor=" + isDoctor +
                ", medicalLicenseNumber='" + medicalLicenseNumber + '\'' +
                ", specialization='" + specialization + '\'' +
                '}';
    }
}
