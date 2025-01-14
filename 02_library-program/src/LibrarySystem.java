import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private List<Book> books;
    private List<Member> members;
    private boolean isLoggedIn;
    private String currentUser;

    public LibrarySystem() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void addBook(String title, String author, String isbn) {
        if (title == null || title.isEmpty() || author == null || author.isEmpty() || isbn == null || isbn.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        Book book = new Book(title, author, isbn);
        books.add(book);
        System.out.println("책 추가 완료!");
    }

    public void removeBook(String isbn) {
        Book bookToRemove = findBookByIsbn(isbn);
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("책 삭제 완료!");
        } else {
            System.out.println("해당 책을 찾을 수 없습니다.");
        }
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("등록된 책이 없습니다.");
        } else {
            for (Book book : books) {
                System.out.println("제목: " + book.getTitle());
                System.out.println("저자: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("대출 상태: " + (book.isBorrowed() ? "대출 중" : "대출 가능"));
                System.out.println();
            }
        }
    }

    public void registerMember(String name, String memberId) {
        if (name == null || name.isEmpty() || memberId == null || memberId.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        Member member = new Member(name, memberId);
        members.add(member);
        System.out.println("회원 등록 완료!");
    }

    public void removeMember(String memberId) {
        Member memberToRemove = findMemberById(memberId);
        if (memberToRemove != null) {
            members.remove(memberToRemove);
            System.out.println("회원 삭제 완료!");
        } else {
            System.out.println("해당 회원을 찾을 수 없습니다.");
        }
    }

    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("등록된 회원이 없습니다.");
        } else {
            for (Member member : members) {
                System.out.println("이름: " + member.getName());
                System.out.println("회원 ID: " + member.getMemberId());
                System.out.println();
            }
        }
    }

    public void borrowBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);

        if (book != null && member != null && !book.isBorrowed()) {
            book.setBorrowed(true);
            book.setBorrower(member.getMemberId());
            System.out.println("책 대출 완료!");
        } else if (book == null) {
            System.out.println("해당 책을 찾을 수 없습니다.");
        } else if (member == null) {
            System.out.println("해당 회원을 찾을 수 없습니다.");
        } else {
            System.out.println("이미 대출 중인 책입니다.");
        }
    }

    public void returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book != null && book.isBorrowed()) {
            book.setBorrowed(false);
            book.setBorrower(null);
            System.out.println("책 반납 완료!");
        } else if (book == null) {
            System.out.println("해당 책을 찾을 수 없습니다.");
        } else {
            System.out.println("이 책은 현재 대출 중이 아닙니다.");
        }
    }

    public void displayBorrowedBooks() {
        int borrowedCount = 0;
        for (Book book : books) {
            if (book.isBorrowed()) {
                System.out.println("제목: " + book.getTitle());
                System.out.println("저자: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("대출자: " + book.getBorrower());
                System.out.println();
                borrowedCount++;
            }
        }
        System.out.println(borrowedCount + "권의 책이 대출 중입니다.");
    }

    public void searchBookByTitle(String title) {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println("제목: " + book.getTitle());
                System.out.println("저자: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("대출 상태: " + (book.isBorrowed() ? "대출 중" : "대출 가능"));
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void searchBookByAuthor(String author) {
        boolean found = false;
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                System.out.println("제목: " + book.getTitle());
                System.out.println("저자: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("대출 상태: " + (book.isBorrowed() ? "대출 중" : "대출 가능"));
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void searchBookByIsbn(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book != null) {
            System.out.println("제목: " + book.getTitle());
            System.out.println("저자: " + book.getAuthor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("대출 상태: " + (book.isBorrowed() ? "대출 중" : "대출 가능"));
        } else {
            System.out.println("해당 ISBN의 책을 찾을 수 없습니다.");
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

    private Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            oos.writeObject(books);
            oos.writeObject(members);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("library_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                books = (List<Book>) ois.readObject();
                members = (List<Member>) ois.readObject();
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
            System.out.println("\n도서관 대출 시스템");
            System.out.println("------------------");
            System.out.println("1. 로그인 (관리자)");
            System.out.println("2. 로그인 (일반 사용자)");
            System.out.println("3. 책 관리");
            System.out.println("4. 회원 관리");
            System.out.println("5. 대출 및 반납");
            System.out.println("6. 검색");
            System.out.println("7. 내 대출 기록 보기 (로그인 후에만 사용 가능)");
            System.out.println("8. 프로그램 종료");

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
                    manageBooks(scanner);
                    break;
                case 4:
                    manageMembers(scanner);
                    break;
                case 5:
                    manageLoans(scanner);
                    break;
                case 6:
                    searchBooks(scanner);
                    break;
                case 7:
                    displayUserLoans();
                    break;
                case 8:
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

    private void manageBooks(Scanner scanner) {
        while (true) {
            System.out.println("\n책 관리 메뉴");
            System.out.println("1. 책 추가");
            System.out.println("2. 책 삭제");
            System.out.println("3. 책 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("저자: ");
                    String author = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    addBook(title, author, isbn);
                    break;
                case 2:
                    System.out.print("삭제할 책의 ISBN: ");
                    String isbnToDelete = scanner.nextLine();
                    removeBook(isbnToDelete);
                    break;
                case 3:
                    displayAllBooks();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageMembers(Scanner scanner) {
        while (true) {
            System.out.println("\n회원 관리 메뉴");
            System.out.println("1. 회원 등록");
            System.out.println("2. 회원 삭제");
            System.out.println("3. 회원 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("이름: ");
                    String name = scanner.nextLine();
                    System.out.print("회원 ID: ");
                    String memberId = scanner.nextLine();
                    registerMember(name, memberId);
                    break;
                case 2:
                    System.out.print("삭제할 회원의 ID: ");
                    String memberIdToDelete = scanner.nextLine();
                    removeMember(memberIdToDelete);
                    break;
                case 3:
                    displayAllMembers();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageLoans(Scanner scanner) {
        while (true) {
            System.out.println("\n대출 및 반납 메뉴");
            System.out.println("1. 책 대출");
            System.out.println("2. 책 반납");
            System.out.println("3. 대출 중인 책 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("대출할 책의 ISBN: ");
                    String isbnToBorrow = scanner.nextLine();
                    System.out.print("대출자의 회원 ID: ");
                    String borrowerId = scanner.nextLine();
                    borrowBook(isbnToBorrow, borrowerId);
                    break;
                case 2:
                    System.out.print("반납할 책의 ISBN: ");
                    String isbnToReturn = scanner.nextLine();
                    returnBook(isbnToReturn);
                    break;
                case 3:
                    displayBorrowedBooks();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void searchBooks(Scanner scanner) {
        while (true) {
            System.out.println("\n검색 메뉴");
            System.out.println("1. 제목으로 검색");
            System.out.println("2. 저자로 검색");
            System.out.println("3. ISBN으로 검색");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("검색할 제목: ");
                    String titleToSearch = scanner.nextLine();
                    searchBookByTitle(titleToSearch);
                    break;
                case 2:
                    System.out.print("검색할 저자: ");
                    String authorToSearch = scanner.nextLine();
                    searchBookByAuthor(authorToSearch);
                    break;
                case 3:
                    System.out.print("검색할 ISBN: ");
                    String isbnToSearch = scanner.nextLine();
                    searchBookByIsbn(isbnToSearch);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void displayUserLoans() {
        if (!isUserLoggedIn()) {
            System.out.println("로그인 후에만 사용 가능합니다.");
            return;
        }

        int borrowedCount = 0;
        for (Book book : books) {
            if (book.isBorrowed() && book.getBorrower().equals(currentUser)) {
                System.out.println("제목: " + book.getTitle());
                System.out.println("저자: " + book.getAuthor());
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println();
                borrowedCount++;
            }
        }
        System.out.println(borrowedCount + "권의 책을 대출 중입니다.");
    }

    public static void main(String[] args) {
        LibrarySystem librarySystem = new LibrarySystem();
        librarySystem.loadDataFromFile();
        librarySystem.runMenu();
    }
}