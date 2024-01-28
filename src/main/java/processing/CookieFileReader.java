package processing;

import model.Cookie;

import java.io.File;
import java.util.Collection;
import java.util.Date;

/**
 * The CookieFileReader interface specifies the method for reading cookie data from files.
 * It is responsible for extracting cookie information from a collection of files for a specific target date.
 */
public interface CookieFileReader {

    /**
     * Reads cookie data from a collection of files and returns the cookies that have activity
     * on the specified target date.
     *
     * @param files the collection of files from which to read the cookie data
     * @param targetDate the date for which the cookie data is to be extracted
     * @return a collection of Cookie objects with their activity on the target date
     */
    Collection<Cookie> readFile(Collection<File> files, Date targetDate);
}
