package ADMIN;

import java.util.Scanner;
import ORDER_SYSTEM.MainOrderSystem;

class AdminMenu {
    private Scanner scanner = new Scanner(System.in);  // Initialize the scanner
    RegisterEmployeeMenu registerEmployee = new RegisterEmployeeMenu();
    AddMenuItem addMenuItem = new AddMenuItem();
    UpdateMenuItem updateMenuItem = new UpdateMenuItem();
    DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

    public void displayMenu() {
        try {
        	MainOrderSystem.clearScreen();
            System.out.println("\t\t\t\t\t\tADMIN MENU");
            System.out.println("\t\t\t===================================================================");
            System.out.println("\t\t\t|                        [1] Add menu item                        |");
            System.out.println("\t\t\t|                        [2] Update menu item                     |");
            System.out.println("\t\t\t|                        [3] Delete menu item                     |");
            System.out.println("\t\t\t|                        [4] Register Employee                    |");
            System.out.println("\t\t\t|                        [5] Logout                               |");
            System.out.println("\t\t\t===================================================================\n\n");
            clearScreenBottom();
            System.out.print("\t\t\tEnter: ");

            // Check if the input is an integer before proceeding
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Clear the newline character

                switch (choice) {
                    case 1:
                        addMenuItem.displayCategoryMenu();
                        break;
                    case 2:
                        updateMenuItem.displayCategoryMenu();
                        break;
                    case 3:
                        deleteMenuItem.displayCategoryMenu();
                        break;
                    case 4:
                        registerEmployee.displayRegisterMenu();
                        break;
                    case 5:
                        System.out.println("\t\t\tLogging out...");
                        MainOrderSystem.main(null);
                        break;
                    default:
                        System.out.println("\t\t\tInvalid choice. Please try again.");
                }
            } else {
                // Clear invalid input and prompt again
                scanner.nextLine();  // Consume the invalid input
                System.out.println("\t\t\tInvalid choice. Please enter a valid number.");
            }
        } catch (Exception e) {
            System.err.println("\t\t\tAn error occurred: " + e.getMessage());
        }
    }
    
    
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
    
    public static void clearScreenBottom() {
        for (int i = 0; i < 40; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }
}
