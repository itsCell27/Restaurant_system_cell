package ADMIN;
import java.util.Scanner;

import ORDER_SYSTEM.MainOrderSystem;

public class RegisterEmployeeMenu {
    public void displayRegisterMenu() {
    	Scanner in = new Scanner(System.in);
    	ViewEmployees viewEmployee = new ViewEmployees();
    	RegisterEmployee register = new RegisterEmployee();
    	AdminMenu ads = new AdminMenu();
    	MainOrderSystem.clearScreen();
    	 System.out.println("\t\t\t\t\t\tREGISTER EMPLOYEE");
         System.out.println("\t\t\t===================================================================");
         System.out.println("\t\t\t|                        [1] View Employees                       |");
         System.out.println("\t\t\t|                        [2] Register Employee                    |");
         System.out.println("\t\t\t|                        [3] Exit                                 |");
         System.out.println("\t\t\t===================================================================\n\n");
         clearScreenBottom();
         System.out.print("\t\t\tEnter: ");
        
        int choice = in.nextInt();
        if(choice == 1){
        	viewEmployee.viewEmployees();
        	displayRegisterMenu();
        	
        }else if(choice == 2){
        	register.RegisterEmployee();
        	displayRegisterMenu();
           
        }else if(choice == 3){
        	ads.displayMenu();
            }else {
            	System.out.println("\t\t\tInvalid Choice. Please enter a valid choice.");
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
