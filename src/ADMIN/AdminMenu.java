package ADMIN;

import java.util.Scanner;
class AdminMenu {
    private Scanner scanner;
    RegisterEmployee registerEmployee = new RegisterEmployee();
    AddMenuItem addmenuitem = new AddMenuItem();
    UpdateMenuItem updatemenuitem = new UpdateMenuItem();
    DeleteMenuItem deletemenuitem = new DeleteMenuItem();

    public void displayMenu() {
    	
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add menu item");
            System.out.println("2. Update menu item");
            System.out.println("3. Delete menu item");
            System.out.println("4. Register Empployee");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                	addmenuitem.AddMenu();
                    break;
                case 2:
                	updatemenuitem.UpdateMenu();
                    break;
                case 3:
                	deletemenuitem.DeleteMenu();
                    break;
                case 4:
                	registerEmployee.RegisterEmployee();
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
