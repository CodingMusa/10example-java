import java.io.Serializable;

class Order implements Serializable {
    private String id;
    private String customerId;
    private String productId;
    private String status;

    public Order(String id, String customerId, String productId) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.status = "ORDERED";
    }

    // Getter and Setter methods
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getProductId() { return productId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}