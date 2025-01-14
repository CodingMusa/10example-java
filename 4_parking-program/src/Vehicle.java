import java.io.Serializable;

class Vehicle implements Serializable {
    private String number;
    private long entryTime;

    public Vehicle(String number, long entryTime) {
        this.number = number;
        this.entryTime = entryTime;
    }

    // Getter and Setter methods
    public String getNumber() { return number; }
    public long getEntryTime() { return entryTime; }
}