package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AdminSystem {
    private static final String ADMIN_RECORD_FILE = "AdminRecord/AdminRecord.csv";
    private static final String ADMIN_KEY_FILE = "AdminRecord/adminkey.csv";

    private String adminUsername;
    private String adminPassword;
    private String securityAnswer;
    private String hardcodedKey;
    private AdminMenu adminMenu = new AdminMenu(); // Initialize here instead of in the constructor

    public AdminSystem() {
        loadAdminCredentials();
        loadHardcodedKey();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in); // Create a new Scanner instance for user input
        int choice;

        while (true) {
        	 System.out.println("                                                                                                                  ADMIN LOGIN                      ");
             System.out.println("                                                                                         ===================================================================");
             System.out.println("                                                                                         |                        [1] Login                                |");
             System.out.println("                                                                                         |                        [2] Forgot Password                      |");
             System.out.println("                                                                                         |                        [3] Exit                                 |");
             System.out.println("                                                                                         ===================================================================\n\n");
             System.out.print("                                                                                                                    Enter: ");
            

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline left by nextInt()
            } else {
                scanner.nextLine(); // Clear invalid input
                System.out.println("                                                                                                                  Invalid choice. Please try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (login(scanner)) { // Pass the scanner to the login method
                        adminMenu.displayMenu(); // Show the admin menu after successful login
                        break;
                    }else{
                    	if(forgotPassword(scanner)) {
                    		break;
                    	}else {
                    		return;
                    	}
                    }
                case 2:
                    if (forgotPassword(scanner)) {
                        
                        break;
                    }else{
                    	return;
                    } 
                case 3:
                    
                    return;
                default:
                    System.out.println("                                                                                                                  Invalid choice. Please try again.");
            }
        }
    }

    private void loadAdminCredentials() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_RECORD_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                adminUsername = parts[0];
                adminPassword = parts[1];
                securityAnswer = parts[2];
            }
        } catch (IOException e) {
            System.err.println("                                                                                                                  Error reading admin credentials: " + e.getMessage());
            System.exit(1);
        }
    }

    private void loadHardcodedKey() {
        try (BufferedReader br = new BufferedReader(new FileReader(ADMIN_KEY_FILE))) {
            hardcodedKey = br.readLine();
        } catch (IOException e) {
            System.err.println("                                                                                                                  Error reading admin key: " + e.getMessage());
            System.exit(1);
        }
    }

    public boolean login(Scanner scanner) {
        int attemptsLeft = 3;
        while (attemptsLeft > 0) {
            System.out.print("                                                                                                                  Enter username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("                                                                                                                  Enter password: ");
            String loginPassword = scanner.nextLine();

            if (loginUsername.equals(adminUsername) && loginPassword.equals(adminPassword)) {
                System.out.println("                                                                                                                  Login successful!");
                return true;
            } else {
                attemptsLeft--;
                System.out.println("                                                                                                                  Invalid username or password. Attempts remaining: " + attemptsLeft);
            }
        }
        System.out.println("                                                                                                                  Too many failed attempts.");
        return false;
    }

    public boolean forgotPassword(Scanner scanner) {
        System.out.print("                                                                                                                  Security Question - What is the CEO's favorite food? ");
        String answer = scanner.nextLine();
        System.out.print("                                                                                                                  Enter the hardcoded key: ");
        String key = scanner.nextLine();

        if (answer.equalsIgnoreCase(securityAnswer) && key.equals(hardcodedKey)) {
            System.out.print("                                                                                                                  Enter new password: ");
            String newPassword = scanner.nextLine();
            adminPassword = newPassword;
            System.out.println("                                                                                                                  Password updated successfully.");
            return true; // Return true if password reset is successful
        } else {
            System.out.println("                                                                                                                  Incorrect security answers.");
            return false; // Return false if password reset fails
        }
    }
}
