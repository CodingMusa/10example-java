import java.io.Serializable;

class Payment implements Serializable {
    private String orderId;
    private int amount;

    public Payment(String orderId, int amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    // Getter and Setter methods
    public String getOrderId() { return orderId; }
    public int getAmount() { return amount; }
}