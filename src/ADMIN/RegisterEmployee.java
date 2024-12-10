package ADMIN;

import java.util.Scanner;

import ORDER_SYSTEM.MainOrderSystem;

public class RegisterEmployee {
	public void RegisterEmployee(){
        Scanner in = new Scanner(System.in);
        EmployeeManager employeeManager = new EmployeeManager(50); // number of employees
        AdminMenu adminMenu = new AdminMenu();
        RegisterEmployeeMenu rgs = new RegisterEmployeeMenu();
        
        System.out.print("\t\t\tEnter username: ");
        String regUsername = in.nextLine();
        System.out.print("\t\t\tEnter password: ");
        String regPassword = in.nextLine();
        System.out.print("\t\t\tConfirm password: ");
        String regConfirmPass = in.nextLine();
       
        System.out.println("\t\t\t1.Submit");
        System.out.println("\t\t\t0.Cancel");
        System.out.print("\t\t\tEnter: ");
        int choice = in.nextInt();
        if(choice == 1){
    
            if (employeeManager.register(regUsername, regPassword,regConfirmPass)) {
               
                rgs.displayRegisterMenu();
                } else {
               
            }
        }else if(choice == 0){
        	rgs.displayRegisterMenu();
            return;
        }else{
            System.out.println("\t\t\tInvalid Choice. Please enter a valid choice.");
            }
    }
}
