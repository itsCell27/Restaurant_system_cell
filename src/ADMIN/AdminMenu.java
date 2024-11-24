package ADMIN;

import java.util.Scanner;

class AdminMenu {
    private Scanner scanner = new Scanner(System.in);  // Initialize the scanner
    RegisterEmployee registerEmployee = new RegisterEmployee();
    AddMenuItem addMenuItem = new AddMenuItem();
    UpdateMenuItem updateMenuItem = new UpdateMenuItem();
    DeleteMenuItem deleteMenuItem = new DeleteMenuItem();

    public void displayMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add menu item");
            System.out.println("2. Update menu item");
            System.out.println("3. Delete menu item");
            System.out.println("4. Register Employee");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            
            // Check if the input is an integer before proceeding
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();  // Clear the newline character

                switch (choice) {
                    case 1:
                        addMenuItem.AddMenu();
                        break;
                    case 2:
                        updateMenuItem.UpdateMenu();
                        break;
                    case 3:
                        deleteMenuItem.DeleteMenu();
                        break;
                    case 4:
                        registerEmployee.RegisterEmployee();
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                // Clear invalid input and prompt again
                scanner.nextLine();
                System.out.println("Invalid choice. Please enter a valid number.");
            }
        }
    }
}
