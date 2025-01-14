import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantSystem {
    private List<Menu> menus;
    private List<Order> orders;
    private List<User> users;
    private boolean isAdminLoggedIn;
    private String currentUser;

    public RestaurantSystem() {
        this.menus = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.users = new ArrayList<>();
        this.isAdminLoggedIn = false;
        this.currentUser = null;
    }

    public void addMenu(String name, int price, String description) {
        if (name == null || name.isEmpty() || price <= 0 || description == null || description.isEmpty()) {
            System.out.println("모든 필드는 필수이며, 가격은 양수여야 합니다.");
            return;
        }

        if (findMenuByName(name) != null) {
            System.out.println("이미 존재하는 메뉴명입니다.");
            return;
        }

        Menu menu = new Menu(name, price, description);
        menus.add(menu);
        System.out.println("메뉴 추가 완료!");
    }

    public void removeMenu(String name) {
        Menu menuToRemove = findMenuByName(name);
        if (menuToRemove != null) {
            menus.remove(menuToRemove);
            System.out.println("메뉴 삭제 완료!");
        } else {
            System.out.println("해당 메뉴를 찾을 수 없습니다.");
        }
    }

    public void displayAllMenus() {
        if (menus.isEmpty()) {
            System.out.println("등록된 메뉴가 없습니다.");
        } else {
            for (Menu menu : menus) {
                System.out.println("메뉴명: " + menu.getName());
                System.out.println("가격: " + menu.getPrice());
                System.out.println("설명: " + menu.getDescription());
                System.out.println();
            }
        }
    }

    private Menu findMenuByName(String name) {
        for (Menu menu : menus) {
            if (menu.getName().equals(name)) {
                return menu;
            }
        }
        return null;
    }

    public void placeOrder(String menuName, int quantity) {
        if (quantity <= 0) {
            System.out.println("수량은 1 이상이어야 합니다.");
            return;
        }

        Menu menu = findMenuByName(menuName);
        if (menu != null) {
            Order order = new Order(menuName, quantity);
            orders.add(order);
            System.out.println("주문 완료!");
        } else {
            System.out.println("해당 메뉴를 찾을 수 없습니다.");
        }
    }

    public void cancelOrder(String menuName) {
        Order orderToRemove = findOrderByName(menuName);
        if (orderToRemove != null) {
            orders.remove(orderToRemove);
            System.out.println("주문 취소 완료!");
        } else {
            System.out.println("해당 주문 내역을 찾을 수 없습니다.");
        }
    }

    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("주문 내역이 없습니다.");
        } else {
            for (Order order : orders) {
                System.out.println("메뉴명: " + order.getMenuName());
                System.out.println("수량: " + order.getQuantity());
                System.out.println();
            }
        }
    }

    private Order findOrderByName(String menuName) {
        for (Order order : orders) {
            if (order.getMenuName().equals(menuName)) {
                return order;
            }
        }
        return null;
    }

    public void calculateTotalPrice() {
        int totalPrice = 0;
        for (Order order : orders) {
            Menu menu = findMenuByName(order.getMenuName());
            if (menu != null) {
                totalPrice += menu.getPrice() * order.getQuantity();
            }
        }
        System.out.println("총 금액: " + totalPrice + "원");
    }

    public void processPayment() {
        calculateTotalPrice();
        orders.clear();
        System.out.println("결제 완료! 주문 내역 초기화");
    }

    public boolean login(String username, String password) {
        if ((username.equals("admin") && password.equals("password")) ||
            (findUserById(username) != null && findUserById(username).getPassword().equals(password))) {
            isAdminLoggedIn = username.equals("admin");
            currentUser = username;
            return true;
        }
        return false;
    }

    public void logout() {
        isAdminLoggedIn = false;
        currentUser = null;
    }

    public boolean isAdminLoggedIn() {
        return isAdminLoggedIn;
    }

    public boolean isUserLoggedIn() {
        return !isAdminLoggedIn && currentUser != null;
    }

    public void searchMenuByName(String name) {
        boolean found = false;
        for (Menu menu : menus) {
            if (menu.getName().contains(name)) {
                System.out.println("메뉴명: " + menu.getName());
                System.out.println("가격: " + menu.getPrice());
                System.out.println("설명: " + menu.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 메뉴를 찾을 수 없습니다.");
        }
    }

    public void searchMenuByPriceRange(int min, int max) {
        boolean found = false;
        for (Menu menu : menus) {
            if (menu.getPrice() >= min && menu.getPrice() <= max) {
                System.out.println("메뉴명: " + menu.getName());
                System.out.println("가격: " + menu.getPrice());
                System.out.println("설명: " + menu.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 가격 범위의 메뉴를 찾을 수 없습니다.");
        }
    }

    public void displayDailySales() {
        int totalSales = 0;
        for (Order order : orders) {
            Menu menu = findMenuByName(order.getMenuName());
            if (menu != null) {
                totalSales += menu.getPrice() * order.getQuantity();
            }
        }
        System.out.println("오늘의 총 매출: " + totalSales + "원");
    }

    public void displayPopularMenus() {
        int[] menuCounts = new int[menus.size()];
        for (Order order : orders) {
            int index = menus.indexOf(findMenuByName(order.getMenuName()));
            if (index != -1) {
                menuCounts[index]++;
            }
        }

        for (int i = 0; i < menus.size(); i++) {
            System.out.println(menus.get(i).getName() + ": " + menuCounts[i] + "회 주문");
        }
    }

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("restaurant_data.dat"))) {
            oos.writeObject(menus);
            oos.writeObject(orders);
            oos.writeObject(users);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("restaurant_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                menus = (List<Menu>) ois.readObject();
                orders = (List<Order>) ois.readObject();
                users = (List<User>) ois.readObject();
                System.out.println("데이터 불러오기 완료");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("데이터 불러오기 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("저장된 데이터 파일이 없습니다.");
        }
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n음식점 메뉴 주문 시스템");
            System.out.println("---------------------");
            System.out.println("1. 관리자 로그인");
            System.out.println("2. 고객 로그인");
            System.out.println("3. 고객 등록");
            System.out.println("4. 메뉴 관리 (관리자 전용)");
            System.out.println("5. 주문하기");
            System.out.println("6. 결제하기");
            System.out.println("7. 메뉴 검색");
            System.out.println("8. 통계 보기");
            System.out.println("9. 데이터 관리");
            System.out.println("10. 프로그램 종료");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    registerCustomer(scanner);
                case 4:
                    manageMenus(scanner);
                    break;
                case 5:
                    placeOrder(scanner);
                    break;
                case 6:
                    processPayment();
                    break;
                case 7:
                    searchMenu(scanner);
                    break;
                case 8:
                    displayStatistics(scanner);
                    break;
                case 9:
                    manageData(scanner);
                    break;
                case 10:
                    saveDataToFile();
                    System.exit(0);
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.print("사용자 ID: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        if (login(username, password)) {
            currentUser = username;
            System.out.println("로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }
    }

    public void registerCustomer(Scanner scanner) {
        System.out.print("아이디: ");
        String id = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        if (findUserById(id) != null) {
            System.out.println("이미 존재하는 아이디입니다.");
            return;
        }

        User newUser = new User(id, name, password);
        users.add(newUser);
        System.out.println("고객 등록 완료!");
    }

    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    private void manageMenus(Scanner scanner) {
        if (isAdminLoggedIn()) {
            System.out.println("1. 메뉴 추가");
            System.out.println("2. 메뉴 삭제");
            System.out.println("3. 메뉴 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addMenu(scanner);
                    break;
                case 2:
                    removeMenu(scanner);
                    break;
                case 3:
                    displayAllMenus();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("관리자 권한이 필요합니다.");
        }
    }

    private void addMenu(Scanner scanner) {
        System.out.print("메뉴명: ");
        String name = scanner.nextLine();
        System.out.print("가격: ");
        int price = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("설명: ");
        String description = scanner.nextLine();

        addMenu(name, price, description);
    }

    private void removeMenu(Scanner scanner) {
        System.out.print("삭제할 메뉴명: ");
        String name = scanner.nextLine();
        removeMenu(name);
    }

    private void placeOrder(Scanner scanner) {
        System.out.print("주문할 메뉴명: ");
        String menuName = scanner.nextLine();
        System.out.print("수량: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        placeOrder(menuName, quantity);
    }

    private void searchMenu(Scanner scanner) {
        System.out.println("1. 메뉴명으로 검색");
        System.out.println("2. 가격 범위로 검색");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                System.out.print("검색할 메뉴명: ");
                String name = scanner.nextLine();
                searchMenuByName(name);
                break;
            case 2:
                System.out.print("최소 가격: ");
                int min = scanner.nextInt();
                System.out.print("최대 가격: ");
                int max = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                searchMenuByPriceRange(min, max);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void displayStatistics(Scanner scanner) {
        System.out.println("1. 일일 매출 통계");
        System.out.println("2. 인기 메뉴 통계");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                displayDailySales();
                break;
            case 2:
                displayPopularMenus();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void manageData(Scanner scanner) {
        System.out.println("1. 데이터 저장");
        System.out.println("2. 데이터 불러오기");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                saveDataToFile();
                break;
            case 2:
                loadDataFromFile();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private <T> T findItemById(List<T> list, String id) {
        for (T item : list) {
            if (item instanceof Menu && ((Menu)item).getName().equals(id)) {
                return item;
            } else if (item instanceof Order && ((Order)item).getMenuName().equals(id)) {
                return item;
            } else if (item instanceof User && ((User)item).getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    private void displayItems(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("등록된 항목이 없습니다.");
        } else {
            for (Object item : list) {
                if (item instanceof Menu) {
                    Menu menu = (Menu) item;
                    System.out.println("메뉴명: " + menu.getName());
                    System.out.println("가격: " + menu.getPrice());
                    System.out.println("설명: " + menu.getDescription());
                } else if (item instanceof Order) {
                    Order order = (Order) item;
                    System.out.println("메뉴명: " + order.getMenuName());
                    System.out.println("수량: " + order.getQuantity());
                } else if (item instanceof User) {
                    User user = (User) item;
                    System.out.println("ID: " + user.getId());
                    System.out.println("이름: " + user.getName());
                }
                System.out.println();
            }
        }
    }
}