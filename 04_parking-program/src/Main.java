public class Main {

    public static void main(String[] args) {
        ParkingSystem system = new ParkingSystem();
        system.loadDataFromFile();
        system.runMenu();
    }
}