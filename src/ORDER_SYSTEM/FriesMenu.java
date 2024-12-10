package ORDER_SYSTEM;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FriesMenu {

    private static final String FILE_PATH = "MenuItems/menu.csv"; // Hardcoded file path

    // Method to read the CSV file and load the menu items dynamically
    public List<MenuItem> readMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        // Check if the file exists before trying to read it
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("\t\t\tError: File not found at path: " + FILE_PATH);
            return menuItems; // Return empty list if file does not exist
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            // Skip the header line
            br.readLine();

            // Read each line in the CSV file
            while ((line = br.readLine()) != null) {
                try {
                    // Splitting by comma, assuming the format is "Item, Price, Category"
                    String[] data = line.split(",");
                    if (data.length != 3) {
                        System.out.println("\t\t\tWarning: Skipping malformed line: " + line);
                        continue;
                    }

                    String name = data[0].trim();
                    double price = Double.parseDouble(data[1].trim());
                    String category = data[2].trim();

                    // Create a MenuItem object and add it to the list
                    menuItems.add(new MenuItem(name, price, category));
                } catch (NumberFormatException e) {
                    System.out.println("\t\t\tWarning: Skipping line with invalid price: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError: An I/O error occurred while reading the file.");
            e.printStackTrace();
        }

        return menuItems;
    }

    // Method to display the Fries menu and process orders
    public void displayMenu(Scanner scanner, HandleMyOrder handleOrder) {
        // Reload the menu items from the CSV file each time
        List<MenuItem> menuItems = readMenuItems();

        // Filter the items that belong to the "Fries" category
        List<MenuItem> friesMenu = menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase("Fries"))
                .collect(Collectors.toList());

        if (friesMenu.isEmpty()) {
            System.out.println("\t\t\tError: No items found in the Fries menu.");
            System.out.println("\t\t\tPress ENTER to continue...");
            scanner.nextLine();
            return;
        }

        // Print the fries menu in tabular format
        System.out.println("\t\t\t\t\t\tFRIES MENU\n");
        System.out.println("\t\t\t===================================================================");
        System.out.printf("\t\t\t| %-5s | %-36s | %-16s |\n", "No.", "Item", "Price");
        System.out.println("\t\t\t===================================================================");
        for (int i = 0; i < friesMenu.size(); i++) {
            MenuItem item = friesMenu.get(i);
            System.out.printf("\t\t\t| %-5d | %-36s | %-16.2f |\n", 
                    i + 1, item.getName(), item.getPrice());
        }
        System.out.println("\t\t\t===================================================================");

        int itemChoice = -1;
        while (true) {
            try {
                System.out.print("\t\t\tPlease select an option (1-" + friesMenu.size() + "), input 0 to go back: ");
                itemChoice = scanner.nextInt();
                if (itemChoice < 0 || itemChoice > friesMenu.size()) {
                    System.out.println("\t\t\tError: Invalid choice. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\t\t\tError: Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        if (itemChoice == 0) {
            return; // Go back to main menu
        }

        // Process the order if valid
        processOrder(friesMenu.get(itemChoice - 1), scanner, handleOrder);
    }

    // Method to process the order for a specific menu item
    private void processOrder(MenuItem item, Scanner scanner, HandleMyOrder handleOrder) {
        int quantity = -1;
        while (true) {
            try {
                System.out.print("\t\t\tEnter the quantity for this item: ");
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("\t\t\tError: Quantity must be greater than zero. Please try again.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\t\t\tError: Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        // Calculate the total price
        double totalPrice = item.getPrice() * quantity;

        int saveOrder = -1;
        while (true) {
            try {
                System.out.print("\t\t\tDo you want to save this order? (1 for Yes, 0 for No): ");
                saveOrder = scanner.nextInt();
                if (saveOrder != 0 && saveOrder != 1) {
                    System.out.println("\t\t\tError: Please enter 1 for Yes or 0 for No.");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\t\t\tError: Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        if (saveOrder == 1) {
            // Create an Order object
            Order order = new Order(item, quantity);

            // Add the order to HandleMyOrder's list
            handleOrder.addOrder(order);

            // Display the order summary
            System.out.println("\n\t\t\tOrder Summary:");
            System.out.println("\t\t\tItem: " + item.getName());
            System.out.println("\t\t\tQuantity: " + quantity);
            System.out.println("\t\t\tPrice: " + String.format("%.2f", totalPrice) + " PHP");
            System.out.println("\n\t\t\tTotal Price: " + String.format("%.2f", totalPrice) + " PHP");
            System.out.println("\t\t\tPress Enter to continue...");
            scanner.nextLine(); // Consume newline
            scanner.nextLine(); // Wait for user to press Enter
        } else {
        	System.out.println("\t\t\tOrder not saved. ");
            //timer
            for (int i = 3; i > 0; i--) { // Countdown 
                System.out.println("\t\t\t" + i + " returning to menu in...");
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (InterruptedException e) {
                    System.out.println("Timer was interrupted!");
                }
            }
            //timer
        }
    }
}
