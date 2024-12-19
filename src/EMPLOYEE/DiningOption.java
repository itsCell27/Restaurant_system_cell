package EMPLOYEE;

import java.util.Scanner;
import ORDER_SYSTEM.MenuItem;
import ORDER_SYSTEM.MainOrderSystem;
import ORDER_SYSTEM.Order;
import ORDER_SYSTEM.BreakfastMenu;
import ORDER_SYSTEM.BurgerMenu;
import ORDER_SYSTEM.ChickenAndPlattersMenu;
import ORDER_SYSTEM.DrinksAndDessertMenu;
import ORDER_SYSTEM.FriesMenu;
import ORDER_SYSTEM.CoffeeMenu;
import ORDER_SYSTEM.HandleMyOrder;

public class DiningOption {

    public static boolean showDiningMenu(
            Scanner scanner,
            ChickenAndPlattersMenu chickenMenu,
            BreakfastMenu breakfastMenu,
            DrinksAndDessertMenu drinksAndDessertMenu,
            BurgerMenu burgerMenu,
            CoffeeMenu coffeeMenu,
            FriesMenu friesMenu,
            HandleMyOrder handleOrder
    ) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\t\t\t\t\t\tDINING OPTION");
            System.out.println("\t\t\t===================================================================");
            System.out.println("\t\t\t|                        [1] Dine In                              |");
            System.out.println("\t\t\t|                        [2] Take Out                             |");
            System.out.println("\t\t\t|                        [3] Go back                              |");
            System.out.println("\t\t\t===================================================================\n\n");

            System.out.print("\t\t\tEnter: ");
            int diningChoice = getValidatedInput(scanner, 1, 3); // Only valid choices are 1 and 2.

            switch (diningChoice) {
                case 1:
                    System.out.println("\t\t\tYou selected Dine In.");
                    HandleMyOrder.setDiningOption("Dine In");
                    displayMainMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);
                    return true; // Once dining option is selected, return true to continue
                case 2:
                    System.out.println("\t\t\tYou selected Take Out.");
                    HandleMyOrder.setDiningOption("Take Out");
                    displayMainMenu(scanner, chickenMenu, breakfastMenu, drinksAndDessertMenu, burgerMenu, coffeeMenu, friesMenu, handleOrder);
                    return true; // Once dining option is selected, return true to continue
                case 3:
                    return false; 
                    
                default:
                    System.out.println("\t\t\tInvalid option. Please choose either 1 or 2.");
                    break;
            }
        }

        return false; // Should not reach here unless there's an error
    }

    private static int getValidatedInput(Scanner scanner, int min, int max) {
        int input = -1;
        boolean valid = false;

        while (!valid) {
            try {
                input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.printf("\t\t\tInvalid input. Please enter a number between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("");
            }
        }
        return input;
    }

    private static void displayMainMenu(
            Scanner scanner,
            ChickenAndPlattersMenu chickenMenu,
            BreakfastMenu breakfastMenu,
            DrinksAndDessertMenu drinksAndDessertMenu,
            BurgerMenu burgerMenu,
            CoffeeMenu coffeeMenu,
            FriesMenu friesMenu,
            HandleMyOrder handleOrder
    ) {
        boolean inMainMenu = true;

        while (inMainMenu) {
            System.out.println("\t\t\t\t\t\tWELCOME TO THE RESTAURANT!");
            System.out.println("\t\t\t===================================================================");
            System.out.println("\t\t\t|                        [1] Breakfast Menu                      |");
            System.out.println("\t\t\t|                        [2] Chicken And Platters                |");
            System.out.println("\t\t\t|                        [3] Burger Menu                         |");
            System.out.println("\t\t\t|                        [4] Drinks & Desserts Menu              |");
            System.out.println("\t\t\t|                        [5] Coffee Menu                         |");
            System.out.println("\t\t\t|                        [6] Fries Menu                          |");
            System.out.println("\t\t\t|                        [7] My Order                            |");
            System.out.println("\t\t\t|                        [0] Go Back                             |");
            System.out.println("\t\t\t===================================================================\n\n");

            System.out.print("\t\t\tEnter: ");
            int choice = getValidatedInput(scanner, 0, 7);

            switch (choice) {
                case 0:
                    System.out.println("\t\t\tGoing back to dining options...");
                    inMainMenu = false; // Exits the loop
                    break;
                case 1:
                    breakfastMenu.displayMenu(scanner, handleOrder);
                    break;
                case 2:
                    chickenMenu.displayMenu(scanner, handleOrder);
                    break;
                case 3:
                    burgerMenu.displayMenu(scanner, handleOrder);
                    break;
                case 4:
                    drinksAndDessertMenu.displayMenu(scanner, handleOrder);
                    break;
                case 5:
                    coffeeMenu.displayMenu(scanner, handleOrder);
                    break;
                case 6:
                    friesMenu.displayMenu(scanner, handleOrder);
                    break;
                case 7:
                    handleOrder.showOrderSummary();
                    break;
                default:
                    System.out.println("\t\t\tInvalid choice. Please try again.");
                    break;
            }
        }
    }
}
