package EMPLOYEE;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class ModifyQuantity{

    // Method to modify the quantity of an item in the order
    public static void modifyQuantity(String orderNumber, List<Order> selectedOrderItems) {
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

        // Prompt user to select the item to modify
        int itemChoice = -1;
        while (itemChoice < 1 || itemChoice > selectedOrderItems.size()) {
            System.out.print("\n\t\t\tSelect the item to modify (0 to cancel): ");
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

        // Get the selected item
        Order selectedItem = selectedOrderItems.get(itemChoice - 1);

        // Store the unit price of the selected item temporarily
        double unitPrice = selectedItem.getUnitPrice();

        // Prompt user for the new quantity
        int newQuantity = -1;
        while (newQuantity <= 0) {
            System.out.print("\n\t\t\tEnter the new quantity for " + selectedItem.getItemName() + ": ");
            if (scanner.hasNextInt()) {
                newQuantity = scanner.nextInt();
                if (newQuantity <= 0) {
                    System.out.println("\t\t\tQuantity must be greater than zero. Please try again.");
                }
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }

        // Update the quantity
        selectedItem.setQuantity(newQuantity);

        // Recalculate the total price based on the updated quantity and unit price
        double newTotalPrice = newQuantity * unitPrice;
        selectedItem.setTotalPrice(newTotalPrice);

        // Display the updated total price
        System.out.println("\n\t\t\tUpdated quantity for " + selectedItem.getItemName() + " to " + newQuantity);
        System.out.println("\t\t\tNew total price: " + String.format("%.2f PHP", selectedItem.getTotalPrice()));

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
                    // For the selected order, update the quantity and total price
                    for (Order order : selectedOrderItems) {
                        if (order.getItemName().equals(orderDetails[1].trim())) {
                            // Update the CSV line with the new quantity and total price
                            orderDetails[2] = String.valueOf(order.getQuantity()); // Update quantity
                            orderDetails[3] = String.format("%.2f PHP", order.getTotalPrice()); // Update total price
                        }
                    }
                }

                // Rebuild the CSV line
                updatedCsvContent.append(String.join(",", orderDetails)).append("\n");
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
