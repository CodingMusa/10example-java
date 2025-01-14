import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
    private List<Contact> contacts;
    private List<Group> groups;
    private boolean isLoggedIn;
    private String currentUser;
    private List<User> users;

    public AddressBook() {
        this.contacts = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.isLoggedIn = false;
        this.currentUser = null;
        this.users = new ArrayList<>();
    }

    public void addContact(String name, String phoneNumber, String email, String group) {
        if (name == null || name.isEmpty() || phoneNumber == null || phoneNumber.isEmpty() || email == null || email.isEmpty() || group == null || group.isEmpty()) {
            System.out.println("모든 필드는 필수입니다.");
            return;
        }

        if (findContactByName(name) != null) {
            System.out.println("이미 존재하는 연락처입니다.");
            return;
        }

        Contact contact = new Contact(name, phoneNumber, email, group);
        contacts.add(contact);
        System.out.println("연락처 추가 완료!");
    }

    public void removeContact(String name) {
        Contact contactToRemove = findContactByName(name);
        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            System.out.println("연락처 삭제 완료!");
        } else {
            System.out.println("해당 연락처를 찾을 수 없습니다.");
        }
    }

    public void updateContact(String name, String newName, String newPhoneNumber, String newEmail, String newGroup) {
        Contact contactToUpdate = findContactByName(name);
        if (contactToUpdate != null) {
            contactToUpdate.setName(newName);
            contactToUpdate.setPhoneNumber(newPhoneNumber);
            contactToUpdate.setEmail(newEmail);
            contactToUpdate.setGroup(newGroup);
            System.out.println("연락처 수정 완료!");
        } else {
            System.out.println("해당 연락처를 찾을 수 없습니다.");
        }
    }

    private Contact findContactByName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null;
    }

    public void searchContactByName(String name) {
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getName().contains(name)) {
                System.out.println("이름: " + contact.getName());
                System.out.println("전화번호: " + contact.getPhoneNumber());
                System.out.println("이메일: " + contact.getEmail());
                System.out.println("그룹: " + contact.getGroup());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 이름과 일치하는 연락처를 찾을 수 없습니다.");
        }
    }

    public void searchContactByPhoneNumber(String phoneNumber) {
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().contains(phoneNumber)) {
                System.out.println("이름: " + contact.getName());
                System.out.println("전화번호: " + contact.getPhoneNumber());
                System.out.println("이메일: " + contact.getEmail());
                System.out.println("그룹: " + contact.getGroup());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 전화번호와 일치하는 연락처를 찾을 수 없습니다.");
        }
    }

    public void searchContactByEmail(String email) {
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getEmail().contains(email)) {
                System.out.println("이름: " + contact.getName());
                System.out.println("전화번호: " + contact.getPhoneNumber());
                System.out.println("이메일: " + contact.getEmail());
                System.out.println("그룹: " + contact.getGroup());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 이메일과 일치하는 연락처를 찾을 수 없습니다.");
        }
    }

    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("등록된 연락처가 없습니다.");
        } else {
            for (Contact contact : contacts) {
                System.out.println("이름: " + contact.getName());
                System.out.println("전화번호: " + contact.getPhoneNumber());
                System.out.println("이메일: " + contact.getEmail());
                System.out.println("그룹: " + contact.getGroup());
                System.out.println();
            }
        }
    }

    public void displayContactsByGroup(String groupName) {
        boolean found = false;
        for (Contact contact : contacts) {
            if (contact.getGroup().equals(groupName)) {
                System.out.println("이름: " + contact.getName());
                System.out.println("전화번호: " + contact.getPhoneNumber());
                System.out.println("이메일: " + contact.getEmail());
                System.out.println();
                found = true;
            }
        }
        if (!found) {
            System.out.println("해당 그룹의 연락처를 찾을 수 없습니다.");
        }
    }

    public void addGroup(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            System.out.println("그룹 이름은 필수입니다.");
            return;
        }

        if (findGroupByName(groupName) != null) {
            System.out.println("이미 존재하는 그룹입니다.");
            return;
        }

        Group group = new Group(groupName);
        groups.add(group);
        System.out.println("그룹 추가 완료!");
    }

    public void removeGroup(String groupName) {
        Group groupToRemove = findGroupByName(groupName);
        if (groupToRemove != null) {
            groups.remove(groupToRemove);
            System.out.println("그룹 삭제 완료!");
        } else {
            System.out.println("해당 그룹을 찾을 수 없습니다.");
        }
    }

    public void addContactToGroup(String contactName, String groupName) {
        Contact contact = findContactByName(contactName);
        Group group = findGroupByName(groupName);
        if (contact != null && group != null) {
            contact.setGroup(groupName);
            group.getContacts().add(contact);
            System.out.println("연락처를 그룹에 추가 완료!");
        } else {
            System.out.println("연락처나 그룹을 찾을 수 없습니다.");
        }
    }

    public void removeContactFromGroup(String contactName, String groupName) {
        Contact contact = findContactByName(contactName);
        Group group = findGroupByName(groupName);
        if (contact != null && group != null) {
            contact.setGroup("");
            group.getContacts().remove(contact);
            System.out.println("연락처를 그룹에서 제거 완료!");
        } else {
            System.out.println("연락처나 그룹을 찾을 수 없습니다.");
        }
    }

    private Group findGroupByName(String name) {
        for (Group group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    public boolean login(String username, String password) {
        User user = findUserById(username);
        if (user != null && user.getPassword().equals(password)) {
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("address_book_data.dat"))) {
            oos.writeObject(contacts);
            oos.writeObject(groups);
            oos.writeObject(users);
            System.out.println("데이터 저장 완료");
        } catch (IOException e) {
            System.err.println("데이터 저장 중 오류 발생: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() {
        File file = new File("address_book_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                contacts = (List<Contact>) ois.readObject();
                groups = (List<Group>) ois.readObject();
                users = (List<User>) ois.readObject();
                System.out.println("데이터 불러오기 완료");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("데이터 불러오기 중 오류 발생: " + e.getMessage());
            }
        } else {
            System.out.println("저장된 데이터 파일이 없습니다.");
        }
    }

    public void displayContactCountStatistics() {
        System.out.println("전체 연락처 수: " + contacts.size());
    }

    public void displayGroupStatistics() {
        for (Group group : groups) {
            System.out.println(group.getName() + " 그룹의 연락처 수: " + group.getContacts().size());
        }
    }

    public void runMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n주소록 관리 프로그램");
            System.out.println("------------------");
            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 연락처 관리");
            System.out.println("4. 연락처 검색");
            System.out.println("5. 연락처 표시");
            System.out.println("6. 그룹 관리");
            System.out.println("7. 통계 보기");
            System.out.println("8. 데이터 관리");
            System.out.println("9. 프로그램 종료");

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
                    manageContacts(scanner);
                    break;
                case 4:
                    searchContacts(scanner);
                    break;
                case 5:
                    displayContacts(scanner);
                    break;
                case 6:
                    manageGroups(scanner);
                    break;
                case 7:
                    displayStatistics(scanner);
                    break;
                case 8:
                    manageData(scanner);
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
        if (findUserById(id) != null) {
            System.out.println("이미 존재하는 ID입니다.");
            return;
        }

        User user = new User(id, name, password);
        users.add(user);
        System.out.println("회원가입 성공");
    }

    private User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // runMenu 메서드 내의 회원가입 부분을 다음과 같이 수정
    private void registerUser(Scanner scanner) {
        System.out.print("사용자 ID: ");
        String id = scanner.nextLine();
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();
        registerUser(id, name, password);
    }

    private void manageContacts(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 연락처 추가");
            System.out.println("2. 연락처 삭제");
            System.out.println("3. 연락처 수정");
            System.out.println("4. 연락처 목록 조회");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addContact(scanner);
                    break;
                case 2:
                    removeContact(scanner);
                    break;
                case 3:
                    updateContact(scanner);
                    break;
                case 4:
                    displayAllContacts();
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addContact(Scanner scanner) {
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("전화번호: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("이메일: ");
        String email = scanner.nextLine();
        System.out.print("그룹: ");
        String group = scanner.nextLine();

        addContact(name, phoneNumber, email, group);
    }

    private void removeContact(Scanner scanner) {
        System.out.print("삭제할 연락처의 이름: ");
        String name = scanner.nextLine();
        removeContact(name);
    }

    private void updateContact(Scanner scanner) {
        System.out.print("수정할 연락처의 이름: ");
        String name = scanner.nextLine();
        System.out.print("새로운 이름: ");
        String newName = scanner.nextLine();
        System.out.print("새로운 전화번호: ");
        String newPhoneNumber = scanner.nextLine();
        System.out.print("새로운 이메일: ");
        String newEmail = scanner.nextLine();
        System.out.print("새로운 그룹: ");
        String newGroup = scanner.nextLine();

        updateContact(name, newName, newPhoneNumber, newEmail, newGroup);
    }

    private void searchContacts(Scanner scanner) {
        System.out.println("1. 이름으로 검색");
        System.out.println("2. 전화번호로 검색");
        System.out.println("3. 이메일로 검색");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                searchContactByName(scanner);
                break;
            case 2:
                searchContactByPhoneNumber(scanner);
                break;
            case 3:
                searchContactByEmail(scanner);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void searchContactByName(Scanner scanner) {
        System.out.print("검색할 이름: ");
        String name = scanner.nextLine();
        searchContactByName(name);
    }

    private void searchContactByPhoneNumber(Scanner scanner) {
        System.out.print("검색할 전화번호: ");
        String phoneNumber = scanner.nextLine();
        searchContactByPhoneNumber(phoneNumber);
    }

    private void searchContactByEmail(Scanner scanner) {
        System.out.print("검색할 이메일: ");
        String email = scanner.nextLine();
        searchContactByEmail(email);
    }

    private void displayContacts(Scanner scanner) {
        System.out.println("1. 전체 연락처 목록");
        System.out.println("2. 특정 그룹의 연락처");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                displayAllContacts();
                break;
            case 2:
                displayContactsByGroup(scanner);
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    private void displayContactsByGroup(Scanner scanner) {
        System.out.print("그룹명: ");
        String groupName = scanner.nextLine();
        displayContactsByGroup(groupName);
    }

    private void manageGroups(Scanner scanner) {
        if (isLoggedIn) {
            System.out.println("1. 그룹 추가");
            System.out.println("2. 그룹 삭제");
            System.out.println("3. 연락처를 그룹에 추가");
            System.out.println("4. 연락처를 그룹에서 제거");
            System.out.print("선택: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    addGroup(scanner);
                    break;
                case 2:
                    removeGroup(scanner);
                    break;
                case 3:
                    addContactToGroup(scanner);
                    break;
                case 4:
                    removeContactFromGroup(scanner);
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } else {
            System.out.println("로그인이 필요합니다.");
        }
    }

    private void addGroup(Scanner scanner) {
        System.out.print("그룹명: ");
        String groupName = scanner.nextLine();
        addGroup(groupName);
    }

    private void removeGroup(Scanner scanner) {
        System.out.print("삭제할 그룹명: ");
        String groupName = scanner.nextLine();
        removeGroup(groupName);
    }

    private void addContactToGroup(Scanner scanner) {
        System.out.print("추가할 연락처의 이름: ");
        String contactName = scanner.nextLine();
        System.out.print("그룹명: ");
        String groupName = scanner.nextLine();
        addContactToGroup(contactName, groupName);
    }

    private void removeContactFromGroup(Scanner scanner) {
        System.out.print("제거할 연락처의 이름: ");
        String contactName = scanner.nextLine();
        System.out.print("그룹명: ");
        String groupName = scanner.nextLine();
        removeContactFromGroup(contactName, groupName);
    }

    private void displayStatistics(Scanner scanner) {
        System.out.println("1. 연락처 수 통계");
        System.out.println("2. 그룹별 연락처 통계");
        System.out.print("선택: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        switch (choice) {
            case 1:
                displayContactCountStatistics();
                break;
            case 2:
                displayGroupStatistics();
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
            if (item instanceof Contact && ((Contact)item).getName().equals(id)) {
                return item;
            } else if (item instanceof Group && ((Group)item).getName().equals(id)) {
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
                if (item instanceof Contact) {
                    Contact contact = (Contact) item;
                    System.out.println("이름: " + contact.getName());
                    System.out.println("전화번호: " + contact.getPhoneNumber());
                    System.out.println("이메일: " + contact.getEmail());
                    System.out.println("그룹: " + contact.getGroup());
                } else if (item instanceof Group) {
                    Group group = (Group) item;
                    System.out.println("그룹명: " + group.getName());
                    System.out.println("연락처 수: " + group.getContacts().size());
                }
                System.out.println();
            }
        }
    }
}