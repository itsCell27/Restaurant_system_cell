package ADMIN;

import java.util.Scanner;

public class RegisterEmployee {
	public void RegisterEmployee(){
        Scanner in = new Scanner(System.in);
        EmployeeManager employeeManager = new EmployeeManager(10);
        AdminMenu adminMenu = new AdminMenu();
        RegisterEmployeeMenu rgs = new RegisterEmployeeMenu();

     System.out.print("Enter username: ");
        String regUsername = in.nextLine();
        System.out.print("Enter password: ");
        String regPassword = in.nextLine();
        System.out.print("Confirm password: ");
        String regConfirmPass = in.nextLine();
       
        System.out.println("1.Submit");
        System.out.println("0.Cancel");
        int choice = in.nextInt();
        if(choice == 1){
    
            if (employeeManager.register(regUsername, regPassword,regConfirmPass)) {
                System.out.println("Registration successful!");
                rgs.displayRegisterMenu();
                } else {
                System.out.println("Registration unsuccessful.");
            }
        }else if(choice == 0){
        	rgs.displayRegisterMenu();
            return;
        }else{
            System.out.println("Invalid Choice. Please enter a valid choice.");
            }
    }
}
