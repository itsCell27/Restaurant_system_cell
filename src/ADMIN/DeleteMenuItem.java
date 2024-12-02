package ADMIN;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeleteMenuItem {

    private static final String FOLDER_PATH = "MenuItems";  // Folder name where the CSV file is stored
    private static final String FILE_NAME = FOLDER_PATH + "/menu.csv";  // CSV file path

    private Scanner scanner;

    // Constructor
    public DeleteMenuItem() {
        this.scanner = new Scanner(System.in);
    }

    // Display the category menu to the user
    public void displayCategoryMenu() {
    	AdminMenu ads = new AdminMenu();
        // Display the categories
        System.out.println("Please select a category:");
        System.out.println("1. Chicken and Platters");
        System.out.println("2. Breakfast");
        System.out.println("3. Burgers");
        System.out.println("4. Drinks and Desserts");
        System.out.println("5. Coffee");
        System.out.println("6. Fries");
        System.out.println("7. Go back");

        // Ask the user to choose a category
        System.out.print("Enter the number of the category: ");
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
                break;
            default:
                System.out.println("Invalid selection. Please choose a valid category.");
                displayCategoryMenu();
                return;  // Exit the method if invalid category is selected
        }

        // Ask user to input the item to delete
        deleteItemFromCategory(category);
    }

    // Method to delete an item from the selected category
    private void deleteItemFromCategory(String category) {
        List<String[]> menuItems = readMenuFromCSV();
        List<String[]> updatedMenuItems = new ArrayList<>();

        // Filter items by category
        for (String[] item : menuItems) {
            if (item[2].equalsIgnoreCase(category)) {
                System.out.println("Item: " + item[0] + " | Price: " + item[1]);
            } else {
                updatedMenuItems.add(item);
            }
        }

        // Ask the user to choose the item to delete
        System.out.print("Enter the item name to delete: ");
        String itemToDelete = scanner.nextLine();

        // Check if the item exists
        boolean itemExists = false;
        for (String[] item : menuItems) {
            if (item[0].equalsIgnoreCase(itemToDelete)) {
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            System.out.println("Item not found. Please enter a valid item name.");
            return;
        }

        // Ask for confirmation before deleting
        System.out.print("Are you sure you want to delete the item: " + itemToDelete + "? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            // Remove the selected item from the menu
            updatedMenuItems.removeIf(item -> item[0].equalsIgnoreCase(itemToDelete));

            // Write the updated menu back to the CSV file
            writeToCSV(updatedMenuItems);
            displayCategoryMenu();
        } else {
        	
            System.out.println("Item deletion canceled.");
            displayCategoryMenu();
        }
    }


    // Method to read the menu from the CSV file
    private List<String[]> readMenuFromCSV() {
        List<String[]> menuItems = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("Menu file does not exist.");
            return menuItems;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                // Skip the header
                if (line.startsWith("Item, Price, Category")) {
                    continue;
                }
                String[] itemDetails = line.split(", ");
                menuItems.add(itemDetails);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the menu: " + e.getMessage());
        }

        return menuItems;
    }

    // Method to write the updated menu back into the CSV file
    private void writeToCSV(List<String[]> updatedMenuItems) {
        // Ensure the folder exists
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdir();  // Create the folder if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            // Write the header
            writer.append("Item, Price, Category\n");

            // Write the updated menu items to the CSV file
            for (String[] item : updatedMenuItems) {
                writer.append(item[0] + ", " + item[1] + ", " + item[2] + "\n");
            }

            System.out.println("Item deleted and menu updated successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while updating the menu: " + e.getMessage());
        }
    }
}
