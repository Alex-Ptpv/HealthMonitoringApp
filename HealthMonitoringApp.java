// package com.keyin.client;

import consoleinterface.AuthenticatedUserMenu;
import consoleinterface.ConsoleUserInterface;
import models.User;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static services.UserManager.checkLoggedInUsers;

public class HealthMonitoringApp {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getCon();
        if (connection != null) {
            System.out.println("Connection successful!");
            try {
                User user = checkLoggedInUsers();
                assert user != null;
                if (user != null && user.getIsLoggedIn()) {
                    if (user.isDoctor()) {
                        AuthenticatedUserMenu.doctorMenu(user);
                    } else {
                        AuthenticatedUserMenu.patientMenu(user);
                    }
                } else {
                    ConsoleUserInterface.startMenu();
                }
                
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to connect to the database!");
        }
    }
}
