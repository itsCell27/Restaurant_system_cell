package ORDER_SYSTEM;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FriesMenu {

    private static final String FILE_PATH = "MenuItems/menu.csv"; // Hardcoded file path

    // Method to read the CSV file and load the menu items dynamically
    public List<MenuItem> readMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        // Print the current working directory for debugging purposes
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        // Check if the file exists before trying to read it
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File not found: " + FILE_PATH);
            return menuItems;  // Return empty list if file does not exist
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            // Skip the header line
            br.readLine();

            // Read each line in the CSV file
            while ((line = br.readLine()) != null) {
                // Splitting by comma, assuming the format is "Item, Price, Category"
                String[] data = line.split(",");

                if (data.length == 3) {
                    String name = data[0].trim();
                    int price = Integer.parseInt(data[1].trim());
                    String category = data[2].trim();

                    // Create a MenuItem object and add it to the list
                    menuItems.add(new MenuItem(name, price, category));
                }
            }
        } catch (IOException e) {
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
            System.out.println("No items found in the Fries menu.");
            return;
        }

        System.out.println("\nFries Menu:");
        for (int i = 0; i < friesMenu.size(); i++) {
            System.out.println((i + 1) + ". " + friesMenu.get(i));
        }

        System.out.print("Please select an option (1-" + friesMenu.size() + "), input 0 to go back: ");
        int itemChoice = scanner.nextInt();
        if (itemChoice == 0) {
            return; // Go back to main menu
        }

        // Process the order if valid
        if (itemChoice > 0 && itemChoice <= friesMenu.size()) {
            processOrder(friesMenu.get(itemChoice - 1), scanner, handleOrder);
        } else {
            System.out.println("Invalid choice. Going back...");
        }
    }

    // Method to process the order for a specific menu item
    private void processOrder(MenuItem item, Scanner scanner, HandleMyOrder handleOrder) {
        System.out.print("Enter the quantity for this item: ");
        int quantity = scanner.nextInt();

        // Calculate the total price
        int totalPrice = item.getPrice() * quantity;

        System.out.print("Do you want to save this order? (1 for Yes, 0 for No): ");
        int saveOrder = scanner.nextInt();

        if (saveOrder == 1) {
            // Create an Order object
            Order order = new Order(item, quantity);

            // Add the order to HandleMyOrder's list
            handleOrder.addOrder(order);

            // Display the order summary
            System.out.println("\nOrder Summary:");
            System.out.println("Item: " + item.getName());
            System.out.println("Quantity: " + quantity);
            System.out.println("Price: " + totalPrice + " PHP");
            System.out.println("\nTotal Price: " + totalPrice + " PHP");
            System.out.println("Press Enter to continue...");
            scanner.nextLine(); // Consume newline
            scanner.nextLine(); // Wait for user to press Enter
        } else {
            System.out.println("Order not saved. Returning to menu.");
        }
    }
}
