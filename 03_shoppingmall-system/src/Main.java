public class Main {
    public static void main(String[] args) {
        ShoppingMallSystem system = new ShoppingMallSystem();
        system.loadDataFromFile();
        system.runMenu();
    }
}