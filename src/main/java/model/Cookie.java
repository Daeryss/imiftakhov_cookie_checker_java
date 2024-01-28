package model;

import java.util.Collection;
import java.util.Date;

/**
 * The Cookie interface defines the structure of a cookie object.
 * It provides methods to manipulate and retrieve information about the cookie,
 * such as adding timestamps and retrieving the cookie's unique identifier.
 */
public interface Cookie {

    /**
     * Adds a timestamp to the collection of times when the cookie was accessed.
     *
     * @param timestamp the Date object representing the time of access
     */
    void addTimestamp(Date timestamp);

    /**
     * Retrieves the unique identifier of the cookie.
     *
     * @return the cookie's unique identifier as a String
     */
    String getCookieId();

    /**
     * Retrieves a collection of timestamps representing the times when the cookie was accessed.
     *
     * @return a Collection of Date objects representing the access times
     */
    Collection<Date> getTimestamps();
}
