package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ViewPendingOrders {

    public static void displayPendingOrders() {
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";

        // A map to hold the orders, with the order number as the key
        Map<Integer, StringBuilder> ordersMap = new TreeMap<>(Collections.reverseOrder());  // TreeMap to sort by order number in descending order
        Map<Integer, Integer> totalPriceMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Process data first
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                String[] orderDetails = line.split(cvsSplitBy);

                // Ensure we have enough columns in the CSV row (9 expected)
                if (orderDetails.length < 9) {
                    continue;  // Skip rows that don't have enough columns
                }

                // Extract the relevant details from the CSV columns
                int orderNumber = Integer.parseInt(orderDetails[0].trim());
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmount = orderDetails[3].trim();  // This is the total price for the quantity
                String status = orderDetails[6].trim();         // Order status

                // Only process orders with "pending" status
                if (status.equalsIgnoreCase("pending")) {
                    // Extract the total price from the "Total Price" field
                    int itemTotalPrice = Integer.parseInt(totalAmount.trim());

                    // Add this item to the map for the given order number
                    StringBuilder orderItems = ordersMap.getOrDefault(orderNumber, new StringBuilder());
                    orderItems.append(String.format("|| %-13s || %-13s || %-27s || %-27s || %-13s ||\n", 
                            "", quantity, itemName, totalAmount + " PHP", ""));
                    ordersMap.put(orderNumber, orderItems);

                    // Add total price to the order
                    totalPriceMap.put(orderNumber, totalPriceMap.getOrDefault(orderNumber, 0) + itemTotalPrice);
                }
            }

            // Now, print the orders in the desired format
            System.out.println("===================================================================================================================");
            System.out.println(String.format("|| %-13s || %-13s || %-27s || %-27s || %-13s ||", 
                    "Order No.", "Quantity", "Items", "Total Price By Item Qty", "Total Price"));
            System.out.println("===================================================================================================================");

            // After all data has been processed, print the results
            for (Map.Entry<Integer, StringBuilder> entry : ordersMap.entrySet()) {
                int orderNumber = entry.getKey();
                StringBuilder orderItems = entry.getValue();
                int totalPrice = totalPriceMap.get(orderNumber);

                // Print the order number and total price on the first line
                System.out.printf("|| %-13d || %-13s || %-27s || %-27s || %-13s ||\n", 
                        orderNumber, "", "", "", totalPrice + " PHP");

                // Print the items for this order
                System.out.print(orderItems.toString());

                // Print the closing line for the order
                System.out.println("===================================================================================================================");
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
    }
}
