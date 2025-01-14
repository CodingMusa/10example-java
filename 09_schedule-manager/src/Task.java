import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private boolean isCompleted;

    public Task(String title) {
        this.title = title;
        this.isCompleted = false;
    }

    // Getter methods
    public String getTitle() { return title; }
    public boolean isCompleted() { return isCompleted; }

    // Setter methods
    public void setTitle(String title) { this.title = title; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}