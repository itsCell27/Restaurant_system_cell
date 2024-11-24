package ADMIN;

import java.util.Scanner;

public class AdminSystem {
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    private AdminMenu adminMenu;
    private Scanner scanner;

    public AdminSystem() {
        adminMenu = new AdminMenu();
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (choice) {
                case 1:
                    if (login()) {
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
    private boolean login() {
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        if (loginUsername.equals(DEFAULT_ADMIN_USERNAME) && loginPassword.equals(DEFAULT_ADMIN_PASSWORD)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
}
