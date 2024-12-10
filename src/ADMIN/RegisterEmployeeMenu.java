package ADMIN;
import java.util.Scanner;

import ORDER_SYSTEM.MainOrderSystem;

public class RegisterEmployeeMenu {
    public void displayRegisterMenu() {
        Scanner in = new Scanner(System.in);
        ViewEmployees viewEmployee = new ViewEmployees();
        RegisterEmployee register = new RegisterEmployee();
        AdminMenu ads = new AdminMenu();

        while (true) {
            try {
                MainOrderSystem.clearScreen();
                System.out.println("\t\t\t\t\t\tREGISTER EMPLOYEE");
                System.out.println("\t\t\t===================================================================");
                System.out.println("\t\t\t|                        [1] View Employees                       |");
                System.out.println("\t\t\t|                        [2] Register Employee                    |");
                System.out.println("\t\t\t|                        [3] Exit                                 |");
                System.out.println("\t\t\t===================================================================\n\n");
                clearScreenBottom();
                System.out.print("\t\t\tEnter: ");

                // Check if input is an integer
                if (in.hasNextInt()) {
                    int choice = in.nextInt();
                    in.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            viewEmployee.viewEmployees();
                            break;
                        case 2:
                            register.RegisterEmployee();
                            break;
                        case 3:
                            ads.displayMenu();
                            return; // Exit the loop and return
                        default:
                            System.out.println("\t\t\tInvalid Choice. Please enter a number between 1 and 3.");
                    }
                } else {
                    // Handle non-integer input
                    in.nextLine(); // Consume invalid input
                    System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                }
            } catch (Exception e) {
                System.err.println("\t\t\tAn error occurred: " + e.getMessage());
                in.nextLine(); // Clear the scanner buffer in case of an error
            }
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {  // Print 50 newlines
            System.out.println();
        }
    }

    public static void clearScreenBottom() {
        for (int i = 0; i < 40; i++) {  // Print 40 newlines
            System.out.println();
        }
    }
}
