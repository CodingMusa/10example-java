import java.io.Serializable;

class Category implements Serializable {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    // Getter and Setter methods
    public String getName() { return name; }
}