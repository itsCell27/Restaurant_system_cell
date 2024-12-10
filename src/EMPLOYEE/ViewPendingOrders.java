package EMPLOYEE;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ViewPendingOrders {

    // Custom class to hold order details
    static class Order {
        int orderNumber;
        List<OrderItem> items = new ArrayList<>();
        
        // Add item to the order
        void addItem(OrderItem item) {
            items.add(item);
        }
        
        // Calculate the total price of the order
        BigDecimal getTotalPrice() {
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderItem item : items) {
                totalPrice = totalPrice.add(item.totalAmount);
            }
            return totalPrice;
        }
    }

    // Custom class to hold individual order item details
    static class OrderItem {
        int quantity;
        String itemName;
        BigDecimal totalAmount;

        OrderItem(int quantity, String itemName, BigDecimal totalAmount) {
            this.quantity = quantity;
            this.itemName = itemName;
            this.totalAmount = totalAmount;
        }
    }

    public static void displayPendingOrders() {
        Scanner scanner = new Scanner(System.in);
        String csvFile = "OrderRecords/order_summary.csv";  // Path to your CSV file
        String line;
        String cvsSplitBy = ",";

        // A map to hold the orders, with the order number as the key
        // Use TreeMap with reverseOrder() to sort the order numbers in descending order
        Map<Integer, Order> ordersMap = new TreeMap<>(Collections.reverseOrder());  // Sorts order numbers in descending order

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
                String orderNumberStr = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmountStr = orderDetails[3].trim();  // This is the total price for the quantity
                String status = orderDetails[6].trim();         // Order status

                // Validate and parse order number
                int orderNumber = 0;
                if (!orderNumberStr.isEmpty()) {
                    try {
                        orderNumber = Integer.parseInt(orderNumberStr);
                    } catch (NumberFormatException e) {
                        System.out.println("\t\t\tInvalid order number: " + orderNumberStr);
                        continue;  // Skip rows with invalid order number
                    }
                }

                // Only process orders with "pending" status
                if (status.equalsIgnoreCase("pending")) {
                    // Convert total amount to BigDecimal for more precise handling
                    BigDecimal itemTotalPrice = new BigDecimal(totalAmountStr.trim());

                    // Get the order, or create a new one if it doesn't exist
                    Order order = ordersMap.getOrDefault(orderNumber, new Order());
                    order.orderNumber = orderNumber;  // Set the order number (in case it's a new order)

                    // Add item to the order
                    order.addItem(new OrderItem(quantity, itemName, itemTotalPrice));

                    // Put the order back into the map with the order number as key
                    ordersMap.put(orderNumber, order);
                }
            }

            // Print header
            System.out.println("\t\t\t===============================================================================================================");
            System.out.println(String.format("\t\t\t| %-13s  %-13s  %-27s  %-27s  %-19s |", 
                    "Order No.", "Quantity", "Items", "Total Price By Item Qty", "Total Price"));
            System.out.println("\t\t\t===============================================================================================================");

            // After all data has been processed, print everything in the unified format
            for (Order order : ordersMap.values()) {
                BigDecimal totalPrice = order.getTotalPrice();

                // Print the order details along with its items in a unified format
                for (OrderItem item : order.items) {
                    System.out.printf("\t\t\t| %-13d  %-13d  %-27s  %-27s  %-19s |\n", 
                            order.orderNumber, item.quantity, item.itemName, 
                            item.totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP) + " PHP", 
                            (item == order.items.get(order.items.size() - 1) ? totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP) + " PHP" : ""));
                }

                // Print the closing line for the order
                System.out.println("\t\t\t===============================================================================================================");
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the CSV file: " + e.getMessage());
        }

        System.out.print("\n\n\t\t\tPress Enter to continue");
        scanner.nextLine(); // Consume newline
 
    }
}
