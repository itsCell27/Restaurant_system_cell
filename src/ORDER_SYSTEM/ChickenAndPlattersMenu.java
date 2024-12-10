package ORDER_SYSTEM;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ChickenAndPlattersMenu {

    private static final String FILE_PATH = "MenuItems/menu.csv"; // Hardcoded file path

    // Method to read the CSV file and load the menu items dynamically
    public List<MenuItem> readMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();

        // Check if the file exists before trying to read it
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("\t\t\tError: File not found - " + FILE_PATH);
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

                    if (data.length == 3) {
                        String name = data[0].trim();
                        double price = Double.parseDouble(data[1].trim()); // Parse as double
                        String category = data[2].trim();

                        // Create a MenuItem object and add it to the list
                        menuItems.add(new MenuItem(name, price, category));
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\t\t\tError: Invalid data format in file. Skipping a line...");
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError: Unable to read the file.");
            e.printStackTrace();
        }

        return menuItems;
    }

    // Method to display the Chicken menu and process orders
    public void displayMenu(Scanner scanner, HandleMyOrder handleOrder) {
        // Reload the menu items from the CSV file each time
        List<MenuItem> menuItems = readMenuItems();

        // Filter the items that belong to the "Chicken And Platters" category
        List<MenuItem> chickenMenu = menuItems.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase("Chicken And Platters"))
                .collect(Collectors.toList());

        if (chickenMenu.isEmpty()) {
            System.out.println("\t\t\tNo items found in the Chicken menu.");
            System.out.println("\t\t\tPress ENTER to continue...");
            scanner.nextLine();
            return;
        }

        // Print the chicken menu in tabular format
        System.out.println("\t\t\tCHICKEN MENU\n");
        System.out.println("\t\t\t===================================================================");
        System.out.printf("\t\t\t| %-5s | %-36s | %-16s |\n", "No.", "Item", "Price");
        System.out.println("\t\t\t===================================================================");
        for (int i = 0; i < chickenMenu.size(); i++) {
            MenuItem item = chickenMenu.get(i);
            System.out.printf("\t\t\t| %-5d | %-36s | %-16.2f |\n",
                    i + 1, item.getName(), item.getPrice());
        }
        System.out.println("\t\t\t===================================================================");

        int itemChoice = getValidatedInput(scanner, "\t\t\tPlease select an option (1-" + chickenMenu.size() + "), input 0 to go back: ", 0, chickenMenu.size());
        if (itemChoice == 0) {
            return; // Go back to main menu
        }

        // Process the order if valid
        processOrder(chickenMenu.get(itemChoice - 1), scanner, handleOrder);
    }

    // Method to process the order for a specific menu item
    private void processOrder(MenuItem item, Scanner scanner, HandleMyOrder handleOrder) {
        int quantity = getValidatedInput(scanner, "\t\t\tEnter the quantity for this item: ", 1, Integer.MAX_VALUE);

        // Calculate the total price
        double totalPrice = item.getPrice() * quantity;

        int saveOrder = getValidatedInput(scanner, "\t\t\tDo you want to save this order? (1 for Yes, 0 for No): ", 0, 1);

        if (saveOrder == 1) {
            // Create an Order object
            Order order = new Order(item, quantity);

            // Add the order to HandleMyOrder's list
            handleOrder.addOrder(order);

            // Display the order summary
            System.out.println("\n\t\t\tOrder Summary:");
            System.out.println("\t\t\tItem: " + item.getName());
            System.out.println("\t\t\tQuantity: " + quantity);
            System.out.printf("\t\t\tPrice: %.2f PHP\n", totalPrice);
            System.out.printf("\n\t\t\tTotal Price: %.2f PHP\n", totalPrice);
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

    // Helper method to validate user input for an integer within a range
    private int getValidatedInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("\t\t\tError: Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("\t\t\tError: Invalid input. Please enter a number.");
                scanner.next(); // Consume invalid input
            }
        }
    }
}
