package ADMIN;

import java.util.Scanner;

public class AdminSystem {
<<<<<<< HEAD
<<<<<<< HEAD
    public static void main(String[] args) {
        int maxAdmins = 10;
        AdminManager adminManager = new AdminManager(maxAdmins);
        AdminMenu adminMenu = new AdminMenu(adminManager);
        Scanner scanner = new Scanner(System.in);
=======
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    private AdminMenu adminMenu;
    private Scanner scanner;

    public AdminSystem() {
        adminMenu = new AdminMenu();
        scanner = new Scanner(System.in);
    }

    public void start() {
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
=======
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    private AdminMenu adminMenu;
    private Scanner scanner;

    public AdminSystem() {
        adminMenu = new AdminMenu();
        scanner = new Scanner(System.in);
    }

    public void start() {
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
        int choice;

        while (true) {
<<<<<<< HEAD
<<<<<<< HEAD
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            // Handle invalid input for menu selection
            choice = getValidIntegerInput(scanner);
=======
=======
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
<<<<<<< HEAD
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
=======
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git

            switch (choice) {
                case 1:
<<<<<<< HEAD
<<<<<<< HEAD
                    // Register user
                    System.out.print("Enter username: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.nextLine();
                    if (adminManager.register(regUsername, regPassword)) {
                        System.out.println("Registration successful!");
                    } else {
                        System.out.println("Username already exists or registration failed.");
=======
                    if (login()) {
                        adminMenu.displayMenu(); // Show the admin menu after successful login
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
=======
                    if (login()) {
                        adminMenu.displayMenu(); // Show the admin menu after successful login
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
                    }
                    break;
                case 2:
<<<<<<< HEAD
<<<<<<< HEAD
                    // Login user
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    if (adminManager.login(loginUsername, loginPassword)) {
                        System.out.println("Login successful!");
                        adminMenu.displayMenu();
                    } else {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3:
                    // Exit
                    System.out.println("Exiting...\nProgram Closed.");
                    scanner.close();
=======
                    System.out.println("Exiting...");
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
=======
                    System.out.println("Exiting...");
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Method to ensure valid integer input
    private static int getValidIntegerInput(Scanner scanner) {
        int input = -1; // Default invalid value
        boolean valid = false;
        
        while (!valid) {
            try {
                System.out.print("Choose an option: ");
                input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                valid = true; // Input is valid
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return input;
=======
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
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
=======
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
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
    }
}
