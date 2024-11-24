package ADMIN;

import java.util.Scanner;

public class AdminAuthForLogout {
    private static final Scanner scanner = new Scanner(System.in);
    private AdminVar adminVar;

    public AdminAuthForLogout(AdminVar adminVar) {
        this.adminVar = adminVar;
    }

    // Method to authenticate admin credentials for logout
    public boolean authenticateForLogout() {
        String enteredUsername, enteredPassword;

        // Prompt user for username and password
        System.out.print("Enter username for logout: ");
        enteredUsername = scanner.nextLine();
        System.out.print("Enter password for logout: ");
        enteredPassword = scanner.nextLine();

        // Check if the credentials match
        if (adminVar.getUsername().equals(enteredUsername) && adminVar.getPassword().equals(enteredPassword)) {
            System.out.println("Logout successful!");
            return true;  // Return true if authentication is successful
        } else {
            System.out.println("Authentication failed. You cannot logout.");
            return false;  // Return false if authentication fails
        }
    }
}
