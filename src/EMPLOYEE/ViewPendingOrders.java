package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ViewPendingOrders {

    public static void displayPendingOrders() {
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";

        // A map to hold the orders, with the order number as the key
        Map<String, StringBuilder> ordersMap = new HashMap<>();
        Map<String, Integer> totalPriceMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure we have enough columns in the CSV row (9 expected)
                if (orderDetails.length < 9) {
                    continue;  // Skip rows that don't have enough columns
                }

                // Extract the relevant details from the CSV columns
                String orderNumber = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmount = orderDetails[3].trim();  // This is the total price for the quantity
                String paymentMethod = orderDetails[4].trim();  // We may not use this now
                String diningOption = orderDetails[5].trim();   // We may not use this now
                String status = orderDetails[6].trim();         // Order status
                String date = orderDetails[7].trim();           // We may not use this now
                String time = orderDetails[8].trim();           // Time column

                // Only process orders with "pending" status
                if (status.equalsIgnoreCase("pending")) {
                    // Extract the total price from the "Total Price" field
                    int itemTotalPrice = Integer.parseInt(totalAmount.trim());

                    // Add this item to the map for the given order number
                    StringBuilder orderItems = ordersMap.getOrDefault(orderNumber, new StringBuilder());
                    orderItems.append(String.format("%d %-15s ..................................%d PHP\n", quantity, itemName, itemTotalPrice));
                    ordersMap.put(orderNumber, orderItems);

                    // Add total price to the order
                    totalPriceMap.put(orderNumber, totalPriceMap.getOrDefault(orderNumber, 0) + itemTotalPrice);
                }
            }

            // Now, print the orders
            for (Map.Entry<String, StringBuilder> entry : ordersMap.entrySet()) {
                String orderNumber = entry.getKey();
                StringBuilder orderItems = entry.getValue();
                int totalPrice = totalPriceMap.get(orderNumber);

                // Print the order number and items
                System.out.println("ORDER #" + orderNumber);
                System.out.print(orderItems.toString());
                System.out.printf("Total               ..................................%d PHP\n", totalPrice);
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}
