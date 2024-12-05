package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MarkOrdersCompleted {

    // Method to display pending orders and mark them as served
    public static void completeOrder() {
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";
        Map<Integer, StringBuilder> ordersMap = new TreeMap<>(Collections.reverseOrder()); // Map to store orders in descending order
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

            // Display the orders in descending order
            if (ordersMap.isEmpty()) {
                System.out.println("No pending orders found.");
                return;
            }

            System.out.println("===================================================================================================================");
            System.out.println(String.format("|| %-13s || %-13s || %-27s || %-27s || %-13s ||", 
                    "Order No.", "Quantity", "Items", "Total Price By Item Qty", "Total Price"));
            System.out.println("===================================================================================================================");

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

            // Get user input to mark an order as completed
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input the Order number to mark as served (0 to go back): ");
            String input = scanner.nextLine();

            // Handle the "go back" option
            if (input.equals("0")) {
                System.out.println("Going back...");
                return;
            }

            // Validate the order number and mark as served
            try {
                int orderInput = Integer.parseInt(input);
                if (ordersMap.containsKey(orderInput)) {
                    updateOrderStatus(csvFile, orderInput);
                    System.out.println("Order #" + orderInput + " marked as served.");
                } else {
                    System.out.println("Invalid order number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid order number.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format in the file.");
        }
    }

    // Method to update the status of an order to "served"
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
                if (orderDetails[0].trim().equals(String.valueOf(orderNumber))) {
                    // Change the status to "served"
                    orderDetails[6] = "served";
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
