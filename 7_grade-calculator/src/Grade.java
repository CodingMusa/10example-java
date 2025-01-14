import java.io.Serializable;

class Grade implements Serializable {
    private String courseName;
    private double score;

    public Grade(String courseName, double score) {
        this.courseName = courseName;
        this.score = score;
    }

    // Getter and Setter methods
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

}