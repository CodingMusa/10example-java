import java.io.Serializable;

class Income implements Serializable {
    private String date;
    private double amount;
    private String category;

    public Income(String date, double amount, String category) {
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    // Getter and Setter methods
    public String getDate() { return date; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
}