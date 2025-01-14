public class Main {

    public static void main(String[] args) {
        MovieTheaterSystem system = new MovieTheaterSystem();
        system.loadDataFromFile();
        system.runMenu();
    }
}