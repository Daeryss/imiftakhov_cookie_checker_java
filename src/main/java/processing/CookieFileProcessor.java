package processing;

import model.Cookie;

import java.io.File;
import java.util.Collection;
import java.util.Date;

/**
 * The CookieFileProcessor interface defines the method for processing cookie files.
 * It is responsible for analyzing the cookie access data within files and determining
 * the most active cookies for a given date.
 */
public interface CookieFileProcessor {

    /**
     * Analyzes the given collection of cookie files and returns the most active cookies for the specified date.
     * The most active cookies are determined based on the frequency of their occurrence on the given date.
     *
     * @param files the collection of files to be analyzed
     * @param date the specific date for which the most active cookies should be determined
     * @return a collection of Cookie objects that were the most active on the specified date
     */
    Collection<Cookie> getMostActiveCookies(Collection<File> files, Date date);
}
