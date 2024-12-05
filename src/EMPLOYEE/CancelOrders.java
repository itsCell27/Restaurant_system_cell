package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CancelOrders {

    // Method to display pending orders and cancel them
    public static void cancelOrder() {
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";
        Map<Integer, StringBuilder> ordersMap = new TreeMap<>(Collections.reverseOrder());  // TreeMap to sort orders by order number in descending order
        Map<Integer, Integer> totalPriceMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read through the file and process pending orders
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure we have enough columns in the CSV row
                if (orderDetails.length < 9) continue;

                int orderNumber = Integer.parseInt(orderDetails[0].trim());
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmount = orderDetails[3].trim();  // Total price for the quantity
                String status = orderDetails[6].trim();       // Order status

                // Only process orders with "pending" status
                if (status.equalsIgnoreCase("pending")) {
                    // Add to the map for displaying orders
                	StringBuilder orderItems = ordersMap.getOrDefault(orderNumber, new StringBuilder());
                    orderItems.append(String.format("|| %-13s || %-13s || %-27s || %-27s || %-13s ||\n", 
                            "", quantity, itemName, totalAmount + " PHP", ""));
                    ordersMap.put(orderNumber, orderItems);

                    // Calculate the total price for the order
                    int itemTotalPrice = Integer.parseInt(totalAmount.trim());
                    totalPriceMap.put(orderNumber, totalPriceMap.getOrDefault(orderNumber, 0) + itemTotalPrice);
                }
            }

            // Display the orders in tabular format
            if (ordersMap.isEmpty()) {
                System.out.println("No pending orders found.");
                return;
            }

            // Print the header for the table
            System.out.println("===================================================================================================================");
            System.out.println(String.format("|| %-13s || %-13s || %-27s || %-27s || %-13s ||", 
                    "Order No.", "Quantity", "Items", "Total Price By Item Qty", "Total Price"));
            System.out.println("===================================================================================================================");

            // Display orders in descending order of order number
            for (Map.Entry<Integer, StringBuilder> entry : ordersMap.entrySet()) {
                int orderNumber = entry.getKey();
                StringBuilder orderItems = entry.getValue();
                int totalPrice = totalPriceMap.get(orderNumber);

                // Print the order number and total price on the first line
                System.out.printf("|| %-13s || %-13s || %-27s || %-27s || %-13s ||\n", 
                        orderNumber, "", "", "", totalPrice + " PHP");

                // Print the items for this order
                System.out.print(orderItems.toString());

                // Print the closing line for the order
                System.out.println("===================================================================================================================");
            }

            // Get user input to cancel an order
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input the Order number to cancel (0 to go back): ");
            String input = scanner.nextLine();

            // Handle the "go back" option
            if (input.equals("0")) {
                System.out.println("Going back...");
                return;
            }

            // Validate the order number and mark as cancelled
            try {
                int orderInput = Integer.parseInt(input);  // Ensure valid integer input
                if (ordersMap.containsKey(orderInput)) {
                    updateOrderStatus(csvFile, orderInput);
                    System.out.println("Order #" + orderInput + " marked as cancelled.");
                } else {
                    System.out.println("Invalid order number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please enter a valid order number.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in the file.");
        }
    }

    // Method to update the status of an order to "cancelled"
    private static void updateOrderStatus(String csvFile, int orderNumber) {
        StringBuilder fileContent = new StringBuilder();
        String line;
        String cvsSplitBy = ",";
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Check if the order number matches
                if (Integer.parseInt(orderDetails[0].trim()) == orderNumber) {
                    // Change the status to "cancelled"
                    orderDetails[6] = "cancelled";
                    updated = true;
                }

                // Rebuild the line with the updated status
                fileContent.append(String.join(",", orderDetails)).append("\n");
            }

            // If the order was updated, write the content back to the file
            if (updated) {
                try (FileWriter writer = new FileWriter(csvFile)) {
                    writer.write(fileContent.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating the CSV file: " + e.getMessage());
        }
    }
}
