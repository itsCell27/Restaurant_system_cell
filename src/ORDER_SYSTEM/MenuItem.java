package ORDER_SYSTEM;

public class MenuItem {
    private String name;
    private double price; // Changed to double to handle decimal values
    private String category;

    // Constructor
    public MenuItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + "                  " + String.format("%.2f", price) + " PHP"; // Format to 2 decimal places
    }
}
