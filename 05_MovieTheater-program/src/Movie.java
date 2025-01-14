import java.io.Serializable;

class Movie implements Serializable {
    private String title;
    private String director;

    public Movie(String title, String director) {
        this.title = title;
        this.director = director;
    }

    // Getter and Setter methods
    public String getTitle() { return title; }
    public String getDirector() { return director; }
}