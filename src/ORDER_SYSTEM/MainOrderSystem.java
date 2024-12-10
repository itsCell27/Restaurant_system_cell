package ORDER_SYSTEM;

import EMPLOYEE.Login;  // Import the Login class from the EMPLOYEE package
import EMPLOYEE.EmployeeMenu;  // Import the EmployeeMenu class
import ADMIN.AdminSystem;
import ADMIN.AdminAuthForLogout;

import java.util.ArrayList;
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

        // Load menu items from the CSV file
        List<MenuItem> menuItems = chickenMenu.readMenuItems();

        // Create an instance of HandleMyOrder class to manage the orders
        HandleMyOrder handleOrder = new HandleMyOrder();
        List<Order> orders = new ArrayList<>();  // List of orders

        // Dining option
        String diningOption = "";

        // Role selection loop
        while (true) {
            int roleChoice = getRoleChoice();  // Get the role choice from a helper method

            // Handle the role selection
            switch (roleChoice) {
                case 1:
                    
                	clearScreen();
                    AdminSystem adminSystem = new AdminSystem();  // Create an instance of AdminSystem
                    adminSystem.start();  // Start the admin system
                    break;
                case 2:
                   
                    boolean loginSuccess = false;
                    while (!loginSuccess) {
                        Login login = new Login();  // Create an instance of the Login class
                        Scanner scannerForEmployee = new Scanner(System.in);  // Scanner for employee login
                        System.out.print("\t\t\tEnter username: ");
                        String employeeUsername = scannerForEmployee.nextLine();  // Read employee username
                        System.out.print("\t\t\tEnter password: ");
                        String employeePassword = scannerForEmployee.nextLine();  // Read employee password

                        // Verify the credentials by calling the public method handleLogin
                        if (login.handleLogin(employeeUsername, employeePassword)) {
                            loginSuccess = true;  // Set flag to true to exit loop on success
                            
                            // After login success, start Employee Menu
                            EmployeeMenu employeeMenu = new EmployeeMenu();  // Create an instance of EmployeeMenu
                            employeeMenu.start();  // Start the employee menu

                        } else {
                           break;  // Exit the loop and go back to role selection
                        }
                    }
                    break;
                case 3:
                    break;
                case 0:
                	System.out.println("\n\t\t\tExiting the program. Goodbye!");
                	System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("\t\t\tInvalid option, please restart and select a valid role.");
                    continue; // If invalid role, go back to role selection
            }

            // After the user completes their role-specific actions, go back to role selection
            if (roleChoice == 1 || roleChoice == 2) {
        
                continue;  // Go back to the role selection loop
            }

            // Dining option loop
            diningOption = "";
            while (true) {
                // Display Dine In, Take Out, and Logout options
                if (diningOption.isEmpty()) {
                	clearScreen();
                	System.out.println("\t\t\t\t\t\tDINING OPTION");
                    System.out.println("\t\t\t===================================================================");
                    System.out.println("\t\t\t|                        [1] Dine In                              |");
                    System.out.println("\t\t\t|                        [2] Take Out                             |");
                    System.out.println("\t\t\t|                        [3] Logout                               |");
                    System.out.println("\t\t\t===================================================================\n\n");
                    clearScreenBottom();
                    System.out.print("\t\t\tEnter: ");
                    
                    int diningChoice = -1;
                    boolean validInput = false;

                    while (!validInput) {
                        try {
                            String input = scanner.nextLine();
                            diningChoice = Integer.parseInt(input.trim());
                            if (diningChoice < 1 || diningChoice > 3) {
                                System.out.println("\t\t\tInvalid input. Please enter a number between 1 and 3.");
                            } else {
                                validInput = true;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\t\t\tInvalid input. Please enter a valid number:");
                        }
                    }

                    
                    // Handle the dining choice and set the global dining option
                    switch (diningChoice) {
                        case 1:
                            System.out.println("\t\t\tYou selected Dine In.");
                            HandleMyOrder.setDiningOption("Dine In"); // Set the dining option
                            diningOption = "Dine In"; // Store the option
                            break;  // Dine In selected
                        case 2:
                            System.out.println("\t\t\tYou selected Take Out.");
                            HandleMyOrder.setDiningOption("Take Out"); // Set the dining option
                            diningOption = "Take Out"; // Store the option
                            break;  // Take Out selected
                        case 3:
                            System.out.println("\t\t\tLogging out...");
                            AdminAuthForLogout adminAuth = new AdminAuthForLogout();
                            if (adminAuth.login(scanner)) { // Call the login method for authentication
                                System.out.println("\t\t\tLogout successful.");
                                diningOption = ""; // Clear dining option after successful logout
                            } else {
                                System.out.println("\t\t\tAuthentication failed. Returning to menu...");
                                continue;
                            }
                             
                            break;
                        default:
                            System.out.println("\t\t\tInvalid option, please select a valid option.");
                            continue;  // Restart loop if invalid option
                    }
                }

                if (diningOption.isEmpty()) {
                    break; // Exit the dining option loop if logged out
                }

                // Display the main menu after dining option is selected
                displayMainMenu();

                // Get user's choice for the main menu
                int choice = -1;
                boolean validInput = false;

                while (!validInput) {
                    try {
                        String input = scanner.nextLine();
                        choice = Integer.parseInt(input.trim());
                        if (choice < 0 || choice > 7) {
                        	clearScreen();
                            System.out.println("\t\t\tInvalid input. Please enter a number between 0 and 7.");
                        } else {
                            validInput = true;
                        }
                    } catch (NumberFormatException e) {
                    	
                        
                    }
                }



                // Handle the user's choice
                switch (choice) {
                    case 0:
                        System.out.println("\t\t\tGoing back to dining options...");
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
                        System.out.println("\t\t\tOption not implemented yet! Please select a valid option.");
                }
            }

            // After logging out from dining options, return to the role selection menu
            System.out.println("\t\t\tReturning to role selection...");
        }
    }

    // Helper method to get role choice (No Scanner parameter)
    private static int getRoleChoice() {
        Scanner scanner = new Scanner(System.in);
        int roleChoice = -1; // Default invalid value
        boolean validInput = false;

        while (!validInput) {
            System.out.println("\n\n\n");
            System.out.println("\t\t\t\t\t\tUSER TYPE SELECTION");
            System.out.println("\t\t\t===================================================================");
            System.out.println("\t\t\t|                        [1] Admin                                |");
            System.out.println("\t\t\t|                        [2] Employee                             |");
            System.out.println("\t\t\t|                        [3] Customer                             |");
            System.out.println("\t\t\t|                        [0] Exit                                 |");
            System.out.println("\t\t\t===================================================================\n\n");
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            clearScreenBottom();
            System.out.print("\t\t\tEnter: ");

            try {
                String input = scanner.nextLine();
                roleChoice = Integer.parseInt(input.trim());
                if (roleChoice < 0 || roleChoice > 3) {
                	clearScreen();
                    System.out.println("\t\t\tInvalid input. Please enter a number between 0 and 3.");
                } else {
                    validInput = true;
                }
            } catch (NumberFormatException e) {
            	clearScreen();
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
            }
        }
        return roleChoice;
    }


    // Method to display the main menu
    private static void displayMainMenu() {
    	clearScreen();
        System.out.println("\t\t\t\t\t\tWELCOME TO THE RESTAURANT!");
        System.out.println("\t\t\t====================================================================");
        System.out.println("\t\t\t|                        [1] Breakfast Menu                        |");
        System.out.println("\t\t\t|                        [2] Chicken And Platters                  |");
        System.out.println("\t\t\t|                        [3] Burger Menu                           |");
        System.out.println("\t\t\t|                        [4] Drinks & Desserts Menu                |");
        System.out.println("\t\t\t|                        [5] Coffee Menu                           |");
        System.out.println("\t\t\t|                        [6] Fries Menu                            |");
        System.out.println("\t\t\t|                        [7] My Order                              |");
        System.out.println("\t\t\t|                        [0] Go Back                               |");
        System.out.println("\t\t\t====================================================================\n\n");
        clearScreenBottom();
        
        System.out.print("\t\t\tEnter: ");
    }
    
    
        public static void clearScreen() {
            for (int i = 0; i < 50; i++) {  // Print 50 newlines
                System.out.println();
            }   
        }
        
        public static void clearScreenBottom() {
            for (int i = 0; i < 30; i++) {  // Print 50 newlines
                System.out.println();
            }   
        }
}