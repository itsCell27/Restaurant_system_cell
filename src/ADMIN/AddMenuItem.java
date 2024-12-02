package ADMIN;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AddMenuItem {

    private static final String FOLDER_PATH = "MenuItems";  // Folder name where the CSV file will be stored
    private static final String FILE_NAME = FOLDER_PATH + "/menu.csv";  // CSV file path

    private Scanner scanner;

    // Constructor
    public AddMenuItem() {
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
            default:
                System.out.println("Invalid selection. Please choose a valid category.");
                displayCategoryMenu();
                return;  // Exit the method if invalid category is selected
        }

        // Ask user to input up to 10 items for the selected category
        addItemsToCategory(category);
    }

    // Method to allow the user to add items to the selected category
    private void addItemsToCategory(String category) {
        String[] itemNames = new String[10];
        int[] itemPrices = new int[10];
        int itemCount = 0;

        // Allow user to input items up to 10
        System.out.println("Enter up to 10 items for the " + category + " category:");

        while (itemCount < 10) {
            // Ask for the item name
            System.out.print("Enter item name (or type 'done' to stop): ");
            String itemName = scanner.nextLine();

            // Check if the user wants to stop adding items
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }

            // Ask for the item price
            System.out.print("Enter price for " + itemName + ": ");
            int itemPrice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline character

            // Store the item details
            itemNames[itemCount] = itemName;
            itemPrices[itemCount] = itemPrice;
            itemCount++;
        }

        // Write the items to the CSV file
        writeToCSV(itemNames, itemPrices, itemCount, category);

        // Reload the updated menu after adding items
        reloadMenu();
    }

    // Method to write the items into the CSV file
    private void writeToCSV(String[] itemNames, int[] itemPrices, int itemCount, String category) {
        // Ensure the folder exists
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdir();  // Create the folder if it doesn't exist
        }

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            // Check if the file is empty and write the header if needed
            boolean isFileEmpty = new File(FILE_NAME).length() == 0;
            if (isFileEmpty) {
                writer.append("Item, Price, Category\n");
            }

            // Write the menu items to the CSV file
            for (int i = 0; i < itemCount; i++) {
                writer.append(itemNames[i] + ", " + itemPrices[i] + ", " + category + "\n");
            }

            System.out.println("Menu items added to the CSV file in the " + FOLDER_PATH + " folder.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the menu: " + e.getMessage());
        }
    }

    // Method to reload the menu from the CSV file after modification
    private void reloadMenu() {
        // In this example, let's just print out the updated menu from the CSV
        System.out.println("Reloading the menu...");

        // You can implement a method to read from CSV and display the updated menu
        readMenuFromCSV();
    }

    // Method to read and display the updated menu from the CSV file
    private void readMenuFromCSV() {
        try (Scanner fileScanner = new Scanner(new File(FILE_NAME))) {
            // Skip the header line if it exists
            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();  // Skip header
            }

            // Display the contents of the file
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the menu: " + e.getMessage());
        }
    }
}
