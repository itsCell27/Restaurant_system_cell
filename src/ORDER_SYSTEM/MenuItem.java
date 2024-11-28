package ORDER_SYSTEM;

public class MenuItem {
    private String name;
    private int price;
    private String category;

    // Constructor
    public MenuItem(String name, int price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return name + " - " + price + " PHP";
    }
}
