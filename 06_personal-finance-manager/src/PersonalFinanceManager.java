import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonalFinanceManager {
    private List<User> users;
    private List<Income> incomes;
    private List<Expense> expenses;
    private List<Category> categories;
    private Budget budget;
    private boolean isLoggedIn;
    private String currentUser;

    public PersonalFinanceManager() {
        this.users = new ArrayList<>();
        this.incomes = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.budget = new Budget(0);
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void registerUser(String id, String name, String password) {
        if (findUserById(id) != null) {
            System.out.println("이미 존재하는 사용자 ID입니다.");
            return;
        }

        User user = new User(id, name, password);
        users.add(user);
        System.out.println("사용자 등록 완료!");
    }

    public void removeUser(String id) {
        User userToRemove = findUserById(id);
        if (userToRemove != null) {
            users.remove(userToRemove);
            System.out.println("사용자 삭제 완료!");
        } else {
            System.out.println("해당 사용자를 찾을 수 없습니다.");
        }
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("등록된 사용자가 없습니다.");
        } else {
            for (User user : users) {
                System.out.println("ID: " + user.getId());
                System.out.println("이름: " + user.getName());
                System.out.println();
            }
        }
    }

    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void addIncome(String date, double amount, String category) {
        if (date == null || date.isEmpty() || amount <= 0 || category == null || category.isEmpty()) {
            System.out.println("모든 필드는 필수이며, 금액은 양수여야 합니다.");
            return;
        }

        if (findIncomeByDate(date) != null) {
            System.out.println("이미 해당 날짜에 수입이 등록되어 있습니다.");
            return;
        }

        Income income = new Income(date, amount, category);
        incomes.add(income);
        System.out.println("수입 추가 완료!");
    }

    public void removeIncome(String date) {
        Income incomeToRemove = findIncomeByDate(date);
        if (incomeToRemove != null) {
            incomes.remove(incomeToRemove);
            System.out.println("수입 삭제 완료!");
        } else {
            System.out.println("해당 수입을 찾을 수 없습니다.");
        }
    }

    public void displayAllIncomes() {
        if (incomes.isEmpty()) {
            System.out.println("등록된 수입이 없습니다.");
        } else {
            for (Income income : incomes) {
                System.out.println("날짜: " + income.getDate());
                System.out.println("금액: " + income.getAmount());
                System.out.println("카테고리: " + income.getCategory());
                System.out.println();
            }
        }
    }

    private Income findIncomeByDate(String date) {
        for (Income income : incomes) {
            if (income.getDate().equals(date)) {
                return income;
            }
        }
        return null;
    }

    public void addExpense(String date, double amount, String category) {
        if (date == null || date.isEmpty() || amount <= 0 || category == null || category.isEmpty()) {
            System.out.println("모든 필드는 필수이며, 금액은 양수여야 합니다.");
            return;
        }

        if (findExpenseByDate(date) != null) {
            System.out.println("이미 해당 날짜에 지출이 등록되어 있습니다.");
            return;
        }

        Expense expense = new Expense(date, amount, category);
        expenses.add(expense);
        System.out.println("지출 추가 완료!");
    }

    public void removeExpense(String date) {
        Expense expenseToRemove = findExpenseByDate(date);
        if (expenseToRemove != null) {
            expenses.remove(expenseToRemove);
            System.out.println("지출 삭제 완료!");
        } else {
            System.out.println("해당 지출을 찾을 수 없습니다.");
        }
    }

    public void displayAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("등록된 지출이 없습니다.");
        } else {
            for (Expense expense : expenses) {
                System.out.println("날짜: " + expense.getDate());
                System.out.println("금액: " + expense.getAmount());
                System.out.println("카테고리: " + expense.getCategory());
                System.out.println();
            }
        }
    }

    private Expense findExpenseByDate(String date) {
        for (Expense expense : expenses) {
            if (expense.getDate().equals(date)) {
                return expense;
            }
        }
        return null;
    }

    public void setBudget(double amount) {
        budget.setAmount(amount);
        System.out.println("예산이 " + amount + "원으로 설정되었습니다.");
    }

    public void displayBudget() {
        System.out.println("현재 예산: " + budget.getAmount() + "원");
    }

    public void checkBudget() {
        double totalExpenses = calculateTotalExpenses();
        if (totalExpenses > budget.getAmount()) {
            System.out.println("예산 초과 경고: 현재 지출 " + totalExpenses + "원, 예산 " + budget.getAmount() + "원");
        } else {
            System.out.println("예산 상태: 현재 지출 " + totalExpenses + "원, 예산 " + budget.getAmount() + "원");
        }
    }

    private double calculateTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public void addCategory(String name) {
        Category category = new Category(name);
        categories.add(category);
        System.out.println("카테고리 추가 완료!");
    }

    public void removeCategory(String name) {
        Category categoryToRemove = findCategoryByName(name);
        if (categoryToRemove != null) {
            categories.remove(categoryToRemove);
            System.out.println("카테고리 삭제 완료!");
        } else {
            System.out.println("해당 카테고리를 찾을 수 없습니다.");
        }
    }

    public void displayAllCategories() {
        if (categories.isEmpty()) {
            System.out.println("등록된 카테고리가 없습니다.");
        } else {
            for (Category category : categories) {
                System.out.println(category.getName());
            }
        }
    }

    private Category findCategoryByName(String name) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public void searchIncomeByDate(String date) {
        boolean found = false;
        for (Income income : incomes) {
            if (income.getDate().equals(date)) {
                System.out.println("날짜: " + income.getDate());
                System.out.println("금액: " + income.getAmount());
                System.out.println("카테고리: " + income.getCategory());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 날짜의 수입을 찾을 수 없습니다.");
        }
    }

    public void searchExpenseByCategory(String category) {
        boolean found = false;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                System.out.println("날짜: " + expense.getDate());
                System.out.println("금액: " + expense.getAmount());
                System.out.println("카테고리: " + expense.getCategory());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 카테고리의 지출을 찾을 수 없습니다.");
        }
    }

    public void searchIncomeByAmountRange(double min, double max) {
        boolean found = false;
        for (Income income : incomes) {
            if (income.getAmount() >= min && income.getAmount() <= max) {
                System.out.println("날짜: " + income.getDate());
                System.out.println("금액: " + income.getAmount());
                System.out.println("카테고리: " + income.getCategory());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 금액 범위의 수입을 찾을 수 없습니다.");
        }
    }

    public void calculateTotalIncomeAndExpense() {
        double totalIncome = 0;
        double totalExpense = 0;
        for (Income income : incomes) {
            totalIncome += income.getAmount();
        }
        for (Expense expense : expenses) {
            totalExpense += expense.getAmount();
        }
        System.out.println("총 수입: " + totalIncome + "원");
        System.out.println("총 지출: " + totalExpense + "원");
        System.out.println("현재 잔액: " + (totalIncome - totalExpense) + "원");
    }

    public void displayMonthlyIncomeAndExpense() {
        for (int month = 1; month <= 12; month++) {
            double monthlyIncome = 0;
            double monthlyExpense = 0;
            for (Income income : incomes) {
                if (Integer.parseInt(income.getDate().split("-")[1]) == month) {
                    monthlyIncome += income.getAmount();
                }
            }
            for (Expense expense : expenses) {
                if (Integer.parseInt(expense.getDate().split("-")[1]) == month) {
                    monthlyExpense += expense.getAmount();
                }
            }
            System.out.println(month + "월 수입: " + monthlyIncome + "원");
            System.out.println(month + "월 지출: " + monthlyExpense + "원");
            System.out.println(month + "월 잔액: " + (monthlyIncome - monthlyExpense) + "원");
            System.out.println();
        }
    }

    public void displayCategoryExpense() {
        for (Category category : categories) {
            double categoryExpense = 0;
            for (Expense expense : expenses) {
                if (expense.getCategory().equals(category.getName())) {
                    categoryExpense += expense.getAmount();
                }
            }
            System.out.println(category.getName() + " 카테고리 지출: " + categoryExpense + "원");
        }
    }

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personal_finance_data.dat"))) {
            oos.writeObject(users);
            oos.writeObject(incomes);
            oos.writeObject(expenses);
            oos.writeObject(categories);
            oos.writeObject(budget);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("personal_finance_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                users = (List<User>) ois.readObject();
                incomes = (List<Income>) ois.readObject();
                expenses = (List<Expense>) ois.readObject();
                categories = (List<Category>) ois.readObject();
                budget = (Budget) ois.readObject();
                System.out.println("데이터 불러오기 완료");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("데이터 불러오기 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("저장된 데이터 파일이 없습니다.");
        }
    }

    public boolean login(String username, String password) {
        if ((username.equals("admin") && password.equals("password")) ||
            (findUserByUsername(username) != null && findUserByUsername(username).getPassword().equals(password))) {
            isLoggedIn = true;
            currentUser = username;
            return true;
        }
        return false;
    }

    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getId().equals(username)) {
                return user;
            }
        }
        return null;
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

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n개인 금융 관리 프로그램");
            System.out.println("------------------------");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 사용자 관리");
            System.out.println("4. 수입 관리");
            System.out.println("5. 지출 관리");
            System.out.println("6. 예산 관리");
            System.out.println("7. 카테고리 관리");
            System.out.println("8. 검색");
            System.out.println("9. 통계");
            System.out.println("10. 데이터 저장 및 불러오기");
            System.out.println("11. 프로그램 종료");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    registerUser(scanner);
                    break;
                case 3:
                    manageUsers(scanner);
                    break;
                case 4:
                    manageIncomes(scanner);
                    break;
                case 5:
                    manageExpenses(scanner);
                    break;
                case 6:
                    manageBudget(scanner);
                    break;
                case 7:
                    manageCategories(scanner);
                    break;
                case 8:
                    search(scanner);
                    break;
                case 9:
                    displayStatistics(scanner);
                    break;
                case 10:
                    manageData(scanner);
                    break;
                case 11:
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

    private void registerUser(Scanner scanner) {
        System.out.print("사용자 ID: ");
        String id = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        registerUser(id, name, password);
    }


    private void manageUsers(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 사용자 추가");
            System.out.println("2. 사용자 삭제");
            System.out.println("3. 사용자 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    removeUser(scanner);
                    break;
                case 3:
                    displayAllUsers();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void removeUser(Scanner scanner) {
        System.out.print("삭제할 사용자의 ID: ");
        String id = scanner.nextLine();
        removeUser(id);
    }

    private void manageIncomes(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 수입 추가");
            System.out.println("2. 수입 삭제");
            System.out.println("3. 수입 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addIncome(scanner);
                    break;
                case 2:
                    removeIncome(scanner);
                    break;
                case 3:
                    displayAllIncomes();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addIncome(Scanner scanner) {
        System.out.print("날짜 (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("금액: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("카테고리: ");
        String category = scanner.nextLine();

        addIncome(date, amount, category);
    }

    private void removeIncome(Scanner scanner) {
        System.out.print("삭제할 수입의 날짜 (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        removeIncome(date);
    }

    private void manageExpenses(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 지출 추가");
            System.out.println("2. 지출 삭제");
            System.out.println("3. 지출 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    removeExpense(scanner);
                    break;
                case 3:
                    displayAllExpenses();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addExpense(Scanner scanner) {
        System.out.print("날짜 (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("금액: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("카테고리: ");
        String category = scanner.nextLine();

        addExpense(date, amount, category);
    }

    private void removeExpense(Scanner scanner) {
        System.out.print("삭제할 지출의 날짜 (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        removeExpense(date);
    }

    private void manageBudget(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 예산 설정");
            System.out.println("2. 예산 조회");
            System.out.println("3. 예산 확인");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    setBudget(scanner);
                    break;
                case 2:
                    displayBudget();
                    break;
                case 3:
                    checkBudget();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void setBudget(Scanner scanner) {
        System.out.print("예산 금액: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        setBudget(amount);
    }

    private void manageCategories(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 카테고리 추가");
            System.out.println("2. 카테고리 삭제");
            System.out.println("3. 카테고리 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addCategory(scanner);
                    break;
                case 2:
                    removeCategory(scanner);
                    break;
                case 3:
                    displayAllCategories();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addCategory(Scanner scanner) {
        System.out.print("카테고리 이름: ");
        String name = scanner.nextLine();
        addCategory(name);
    }

    private void removeCategory(Scanner scanner) {
        System.out.print("삭제할 카테고리 이름: ");
        String name = scanner.nextLine();
        removeCategory(name);
    }

    private void search(Scanner scanner) {
        System.out.println("1. 수입 날짜로 검색");
        System.out.println("2. 지출 카테고리로 검색");
        System.out.println("3. 수입 금액 범위로 검색");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                searchIncomeByDate(scanner);
                break;
            case 2:
                searchExpenseByCategory(scanner);
                break;
            case 3:
                searchIncomeByAmountRange(scanner);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void searchIncomeByDate(Scanner scanner) {
        System.out.print("검색할 날짜 (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        searchIncomeByDate(date);
    }

    private void searchExpenseByCategory(Scanner scanner) {
        System.out.print("검색할 카테고리: ");
        String category = scanner.nextLine();
        searchExpenseByCategory(category);
    }

    private void searchIncomeByAmountRange(Scanner scanner) {
        System.out.print("최소 금액: ");
        double min = scanner.nextDouble();
        System.out.print("최대 금액: ");
        double max = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        searchIncomeByAmountRange(min, max);
    }

    private void displayStatistics(Scanner scanner) {
        System.out.println("1. 총 수입/지출 계산");
        System.out.println("2. 월별 수입/지출 통계");
        System.out.println("3. 카테고리별 지출 통계");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                calculateTotalIncomeAndExpense();
                break;
            case 2:
                displayMonthlyIncomeAndExpense();
                break;
            case 3:
                displayCategoryExpense();
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
            if (item instanceof User && ((User)item).getId().equals(id)) {
                return item;
            } else if (item instanceof Income && ((Income)item).getDate().equals(id)) {
                return item;
            } else if (item instanceof Expense && ((Expense)item).getDate().equals(id)) {
                return item;
            } else if (item instanceof Category && ((Category)item).getName().equals(id)) {
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
                if (item instanceof User) {
                    User user = (User) item;
                    System.out.println("ID: " + user.getId());
                    System.out.println("이름: " + user.getName());
                } else if (item instanceof Income) {
                    Income income = (Income) item;
                    System.out.println("날짜: " + income.getDate());
                    System.out.println("금액: " + income.getAmount());
                    System.out.println("카테고리: " + income.getCategory());
                } else if (item instanceof Expense) {
                    Expense expense = (Expense) item;
                    System.out.println("날짜: " + expense.getDate());
                    System.out.println("금액: " + expense.getAmount());
                    System.out.println("카테고리: " + expense.getCategory());
                } else if (item instanceof Category) {
                    Category category = (Category) item;
                    System.out.println("카테고리 이름: " + category.getName());
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        PersonalFinanceManager manager = new PersonalFinanceManager();
        manager.runMenu();
    }
}