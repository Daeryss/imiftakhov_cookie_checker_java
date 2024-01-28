package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents the implementation of a cookie with a unique ID and timestamps.
 * It stores the cookie's identifier and a list of Date objects representing
 * the times at which the cookie was accessed.
 */
public class CookieImpl implements Cookie {

    private String cookieId;
    private List<Date> timestamps;

    /**
     * Constructs a new CookieImpl with the specified identifier.
     *
     * @param cookieId the unique identifier for this cookie
     */
    public CookieImpl(String cookieId) {
        this.cookieId = cookieId;
        this.timestamps = new ArrayList<>();
    }

    /**
     * Adds a timestamp to the list of access times for this cookie.
     *
     * @param timestamp the Date object to be added to the cookie's timestamps
     */
    @Override
    public void addTimestamp(Date timestamp) {
        this.timestamps.add(timestamp);
    }

    /**
     * Returns the unique identifier of this cookie.
     *
     * @return the cookie identifier as a String
     */
    @Override
    public String getCookieId() {
        return cookieId;
    }

    /**
     * Returns the list of access times for this cookie.
     *
     * @return a List of Date objects representing the timestamps
     */
    public List<Date> getTimestamps() {
        return timestamps;
    }

    /**
     * Compares this cookie with the specified object for equality.
     *
     * @param o the object to be compared for equality with this cookie
     * @return true if the specified object is equal to this cookie
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CookieImpl)) return false;
        CookieImpl cookie = (CookieImpl) o;
        return cookieId.equals(cookie.cookieId);
    }

    /**
     * Returns the hash code value for this cookie.
     *
     * @return a hash code value for this cookie
     */
    @Override
    public int hashCode() {
        return cookieId.hashCode();
    }

    /**
     * Returns a string representation of the cookie.
     *
     * @return a string representation of the cookie, including its identifier and timestamps
     */
    @Override
    public String toString() {
        return "CookieImpl{" +
                "cookieId='" + cookieId + '\'' +
                ", timestamps=" + timestamps +
                '}';
    }
}
