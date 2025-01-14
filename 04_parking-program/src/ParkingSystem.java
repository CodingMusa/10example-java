import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParkingSystem {
    private List<ParkingSpace> parkingSpaces;
    private List<Vehicle> parkedVehicles;
    private boolean isLoggedIn;
    private String currentUser;
    private int baseFee = 1000; // 기본 요금 (원)
    private int additionalFeePerHour = 500; // 시간당 추가 요금 (원)
    private Map<String, String> registeredUsers = new HashMap<>();

    public ParkingSystem() {
        this.parkingSpaces = new ArrayList<>();
        this.parkedVehicles = new ArrayList<>();
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void registerUser(String username, String password) {
        if (registeredUsers.containsKey(username)) {
            System.out.println("이미 존재하는 사용자 이름입니다.");
            return;
        }

        registeredUsers.put(username, password);
        System.out.println("사용자 등록 완료!");
    }

    public void addParkingSpace(int number) {
        if (number <= 0) {
            System.out.println("주차 공간 번호는 양수여야 합니다.");
            return;
        }

        if (findParkingSpaceByNumber(number) != null) {
            System.out.println("이미 존재하는 주차 공간 번호입니다.");
            return;
        }

        ParkingSpace space = new ParkingSpace(number);
        parkingSpaces.add(space);
        System.out.println("주차 공간 추가 완료!");
    }

    public void removeParkingSpace(int number) {
        ParkingSpace spaceToRemove = findParkingSpaceByNumber(number);
        if (spaceToRemove != null) {
            parkingSpaces.remove(spaceToRemove);
            System.out.println("주차 공간 삭제 완료!");
        } else {
            System.out.println("해당 주차 공간을 찾을 수 없습니다.");
        }
    }

    public void displayAllParkingSpaces() {
        if (parkingSpaces.isEmpty()) {
            System.out.println("등록된 주차 공간이 없습니다.");
        } else {
            for (ParkingSpace space : parkingSpaces) {
                System.out.println("주차 공간 번호: " + space.getNumber());
                System.out.println("사용 여부: " + (space.isOccupied() ? "사용 중" : "사용 가능"));
                System.out.println();
            }
        }
    }

    public void enterVehicle(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isEmpty()) {
            System.out.println("차량 번호는 필수입니다.");
            return;
        }

        if (findVehicleByNumber(vehicleNumber) != null) {
            System.out.println("이미 주차 중인 차량입니다.");
            return;
        }

        ParkingSpace availableSpace = findAvailableParkingSpace();
        if (availableSpace != null) {
            availableSpace.setOccupied(true);
            availableSpace.setVehicleNumber(vehicleNumber);
            Vehicle vehicle = new Vehicle(vehicleNumber, System.currentTimeMillis());
            parkedVehicles.add(vehicle);
            System.out.println("차량 입차 완료!");
        } else {
            System.out.println("사용 가능한 주차 공간이 없습니다.");
        }
    }

    public void exitVehicle(String vehicleNumber) {
        Vehicle vehicle = findVehicleByNumber(vehicleNumber);
        if (vehicle != null) {
            ParkingSpace space = findParkingSpaceByVehicleNumber(vehicleNumber);
            space.setOccupied(false);
            space.setVehicleNumber(null);
            parkedVehicles.remove(vehicle);
            System.out.println("차량 출차 완료!");
        } else {
            System.out.println("해당 차량을 찾을 수 없습니다.");
        }
    }

    public void displayParkedVehicles() {
        if (parkedVehicles.isEmpty()) {
            System.out.println("주차 중인 차량이 없습니다.");
        } else {
            for (Vehicle vehicle : parkedVehicles) {
                System.out.println("차량 번호: " + vehicle.getNumber());
                System.out.println("입차 시간: " + new java.util.Date(vehicle.getEntryTime()));
                System.out.println();
            }
        }
    }

    public void setBaseFee(int fee) {
        this.baseFee = fee;
        System.out.println("기본 요금이 " + fee + "원으로 설정되었습니다.");
    }

    public void setAdditionalFeePerHour(int fee) {
        this.additionalFeePerHour = fee;
        System.out.println("시간당 추가 요금이 " + fee + "원으로 설정되었습니다.");
    }

    public int calculateFee(String vehicleNumber) {
        Vehicle vehicle = findVehicleByNumber(vehicleNumber);
        if (vehicle != null) {
            long entryTime = vehicle.getEntryTime();
            long currentTime = System.currentTimeMillis();
            long duration = currentTime - entryTime;
            long hours = duration / (60 * 60 * 1000);
            int fee = baseFee + (int)(hours * additionalFeePerHour);
            return fee;
        } else {
            System.out.println("해당 차량을 찾을 수 없습니다.");
            return 0;
        }
    }

    public void searchVehicleByNumber(String number) {
        Vehicle vehicle = findVehicleByNumber(number);
        if (vehicle != null) {
            System.out.println("차량 번호: " + vehicle.getNumber());
            System.out.println("입차 시간: " + new java.util.Date(vehicle.getEntryTime()));
            ParkingSpace space = findParkingSpaceByVehicleNumber(number);
            System.out.println("주차 공간 번호: " + space.getNumber());
        } else {
            System.out.println("해당 차량을 찾을 수 없습니다.");
        }
    }

    public void searchParkingSpaceByNumber(int number) {
        ParkingSpace space = findParkingSpaceByNumber(number);
        if (space != null) {
            System.out.println("주차 공간 번호: " + space.getNumber());
            System.out.println("사용 여부: " + (space.isOccupied() ? "사용 중" : "사용 가능"));
            if (space.isOccupied()) {
                System.out.println("차량 번호: " + space.getVehicleNumber());
            }
        } else {
            System.out.println("해당 주차 공간을 찾을 수 없습니다.");
        }
    }

    public boolean login(String username, String password) {
        if ((username.equals("admin") && password.equals("password")) ||
            (registeredUsers.containsKey(username) && registeredUsers.get(username).equals(password))) {
            isLoggedIn = true;
            currentUser = username;
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("parking_data.dat"))) {
            oos.writeObject(parkingSpaces);
            oos.writeObject(parkedVehicles);
            oos.writeObject(baseFee);
            oos.writeObject(additionalFeePerHour);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("parking_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                parkingSpaces = (List<ParkingSpace>) ois.readObject();
                parkedVehicles = (List<Vehicle>) ois.readObject();
                baseFee = ois.readInt();
                additionalFeePerHour = ois.readInt();
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
            System.out.println("\n주차장 관리 시스템");
            System.out.println("------------------");
            System.out.println("1. 로그인 (관리자)");
            System.out.println("2. 로그인 (일반 사용자)");
            System.out.println("3. 사용자 등록");
            System.out.println("4. 주차 공간 관리");
            System.out.println("5. 차량 관리");
            System.out.println("6. 요금 관리");
            System.out.println("7. 검색");
            System.out.println("8. 내 차량 정보 보기 (로그인 후에만 사용 가능)");
            System.out.println("9. 프로그램 종료");

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
                    registerUser(scanner);
                    break;
                case 4:
                    manageParkingSpaces(scanner);
                    break;
                case 5:
                    manageVehicles(scanner);
                    break;
                case 6:
                    manageFees(scanner);
                    break;
                case 7:
                    search(scanner);
                    break;
                case 8:
                    displayUserVehicle();
                    break;
                case 9:
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

    private void registerUser(Scanner scanner) {
        System.out.print("사용자 이름: ");
        String username = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        registerUser(username, password);
    }

    private void manageParkingSpaces(Scanner scanner) {
        while (true) {
            System.out.println("\n주차 공간 관리 메뉴");
            System.out.println("1. 주차 공간 추가");
            System.out.println("2. 주차 공간 삭제");
            System.out.println("3. 주차 공간 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("주차 공간 번호: ");
                    int number = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    addParkingSpace(number);
                    break;
                case 2:
                    System.out.print("삭제할 주차 공간의 번호: ");
                    int numberToDelete = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    removeParkingSpace(numberToDelete);
                    break;
                case 3:
                    displayAllParkingSpaces();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageVehicles(Scanner scanner) {
        while (true) {
            System.out.println("\n차량 관리 메뉴");
            System.out.println("1. 차량 입차");
            System.out.println("2. 차량 출차");
            System.out.println("3. 주차 중인 차량 목록 보기");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("차량 번호: ");
                    String vehicleNumber = scanner.nextLine();
                    enterVehicle(vehicleNumber);
                    break;
                case 2:
                    System.out.print("출차할 차량의 번호: ");
                    String vehicleNumberToExit = scanner.nextLine();
                    exitVehicle(vehicleNumberToExit);
                    break;
                case 3:
                    displayParkedVehicles();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void manageFees(Scanner scanner) {
        while (true) {
            System.out.println("\n요금 관리 메뉴");
            System.out.println("1. 기본 요금 설정");
            System.out.println("2. 추가 요금 설정");
            System.out.println("3. 요금 계산");
            System.out.println("4. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("새로운 기본 요금: ");
                    int newBaseFee = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    setBaseFee(newBaseFee);
                    break;
                case 2:
                    System.out.print("새로운 추가 요금: ");
                    int newAdditionalFee = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    setAdditionalFeePerHour(newAdditionalFee);
                    break;
                case 3:
                    System.out.print("요금을 계산할 차량 번호: ");
                    String vehicleNumberForFee = scanner.nextLine();
                    int fee = calculateFee(vehicleNumberForFee);
                    if (fee > 0) {
                        System.out.println("요금: " + fee + "원");
                    }
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
            System.out.println("1. 차량 번호로 검색");
            System.out.println("2. 주차 공간 번호로 검색");
            System.out.println("3. 이전 메뉴로");

            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    System.out.print("검색할 차량 번호: ");
                    String vehicleNumberToSearch = scanner.nextLine();
                    searchVehicleByNumber(vehicleNumberToSearch);
                    break;
                case 2:
                    System.out.print("검색할 주차 공간 번호: ");
                    int spaceNumberToSearch = scanner.nextInt();
                    scanner.nextLine(); // Consume newline left-over
                    searchParkingSpaceByNumber(spaceNumberToSearch);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
        }
    }

    private void displayUserVehicle() {
        if (!isUserLoggedIn()) {
            System.out.println("로그인 후에만 사용 가능합니다.");
            return;
        }

        Vehicle userVehicle = findVehicleByNumber(currentUser);
        if (userVehicle != null) {
            System.out.println("사용자 차량 정보:");
            System.out.println("차량 번호: " + userVehicle.getNumber());
            System.out.println("입차 시간: " + new java.util.Date(userVehicle.getEntryTime()));

            ParkingSpace space = findParkingSpaceByVehicleNumber(userVehicle.getNumber());
            System.out.println("주차 공간 번호: " + space.getNumber());

            // 요금 정보 추가
            int fee = calculateFee(userVehicle.getNumber());
            System.out.println("현재 누적 요금: " + fee + "원");
        } else {
            System.out.println("현재 주차 중인 차량이 없습니다.");
        }
    }

    private ParkingSpace findParkingSpaceByNumber(int number) {
        for (ParkingSpace space : parkingSpaces) {
            if (space.getNumber() == number) {
                return space;
            }
        }
        return null;
    }

    private Vehicle findVehicleByNumber(String number) {
        for (Vehicle vehicle : parkedVehicles) {
            if (vehicle.getNumber().equals(number)) {
                return vehicle;
            }
        }
        return null;
    }

    private ParkingSpace findParkingSpaceByVehicleNumber(String number) {
        for (ParkingSpace space : parkingSpaces) {
            if (space.getVehicleNumber().equals(number)) {
                return space;
            }
        }
        return null;
    }

    private ParkingSpace findAvailableParkingSpace() {
        for (ParkingSpace space : parkingSpaces) {
            if (!space.isOccupied()) {
                return space;
            }
        }
        return null;
    }
}