import java.io.Serializable;

class Budget implements Serializable {
    private double amount;

    public Budget(double amount) {
        this.amount = amount;
    }

    // Getter and Setter methods
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}