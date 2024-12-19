package EMPLOYEE;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class RemoveOrder{

    // Method to remove an item from the order
    public static void removeOrder(String orderNumber, List<Order> selectedOrderItems) {
        Scanner scanner = new Scanner(System.in);

        // Display the items in the selected order
        System.out.println("\n\t\t\tItems in Order #" + orderNumber + ":");
        int itemIndex = 1;
        for (Order order : selectedOrderItems) {
            System.out.println("\t\t\t[" + itemIndex++ + "] " 
                + order.getItemName() 
                + " - Quantity: " + order.getQuantity()
                + " | Unit Price: " + String.format("%.2f PHP", order.getUnitPrice())
                + " | Total Price: " + String.format("%.2f PHP", order.getTotalPrice()));
        }

        // Prompt user to select the item to remove
        int itemChoice = -1;
        while (itemChoice < 1 || itemChoice > selectedOrderItems.size()) {
            System.out.print("\n\t\t\tSelect the item to remove (0 to cancel): ");
            if (scanner.hasNextInt()) {
                itemChoice = scanner.nextInt();
                if (itemChoice == 0) {
                    System.out.println("\t\t\tReturning to order details.");
                    return; // Exit to order details if user chooses 0
                }
                if (itemChoice < 1 || itemChoice > selectedOrderItems.size()) {
                    System.out.println("\t\t\tInvalid choice, please select a valid item.");
                }
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }

        // Get the selected item to remove
        Order selectedItem = selectedOrderItems.get(itemChoice - 1);

        // Remove the selected item from the list
        selectedOrderItems.remove(itemChoice - 1);

        // Display a confirmation of removal
        System.out.println("\n\t\t\tRemoved item: " + selectedItem.getItemName());

        // Save the updated order to the CSV file
        saveUpdatedOrderToCSV(orderNumber, selectedOrderItems);
    }

    // Method to save the updated order details to the CSV file
    private static void saveUpdatedOrderToCSV(String orderNumber, List<Order> selectedOrderItems) {
        String csvFile = "OrderRecords/order_summary.csv"; // Path to the CSV file
        StringBuilder updatedCsvContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // Read the CSV file line by line
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(",");
                if (orderDetails.length < 8) continue; // Skip incomplete rows

                String currentOrderNumber = orderDetails[0].trim();
                if (currentOrderNumber.equals(orderNumber)) {
                    // Check if the item is in the list of selected items
                    boolean itemFound = false;
                    for (Order order : selectedOrderItems) {
                        if (order.getItemName().equals(orderDetails[1].trim())) {
                            itemFound = true;
                            break;
                        }
                    }

                    // If the item is not in the updated order, add the line to the updated CSV content
                    if (itemFound) {
                        updatedCsvContent.append(String.join(",", orderDetails)).append("\n");
                    }
                } else {
                    // If not the selected order number, include it in the updated CSV content
                    updatedCsvContent.append(String.join(",", orderDetails)).append("\n");
                }
            }

            // Write the updated content back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write(updatedCsvContent.toString());
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError updating the CSV file: " + e.getMessage());
        }
    }
}
