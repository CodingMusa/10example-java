import java.io.Serializable;

class Order implements Serializable {
    private String menuName;
    private int quantity;

    public Order(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    // Getter and Setter methods
    public String getMenuName() { return menuName; }
    public int getQuantity() { return quantity; }

    public void setMenuName(String menuName) { this.menuName = menuName; }
    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}