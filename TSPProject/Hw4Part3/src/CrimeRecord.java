/**
 * The crime record based on the date the user input and record information saved in the the csv file.
 */
public class CrimeRecord {

    /**
     * The location, time, offense type, the date and tract or the crime.
     */
    private double x;
    private double y;
    private int time;
    private String street;
    private String offense;
    private String date;
    private String tract;
    private double lat;
    private double lon;

    /**
     * Default constructor
     */
    public CrimeRecord() {
    }

    /**
     * Constructor method.
     * @param x location x
     * @param y location y
     * @param time time
     * @param street street
     * @param offense type of offense
     * @param date date
     * @param tract tract
     * @param lat latitude
     * @param lon longitude
     */
    public CrimeRecord(double x, double y, int time, String street, String offense, String date, String tract, double lat, double lon) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.street = street;
        this.offense = offense;
        this.date = date;
        this.tract = tract;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Get location x.
     * @return location x
     */
    public double getX() {
        return x;
    }

    /**
     * Set location .
     * @param x location x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get location y.
     * @return location y
     */
    public double getY() {
        return y;
    }

    /**
     * Set location y.
     * @param y location y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get time.
     */
    public int getTime() {
        return time;
    }

    /**
     * Set time.
     * @param time time
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Get street.
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set street.
     * @param street street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get offense type.
     * @return offense type
     */
    public String getOffense() {
        return offense;
    }

    /**
     * Set offense type
     * @param offense offense type
     */
    public void setOffense(String offense) {
        this.offense = offense;
    }

    /**
     * Get date
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set date
     * @param date date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Get tract.
     * @return tract.
     */
    public String getTract() {
        return tract;
    }

    /**
     * Set tract
     * @param tract tract
     */
    public void setTract(String tract) {
        this.tract = tract;
    }

    /**
     * Get latitude.
     * @return latitude
     */
    public double getLat() {
        return lat;
    }

    /**
     * Set latitude
     * @param lat latitude
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Get longitude.
     * @return longitude
     */
    public double getLon() {
        return lon;
    }

    /**
     * Set longitude
     */
    public void setLon(double lon) {
        this.lon = lon;
    }
}
