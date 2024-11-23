package ADMIN;

import MENU_DATA_HANDLING.MenuData;
import java.util.Scanner;

public class UpdateMenuItem {
    Scanner scanner = new Scanner(System.in);

    public void UpdateMenu() {
        while (true) {
            // Ask the user to select a category
            System.out.println("Select Category to Update Item:");
            System.out.println("1. Chicken and Platters");
            System.out.println("2. Breakfast");
            System.out.println("3. Burger");
            System.out.println("4. Drinks and Desserts");
            System.out.println("5. Coffee");
            System.out.println("6. Fries");
            System.out.println("0 to Return.");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 0) {
                return; // Exit the method if the user chooses 0
            }

            if (choice < 0 || choice > 6) {
                System.out.println("Invalid category choice! Please try again.");
                continue;
            }

            // Display the items in the selected category
            displayItemsByCategory(choice);

            while (true) {
                // Ask for the index of the item to update
                System.out.print("Enter the index (number) of the item you want to update (or 0 to cancel): ");
                int index = scanner.nextInt() - 1; // Convert to zero-based index
                scanner.nextLine(); // Consume newline

                if (index == -1) {
                    System.out.println("Operation cancelled.");
                    return; // Cancel the operation if the user enters 0
                }

                if (index < 0 || index >= getCategorySize(choice)) {
                    System.out.println("Invalid index! Please try again.");
                    continue;
                }

                // Ask for new name and/or price
                System.out.print("Enter the new name for the item (or press Enter to keep the current name): ");
                String newName = scanner.nextLine();
                if (!newName.isEmpty()) {
                    updateItemName(choice, index, newName);
                }

                System.out.print("Enter the new price for the item (or -1 to keep the current price): ");
                int newPrice = scanner.nextInt();
                if (newPrice != -1) {
                    updateItemPrice(choice, index, newPrice);
                }

                System.out.println("Item updated successfully!");
                waitForEnter();
                break; // Exit the loop after updating the item
            }
        }
    }

    // Method to display items based on category
    private void displayItemsByCategory(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Chicken and Platters Menu:");
                for (int i = 0; i < MenuData.chickenAndPlattersItemNames.size(); i++) {
                    System.out.println(i + 1 + ". " + MenuData.chickenAndPlattersItemNames.get(i) + " - " + MenuData.chickenAndPlattersItemPrices.get(i) + " INR");
                }
                break;
            case 2:
                System.out.println("Breakfast Menu:");
                for (int i = 0; i < MenuData.breakfastItemNames.size(); i++) {
                    System.out.println(i + 1 + ". " + MenuData.breakfastItemNames.get(i) + " - " + MenuData.breakfastItemPrices.get(i) + " INR");
                }
                break;
            case 3:
                System.out.println("Burger Menu:");
                for (int i = 0; i < MenuData.burgerItemNames.size(); i++) {
                    System.out.println(i + 1 + ". " + MenuData.burgerItemNames.get(i) + " - " + MenuData.burgerItemPrices.get(i) + " INR");
                }
                break;
            case 4:
                System.out.println("Drinks and Desserts Menu:");
                for (int i = 0; i < MenuData.drinksAndDessertsItemNames.size(); i++) {
                    System.out.println(i + 1 + ". " + MenuData.drinksAndDessertsItemNames.get(i) + " - " + MenuData.drinksAndDessertsItemPrices.get(i) + " INR");
                }
                break;
            case 5:
                System.out.println("Coffee Menu:");
                for (int i = 0; i < MenuData.coffeeItemNames.size(); i++) {
                    System.out.println(i + 1 + ". " + MenuData.coffeeItemNames.get(i) + " - " + MenuData.coffeeItemPrices.get(i) + " INR");
                }
                break;
            case 6:
                System.out.println("Fries Menu:");
                for (int i = 0; i < MenuData.friesItemNames.size(); i++) {
                    System.out.println(i + 1 + ". " + MenuData.friesItemNames.get(i) + " - " + MenuData.friesItemPrices.get(i) + " INR");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Get the number of items in the selected category
    private int getCategorySize(int choice) {
        switch (choice) {
            case 1: return MenuData.chickenAndPlattersItemNames.size();
            case 2: return MenuData.breakfastItemNames.size();
            case 3: return MenuData.burgerItemNames.size();
            case 4: return MenuData.drinksAndDessertsItemNames.size();
            case 5: return MenuData.coffeeItemNames.size();
            case 6: return MenuData.friesItemNames.size();
            default: return 0;
        }
    }

    // Update the item name in the selected category
    private void updateItemName(int choice, int index, String newName) {
        switch (choice) {
            case 1:
                MenuData.chickenAndPlattersItemNames.set(index, newName);
                break;
            case 2:
                MenuData.breakfastItemNames.set(index, newName);
                break;
            case 3:
                MenuData.burgerItemNames.set(index, newName);
                break;
            case 4:
                MenuData.drinksAndDessertsItemNames.set(index, newName);
                break;
            case 5:
                MenuData.coffeeItemNames.set(index, newName);
                break;
            case 6:
                MenuData.friesItemNames.set(index, newName);
                break;
        }
    }

    // Update the item price in the selected category
    private void updateItemPrice(int choice, int index, int newPrice) {
        switch (choice) {
            case 1:
                MenuData.chickenAndPlattersItemPrices.set(index, newPrice);
                break;
            case 2:
                MenuData.breakfastItemPrices.set(index, newPrice);
                break;
            case 3:
                MenuData.burgerItemPrices.set(index, newPrice);
                break;
            case 4:
                MenuData.drinksAndDessertsItemPrices.set(index, newPrice);
                break;
            case 5:
                MenuData.coffeeItemPrices.set(index, newPrice);
                break;
            case 6:
                MenuData.friesItemPrices.set(index, newPrice);
                break;
        }
    }

    // Wait for the user to press Enter
    public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }
}
