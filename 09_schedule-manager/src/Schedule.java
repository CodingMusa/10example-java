import java.io.Serializable;
import java.time.LocalDate;

public class Schedule implements Serializable {
    private String title;
    private LocalDate date;
    private String description;

    public Schedule(String title, LocalDate date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getter methods
    public String getTitle() { return title; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }

    // Setter methods
    public void setTitle(String title) { this.title = title; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setDescription(String description) { this.description = description; }
}