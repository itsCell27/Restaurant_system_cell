package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AdminAuthForLogout {
    private static final String ADMIN_RECORD_FILE = "AdminRecord/AdminRecord.csv";

    private String adminUsername;
    private String adminPassword;
    private String securityAnswer = "pizza"; // Example security answer (e.g., CEO's favorite food)
    private String hardcodedKey = "admin123"; // Example hardcoded key

    public AdminAuthForLogout() {
        loadAdminCredentials();
    }

    // Load admin credentials from the file
    private void loadAdminCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_RECORD_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                adminUsername = parts[0];
                adminPassword = parts[1];
            }
        } catch (IOException e) {
            System.err.println("Error reading admin credentials: " + e.getMessage());
            System.exit(1);
        }
    }

    // Login method for authentication
    public boolean login(Scanner scanner) {
        int attemptsLeft = 3; // Maximum attempts allowed

        System.out.println("\n--- Admin Login ---");
        while (attemptsLeft > 0) {
            System.out.print("Enter username: ");
            String inputUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String inputPassword = scanner.nextLine();

            if (inputUsername.equals(adminUsername) && inputPassword.equals(adminPassword)) {
                System.out.println("Login successful!");
                return true; // Login successful
            } else {
                attemptsLeft--;
                System.out.println("Invalid credentials. Attempts remaining: " + attemptsLeft);
            }
        }
        System.out.println("Too many failed login attempts.");

        // If login fails after 3 attempts, ask security question
        return forgotPassword(scanner);
    }

    // Forgot password method (with security question and hardcoded key)
    public boolean forgotPassword(Scanner scanner) {
        System.out.print("Security Question - What is the CEO's favorite food? ");
        String answer = scanner.nextLine();
        System.out.print("Enter the hardcoded key: ");
        String key = scanner.nextLine();

        if (answer.equalsIgnoreCase(securityAnswer) && key.equals(hardcodedKey)) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            adminPassword = newPassword;
            System.out.println("Password updated successfully.");
            return true; // Return true if password reset is successful
        } else {
            System.out.println("Incorrect security answers or key.");
            return false; // Return false if password reset fails
        }
    }
}
