import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Group implements Serializable {
    private String name;
    private List<Contact> contacts;

    public Group(String name) {
        this.name = name;
        this.contacts = new ArrayList<>();
    }

    // Getter and Setter methods
    public String getName() { return name; }
    public List<Contact> getContacts() { return contacts; }
}
