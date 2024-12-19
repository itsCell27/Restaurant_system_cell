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
			System.out.println("\t\t\t\t\t\tEMPLOYEE MENU");
			System.out.println("\t\t\t====================================================================");
			System.out.println("\t\t\t|                        [1] Manage Orders                          |");
			System.out.println("\t\t\t|                        [2] Status of orders                       |");
			System.out.println("\t\t\t|                        [3] Manual order                           |");
			System.out.println("\t\t\t|                        [4] Order logs                             |");
			System.out.println("\t\t\t|                        [5] Exit to Employee                       |");
			System.out.println("\t\t\t====================================================================\n\n");
			clearScreenBottom();

			try {
			    System.out.print("\t\t\tEnter: ");
			    int choice = scanner.nextInt();
			
			    switch (choice) {
			        case 1:
			            TicketOrder.handleOrder(); // This should be defined in another class
			            break;
			        case 2:
			            ViewPendingOrders.displayOrdersByStatus(); // This should be defined in another class
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
			            System.out.println("\t\t\tInvalid option. Please try again.");
			    }
			} catch (InputMismatchException e) {
			    System.out.println("\t\t\tInvalid input. Please enter a number between 1 and 5.");
			    scanner.nextLine(); // Clear the invalid input from the scanner
			    
			    System.out.print("\n\t\t\tPress ENTER to continue...");
			    scanner.nextLine();
			}
        }
        
        return true;  // If the loop ends, the user has logged out
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
