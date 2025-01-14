import java.io.Serializable;

class Seat implements Serializable {
    private String number;
    private boolean isReserved;

    public Seat(String number) {
        this.number = number;
        this.isReserved = false;
    }

    // Getter and Setter methods
    public String getNumber() { return number; }
    public boolean isReserved() { return isReserved; }
    public void setReserved(boolean reserved) { isReserved = reserved; }
}