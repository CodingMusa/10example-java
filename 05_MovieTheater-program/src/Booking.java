import java.io.Serializable;

class Booking implements Serializable {
    private String movieTitle;
    private String showTime;
    private String seatNumber;
    private String customerId;

    public Booking(String movieTitle, String showTime, String seatNumber, String customerId) {
        this.movieTitle = movieTitle;
        this.showTime = showTime;
        this.seatNumber = seatNumber;
        this.customerId = customerId;
    }

    // Getter and Setter methods
    public String getMovieTitle() { return movieTitle; }
    public String getShowTime() { return showTime; }
    public String getSeatNumber() { return seatNumber; }
    public String getCustomerId() { return customerId; }
}