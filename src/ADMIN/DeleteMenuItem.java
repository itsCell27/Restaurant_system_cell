package ADMIN;
import MENU_DATA_HANDLING.MenuData;
import java.util.Scanner;

public class DeleteMenuItem {
    Scanner scanner = new Scanner(System.in);

    public void DeleteMenuItem() {
        int choice = -1; // Initial invalid value to enter the loop

        while (choice != 0) {
            // Display category options
            System.out.println("Select Category to Delete Item:");
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

            if (choice == 0) {
                return; // Exit the method if user chooses to return
            }

            // Display the items based on the selected category
            switch (choice) {
                case 1 -> deleteFromCategory("Chicken and Platters", MenuData.chickenAndPlattersItemNames);
                case 2 -> deleteFromCategory("Breakfast", MenuData.breakfastItemNames);
                case 3 -> deleteFromCategory("Burger", MenuData.burgerItemNames);
                case 4 -> deleteFromCategory("Drinks and Desserts", MenuData.drinksAndDessertsItemNames);
                case 5 -> deleteFromCategory("Coffee", MenuData.coffeeItemNames);
                case 6 -> deleteFromCategory("Fries", MenuData.friesItemNames);
                default -> System.out.println("Invalid choice! Please select a valid category.");
            }
        }
    }

    // Method to handle item deletion from a specific category
    private void deleteFromCategory(String categoryName, java.util.List<String> itemNames) {
        System.out.println("\n--- " + categoryName + " ---");
        if (itemNames.isEmpty()) {
            System.out.println("No items in this category.");
            return;
        }

        // Display the items in the category
        for (int i = 0; i < itemNames.size(); i++) {
            System.out.println((i + 1) + ". " + itemNames.get(i));
        }

        System.out.print("Enter the number of the item to delete or 0 to return: ");
        int itemChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Exit if the user chooses 0
        if (itemChoice == 0) {
            return;
        }

        // Check if the input is valid
        if (itemChoice < 1 || itemChoice > itemNames.size()) {
            System.out.println("Invalid choice! Please try again.");
            return;
        }

        // Remove the item based on the selected index
        String itemName = itemNames.get(itemChoice - 1);
        itemNames.remove(itemChoice - 1);

        // Remove the corresponding price (we assume prices are handled similarly in the MenuData class)
        removePriceFromCategory(categoryName, itemChoice - 1);

        System.out.println(itemName + " has been deleted from " + categoryName + ".");
    }

    // Method to remove the price from the corresponding category
    private void removePriceFromCategory(String categoryName, int index) {
        switch (categoryName) {
            case "Chicken and Platters" -> {
                MenuData.chickenAndPlattersItemPrices.remove(index);
            }
            case "Breakfast" -> {
                MenuData.breakfastItemPrices.remove(index);
            }
            case "Burger" -> {
                MenuData.burgerItemPrices.remove(index);
            }
            case "Drinks and Desserts" -> {
                MenuData.drinksAndDessertsItemPrices.remove(index);
            }
            case "Coffee" -> {
                MenuData.coffeeItemPrices.remove(index);
            }
            case "Fries" -> {
                MenuData.friesItemPrices.remove(index);
            }
            default -> {
                System.out.println("Error: Unknown category!");
            }
        }
    }

    public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}
