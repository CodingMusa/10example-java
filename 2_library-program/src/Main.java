public class Main {
    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.loadDataFromFile();
        librarySystem.runMenu();
    }
}