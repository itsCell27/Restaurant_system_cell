package ORDER_SYSTEM;

import EMPLOYEE.Login;  // Import the Login class from the EMPLOYEE package
import EMPLOYEE.EmployeeMenu;  // Import the EmployeeMenu class
import ADMIN.AdminSystem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MainOrderSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create the scanner once here

        // Create instances of the menu classes
        ChickenAndPlattersMenu chickenMenu = new ChickenAndPlattersMenu();
        BreakfastMenu breakfastMenu = new BreakfastMenu();
        DrinksAndDessertMenu drinksAndDessertMenu = new DrinksAndDessertMenu(); // Drinks & Desserts menu
        BurgerMenu burgerMenu = new BurgerMenu(); // Burger menu
        CoffeeMenu coffeeMenu = new CoffeeMenu(); // Coffee menu
        FriesMenu friesMenu = new FriesMenu(); // Fries menu
        
        // Exception checker
    	boolean caught_an_exception;

        // Load menu items from the CSV file
        List<MenuItem> menuItems = chickenMenu.readMenuItems();

        // Create an instance of HandleMyOrder class to manage the orders
        HandleMyOrder handleOrder = new HandleMyOrder();
        List<Order> orders = new ArrayList<>();  // List of orders

        // Dining option
        String diningOption = "";

        // Role selection loop
		// added comment
        while (true) {
        	
        	do {
        		
        		caught_an_exception = false;
        		
	        	try {
		        	getRoleChoice();
		        	
		            int roleChoice = scanner.nextInt();
		            
		            // Handle the role selection
		            switch (roleChoice) {
		                case 1:
		                    System.out.println("You selected Admin.");
		                    AdminSystem adminSystem = new AdminSystem();  // Create an instance of AdminSystem
		                    adminSystem.start();  // Start the admin system
		                    break;
		                case 2:
		                    System.out.println("You selected Employee.");
		                    // Loop to allow retry if login fails
		                    boolean loginSuccess = false;
		                    while (!loginSuccess) {
		                        Login login = new Login();  // Create an instance of the Login class
		                        Scanner scannerForEmployee = new Scanner(System.in);  // Scanner for employee login
		                        System.out.print("Enter username: ");
		                        String employeeUsername = scannerForEmployee.nextLine();  // Read employee username
		                        System.out.print("Enter password: ");
		                        String employeePassword = scannerForEmployee.nextLine();  // Read employee password
		
		                        // Verify the credentials by calling the public method handleLogin
		                        if (login.handleLogin(employeeUsername, employeePassword)) {
		                            System.out.println("Employee login successful!");
		                            loginSuccess = true;  // Set flag to true to exit loop on success
		                            
		                            // After login success, start Employee Menu
		                            EmployeeMenu employeeMenu = new EmployeeMenu();  // Create an instance of EmployeeMenu
		                            employeeMenu.start();  // Start the employee menu
		
		                        } else {
		                            System.out.println("Invalid username or password for Employee.");
		                            System.out.println("Returning to role selection...");
		                            break;  // Exit the loop and go back to role selection
		                        }
		                    }
		                    break;
		                case 3:
		                    System.out.println("You selected Customer.");
		                    break;
		                default:
		                    System.out.println("Invalid option, please restart and select a valid role.");
		                    continue; // If invalid role, go back to role selection
		            }
		            
		            // After the user completes their role-specific actions, go back to role selection
		            if (roleChoice == 1 || roleChoice == 2) {
		                System.out.println("Returning to role selection...");
		                continue;  // Go back to the role selection loop
		            }
		            
	        	} catch (InputMismatchException e) {
	                System.out.println("Invalid input. Please enter an integer.");
	                caught_an_exception = true;
	                scanner.next(); // Clear the invalid input
	            }
        	} while (caught_an_exception);


            // Dining option loop
            diningOption = "";
            while (true) {
            	
            	do {
            		
            		// for checking if an exception is caught
                	caught_an_exception = false;
                	
	            	try {
		                // Display Dine In, Take Out, and Logout options
		                if (diningOption.isEmpty()) {
		                    System.out.println("\nSelect an option:");
		                    System.out.println("1. Dine In");
		                    System.out.println("2. Take Out");
		                    System.out.println("3. Logout");
		                    System.out.print("Please select an option: ");
		                    
		                    int diningChoice = scanner.nextInt();
		                    
		                    // Handle the dining choice and set the global dining option
		                    switch (diningChoice) {
		                        case 1:
		                            System.out.println("You selected Dine In.");
		                            HandleMyOrder.setDiningOption("Dine In"); // Set the dining option
		                            diningOption = "Dine In"; // Store the option
		                            break;  // Dine In selected
		                        case 2:
		                            System.out.println("You selected Take Out.");
		                            HandleMyOrder.setDiningOption("Take Out"); // Set the dining option
		                            diningOption = "Take Out"; // Store the option
		                            break;  // Take Out selected
		                        case 3:
		                            System.out.println("Logging out...");
		                            diningOption = "";  // Clear dining option after logout
		                            break; // Log out and return to role selection
		                        default:
		                            System.out.println("Invalid option, please select a valid option.");
		                            continue;  // Restart loop if invalid option
		                    }
		                }
	            	} catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Please enter an integer.");
	                    caught_an_exception = true;
	                    scanner.next(); // Clear the invalid input
	                }
            	} while(caught_an_exception);
	            

                if (diningOption.isEmpty()) {
                    break; // Exit the dining option loop if logged out
                }
                
                do {
                	// for checking if an exception is caught
                	caught_an_exception = false;
                	
	                try {
	                	
		                // Display the main menu after dining option is selected
		                displayMainMenu();
		
		                // Get user's choice for the main menu
		                int choice = scanner.nextInt();
		
		                // Handle the user's choice
		                switch (choice) {
		                    case 0:
		                        System.out.println("Going back to dining options...");
		                        diningOption = ""; // Reset dining option so it can ask for it again
		                        break; // Go back to dining options
		                    case 1:
		                        // Show the Breakfast menu and process the order
		                        breakfastMenu.displayMenu(scanner, handleOrder);
		                        break;  // Keep the program running after processing Breakfast Menu
		                    case 2:
		                        // Show the Chicken and Platters menu and process the order
		                        chickenMenu.displayMenu( scanner, handleOrder);
		                        break;  // Keep the program running after processing Chicken And Platters Menu
		                    case 3:
		                        // Show the Burger menu and process the order
		                        burgerMenu.displayMenu( scanner, handleOrder);
		                        break;  // Keep the program running after processing Burger Menu
		                    case 4:
		                        // Show the Drinks & Desserts menu and process the order
		                        drinksAndDessertMenu.displayMenu( scanner, handleOrder);
		                        break;  // Keep the program running after processing Drinks & Desserts Menu
		                    case 5:
		                        // Show the Coffee menu and process the order
		                        coffeeMenu.displayMenu( scanner, handleOrder);
		                        break;  // Keep the program running after processing Coffee Menu
		                    case 6:
		                        // Show the Fries menu and process the order
		                        friesMenu.displayMenu( scanner, handleOrder);
		                        break;  // Keep the program running after processing Fries Menu
		                    case 7:
		                        // View the current temporary orders
		                        handleOrder.showOrderSummary(); // Show temporary orders in the HandleMyOrder class
		                        break;  // Keep the program running after viewing the orders
		                    default:
		                        System.out.println("Option not implemented yet! Please select a valid option.");
		                }
	                } catch (InputMismatchException e) {
	                    System.out.println("Invalid input. Please enter an integer.");
	                    caught_an_exception = true;
	                    scanner.next(); // Clear the invalid input
	                }
                } while (caught_an_exception);
                
            }

            // After logging out from dining options, return to the role selection menu
            System.out.println("Returning to role selection...");
        }
    }

    // Helper method to get role choice (No Scanner parameter)
    private static void getRoleChoice() {
        
        System.out.println("Select your role:");
        System.out.println("1. Admin");
        System.out.println("2. Employee");
        System.out.println("3. Customer");
        System.out.print("Please select an option: ");
    }

    // Method to display the main menu
    private static void displayMainMenu() {
    	
        System.out.println("\nWelcome to the Restaurant!");
        System.out.println("1. Breakfast Menu");
        System.out.println("2. Chicken And Platters");
        System.out.println("3. Burger Menu");
        System.out.println("4. Drinks & Desserts Menu");
        System.out.println("5. Coffee Menu");
        System.out.println("6. Fries Menu");
        System.out.println("7. My Order");
        System.out.println("0. Go Back");
        System.out.print("Please select an option: ");
    }
}
