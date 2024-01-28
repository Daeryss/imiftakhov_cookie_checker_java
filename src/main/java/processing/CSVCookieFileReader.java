package processing;

import model.Cookie;
import model.CookieImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the CookieFileReader interface for reading cookies from CSV files.
 * It parses the CSV data and creates Cookie instances for each entry that matches the specified date.
 */
public class CSVCookieFileReader implements CookieFileReader {
    private static final Logger LOGGER = Logger.getLogger(CSVCookieFileReader.class.getName());

    private static final String CSV_SPLIT_BY = ",";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    /**
     * Reads cookie data from the given collection of CSV files and returns cookies active on the target date.
     *
     * @param files      the collection of CSV files to be read
     * @param targetDate the date for which to filter the cookies
     * @return a collection of Cookie objects active on the specified date
     */
    @Override
    public Collection<Cookie> readFile(Collection<File> files, Date targetDate) {
        Map<String, Cookie> cookies = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.DATE, 1);
        Date nextDay = calendar.getTime();

        for (File file : files) {
            if (isValidFile(file)) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    LOGGER.log(Level.INFO, "Start reading file " + file.getAbsolutePath());
                    // Flag for checking the first line (header):
                    boolean isFirstLine = true;

                    String line;
                    while ((line = br.readLine()) != null) {
                        if (isFirstLine) {
                            isFirstLine = false; // Skip the first line and continue
                            continue;
                        }

                        String[] cookieEntry = line.split(CSV_SPLIT_BY);
                        Date timestamp = null;
                        if (cookieEntry.length == 2) {
                            try {
                                timestamp = DATE_FORMAT.parse(cookieEntry[1]);
                            } catch (ParseException e) {
                                LOGGER.log(Level.SEVERE, "Date parse exception in file: " + file.getAbsolutePath() +
                                        ", incorrect date format: " + cookieEntry[1]);
                                continue;
                            }
                        } else {
                            LOGGER.log(Level.SEVERE, "Incorrect file content: " + line);
                            continue;
                        }

                        // Stop reading the file if the next day's record is reached
                        if (timestamp.after(nextDay)) {
                            break;
                        }

                        if (isSameDay(timestamp, targetDate)) {
                            String cookieId = cookieEntry[0];
                            Cookie cookie = cookies.getOrDefault(cookieId, new CookieImpl(cookieId));
                            cookie.addTimestamp(timestamp);
                            cookies.put(cookieId, cookie);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (file.isDirectory()) {
                    LOGGER.log(Level.SEVERE, "File cannot be read because it is a directory " +
                            file.getAbsolutePath());
                } else if (file.canRead()) {
                    LOGGER.log(Level.SEVERE, "File cannot be read " + file.getAbsolutePath());
                } else if (!file.getName().endsWith(".csv")) {
                    LOGGER.log(Level.SEVERE, "File cannot be read, incorrect file format " +
                            file.getAbsolutePath());
                }
            }
        }

        return cookies.values();
    }

    /**
     * Checks if two dates are on the same calendar day.
     *
     * @param date1 the first date to compare
     * @param date2 the second date to compare
     * @return true if both dates are on the same day, false otherwise
     */
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Validates if the provided file is a readable CSV file.
     *
     * @param file the file to check
     * @return true if the file is a valid CSV file, false otherwise
     */
    private static boolean isValidFile(File file) {
        return file.isFile() && file.canRead() && file.getName().endsWith(".csv");
    }
}

