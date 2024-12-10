package ADMIN;

import java.util.Scanner;

import ORDER_SYSTEM.MainOrderSystem;

public class RegisterEmployee {
    public void RegisterEmployee() {
        Scanner in = new Scanner(System.in);
        EmployeeManager employeeManager = new EmployeeManager(50); // Max number of employees
        RegisterEmployeeMenu rgs = new RegisterEmployeeMenu();

        while (true) {
            try {
                System.out.print("\t\t\tEnter username: ");
                String regUsername = in.nextLine();

                System.out.print("\t\t\tEnter password: ");
                String regPassword = in.nextLine();

                System.out.print("\t\t\tConfirm password: ");
                String regConfirmPass = in.nextLine();

                System.out.println("\t\t\t1. Submit");
                System.out.println("\t\t\t0. Cancel");
                System.out.print("\t\t\tEnter: ");

                if (in.hasNextInt()) {
                    int choice = in.nextInt();
                    in.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1: // Submit
                            if (employeeManager.register(regUsername, regPassword, regConfirmPass)) {
                                System.out.println("\t\t\tEmployee registered successfully!");
                                rgs.displayRegisterMenu();
                                return; // Exit the method after successful registration
                            } else {
                                System.out.println("\t\t\tRegistration failed. Please try again.");
                            }
                            break;
                        case 0: // Cancel
                            System.out.println("\t\t\tRegistration canceled.");
                            rgs.displayRegisterMenu();
                            return; // Exit the method
                        default:
                            System.out.println("\t\t\tInvalid choice. Please enter 1 or 0.");
                            break;
                    }
                } else {
                    // Handle non-integer input
                    in.nextLine(); // Clear invalid input
                    System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                }
            } catch (Exception e) {
                System.err.println("\t\t\tAn error occurred: " + e.getMessage());
                in.nextLine(); // Clear scanner buffer in case of error
            }
        }
    }
}
