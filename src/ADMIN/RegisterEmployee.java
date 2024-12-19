package ADMIN;

import java.util.Scanner;
import java.text.DecimalFormat; // For formatting employee ID
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class RegisterEmployee {

    // Remove the line with SECURITY_KEY being initialized as a FileReader.
    // private static final String SECURITY_KEY = FileReader("AdminRecord/adminkey.csv"); // Incorrect!

    // Method to read the security key from a file
    public String readSecurityKeyFromFile() {
        String key = "";
        try (BufferedReader reader = new BufferedReader(new FileReader("AdminRecord/adminkey.csv"))) {
            key = reader.readLine();  // Read the first line of the file, which should contain the security key
        } catch (IOException e) {
            System.err.println("Error reading security key from file: " + e.getMessage());
        }
        return key;
    }

    public void RegisterEmployee() {
        Scanner in = new Scanner(System.in);
        EmployeeManager employeeManager = new EmployeeManager(50); // Max number of employees
        RegisterEmployeeMenu rgs = new RegisterEmployeeMenu();  // RegisterEmployeeMenu instance

        while (true) {
            try {
                // Step 1: Gather name and password
                String regName = "";
                while (true) {
                    System.out.print("\t\t\tEnter name: ");
                    regName = in.nextLine();

                    // Check if the name contains any digits
                    if (regName.matches(".*\\d.*")) {
                        System.out.println("\t\t\tName cannot contain numbers. Please enter a valid name.");
                    } else {
                        break; // Exit the loop if the name is valid
                    }
                }

                System.out.print("\t\t\tEnter password: ");
                String regPassword = in.nextLine();
                
                // Step 2: Confirm the password
                String regConfirmPass = "";
                while (true) {
                    System.out.print("\t\t\tConfirm password: ");
                    regConfirmPass = in.nextLine();

                    // Check if the password matches the confirmation password
                    if (regPassword.equals(regConfirmPass)) {
                        break; // Exit the loop if passwords match
                    } else {
                        System.out.println("\t\t\tPasswords do not match. Please try again.");
                    }
                }

                // Step 3: Generate unique employee ID (emplo01, emplo02, etc.)
                String employeeId = generateEmployeeId();

                // Step 4: Ask for the employee role
                String position = "";
                while (true) {
                    System.out.println("\t\t\tSelect Position:");
                    System.out.println("\t\t\t1. Manager");
                    System.out.println("\t\t\t2. Counter");
                    System.out.println("\t\t\t3. Kitchen");
                    System.out.println("\t\t\t4. Service");
                    System.out.println("\t\t\t5. Housekeeping");
                    System.out.print("\t\t\tEnter the Position number: ");
                    int positionChoice = in.nextInt();
                    in.nextLine(); // Clear newline character

                    switch (positionChoice) {
                        case 1: position = "Manager"; break;
                        case 2: position = "Counter"; break;
                        case 3: position = "Kitchen"; break;
                        case 4: position = "Service"; break;
                        case 5: position = "Housekeeping"; break;
                        default: 
                            System.out.println("\t\t\tInvalid position selected. Please enter a valid number.");
                            continue; // Ask again if input is invalid
                    }
                    break; // Exit the loop if valid input is provided
                }

                // Step 5: Ask for the security key before submitting the profile
                System.out.print("\t\t\tEnter security key to submit: ");
                String inputKey = in.nextLine();

                // Read the security key from the file
                String actualSecurityKey = readSecurityKeyFromFile();

                // Validate the security key and exit if invalid
                if (!inputKey.equals(actualSecurityKey)) {
                    System.out.println("\t\t\tInvalid security key. Registration is canceled.");
                    return; // Exit the registration process if key is incorrect
                }

                // Step 6: Submit the registration
                System.out.println("\t\t\t1. Submit");
                System.out.println("\t\t\t0. Cancel");
                System.out.print("\t\t\tEnter: ");
                
                int choice = in.nextInt();
                in.nextLine(); // Clear newline character

                switch (choice) {
                    case 1: // Submit
                        if (employeeManager.register(employeeId, regName, regPassword, regConfirmPass, position)) {
                            System.out.println("\t\t\tEmployee registered successfully!");
                            return; // Exit immediately after registration
                        } else {
                            System.out.println("\t\t\tRegistration failed. Please try again.");
                        }
                        break;
                    case 0: // Cancel
                        System.out.println("\t\t\tRegistration canceled.");
                        return; // Exit immediately if registration is canceled
                    default:
                        System.out.println("\t\t\tInvalid choice. Please enter 1 or 0.");
                        break;
                }
            } catch (Exception e) {
                System.err.println("\t\t\tAn error occurred: " + e.getMessage());
                in.nextLine(); // Clear scanner buffer in case of error
            }
        }
    }



    // Method to generate a unique 7-character employee ID (emplo01, emplo02, etc.)
    private String generateEmployeeId() {
        EmployeeManager manager = new EmployeeManager(50);
        int idNumber = manager.getEmployeeCount() + 1; // Get the current employee count and increment
        DecimalFormat format = new DecimalFormat("00000"); // Format the ID to always have 5 digits
        return "E" + format.format(idNumber);
    }
}
