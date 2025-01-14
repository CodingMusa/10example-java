import java.io.Serializable;

class Shipping implements Serializable {
    private String orderId;
    private String status;

    public Shipping(String orderId) {
        this.orderId = orderId;
        this.status = "PREPARING";
    }

    // Getter and Setter methods
    public String getOrderId() { return orderId; }
    public String getStatus() { return status; }
}