package ORDER_SYSTEM;

public class DiningOption {
    private String option;

    // Constructor to set the dining option
    public DiningOption(String option) {
        if ("Dine In".equalsIgnoreCase(option) || "Take Out".equalsIgnoreCase(option)) {
            this.option = option;
        } else {
            throw new IllegalArgumentException("Invalid dining option. Choose 'Dine In' or 'Take Out'.");
        }
    }

    public String getOption() {
        return option;
    }
}
