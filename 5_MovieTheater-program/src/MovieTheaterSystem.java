import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieTheaterSystem {
    private List<Movie> movies;
    private List<ShowTime> showTimes;
    private List<Seat> seats;
    private List<Customer> customers;
    private List<Booking> bookings;
    private boolean isLoggedIn;
    private String currentUser;

    public MovieTheaterSystem() {
        this.movies = new ArrayList<>();
        this.showTimes = new ArrayList<>();
        this.seats = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void addMovie(String title, String director) {
        if (title == null || title.isEmpty() || director == null || director.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        if (findMovieByTitle(title) != null) {
            System.out.println("이미 존재하는 영화 제목입니다.");
            return;
        }

        Movie movie = new Movie(title, director);
        movies.add(movie);
        System.out.println("영화 추가 완료!");
    }

    public void removeMovie(String title) {
        Movie movieToRemove = findMovieByTitle(title);
        if (movieToRemove != null) {
            movies.remove(movieToRemove);
            System.out.println("영화 삭제 완료!");
        } else {
            System.out.println("해당 영화를 찾을 수 없습니다.");
        }
    }

    public void displayAllMovies() {
        if (movies.isEmpty()) {
            System.out.println("등록된 영화가 없습니다.");
        } else {
            for (Movie movie : movies) {
                System.out.println("제목: " + movie.getTitle());
                System.out.println("감독: " + movie.getDirector());
                System.out.println();
            }
        }
    }

    private Movie findMovieByTitle(String title) {
        for (Movie movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    public void addShowTime(String date, String time) {
        ShowTime showTime = new ShowTime(date, time);
        showTimes.add(showTime);
        System.out.println("상영 시간 추가 완료!");
    }

    public void removeShowTime(String date, String time) {
        ShowTime showTimeToRemove = findShowTime(date, time);
        if (showTimeToRemove != null) {
            showTimes.remove(showTimeToRemove);
            System.out.println("상영 시간 삭제 완료!");
        } else {
            System.out.println("해당 상영 시간을 찾을 수 없습니다.");
        }
    }

    public void displayAllShowTimes() {
        if (showTimes.isEmpty()) {
            System.out.println("등록된 상영 시간이 없습니다.");
        } else {
            for (ShowTime showTime : showTimes) {
                System.out.println("날짜: " + showTime.getDate());
                System.out.println("시간: " + showTime.getTime());
                System.out.println();
            }
        }
    }

    private ShowTime findShowTime(String date, String time) {
        for (ShowTime showTime : showTimes) {
            if (showTime.getDate().equals(date) && showTime.getTime().equals(time)) {
                return showTime;
            }
        }
        return null;
    }

    public void addSeat(String number) {
        Seat seat = new Seat(number);
        seats.add(seat);
        System.out.println("좌석 추가 완료!");
    }

    public void removeSeat(String number) {
        Seat seatToRemove = findSeatByNumber(number);
        if (seatToRemove != null) {
            seats.remove(seatToRemove);
            System.out.println("좌석 삭제 완료!");
        } else {
            System.out.println("해당 좌석을 찾을 수 없습니다.");
        }
    }

    public void displayAllSeats() {
        if (seats.isEmpty()) {
            System.out.println("등록된 좌석이 없습니다.");
        } else {
            for (Seat seat : seats) {
                System.out.println("좌석 번호: " + seat.getNumber());
                System.out.println("예약 상태: " + (seat.isReserved() ? "예약됨" : "예약 가능"));
                System.out.println();
            }
        }
    }

    private Seat findSeatByNumber(String number) {
        for (Seat seat : seats) {
            if (seat.getNumber().equals(number)) {
                return seat;
            }
        }
        return null;
    }

    public void registerCustomer(String id, String name) {
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

    private Customer findCustomerById(String id) {
        for (Customer customer : customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public void bookMovie(String movieTitle, String showTime, String seatNumber, String customerId) {
        if (movieTitle == null || movieTitle.isEmpty() || showTime == null || showTime.isEmpty() ||
            seatNumber == null || seatNumber.isEmpty() || customerId == null || customerId.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        Movie movie = findMovieByTitle(movieTitle);
        ShowTime showTimeObj = findShowTime(showTime.split(" ")[0], showTime.split(" ")[1]);
        Seat seat = findSeatByNumber(seatNumber);
        Customer customer = findCustomerById(customerId);

        if (movie != null && showTimeObj != null && seat != null && customer != null && !seat.isReserved()) {
            Booking booking = new Booking(movieTitle, showTime, seatNumber, customerId);
            bookings.add(booking);
            seat.setReserved(true);
            System.out.println("예매 완료!");
        } else {
            System.out.println("예매 실패. 영화, 상영 시간, 좌석, 고객 정보를 확인해주세요.");
        }
    }

    public void cancelBooking(String movieTitle, String showTime, String seatNumber) {
        Booking bookingToRemove = findBooking(movieTitle, showTime, seatNumber);
        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove);
            Seat seat = findSeatByNumber(seatNumber);
            seat.setReserved(false);
            System.out.println("예매 취소 완료!");
        } else {
            System.out.println("해당 예매를 찾을 수 없습니다.");
        }
    }

    public void displayAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("등록된 예매가 없습니다.");
        } else {
            for (Booking booking : bookings) {
                System.out.println("영화 제목: " + booking.getMovieTitle());
                System.out.println("상영 시간: " + booking.getShowTime());
                System.out.println("좌석 번호: " + booking.getSeatNumber());
                System.out.println("고객 ID: " + booking.getCustomerId());
                System.out.println();
            }
        }
    }

    private Booking findBooking(String movieTitle, String showTime, String seatNumber) {
        for (Booking booking : bookings) {
            if (booking.getMovieTitle().equals(movieTitle) &&
                booking.getShowTime().equals(showTime) &&
                booking.getSeatNumber().equals(seatNumber)) {
                return booking;
            }
        }
        return null;
    }

    public void searchMovieByTitle(String title) {
        boolean found = false;
        for (Movie movie : movies) {
            if (movie.getTitle().toLowerCase().contains(title.toLowerCase())) {
                System.out.println("제목: " + movie.getTitle());
                System.out.println("감독: " + movie.getDirector());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("검색 결과가 없습니다.");
        }
    }

    public void searchShowTime(String date, String time) {
        ShowTime showTime = findShowTime(date, time);
        if (showTime != null) {
            System.out.println("날짜: " + showTime.getDate());
            System.out.println("시간: " + showTime.getTime());
        } else {
            System.out.println("해당 상영 시간을 찾을 수 없습니다.");
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

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("movie_theater_data.dat"))) {
            oos.writeObject(movies);
            oos.writeObject(showTimes);
            oos.writeObject(seats);
            oos.writeObject(customers);
            oos.writeObject(bookings);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("movie_theater_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                movies = (List<Movie>) ois.readObject();
                showTimes = (List<ShowTime>) ois.readObject();
                seats = (List<Seat>) ois.readObject();
                customers = (List<Customer>) ois.readObject();
                bookings = (List<Booking>) ois.readObject();
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

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n영화 예매 시스템");
            System.out.println("----------------");
            System.out.println("1. 로그인 (관리자)");
            System.out.println("2. 로그인 (일반 사용자)");
            System.out.println("3. 영화 관리");
            System.out.println("4. 상영 시간 관리");
            System.out.println("5. 좌석 관리");
            System.out.println("6. 예매 관리");
            System.out.println("7. 고객 관리");
            System.out.println("8. 검색");
            System.out.println("9. 내 예매 정보 보기 (로그인 후에만 사용 가능)");
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
                    manageMovies(scanner);
                    break;
                case 4:
                    manageShowTimes(scanner);
                    break;
                case 5:
                    manageSeats(scanner);
                    break;
                case 6:
                    manageBookings(scanner);
                    break;
                case 7:
                    manageCustomers(scanner);
                    break;
                case 8:
                    search(scanner);
                    break;
                case 9:
                    displayUserBookings();
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

    private void manageMovies(Scanner scanner) {
        while (true) {
            System.out.println("\n영화 관리 메뉴");
            System.out.println("1. 영화 추가");
            System.out.println("2. 영화 삭제");
            System.out.println("3. 영화 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("제목: ");
                    String title = scanner.nextLine();
                    System.out.print("감독: ");
                    String director = scanner.nextLine();
                    addMovie(title, director);
                    break;
                case 2:
                    System.out.print("삭제할 영화의 제목: ");
                    String titleToDelete = scanner.nextLine();
                    removeMovie(titleToDelete);
                    break;
                case 3:
                    displayAllMovies();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageShowTimes(Scanner scanner) {
        while (true) {
            System.out.println("\n상영 시간 관리 메뉴");
            System.out.println("1. 상영 시간 추가");
            System.out.println("2. 상영 시간 삭제");
            System.out.println("3. 상영 시간 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("날짜: ");
                    String date = scanner.nextLine();
                    System.out.print("시간: ");
                    String time = scanner.nextLine();
                    addShowTime(date, time);
                    break;
                case 2:
                    System.out.print("삭제할 상영 시간의 날짜: ");
                    String dateToDelete = scanner.nextLine();
                    System.out.print("삭제할 상영 시간의 시간: ");
                    String timeToDelete = scanner.nextLine();
                    removeShowTime(dateToDelete, timeToDelete);
                    break;
                case 3:
                    displayAllShowTimes();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageSeats(Scanner scanner) {
        while (true) {
            System.out.println("\n좌석 관리 메뉴");
            System.out.println("1. 좌석 추가");
            System.out.println("2. 좌석 삭제");
            System.out.println("3. 좌석 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("좌석 번호: ");
                    String number = scanner.nextLine();
                    addSeat(number);
                    break;
                case 2:
                    System.out.print("삭제할 좌석의 번호: ");
                    String numberToDelete = scanner.nextLine();
                    removeSeat(numberToDelete);
                    break;
                case 3:
                    displayAllSeats();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageBookings(Scanner scanner) {
        while (true) {
            System.out.println("\n예매 관리 메뉴");
            System.out.println("1. 영화 예매");
            System.out.println("2. 예매 취소");
            System.out.println("3. 예매 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("영화 제목: ");
                    String movieTitle = scanner.nextLine();
                    System.out.print("상영 시간 (yyyy-MM-dd HH:mm): ");
                    String showTime = scanner.nextLine();
                    System.out.print("좌석 번호: ");
                    String seatNumber = scanner.nextLine();
                    System.out.print("고객 ID: ");
                    String customerId = scanner.nextLine();
                    bookMovie(movieTitle, showTime, seatNumber, customerId);
                    break;
                case 2:
                    System.out.print("취소할 예매의 영화 제목: ");
                    String movieTitleToDelete = scanner.nextLine();
                    System.out.print("취소할 예매의 상영 시간 (yyyy-MM-dd HH:mm): ");
                    String showTimeToDelete = scanner.nextLine();
                    System.out.print("취소할 예매의 좌석 번호: ");
                    String seatNumberToDelete = scanner.nextLine();
                    cancelBooking(movieTitleToDelete, showTimeToDelete, seatNumberToDelete);
                    break;
                case 3:
                    displayAllBookings();
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

    private void search(Scanner scanner) {
        while (true) {
            System.out.println("\n검색 메뉴");
            System.out.println("1. 영화 제목으로 검색");
            System.out.println("2. 상영 시간으로 검색");
            System.out.println("3. 고객명으로 검색");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("검색할 영화 제목: ");
                    String movieTitleToSearch = scanner.nextLine();
                    searchMovieByTitle(movieTitleToSearch);
                    break;
                case 2:
                    System.out.print("검색할 상영 시간 (yyyy-MM-dd HH:mm): ");
                    String showTimeToSearch = scanner.nextLine();
                    searchShowTime(showTimeToSearch.split(" ")[0], showTimeToSearch.split(" ")[1]);
                    break;
                case 3:
                    System.out.print("검색할 고객명: ");
                    String customerNameToSearch = scanner.nextLine();
                    searchCustomerByName(customerNameToSearch);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void displayUserBookings() {
        if (!isUserLoggedIn()) {
            System.out.println("로그인 후에만 사용 가능합니다.");
            return;
        }

        int bookingCount = 0;
        for (Booking booking : bookings) {
            if (booking.getCustomerId().equals(currentUser)) {
                System.out.println("영화 제목: " + booking.getMovieTitle());
                System.out.println("상영 시간: " + booking.getShowTime());
                System.out.println("좌석 번호: " + booking.getSeatNumber());
                System.out.println();
                bookingCount++;
            }
        }
        System.out.println(bookingCount + "건의 예매가 있습니다.");
    }

    private <T> T findItemById(List<T> list, String id) {
        for (T item : list) {
            if (item instanceof Movie && ((Movie)item).getTitle().equals(id)) {
                return item;
            } else if (item instanceof ShowTime && ((ShowTime)item).getDate().equals(id)) {
                return item;
            } else if (item instanceof Seat && ((Seat)item).getNumber().equals(id)) {
                return item;
            } else if (item instanceof Customer && ((Customer)item).getId().equals(id)) {
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
                if (item instanceof Movie) {
                    Movie movie = (Movie) item;
                    System.out.println("제목: " + movie.getTitle());
                    System.out.println("감독: " + movie.getDirector());
                } else if (item instanceof ShowTime) {
                    ShowTime showTime = (ShowTime) item;
                    System.out.println("날짜: " + showTime.getDate());
                    System.out.println("시간: " + showTime.getTime());
                } else if (item instanceof Seat) {
                    Seat seat = (Seat) item;
                    System.out.println("좌석 번호: " + seat.getNumber());
                    System.out.println("예약 상태: " + (seat.isReserved() ? "예약됨" : "예약 가능"));
                } else if (item instanceof Customer) {
                    Customer customer = (Customer) item;
                    System.out.println("고객 ID: " + customer.getId());
                    System.out.println("이름: " + customer.getName());
                } else if (item instanceof Booking) {
                    Booking booking = (Booking) item;
                    System.out.println("영화 제목: " + booking.getMovieTitle());
                    System.out.println("상영 시간: " + booking.getShowTime());
                    System.out.println("좌석 번호: " + booking.getSeatNumber());
                    System.out.println("고객 ID: " + booking.getCustomerId());
                }
                System.out.println();
            }
        }
    }
}