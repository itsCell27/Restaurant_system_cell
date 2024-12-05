package ADMIN;

import java.io.*;
import java.util.*;

public class UpdateMenuItem {

    private static final String FOLDER_PATH = "MenuItems";  // Folder name where the CSV file is stored
    private static final String FILE_NAME = FOLDER_PATH + "/menu.csv";  // CSV file path
     Scanner scanner;

    // Constructor
    public UpdateMenuItem() {
        this.scanner = new Scanner(System.in);
    }

    // Display the category menu to the user
    public void displayCategoryMenu() {
    	AdminMenu ads = new AdminMenu();
        // Display the categories
    	System.out.println("                                                                                                                  CATEGORIES");
    	System.out.println("                                                                                         ===================================================================");
        System.out.println("                                                                                         |                        [1] Chicken and Platters                 |");
        System.out.println("                                                                                         |                        [2] Breakfast                            |");
        System.out.println("                                                                                         |                        [3] Burgers                              |");
        System.out.println("                                                                                         |                        [4] Drinks and Desserts                  |");
        System.out.println("                                                                                         |                        [5] Coffee                               |");
        System.out.println("                                                                                         |                        [6] Fries                                |");
        System.out.println("                                                                                         |                        [7] Go back                              |");
        System.out.println("                                                                                         ===================================================================\n\n");
          System.out.print("                                                                                                                  Enter: ");
        
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Define category variables
        String category = "";

        // Handle category selection
        switch (categoryChoice) {
            case 1:
                category = "Chicken and Platters";
                break;
            case 2:
                category = "Breakfast";
                break;
            case 3:
                category = "Burger";
                break;
            case 4:
                category = "Drinks and Dessert";
                break;
            case 5:
                category = "Coffee";
                break;
            case 6:
                category = "Fries";
                break;
            case 7:
            	ads.displayMenu();
            default:
                System.out.println("Invalid selection. Please choose a valid category.");
                displayCategoryMenu(); // Exit the method if invalid category is selected
        }

        // Allow the user to update items in the selected category
        updateItemsInCategory(category);
    }

    // Method to allow the user to update items in the selected category
 // Method to allow the user to update items in the selected category
 // Method to allow the user to update items in the selected category
    private void updateItemsInCategory(String category) {
        AdminMenu adminMenu = new AdminMenu();
        // Read the existing menu items from the CSV file
        List<String[]> menuItems = readMenuFromCSV();

        // Filter items by category
        List<String[]> categoryItems = new ArrayList<>();
        for (String[] item : menuItems) {
            if (item[2].equalsIgnoreCase(category)) { // Case-insensitive matching for category
                categoryItems.add(item);
            }
        }

        // Check if there are any items in the selected category
        if (categoryItems.isEmpty()) {
            System.out.println("\nNo items found in the " + category + " category.\n");
            return;
        }

        // Display the items in the selected category in a tabular format
        System.out.println("                                                                                         ==============================================================");
        System.out.printf("                                                                                         | %-5s | %-30s | %-10s |\n", "No.", "Item Name", "Price");
        System.out.println("                                                                                         ==============================================================");
        for (int i = 0; i < categoryItems.size(); i++) {
            String[] item = categoryItems.get(i);
            System.out.printf("                                                                                         | %-5s | %-30s | %-10s |\n", (i + 1), item[0], item[1]);
        }
        System.out.println("                                                                                         ===============================================================\n");
        System.out.println("                                                                                         Enter 0 to go back to the category menu.");

        // Ask user to select the item to update or go back
        int itemChoice = -1;
        while (itemChoice < 0 || itemChoice > categoryItems.size()) {
            System.out.print("                                                                                         Enter the number of the item to update: ");
            itemChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            if (itemChoice == 0) {
            	displayCategoryMenu(); // Go back to the main menu
                return;
            }

            if (itemChoice < 1 || itemChoice > categoryItems.size()) {
                System.out.println("                                                                                         Invalid selection. Please choose a valid item.");
            }
        }

        // Get the selected item
        String[] selectedItem = categoryItems.get(itemChoice - 1);
        System.out.println("\nYou selected: " + selectedItem[0] + " - Price: " + selectedItem[1]);

        // Ask the user if they want to change the item name
        System.out.print("                                                                                         Do you want to change the item name? (y/n): ");
        String changeName = scanner.nextLine();

        if (changeName.equalsIgnoreCase("y")) {
            System.out.print("                                                                                         Enter the new name for " + selectedItem[0] + ": ");
            String newName = scanner.nextLine();
            selectedItem[0] = newName;  // Update item name
        }

        // Ask the user if they want to change the item price
        System.out.print("                                                                                         Do you want to change the price? (y/n): ");
        String changePrice = scanner.nextLine();

        if (changePrice.equalsIgnoreCase("y")) {
            System.out.print("                                                                                         Enter the new price for " + selectedItem[0] + ": ");
            int newPrice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character
            selectedItem[1] = String.valueOf(newPrice);  // Update item price
        }

        // Update the main menu list with the modified item
        for (String[] menuItem : menuItems) {
            if (menuItem[0].equalsIgnoreCase(selectedItem[0]) && 
                menuItem[2].equalsIgnoreCase(category)) {
                menuItem[1] = selectedItem[1]; // Update the item's price in the main list
            }
        }

        // Write the updated items back to the CSV file
        writeToCSV(menuItems);
        System.out.println("\n                                                                                         Item updated successfully!\n");
        
        // Redisplay the updated category items
        updateItemsInCategory(category);
        
    }
    
    

    // Method to read the menu from the CSV file
    private List<String[]> readMenuFromCSV() {
        List<String[]> menuItems = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (line.startsWith("Item, Price, Category")) {
                    continue;
                }

                // Split the line into parts (item name, price, category)
                String[] parts = line.split(", ");
                if (parts.length == 3) {
                    menuItems.add(parts);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the menu: " + e.getMessage());
        }

        return menuItems;
    }

    // Method to write the updated menu back to the CSV file
    private void writeToCSV(List<String[]> menuItems) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.append("Item, Price, Category\n");  // Write the header

            // Write each item to the file
            for (String[] item : menuItems) {
                writer.append(String.join(", ", item) + "\n");
            }

            System.out.println("Menu items updated in the CSV file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the updated menu: " + e.getMessage());
        }
    }
}
