package EMPLOYEE;
import ORDER_SYSTEM.MainOrderSystem;
import java.util.InputMismatchException;
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
        	MainOrderSystem.clearScreen();
System.out.println("                                                                                                                     EMPLOYEE MENU");
System.out.println("                                                                                         ===================================================================");
System.out.println("                                                                                         |                        [1] Ticket Order                          |");
System.out.println("                                                                                         |                        [2] View Pending Orders                   |");
System.out.println("                                                                                         |                        [3] Mark Orders as Completed              |");
System.out.println("                                                                                         |                        [4] Cancel Orders                         |");
System.out.println("                                                                                         |                        [5] Logout                                |");
System.out.println("                                                                                         ===================================================================\n\n");
MainOrderSystem.clearScreenBottom();
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");
System.out.print("\n");

try {
    System.out.print("                                                                                                                  Enter: ");
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
            MainOrderSystem.clearScreen();
            return false;  // Return false to indicate logout and go back to role selection
        default:
            System.out.println("Invalid option. Please try again.");
    }
} catch (InputMismatchException e) {
    System.out.println("Invalid input. Please enter a number between 1 and 5.");
    scanner.nextLine(); // Clear the invalid input from the scanner
}
}
        
        return true;  // If the loop ends, the user has logged out
    }
}
