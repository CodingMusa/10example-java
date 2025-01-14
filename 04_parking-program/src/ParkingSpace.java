import java.io.Serializable;

class ParkingSpace implements Serializable {
    private int number;
    private boolean isOccupied;
    private String vehicleNumber;

    public ParkingSpace(int number) {
        this.number = number;
        this.isOccupied = false;
        this.vehicleNumber = null;
    }

    // Getter and Setter methods
    public int getNumber() { return number; }
    public boolean isOccupied() { return isOccupied; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
}