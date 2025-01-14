import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScheduleManager {
    private List<Schedule> schedules;
    private List<Task> tasks;
    private List<User> users;
    private boolean isLoggedIn;
    private String currentUser;

    public ScheduleManager() {
        this.schedules = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.users = new ArrayList<>();
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void addSchedule(String title, LocalDate date, String description) {
        if (title == null || title.isEmpty() || date == null || description == null || description.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        if (findScheduleByDate(date) != null) {
            System.out.println("이미 해당 날짜에 일정이 있습니다.");
            return;
        }

        Schedule schedule = new Schedule(title, date, description);
        schedules.add(schedule);
        System.out.println("일정 추가 완료!");
    }

    public void removeSchedule(LocalDate date) {
        Schedule scheduleToRemove = findScheduleByDate(date);
        if (scheduleToRemove != null) {
            schedules.remove(scheduleToRemove);
            System.out.println("일정 삭제 완료!");
        } else {
            System.out.println("해당 날짜의 일정을 찾을 수 없습니다.");
        }
    }

    public void updateSchedule(LocalDate date, String newTitle, String newDescription) {
        Schedule scheduleToUpdate = findScheduleByDate(date);
        if (scheduleToUpdate != null) {
            scheduleToUpdate.setTitle(newTitle);
            scheduleToUpdate.setDescription(newDescription);
            System.out.println("일정 수정 완료!");
        } else {
            System.out.println("해당 날짜의 일정을 찾을 수 없습니다.");
        }
    }

    private Schedule findScheduleByDate(LocalDate date) {
        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(date)) {
                return schedule;
            }
        }
        return null;
    }

    public void addTask(String title) {
        if (title == null || title.isEmpty()) {
            System.out.println("태스크 제목은 필수입니다.");
            return;
        }

        if (findTaskByTitle(title) != null) {
            System.out.println("이미 존재하는 태스크입니다.");
            return;
        }

        Task task = new Task(title);
        tasks.add(task);
        System.out.println("태스크 추가 완료!");
    }

    public void completeTask(String title) {
        Task taskToComplete = findTaskByTitle(title);
        if (taskToComplete != null) {
            taskToComplete.setCompleted(true);
            System.out.println("태스크 완료 표시!");
        } else {
            System.out.println("해당 태스크를 찾을 수 없습니다.");
        }
    }

    public void removeTask(String title) {
        Task taskToRemove = findTaskByTitle(title);
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            System.out.println("태스크 삭제 완료!");
        } else {
            System.out.println("해당 태스크를 찾을 수 없습니다.");
        }
    }

    private Task findTaskByTitle(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null;
    }

    public void searchScheduleByDate(LocalDate date) {
        boolean found = false;
        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(date)) {
                System.out.println("일정명: " + schedule.getTitle());
                System.out.println("설명: " + schedule.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 날짜의 일정을 찾을 수 없습니다.");
        }
    }

    public void searchScheduleByKeyword(String keyword) {
        boolean found = false;
        for (Schedule schedule : schedules) {
            if (schedule.getTitle().contains(keyword) || schedule.getDescription().contains(keyword)) {
                System.out.println("일정명: " + schedule.getTitle());
                System.out.println("날짜: " + schedule.getDate());
                System.out.println("설명: " + schedule.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 키워드와 일치하는 일정을 찾을 수 없습니다.");
        }
    }

    public void displayTodaySchedule() {
        LocalDate today = LocalDate.now();
        boolean found = false;
        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(today)) {
                System.out.println("일정명: " + schedule.getTitle());
                System.out.println("설명: " + schedule.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("오늘의 일정이 없습니다.");
        }
    }

    public void displayThisWeekSchedule() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        boolean found = false;
        for (Schedule schedule : schedules) {
            if (!schedule.getDate().isBefore(startOfWeek) && !schedule.getDate().isAfter(endOfWeek)) {
                System.out.println("일정명: " + schedule.getTitle());
                System.out.println("날짜: " + schedule.getDate());
                System.out.println("설명: " + schedule.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("이번주의 일정이 없습니다.");
        }
    }

    public void displayThisMonthSchedule() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        boolean found = false;
        for (Schedule schedule : schedules) {
            if (!schedule.getDate().isBefore(startOfMonth) && !schedule.getDate().isAfter(endOfMonth)) {
                System.out.println("일정명: " + schedule.getTitle());
                System.out.println("날짜: " + schedule.getDate());
                System.out.println("설명: " + schedule.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("이번달의 일정이 없습니다.");
        }
    }

    public void checkUpcomingSchedules() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        boolean found = false;
        for (Schedule schedule : schedules) {
            if (schedule.getDate().equals(tomorrow)) {
                System.out.println("내일의 일정: " + schedule.getTitle());
                System.out.println("내용: " + schedule.getDescription());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("내일의 일정이 없습니다.");
        }
    }

    public void displayCompletedTasksStatistics() {
        int completedCount = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedCount++;
            }
        }
        System.out.println("완료된 태스크 수: " + completedCount);
        System.out.println("전체 태스크 수: " + tasks.size());
        System.out.printf("완료율: %.2f%%%n", (double) completedCount / tasks.size() * 100);
    }

    public void displayIncompleteTasksStatistics() {
        int incompleteCount = 0;
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                incompleteCount++;
            }
        }
        System.out.println("미완료 태스크 수: " + incompleteCount);
        System.out.println("전체 태스크 수: " + tasks.size());
        System.out.printf("미완료율: %.2f%%%n", (double) incompleteCount / tasks.size() * 100);
    }

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("schedule_manager_data.dat"))) {
            oos.writeObject(schedules);
            oos.writeObject(tasks);
            oos.writeObject(users);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("schedule_manager_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                schedules = (List<Schedule>) ois.readObject();
                tasks = (List<Task>) ois.readObject();
                users = (List<User>) ois.readObject();
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
            (findUserByUsernameAndPassword(username, password) != null)) {
            isLoggedIn = true;
            currentUser = username;
            return true;
        }
        return false;
    }

    private User findUserByUsernameAndPassword(String username, String password) {
        for (User user : users) {
            if (user.getId().equals(username) && user.getPassword().equals(password)) {
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
            System.out.println("\n개인 일정 관리 애플리케이션");
            System.out.println("------------------------");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 일정 관리");
            System.out.println("4. 태스크 관리");
            System.out.println("5. 검색");
            System.out.println("6. 표시");
            System.out.println("7. 알림 설정");
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
                    registerUser(scanner);
                    break;
                case 3:
                    manageSchedules(scanner);
                    break;
                case 4:
                    manageTasks(scanner);
                    break;
                case 5:
                    search(scanner);
                    break;
                case 6:
                    display(scanner);
                    break;
                case 7:
                    checkUpcomingSchedules();
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

    public void registerUser(String id, String name, String password) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        if (findUserById(id) != null) {
            System.out.println("이미 존재하는 ID입니다.");
            return;
        }

        User newUser = new User(id, name, password);
        users.add(newUser);
        System.out.println("회원가입 완료!");
    }

    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
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

    private void manageSchedules(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 일일 일정 추가");
            System.out.println("2. 주간 일정 추가");
            System.out.println("3. 월간 일정 추가");
            System.out.println("4. 일정 삭제");
            System.out.println("5. 일정 수정");
            System.out.println("6. 일정 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addDailySchedule(scanner);
                    break;
                case 2:
                    addWeeklySchedule(scanner);
                    break;
                case 3:
                    addMonthlySchedule(scanner);
                    break;
                case 4:
                    removeSchedule(scanner);
                    break;
                case 5:
                    updateSchedule(scanner);
                    break;
                case 6:
                    displayAllSchedules();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addDailySchedule(Scanner scanner) {
        System.out.print("일정명: ");
        String title = scanner.nextLine();
        System.out.print("날짜 (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("설명: ");
        String description = scanner.nextLine();

        addSchedule(title, date, description);
    }

    private void addWeeklySchedule(Scanner scanner) {
        System.out.print("일정명: ");
        String title = scanner.nextLine();
        System.out.print("시작 날짜 (yyyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("종료 날짜 (yyyy-MM-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        System.out.print("설명: ");
        String description = scanner.nextLine();

        while (!startDate.isAfter(endDate)) {
            addSchedule(title, startDate, description);
            startDate = startDate.plusDays(1);
        }
    }

    private void addMonthlySchedule(Scanner scanner) {
        System.out.print("일정명: ");
        String title = scanner.nextLine();
        System.out.print("시작 날짜 (yyyy-MM-dd): ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.print("종료 날짜 (yyyy-MM-dd): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
        System.out.print("설명: ");
        String description = scanner.nextLine();

        while (!startDate.isAfter(endDate)) {
            addSchedule(title, startDate, description);
            startDate = startDate.plusMonths(1);
        }
    }

    private void removeSchedule(Scanner scanner) {
        System.out.print("삭제할 일자의 날짜 (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        removeSchedule(date);
    }

    private void updateSchedule(Scanner scanner) {
        System.out.print("수정할 일자의 날짜 (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        System.out.print("새로운 일정명: ");
        String newTitle = scanner.nextLine();
        System.out.print("새로운 설명: ");
        String newDescription = scanner.nextLine();

        updateSchedule(date, newTitle, newDescription);
    }

    private void displayAllSchedules() {
        if (schedules.isEmpty()) {
            System.out.println("등록된 일정이 없습니다.");
        } else {
            for (Schedule schedule : schedules) {
                System.out.println("일정명: " + schedule.getTitle());
                System.out.println("날짜: " + schedule.getDate());
                System.out.println("설명: " + schedule.getDescription());
                System.out.println();
            }
        }
    }

    private void manageTasks(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 태스크 추가");
            System.out.println("2. 태스크 완료 표시");
            System.out.println("3. 태스크 삭제");
            System.out.println("4. 태스크 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    completeTask(scanner);
                    break;
                case 3:
                    removeTask(scanner);
                    break;
                case 4:
                    displayAllTasks();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addTask(Scanner scanner) {
        System.out.print("태스크명: ");
        String title = scanner.nextLine();

        addTask(title);
    }

    private void completeTask(Scanner scanner) {
        System.out.print("완료 표시할 태스크명: ");
        String title = scanner.nextLine();

        completeTask(title);
    }

    private void removeTask(Scanner scanner) {
        System.out.print("삭제할 태스크명: ");
        String title = scanner.nextLine();

        removeTask(title);
    }

    private void displayAllTasks() {
        if (tasks.isEmpty()) {
            System.out.println("등록된 태스크가 없습니다.");
        } else {
            for (Task task : tasks) {
                System.out.println("태스크명: " + task.getTitle());
                System.out.println("완료 여부: " + (task.isCompleted() ? "완료" : "미완료"));
                System.out.println();
            }
        }
    }

    private void search(Scanner scanner) {
        System.out.println("1. 날짜로 검색");
        System.out.println("2. 키워드로 검색");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                searchScheduleByDate(scanner);
                break;
            case 2:
                searchScheduleByKeyword(scanner);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void searchScheduleByDate(Scanner scanner) {
        System.out.print("검색할 날짜 (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        searchScheduleByDate(date);
    }

    private void searchScheduleByKeyword(Scanner scanner) {
        System.out.print("검색할 키워드: ");
        String keyword = scanner.nextLine();
        searchScheduleByKeyword(keyword);
    }

    private void display(Scanner scanner) {
        System.out.println("1. 오늘의 일정 표시");
        System.out.println("2. 이번주의 일정 표시");
        System.out.println("3. 이번달의 일정 표시");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                displayTodaySchedule();
                break;
            case 2:
                displayThisWeekSchedule();
                break;
            case 3:
                displayThisMonthSchedule();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void displayStatistics(Scanner scanner) {
        System.out.println("1. 완료된 태스크 통계");
        System.out.println("2. 미완료 태스크 통계");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                displayCompletedTasksStatistics();
                break;
            case 2:
                displayIncompleteTasksStatistics();
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
            if (item instanceof Schedule && ((Schedule)item).getTitle().equals(id)) {
                return item;
            } else if (item instanceof Task && ((Task)item).getTitle().equals(id)) {
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
                if (item instanceof Schedule) {
                    Schedule schedule = (Schedule) item;
                    System.out.println("일정명: " + schedule.getTitle());
                    System.out.println("날짜: " + schedule.getDate());
                    System.out.println("설명: " + schedule.getDescription());
                } else if (item instanceof Task) {
                    Task task = (Task) item;
                    System.out.println("태스크명: " + task.getTitle());
                    System.out.println("완료 여부: " + (task.isCompleted() ? "완료" : "미완료"));
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