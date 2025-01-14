import java.io.Serializable;

class Menu implements Serializable {
    private String name;
    private int price;
    private String description;

    public Menu(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getter and Setter methods
    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = name; }
    public void setPrice(int price) {
        if (price > 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }
    public void setDescription(String description) { this.description = description; }
}