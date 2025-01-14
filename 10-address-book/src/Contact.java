import java.io.Serializable;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;
    private String group;

    public Contact(String name, String phoneNumber, String email, String group) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.group = group;
    }

    // Getter and Setter methods
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getGroup() { return group; }
    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }
    public void setGroup(String group) { this.group = group; }
}