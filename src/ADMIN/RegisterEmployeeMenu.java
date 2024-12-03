package ADMIN;
import java.util.Scanner;

public class RegisterEmployeeMenu {
    public void displayRegisterMenu() {
    	Scanner in = new Scanner(System.in);
    	ViewEmployees viewEmployee = new ViewEmployees();
    	RegisterEmployee register = new RegisterEmployee();
    	AdminMenu ads = new AdminMenu();
        System.out.println("==== Register Employee ====");
        System.out.println("1. View Employees");
        System.out.println("2. Register Employee");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = in.nextInt();
        if(choice == 1){
        	viewEmployee.ViewEmployees();
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
