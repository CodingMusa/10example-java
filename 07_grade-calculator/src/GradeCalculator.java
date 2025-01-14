import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GradeCalculator {
    private List<Course> courses;
    private List<Grade> grades;
    private List<Student> students;
    private double gpaScale;
    private boolean isLoggedIn;
    private String currentUser;

    public GradeCalculator() {
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.students = new ArrayList<>();
        this.gpaScale = 4.5; // 기본값으로 4.5점제 설정
        this.isLoggedIn = false;
        this.currentUser = null;
    }

    public void addCourse(String name, int credits) {
        if (name == null || name.isEmpty() || credits <= 0) {
            System.out.println("과목명은 필수이며, 학점은 양수여야 합니다.");
            return;
        }

        if (findCourseByName(name) != null) {
            System.out.println("이미 존재하는 과목명입니다.");
            return;
        }

        Course course = new Course(name, credits);
        courses.add(course);
        System.out.println("과목 추가 완료!");
    }

    public void removeCourse(String name) {
        Course courseToRemove = findCourseByName(name);
        if (courseToRemove != null) {
            courses.remove(courseToRemove);
            System.out.println("과목 삭제 완료!");
        } else {
            System.out.println("해당 과목을 찾을 수 없습니다.");
        }
    }

    public void displayAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("등록된 과목이 없습니다.");
        } else {
            for (Course course : courses) {
                System.out.println("과목명: " + course.getName());
                System.out.println("학점: " + course.getCredits());
                System.out.println();
            }
        }
    }

    private Course findCourseByName(String name) {
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }

    public void addGrade(String courseName, double score) {
        if (courseName == null || courseName.isEmpty() || score < 0 || score > 100) {
            System.out.println("과목명은 필수이며, 점수는 0 ~ 100 사이여야 합니다.");
            return;
        }

        if (findGradeByCourseName(courseName) != null) {
            System.out.println("이미 해당 과목의 성적이 등록되어 있습니다.");
            return;
        }

        Grade grade = new Grade(courseName, score);
        grades.add(grade);
        System.out.println("성적 추가 완료!");
    }

    public void updateGrade(String courseName, double newScore) {
        Grade gradeToUpdate = findGradeByCourseName(courseName);
        if (gradeToUpdate != null) {
            gradeToUpdate.setScore(newScore);
            System.out.println("성적 수정 완료!");
        } else {
            System.out.println("해당 과목의 성적을 찾을 수 없습니다.");
        }
    }

    private Grade findGradeByCourseName(String courseName) {
        for (Grade grade : grades) {
            if (grade.getCourseName().equals(courseName)) {
                return grade;
            }
        }
        return null;
    }

    public void setGpaScale(double scale) {
        if (scale == 4.5 || scale == 4.3) {
            this.gpaScale = scale;
            System.out.println(scale + "점제로 설정되었습니다.");
        } else {
            System.out.println("잘못된 학점 계산 방식입니다. 4.5점제 또는 4.3점제만 가능합니다.");
        }
    }

    public void displayGpaScale() {
        System.out.println("현재 학점 계산 방식: " + gpaScale + "점제");
    }

    public void calculateGrade(String courseName) {
        Grade grade = findGradeByCourseName(courseName);
        if (grade != null) {
            double score = grade.getScore();
            double gradePoint = calculateGradePoint(score);
            System.out.println(courseName + "의 학점: " + gradePoint);
        } else {
            System.out.println("해당 과목의 성적을 찾을 수 없습니다.");
        }
    }

    private double calculateGradePoint(double score) {
        if (gpaScale == 4.5) {
            if (score >= 95) return 4.5;
            else if (score >= 90) return 4.0;
            else if (score >= 85) return 3.5;
            else if (score >= 80) return 3.0;
            else if (score >= 75) return 2.5;
            else if (score >= 70) return 2.0;
            else if (score >= 65) return 1.5;
            else if (score >= 60) return 1.0;
            else return 0.0;
        } else { // 4.3점제
            if (score >= 95) return 4.3;
            else if (score >= 90) return 4.0;
            else if (score >= 85) return 3.7;
            else if (score >= 80) return 3.3;
            else if (score >= 75) return 3.0;
            else if (score >= 70) return 2.7;
            else if (score >= 65) return 2.3;
            else if (score >= 60) return 2.0;
            else return 0.0;
        }
    }

    public void calculateGpa() {
        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Grade grade : grades) {
            Course course = findCourseByName(grade.getCourseName());
            if (course != null) {
                double gradePoint = calculateGradePoint(grade.getScore());
                totalGradePoints += gradePoint * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        if (totalCredits > 0) {
            double gpa = totalGradePoints / totalCredits;
            System.out.printf("현재 GPA: %.2f%n", gpa);
        } else {
            System.out.println("GPA 계산을 위한 데이터가 없습니다.");
        }
    }

    public void searchGradeByCourseName(String courseName) {
        Grade grade = findGradeByCourseName(courseName);
        if (grade != null) {
            System.out.println("과목명: " + grade.getCourseName());
            System.out.println("점수: " + grade.getScore());
            System.out.println("학점: " + calculateGradePoint(grade.getScore()));
        } else {
            System.out.println("해당 과목의 성적을 찾을 수 없습니다.");
        }
    }

    public void searchGradeByGradePoint(double gradePoint) {
        boolean found = false;
        for (Grade grade : grades) {
            if (Math.abs(calculateGradePoint(grade.getScore()) - gradePoint) < 0.01) {
                System.out.println("과목명: " + grade.getCourseName());
                System.out.println("점수: " + grade.getScore());
                System.out.println("학점: " + gradePoint);
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 학점의 성적을 찾을 수 없습니다.");
        }
    }

    public void displayStatistics() {
        int[] gradeCounts = new int[10]; // 0.0 ~ 4.5까지 0.5단위로 구분
        double totalScore = 0;
        int totalCourses = 0;

        for (Grade grade : grades) {
            double gradePoint = calculateGradePoint(grade.getScore());
            int index = (int) Math.floor(gradePoint * 2); // 0.0 ~ 4.5를 0 ~ 9로 변환
            gradeCounts[index]++;
            totalScore += grade.getScore();
            totalCourses++;
        }

        System.out.println("전체 성적 통계:");
        System.out.printf("평균 점수: %.2f%n", totalScore / totalCourses);
        System.out.println("학점별 통계:");
        for (int i = 0; i < 10; i++) {
            double grade = i / 2.0;
            System.out.printf("%.1f ~ %.1f: %d건%n", grade, grade + 0.5, gradeCounts[i]);
        }
    }

    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("grade_calculator_data.dat"))) {
            oos.writeObject(courses);
            oos.writeObject(grades);
            oos.writeObject(students);
            oos.writeObject(gpaScale);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("grade_calculator_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                courses = (List<Course>) ois.readObject();
                grades = (List<Grade>) ois.readObject();
                students = (List<Student>) ois.readObject();
                gpaScale = (double) ois.readObject();
                System.out.println("데이터 불러오기 완료");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("데이터 불러오기 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("저장된 데이터 파일이 없습니다.");
        }
    }

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("password")) {
            isLoggedIn = true;
            currentUser = "admin";
            return true;
        }
        Student student = findStudentById(username);
        if (student != null && student.getPassword().equals(password)) {
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

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n학점 계산기 프로그램");
            System.out.println("------------------");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 과목 관리");
            System.out.println("4. 성적 입력");
            System.out.println("5. 학점 계산 방식 선택");
            System.out.println("6. 학점 계산");
            System.out.println("7. GPA 계산");
            System.out.println("8. 성적 검색");
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
                    manageCourses(scanner);
                    break;
                case 4:
                    manageGrades(scanner);
                    break;
                case 5:
                    selectGpaScale(scanner);
                    break;
                case 6:
                    calculateGrade(scanner);
                    break;
                case 7:
                    calculateGpa(scanner);
                    break;
                case 8:
                    searchGrade(scanner);
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

    public void registerUser(String id, String name, String password) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty() || password == null || password.isEmpty()) {
            System.out.println("모든 정보를 정확히 입력해야 합니다.");
            return;
        }

        if (findStudentById(id) != null) {
            System.out.println("이미 존재하는 사용자 ID입니다.");
            return;
        }

        Student newStudent = new Student(id, name, password);
        students.add(newStudent);
        System.out.println("회원가입 성공!");
    }

    private Student findStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private void manageCourses(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 과목 추가");
            System.out.println("2. 과목 삭제");
            System.out.println("3. 과목 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addCourse(scanner);
                    break;
                case 2:
                    removeCourse(scanner);
                    break;
                case 3:
                    displayAllCourses();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addCourse(Scanner scanner) {
        System.out.print("과목명: ");
        String name = scanner.nextLine();
        System.out.print("학점: ");
        int credits = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        addCourse(name, credits);
    }

    private void removeCourse(Scanner scanner) {
        System.out.print("삭제할 과목명: ");
        String name = scanner.nextLine();
        removeCourse(name);
    }

    private void manageGrades(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 성적 추가");
            System.out.println("2. 성적 수정");
            System.out.println("3. 성적 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addGrade(scanner);
                    break;
                case 2:
                    updateGrade(scanner);
                    break;
                case 3:
                    displayAllGrades();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addGrade(Scanner scanner) {
        System.out.print("과목명: ");
        String courseName = scanner.nextLine();
        System.out.print("점수: ");
        double score = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        addGrade(courseName, score);
    }

    private void updateGrade(Scanner scanner) {
        System.out.print("수정할 과목명: ");
        String courseName = scanner.nextLine();
        System.out.print("새로운 점수: ");
        double newScore = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        updateGrade(courseName, newScore);
    }

    private void displayAllGrades() {
        if (grades.isEmpty()) {
            System.out.println("등록된 성적이 없습니다.");
        } else {
            for (Grade grade : grades) {
                System.out.println("과목명: " + grade.getCourseName());
                System.out.println("점수: " + grade.getScore());
                System.out.println("학점: " + calculateGradePoint(grade.getScore()));
                System.out.println();
            }
        }
    }

    private void selectGpaScale(Scanner scanner) {
        System.out.println("1. 4.5점제");
        System.out.println("2. 4.3점제");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                setGpaScale(4.5);
                break;
            case 2:
                setGpaScale(4.3);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void calculateGrade(Scanner scanner) {
        System.out.print("학점을 계산할 과목명: ");
        String courseName = scanner.nextLine();
        calculateGrade(courseName);
    }

    private void calculateGpa(Scanner scanner) {
        calculateGpa();
    }

    private void searchGrade(Scanner scanner) {
        System.out.println("1. 과목명으로 검색");
        System.out.println("2. 학점으로 검색");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                searchGradeByCourseName(scanner);
                break;
            case 2:
                searchGradeByGradePoint(scanner);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void searchGradeByCourseName(Scanner scanner) {
        System.out.print("검색할 과목명: ");
        String courseName = scanner.nextLine();
        searchGradeByCourseName(courseName);
    }

    private void searchGradeByGradePoint(Scanner scanner) {
        System.out.print("검색할 학점: ");
        double gradePoint = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over
        searchGradeByGradePoint(gradePoint);
    }

    private void displayStatistics(Scanner scanner) {
        displayStatistics();
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
            if (item instanceof Course && ((Course)item).getName().equals(id)) {
                return item;
            } else if (item instanceof Grade && ((Grade)item).getCourseName().equals(id)) {
                return item;
            } else if (item instanceof Student && ((Student)item).getId().equals(id)) {
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
                if (item instanceof Course) {
                    Course course = (Course) item;
                    System.out.println("과목명: " + course.getName());
                    System.out.println("학점: " + course.getCredits());
                } else if (item instanceof Grade) {
                    Grade grade = (Grade) item;
                    System.out.println("과목명: " + grade.getCourseName());
                    System.out.println("점수: " + grade.getScore());
                    System.out.println("학점: " + calculateGradePoint(grade.getScore()));
                } else if (item instanceof Student) {
                    Student student = (Student) item;
                    System.out.println("ID: " + student.getId());
                    System.out.println("이름: " + student.getName());
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        GradeCalculator calculator = new GradeCalculator();
        calculator.runMenu();
    }
}
