package ORDER_SYSTEM;

public class Order {
    private MenuItem item;
    private int quantity;

    public Order(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    // Update getTotalAmount to handle double
    public double getTotalAmount() {
        return item.getPrice() * quantity; // Assuming item.getPrice() returns a double
    }

    public void displaySummary() {
        System.out.println("\t\t\tOrder Summary:");
        System.out.println("\t\t\tItem: " + item.getName());
        System.out.println("\t\t\tQuantity: " + quantity);
        System.out.println("\t\t\tPrice: " + String.format("%.2f", getTotalAmount()) + " PHP");
    }
}
