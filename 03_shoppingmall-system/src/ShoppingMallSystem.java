import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingMallSystem {
    private List<Product> products;
    private List<Customer> customers;
    private List<Order> orders;
    private List<Payment> payments;
    private List<Shipping> shippings;
    private boolean isLoggedIn;
    private String currentUser;

    public ShoppingMallSystem() {
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.shippings = new ArrayList<>();
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void addProduct(String id, String name, int price, int stock) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty() || price <= 0 || stock < 0) {
            System.out.println("모든 필드는 필수이며, 가격과 재고는 양수여야 합니다.");
            return;
        }

        Product product = new Product(id, name, price, stock);
        products.add(product);
        System.out.println("제품 추가 완료!");
    }

    public void removeProduct(String id) {
        Product productToRemove = findProductById(id);
        if (productToRemove != null) {
            products.remove(productToRemove);
            System.out.println("제품 삭제 완료!");
        } else {
            System.out.println("해당 제품을 찾을 수 없습니다.");
        }
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("등록된 제품이 없습니다.");
        } else {
            for (Product product : products) {
                System.out.println("제품 ID: " + product.getId());
                System.out.println("제품명: " + product.getName());
                System.out.println("가격: " + product.getPrice() + "원");
                System.out.println("재고: " + product.getStock() + "개");
                System.out.println();
            }
        }
    }

    public void registerCustomer(String id, String name) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        Customer customer = new Customer(id, name);
        customers.add(customer);
        System.out.println("고객 등록 완료!");
    }

    public void removeCustomer(String id) {
        Customer customerToRemove = findCustomerById(id);
        if (customerToRemove != null) {
            customers.remove(customerToRemove);
            System.out.println("고객 삭제 완료!");
        } else {
            System.out.println("해당 고객을 찾을 수 없습니다.");
        }
    }

    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("등록된 고객이 없습니다.");
        } else {
            for (Customer customer : customers) {
                System.out.println("고객 ID: " + customer.getId());
                System.out.println("이름: " + customer.getName());
                System.out.println();
            }
        }
    }

    public void createOrder(String orderId, String customerId, String productId) {
        Customer customer = findCustomerById(customerId);
        Product product = findProductById(productId);

        if (customer != null && product != null && product.getStock() > 0) {
            Order order = new Order(orderId, customerId, productId);
            orders.add(order);
            product.setStock(product.getStock() - 1);
            System.out.println("주문 생성 완료!");
        } else if (customer == null) {
            System.out.println("해당 고객을 찾을 수 없습니다.");
        } else if (product == null) {
            System.out.println("해당 제품을 찾을 수 없습니다.");
        } else {
            System.out.println("제품 재고가 부족합니다.");
        }
    }

    public void displayAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("등록된 주문이 없습니다.");
        } else {
            for (Order order : orders) {
                System.out.println("주문번호: " + order.getId());
                System.out.println("고객 ID: " + order.getCustomerId());
                System.out.println("제품 ID: " + order.getProductId());
                System.out.println("주문 상태: " + order.getStatus());
                System.out.println();
            }
        }
    }

    public void updateOrderStatus(String orderId, String newStatus) {
        Order order = findOrderById(orderId);
        if (order != null) {
            order.setStatus(newStatus);
            System.out.println("주문 상태 업데이트됨: " + newStatus);
        } else {
            System.out.println("해당 주문번호를 찾을 수 없습니다.");
        }
    }

    public void addPayment(String orderId, int amount) {
        Payment payment = new Payment(orderId, amount);
        payments.add(payment);
        System.out.println("결제 정보 추가 완료!");
    }

    public void displayPaymentInfo(String orderId) {
        Payment payment = findPaymentByOrderId(orderId);
        if (payment != null) {
            System.out.println("주문번호: " + payment.getOrderId());
            System.out.println("결제 금액: " + payment.getAmount() + "원");
        } else {
            System.out.println("해당 주문번호의 결제 정보를 찾을 수 없습니다.");
        }
    }

    public void addShippingInfo(String orderId) {
        Shipping shipping = new Shipping(orderId);
        shippings.add(shipping);
        System.out.println("배송 정보 추가 완료!");
    }

    public void displayShippingInfo(String orderId) {
        Shipping shipping = findShippingByOrderId(orderId);
        if (shipping != null) {
            System.out.println("주문번호: " + shipping.getOrderId());
            System.out.println("배송 상태: " + shipping.getStatus());
        } else {
            System.out.println("해당 주문번호의 배송 정보를 찾을 수 없습니다.");
        }
    }

    public void searchProductByName(String name) {
        boolean found = false;
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("제품 ID: " + product.getId());
                System.out.println("제품명: " + product.getName());
                System.out.println("가격: " + product.getPrice() + "원");
                System.out.println("재고: " + product.getStock() + "개");
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void searchCustomerByName(String name) {
        boolean found = false;
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(name.toLowerCase())) {
                System.out.println("고객 ID: " + customer.getId());
                System.out.println("이름: " + customer.getName());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void searchOrderById(String orderId) {
        Order order = findOrderById(orderId);
        if (order != null) {
            System.out.println("주문번호: " + order.getId());
            System.out.println("고객 ID: " + order.getCustomerId());
            System.out.println("제품 ID: " + order.getProductId());
            System.out.println("주문 상태: " + order.getStatus());
        } else {
            System.out.println("해당 주문번호를 찾을 수 없습니다.");
        }
    }

    public boolean login(String username, String password) {
        if ((username.equals("admin") && password.equals("password")) ||
                (username.equals(currentUser) && password.equals("12345"))) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public void logout() {
        isLoggedIn = false;
        currentUser = null;
    }

    public boolean isAdminLoggedIn() {
        return isLoggedIn && currentUser.equals("admin");
    }

    public boolean isUserLoggedIn() {
        return isLoggedIn && !currentUser.equals("admin");
    }

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mall_data.dat"))) {
            oos.writeObject(products);
            oos.writeObject(customers);
            oos.writeObject(orders);
            oos.writeObject(payments);
            oos.writeObject(shippings);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("mall_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                products = (List<Product>) ois.readObject();
                customers = (List<Customer>) ois.readObject();
                orders = (List<Order>) ois.readObject();
                payments = (List<Payment>) ois.readObject();
                shippings = (List<Shipping>) ois.readObject();
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
            System.out.println("\n온라인 쇼핑몰 주문 처리 시스템");
            System.out.println("-------------------------------");
            System.out.println("1. 로그인 (관리자)");
            System.out.println("2. 로그인 (일반 사용자)");
            System.out.println("3. 제품 관리");
            System.out.println("4. 고객 관리");
            System.out.println("5. 주문 관리");
            System.out.println("6. 결제 관리");
            System.out.println("7. 배송 관리");
            System.out.println("8. 검색");
            System.out.println("9. 내 주문 정보 보기 (로그인 후에만 사용 가능)");
            System.out.println("10. 프로그램 종료");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    loginAdmin(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    manageProducts(scanner);
                    break;
                case 4:
                    manageCustomers(scanner);
                    break;
                case 5:
                    manageOrders(scanner);
                    break;
                case 6:
                    managePayments(scanner);
                    break;
                case 7:
                    manageShippings(scanner);
                    break;
                case 8:
                    search(scanner);
                    break;
                case 9:
                    displayUserOrders();
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

    private void loginAdmin(Scanner scanner) {
        System.out.print("관리자 ID: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        if (login(username, password)) {
            currentUser = "admin";
            System.out.println("관리자 로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }
    }

    private void loginUser(Scanner scanner) {
        System.out.print("사용자 이름: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        if (login(username, password)) {
            currentUser = username;
            System.out.println("사용자 로그인 성공");
        } else {
            System.out.println("로그인 실패");
        }
    }

    private void manageProducts(Scanner scanner) {
        while (true) {
            System.out.println("\n제품 관리 메뉴");
            System.out.println("1. 제품 추가");
            System.out.println("2. 제품 삭제");
            System.out.println("3. 제품 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("제품 ID: ");
                    String id = scanner.nextLine();
                    System.out.print("제품명: ");
                    String name = scanner.nextLine();
                    System.out.print("가격: ");
                    int price = scanner.nextInt();
                    System.out.print("재고: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    addProduct(id, name, price, stock);
                    break;
                case 2:
                    System.out.print("삭제할 제품의 ID: ");
                    String idToDelete = scanner.nextLine();
                    removeProduct(idToDelete);
                    break;
                case 3:
                    displayAllProducts();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageCustomers(Scanner scanner) {
        while (true) {
            System.out.println("\n고객 관리 메뉴");
            System.out.println("1. 고객 등록");
            System.out.println("2. 고객 삭제");
            System.out.println("3. 고객 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("고객 ID: ");
                    String id = scanner.nextLine();
                    System.out.print("이름: ");
                    String name = scanner.nextLine();
                    registerCustomer(id, name);
                    break;
                case 2:
                    System.out.print("삭제할 고객의 ID: ");
                    String idToDelete = scanner.nextLine();
                    removeCustomer(idToDelete);
                    break;
                case 3:
                    displayAllCustomers();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageOrders(Scanner scanner) {
        while (true) {
            System.out.println("\n주문 관리 메뉴");
            System.out.println("1. 주문 생성");
            System.out.println("2. 주문 목록 보기");
            System.out.println("3. 주문 상태 변경");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("주문번호: ");
                    String orderId = scanner.nextLine();
                    System.out.print("고객 ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("제품 ID: ");
                    String productId = scanner.nextLine();
                    createOrder(orderId, customerId, productId);
                    break;
                case 2:
                    displayAllOrders();
                    break;
                case 3:
                    System.out.print("주문번호: ");
                    String orderIdToUpdate = scanner.nextLine();
                    System.out.print("새로운 상태: ");
                    String newStatus = scanner.nextLine();
                    updateOrderStatus(orderIdToUpdate, newStatus);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void managePayments(Scanner scanner) {
        while (true) {
            System.out.println("\n결제 관리 메뉴");
            System.out.println("1. 결제 정보 추가");
            System.out.println("2. 결제 정보 보기");
            System.out.println("3. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("주문번호: ");
                    String orderId = scanner.nextLine();
                    System.out.print("결제 금액: ");
                    int amount = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    addPayment(orderId, amount);
                    break;
                case 2:
                    System.out.print("주문번호: ");
                    String orderIdToDisplay = scanner.nextLine();
                    displayPaymentInfo(orderIdToDisplay);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageShippings(Scanner scanner) {
        while (true) {
            System.out.println("\n배송 관리 메뉴");
            System.out.println("1. 배송 정보 추가");
            System.out.println("2. 배송 정보 보기");
            System.out.println("3. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("주문번호: ");
                    String orderId = scanner.nextLine();
                    addShippingInfo(orderId);
                    break;
                case 2:
                    System.out.print("주문번호: ");
                    String orderIdToDisplay = scanner.nextLine();
                    displayShippingInfo(orderIdToDisplay);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void search(Scanner scanner) {
        while (true) {
            System.out.println("\n검색 메뉴");
            System.out.println("1. 제품명으로 검색");
            System.out.println("2. 고객명으로 검색");
            System.out.println("3. 주문번호로 검색");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("검색할 제품명: ");
                    String productName = scanner.nextLine();
                    searchProductByName(productName);
                    break;
                case 2:
                    System.out.print("검색할 고객명: ");
                    String customerName = scanner.nextLine();
                    searchCustomerByName(customerName);
                    break;
                case 3:
                    System.out.print("검색할 주문번호: ");
                    String orderId = scanner.nextLine();
                    searchOrderById(orderId);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void displayUserOrders() {
        if (!isUserLoggedIn()) {
            System.out.println("로그인 후에만 사용 가능합니다.");
            return;
        }

        int orderCount = 0;
        for (Order order : orders) {
            if (order.getCustomerId().equals(currentUser)) {
                System.out.println("주문번호: " + order.getId());
                System.out.println("제품 ID: " + order.getProductId());
                System.out.println("주문 상태: " + order.getStatus());
                System.out.println();
                orderCount++;
            }
        }
        System.out.println(orderCount + "건의 주문이 있습니다.");
    }

    private <T> T findItemById(List<T> list, String id) {
        for (T item : list) {
            if (item instanceof Product && ((Product)item).getId().equals(id)) {
                return item;
            } else if (item instanceof Customer && ((Customer)item).getId().equals(id)) {
                return item;
            } else if (item instanceof Order && ((Order)item).getId().equals(id)) {
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
                if (item instanceof Product) {
                    Product product = (Product) item;
                    System.out.println("제품 ID: " + product.getId());
                    System.out.println("제품명: " + product.getName());
                    System.out.println("가격: " + product.getPrice() + "원");
                    System.out.println("재고: " + product.getStock() + "개");
                } else if (item instanceof Customer) {
                    Customer customer = (Customer) item;
                    System.out.println("고객 ID: " + customer.getId());
                    System.out.println("이름: " + customer.getName());
                } else if (item instanceof Order) {
                    Order order = (Order) item;
                    System.out.println("주문번호: " + order.getId());
                    System.out.println("고객 ID: " + order.getCustomerId());
                    System.out.println("제품 ID: " + order.getProductId());
                    System.out.println("주문 상태: " + order.getStatus());
                }
                System.out.println();
            }
        }
    }

    public Payment findPaymentByOrderId(String orderId) {
        for (Payment payment : payments) {
            if (payment.getOrderId().equals(orderId)) {
                return payment;
            }
        }
        return null;
    }

    public Shipping findShippingByOrderId(String orderId) {
        for (Shipping shipping : shippings) {
            if (shipping.getOrderId().equals(orderId)) {
                return shipping;
            }
        }
        return null;
    }

    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Customer findCustomerById(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public Order findOrderById(String id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

}