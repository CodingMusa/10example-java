import java.io.Serializable;

class Member implements Serializable {
    private String name;
    private String memberId;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
    }

    // Getter and Setter methods
    public String getName() { return name; }
    public String getMemberId() { return memberId; }
    public void setName(String name) { this.name = name; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
}