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
    	 System.out.println("                                                                                                                  REGISTER EMPLOYEE                     ");
         System.out.println("                                                                                         ===================================================================");
         System.out.println("                                                                                         |                        [1] View Employees                       |");
         System.out.println("                                                                                         |                        [2] Register Employee                    |");
         System.out.println("                                                                                         |                        [3] Exit                                 |");
         System.out.println("                                                                                         ===================================================================\n\n");
         MainOrderSystem.clearScreenBottom();
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.println("\n");
         System.out.print("                                                                                                                    Enter: ");
        
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
            	System.out.println("Invalid Choice. Please enter a valid choice.");
            }
    }
}
