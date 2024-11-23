package ADMIN;

import MENU_DATA_HANDLING.MenuData;
import java.util.Scanner;

public class AddMenuItem {
    Scanner scanner = new Scanner(System.in);

    public void addMenu() {
        int choice = -1; // Initial invalid value to enter the loop

        while (choice != 0) {
            // Display the category options to the user
            System.out.println("Select Category to Add Item:");
            System.out.println("1. Chicken and Platters");
            System.out.println("2. Breakfast");
            System.out.println("3. Burger");
            System.out.println("4. Drinks and Desserts");
            System.out.println("5. Coffee");
            System.out.println("6. Fries");
            System.out.println("0 to Return.");
            System.out.print("Enter choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Exit the loop if the user chooses 0 (Return)
            if (choice == 0) {
                return;
            }

            // Check if the choice is valid
            if (choice < 1 || choice > 6) {
                System.out.println("Invalid choice! Please try again.");
                continue; // Continue the loop and prompt again
            }

            // Prompt for item details (name and price)
            System.out.print("Enter the Item Name: ");
            String itemName = scanner.nextLine();

            System.out.print("Enter the Item Price: ");
            int itemPrice = scanner.nextInt();

            // Add the item to the appropriate menu based on the user's choice
            switch (choice) {
                case 1 -> {
                    MenuData.chickenAndPlattersItemNames.add(itemName);
                    MenuData.chickenAndPlattersItemPrices.add(itemPrice);
                }
                case 2 -> {
                    MenuData.breakfastItemNames.add(itemName);
                    MenuData.breakfastItemPrices.add(itemPrice);
                }
                case 3 -> {
                    MenuData.burgerItemNames.add(itemName);
                    MenuData.burgerItemPrices.add(itemPrice);
                }
                case 4 -> {
                    MenuData.drinksAndDessertsItemNames.add(itemName);
                    MenuData.drinksAndDessertsItemPrices.add(itemPrice);
                }
                case 5 -> {
                    MenuData.coffeeItemNames.add(itemName);
                    MenuData.coffeeItemPrices.add(itemPrice);
                }
                case 6 -> {
                    MenuData.friesItemNames.add(itemName);
                    MenuData.friesItemPrices.add(itemPrice);
                }
            }

            // Confirm successful addition of item
            System.out.println("Item added successfully!");
            waitForEnter();
        }
    }
    public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }
}
