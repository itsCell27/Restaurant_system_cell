package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    private static final String FILE_PATH = "EmployeeList/employees.csv";  // Path to employee data file

    // Method to handle employee login directly, accepting username and password
    public boolean handleLogin(String username, String password) {
        // Verify credentials by calling the verifyCredentials method
        if (verifyCredentials(username, password)) {
            System.out.println("Login successful!");
            return true;  // Return true if login is successful
        } else {
            System.out.println("Invalid username or password.");
            return false;  // Return false if login fails
        }
    }

    // Method to verify employee credentials from the file
    private boolean verifyCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2 && data[0].equals(username) && data[1].equals(password)) {
                    return true;  // Username and password match
                }
            }
        } catch (IOException e) {
            System.out.println("Error while verifying credentials: " + e.getMessage());
        }
        return false;  // No match found
    }
}
