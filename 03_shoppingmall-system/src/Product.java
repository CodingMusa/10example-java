import java.io.Serializable;

class Product implements Serializable {
    private String id;
    private String name;
    private int price;
    private int stock;

    public Product(String id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter and Setter methods
    public String getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}