import java.io.Serializable;

class Account implements Serializable {
    private String id;
    private String name;
    private double balance;

    public Account(String id, String name, double initialBalance) {
        this.id = id;
        this.name = name;
        this.balance = initialBalance;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}