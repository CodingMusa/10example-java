import java.io.Serializable;

class ShowTime implements Serializable {
    private String date;
    private String time;

    public ShowTime(String date, String time) {
        this.date = date;
        this.time = time;
    }

    // Getter and Setter methods
    public String getDate() { return date; }
    public String getTime() { return time; }
}