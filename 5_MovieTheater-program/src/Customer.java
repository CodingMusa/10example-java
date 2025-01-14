import java.io.Serializable;

class Customer implements Serializable {
    private String id;
    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter and Setter methods
    public String getId() { return id; }
    public String getName() { return name; }
}
