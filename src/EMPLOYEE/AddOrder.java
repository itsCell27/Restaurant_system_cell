package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AddOrder {

    // Method to display the add order menu
    public static void addOrder(String orderNumber, List<Order> selectedOrderItems) {
        int menuChoice = -1;
        Scanner scanner = new Scanner(System.in);

        // Display the menu options
        while (menuChoice != 0) {
            System.out.println("\n\t\t\tBreakfast Menu");
            System.out.println("\t\t\t[2] Chicken And Platters");
            System.out.println("\t\t\t[3] Burger Menu");
            System.out.println("\t\t\t[4] Drinks & Desserts Menu");
            System.out.println("\t\t\t[5] Coffee Menu");
            System.out.println("\t\t\t[6] Fries Menu");
            System.out.println("\t\t\t[7] My Order");
            System.out.println("\t\t\t[0] Go Back");
            System.out.print("\n\t\t\tEnter your choice: ");
            
            if (scanner.hasNextInt()) {
                menuChoice = scanner.nextInt();

                switch (menuChoice) {
                    case 2:
                        displayItems("Chicken and Platters");
                        break;
                    case 3:
                        displayItems("Burger");
                        break;
                    case 4:
                        displayItems("Drinks and Dessert");
                        break;
                    case 5:
                        displayItems("Coffee");
                        break;
                    case 6:
                        displayItems("Fries");
                        break;
                    case 7:
                        System.out.println("\t\t\tMy Order selected.");
                        // Here you can add the functionality to display the current order
                        break;
                    case 0:
                        System.out.println("\t\t\tGoing back to previous menu.");
                        return; // Go back to the previous menu
                    default:
                        System.out.println("\t\t\tInvalid option. Please select a valid menu option.");
                }
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Method to display items based on category selected by the user
    private static void displayItems(String category) {
        List<MenuItem> items = readMenuItemsFromCSV();
        System.out.println("\n\t\t\tItems in " + category + " category:");
        
        boolean found = false;
        for (MenuItem item : items) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                found = true;
                System.out.println("\t\t\t" + item.getItemName() + " - " + item.getPrice() + " INR");
            }
        }

        if (!found) {
            System.out.println("\t\t\tNo items found for this category.");
        }
    }

    // Method to read items from the CSV file
    private static List<MenuItem> readMenuItemsFromCSV() {
        List<MenuItem> items = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("MenuItems/menu.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String itemName = data[0].trim();
                    double price = Double.parseDouble(data[1].trim());
                    String category = data[2].trim();
                    items.add(new MenuItem(itemName, price, category));
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError reading menu file: " + e.getMessage());
        }
        return items;
    }

    // MenuItem class to hold item details
    static class MenuItem {
        private String itemName;
        private double price;
        private String category;

        public MenuItem(String itemName, double price, String category) {
            this.itemName = itemName;
            this.price = price;
            this.category = category;
        }

        public String getItemName() {
            return itemName;
        }

        public double getPrice() {
            return price;
        }

        public String getCategory() {
            return category;
        }
    }
}
