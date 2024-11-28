package EMPLOYEE;

import java.util.Scanner;

public class EmployeeMenu {
    private Scanner scanner;

    public EmployeeMenu() {
        scanner = new Scanner(System.in);
    }

    // Start method to display the employee menu and handle actions
    public boolean start() {
        boolean running = true;

        while (running) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. Ticket Order");
            System.out.println("2. View Pending Orders");
            System.out.println("3. Mark Orders as Completed");
            System.out.println("4. Cancel Orders");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    TicketOrder.handleOrder(); // This should be defined in another class
                    break;
                case 2:
                    ViewPendingOrders.displayPendingOrders(); // This should be defined in another class
                    break;
                case 3:
                    MarkOrdersCompleted.completeOrder(); // This should be defined in another class
                    break;
                case 4:
                    CancelOrders.cancelOrder(); // This should be defined in another class
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return false;  // Return false to indicate logout and go back to role selection
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        return true;  // If the loop ends, the user has logged out
    }
}
