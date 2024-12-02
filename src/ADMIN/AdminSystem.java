package ADMIN;

import java.util.Scanner;

public class AdminSystem {
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    private AdminMenu adminMenu = new AdminMenu();  // Initialize here instead of in the constructor

    public void start() {
        Scanner scanner = new Scanner(System.in);  // Create a new Scanner instance for user input
        int choice;

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            
            // Check if the input is an integer before proceeding
            if(scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline left by nextInt()
            } else {
                // Handle invalid input by clearing the buffer and asking again
                scanner.nextLine();  // Clear invalid input
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (login(scanner)) {  // Pass the scanner to the login method
                        adminMenu.displayMenu(); // Show the admin menu after successful login
                    }
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handles admin login logic
    private boolean login(Scanner scanner) {
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();  // Read username
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();  // Read password

        if (loginUsername.equals(DEFAULT_ADMIN_USERNAME) && loginPassword.equals(DEFAULT_ADMIN_PASSWORD)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
}
