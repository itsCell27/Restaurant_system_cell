package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MarkOrdersCompleted {

    // Method to display pending orders and mark them as served
    public static void completeOrder() {
        String csvFile = "OrderRecords/order_records.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";
        Map<String, String> orderStatuses = new HashMap<>();
        Map<String, StringBuilder> ordersMap = new HashMap<>();
        Map<String, Integer> totalPriceMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read through the file and process pending orders
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] orderDetails = line.split(cvsSplitBy);

                String orderNumber = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmount = orderDetails[3].trim();  // This is the total price for the quantity
                String status = orderDetails[5].trim();

                // Only process orders with "pending" status
                if (status.equals("pending")) {
                    // Add to the map for displaying orders
                    StringBuilder orderItems = ordersMap.getOrDefault(orderNumber, new StringBuilder());
                    orderItems.append(String.format("%d %-15s ..................................%s\n", quantity, itemName, totalAmount));
                    ordersMap.put(orderNumber, orderItems);

                    // Calculate the total price for the order
                    int itemTotalPrice = Integer.parseInt(totalAmount.replace(" PHP", "").trim());
                    totalPriceMap.put(orderNumber, totalPriceMap.getOrDefault(orderNumber, 0) + itemTotalPrice);

                    // Store the status of the order
                    orderStatuses.put(orderNumber, status);
                }
            }

            // Display the orders
            for (Map.Entry<String, StringBuilder> entry : ordersMap.entrySet()) {
                String orderNumber = entry.getKey();
                StringBuilder orderItems = entry.getValue();
                int totalPrice = totalPriceMap.get(orderNumber);

                System.out.println("ORDER #" + orderNumber);
                System.out.print(orderItems.toString());
                System.out.printf("Total               ..................................%d PHP\n", totalPrice);
                System.out.println();
            }

            // Get user input to mark an order as completed
            System.out.print("Input the Order number to mark as served (0 to go back): ");
            int orderNumberToMark = Integer.parseInt(System.console().readLine());

            // If the user selects 0, exit the method
            if (orderNumberToMark == 0) {
                System.out.println("Going back...");
                return;
            }

            // If the order number is valid, mark the order as served
            String orderNumberStr = String.valueOf(orderNumberToMark);
            if (ordersMap.containsKey(orderNumberStr)) {
                // Read the file again to update the status
                updateOrderStatus(csvFile, orderNumberStr);
                System.out.println("Order #" + orderNumberStr + " marked as served.");
            } else {
                System.out.println("Invalid order number.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }

    // Method to update the status of an order to "served"
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
                    // Change the status to "served"
                    orderDetails[5] = "served";
                    updated = true;
                }

                // Rebuild the line with the updated status
                fileContent.append(String.join(",", orderDetails));
                fileContent.append("\n");
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

    public static void main(String[] args) {
        // Call the method to display pending orders and mark them as completed
        completeOrder();
    }
}
