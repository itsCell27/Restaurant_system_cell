package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CancelOrders {

    // Method to display pending orders and cancel them
    public static void cancelOrder() {
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";
        Map<String, StringBuilder> ordersMap = new HashMap<>();
        Map<String, Integer> totalPriceMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read through the file and process pending orders
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure we have enough columns in the CSV row
                if (orderDetails.length < 9) continue;

                String orderNumber = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmount = orderDetails[3].trim();  // Total price for the quantity
                String status = orderDetails[6].trim();       // Order status

                // Only process orders with "pending" status
                if (status.equalsIgnoreCase("pending")) {
                    // Add to the map for displaying orders
                    StringBuilder orderItems = ordersMap.getOrDefault(orderNumber, new StringBuilder());
                    orderItems.append(String.format("%d %-15s ..................................%s PHP\n", quantity, itemName, totalAmount));
                    ordersMap.put(orderNumber, orderItems);

                    // Calculate the total price for the order
                    int itemTotalPrice = Integer.parseInt(totalAmount.trim());
                    totalPriceMap.put(orderNumber, totalPriceMap.getOrDefault(orderNumber, 0) + itemTotalPrice);
                }
            }

            // Display the orders
            if (ordersMap.isEmpty()) {
                System.out.println("No pending orders found.");
                return;
            }

            for (Map.Entry<String, StringBuilder> entry : ordersMap.entrySet()) {
                String orderNumber = entry.getKey();
                StringBuilder orderItems = entry.getValue();
                int totalPrice = totalPriceMap.get(orderNumber);

                System.out.println("ORDER #" + orderNumber);
                System.out.print(orderItems.toString());
                System.out.printf("Total               ..................................%d PHP\n", totalPrice);
                System.out.println();
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
            if (ordersMap.containsKey(input)) {
                updateOrderStatus(csvFile, input);
                System.out.println("Order #" + input + " marked as cancelled.");
            } else {
                System.out.println("Invalid order number.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in the file.");
        }
    }

    // Method to update the status of an order to "cancelled"
    private static void updateOrderStatus(String csvFile, String orderNumber) {
        StringBuilder fileContent = new StringBuilder();
        String line;
        String cvsSplitBy = ",";
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);

                // Check if the order number matches
                if (orderDetails[0].trim().equals(orderNumber)) {
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
