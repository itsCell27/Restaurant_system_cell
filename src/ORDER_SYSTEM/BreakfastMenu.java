package ORDER_SYSTEM;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//Enhanced BreakfastMenu class with error handling
public class BreakfastMenu {

 private static final String FILE_PATH = "MenuItems/menu.csv"; // Hardcoded file path

 public List<MenuItem> readMenuItems() {
     List<MenuItem> menuItems = new ArrayList<>();
     File file = new File(FILE_PATH);

     if (!file.exists()) {
         System.out.println("\t\t\tError: File not found at " + FILE_PATH);
         return menuItems;
     }

     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
         String line;
         br.readLine(); // Skip header

         while ((line = br.readLine()) != null) {
             String[] data = line.split(",");
             if (data.length == 3) {
                 try {
                     String name = data[0].trim();
                     double price = Double.parseDouble(data[1].trim());
                     String category = data[2].trim();
                     menuItems.add(new MenuItem(name, price, category));
                 } catch (NumberFormatException e) {
                     System.out.println("\t\t\tWarning: Invalid price format in line: " + line);
                 }
             } else {
                 System.out.println("\t\t\tWarning: Invalid data format in line: " + line);
             }
         }
     } catch (IOException e) {
         System.out.println("\t\t\tError reading file: " + e.getMessage());
     }

     return menuItems;
 }

 public void displayMenu(Scanner scanner, HandleMyOrder handleOrder) {
     List<MenuItem> menuItems = readMenuItems();
     List<MenuItem> breakfastMenu = menuItems.stream()
             .filter(item -> item.getCategory().equalsIgnoreCase("Breakfast"))
             .collect(Collectors.toList());

     if (breakfastMenu.isEmpty()) {
         System.out.println("\t\t\tNo items found in the Breakfast menu.");
         System.out.println("\t\t\tPress ENTER to continue...");
         scanner.nextLine();
         return;
     }

     System.out.println("\t\t\t\t\t\tBREAKFAST MENU\n");
     System.out.println("\t\t\t===================================================================");
     System.out.printf("\t\t\t| %-5s | %-36s | %-16s |\n", "No.", "Item", "Price");
     System.out.println("\t\t\t===================================================================");
     for (int i = 0; i < breakfastMenu.size(); i++) {
         MenuItem item = breakfastMenu.get(i);
         System.out.printf("\t\t\t| %-5d | %-36s | %-16.2f |\n",
                 i + 1, item.getName(), item.getPrice());
     }
     System.out.println("\t\t\t===================================================================");

     int itemChoice = -1;
     while (itemChoice < 0 || itemChoice > breakfastMenu.size()) {
         System.out.print("\t\t\tPlease select an option (1-" + breakfastMenu.size() + "), input 0 to go back: ");
         try {
             itemChoice = scanner.nextInt();
             if (itemChoice == 0) {
                 return; // Go back to the main menu
             } else if (itemChoice < 1 || itemChoice > breakfastMenu.size()) {
                 System.out.println("\t\t\tInvalid choice. Please try again.");
             }
         } catch (InputMismatchException e) {
        	 System.out.println("\t\t\tError: Please enter a valid number.");
             scanner.nextLine(); // Clear the invalid input
         }
     }

     processOrder(breakfastMenu.get(itemChoice - 1), scanner, handleOrder);
 }

 private void processOrder(MenuItem item, Scanner scanner, HandleMyOrder handleOrder) {
     int quantity = -1;
     while (quantity < 1) {
         System.out.print("\t\t\tEnter the quantity for this item: ");
         try {
             quantity = scanner.nextInt();
             if (quantity < 1) {
                 System.out.println("\t\t\tQuantity must be at least 1. Please try again.");
             }
         } catch (InputMismatchException e) {
             System.out.println("\t\t\tInvalid input. Please enter a valid number.");
             scanner.next(); // Clear invalid input
         }
     }

     double totalPrice = item.getPrice() * quantity;

     int saveOrder = -1;
     while (saveOrder != 0 && saveOrder != 1) {
         System.out.print("\t\t\tDo you want to save this order? (1 for Yes, 0 for No): ");
         try {
             saveOrder = scanner.nextInt();
             if (saveOrder != 0 && saveOrder != 1) {
                 System.out.println("\t\t\tInvalid choice. Please enter 1 or 0.");
             }
         } catch (InputMismatchException e) {
             System.out.println("\t\t\tInvalid input. Please enter 1 or 0.");
             scanner.next(); // Clear invalid input
         }
     }

     if (saveOrder == 1) {
         Order order = new Order(item, quantity);
         handleOrder.addOrder(order);

         System.out.println("\n\t\t\tOrder Summary:");
         System.out.println("\t\t\tItem: " + item.getName());
         System.out.println("\t\t\tQuantity: " + quantity);
         System.out.println("\t\t\tPrice: " + String.format("%.2f", totalPrice) + " PHP");
         System.out.println("\n\t\t\tTotal Price: " + String.format("%.2f", totalPrice) + " PHP");
         System.out.println("\t\t\tPress Enter to continue...");
         scanner.nextLine(); // Consume newline
         scanner.nextLine(); // Wait for Enter
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
}

