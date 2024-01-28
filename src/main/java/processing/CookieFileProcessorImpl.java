package processing;

import model.Cookie;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;

/**
 * Implementation of the CookieFileProcessor interface that processes cookie files.
 * It utilizes a CSVCookieFileReader to read and analyze the cookie data from files.
 */
public class CookieFileProcessorImpl implements CookieFileProcessor {
    private static final Logger LOGGER = Logger.getLogger(CSVCookieFileReader.class.getName());
    private final CSVCookieFileReader csvCookieFileReader;

    /**
     * Constructs a new CookieFileProcessorImpl with a default CSVCookieFileReader.
     */
    public CookieFileProcessorImpl() {
        this.csvCookieFileReader = new CSVCookieFileReader();
    }

    /**
     * Parses the arguments to extract file paths and the target date.
     * Then it processes the files to find the most active cookies for the specified date. <p>
     *     Expected correct format: <p>
     *
     *  args[0] - tag "-f" tag followed by a list of file paths<p>
     *  args[1] - path to cookie file at least one<p>
     *  args[n] - tag "-d" following the file list<p>
     *  args[n+1] - the date to search for the most popular cookie file in YYYY-mm-DD format<p>
     */
    public void parse(String[] args) {
        if (args != null && args.length > 0) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                ArrayList<File> files = new ArrayList<>();
                Date date = null;

                // Анализ аргументов командной строки
                for (int i = 0; i < args.length; i++) {
                    if ("-f".equals(args[i])) {
                        i++;
                        while (!args[i].equals("-d")) {
                            files.add(new File(args[i]));
                            i++;
                        }
                    }
                    if ("-d".equals(args[i])) {
                        try {
                            date = dateFormat.parse(args[++i]);
                        } catch (ParseException e) {
                            LOGGER.log(Level.SEVERE, e.getMessage());
                        }
                    }
                }

                if (!files.isEmpty() && date != null) {
                    Collection<Cookie> mostActiveCookies = getMostActiveCookies(files, date);

                    for (Cookie cookie : mostActiveCookies) {
                        System.out.println(cookie.getCookieId());
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Usage: CookieAnalyzerApp -f <path-to-cookie-file> -d <date>");
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing the cookie log: " + e.getMessage());
            }
        } else {
            LOGGER.log(Level.WARNING, "Incorrect argument list");
        }
    }

    /**
     * Processes the provided collection of files and identifies the most active cookies for the specified date.
     * The most active cookies are determined by the frequency of their appearance on that particular date.
     *
     * @param files the collection of cookie log files to be analyzed
     * @param date the date for which to find the most active cookies
     * @return a collection of the most active cookies for the specified date
     */
    @Override
    public Collection<Cookie> getMostActiveCookies(Collection<File> files, Date date) {
        Collection<Cookie> cookies = csvCookieFileReader.readFile(files, date);
        Map<Cookie, Integer> cookieCount = new HashMap<>();
        int maxCount = 0;

        // Подсчитываем количество вхождений каждого куки
        for (Cookie cookie : cookies) {
            int count = cookie.getTimestamps().size();
            cookieCount.put(cookie, count);
            maxCount = Math.max(maxCount, count);
        }

        // Собираем список самых активных куки
        List<Cookie> mostActiveCookies = new ArrayList<>();
        for (Map.Entry<Cookie, Integer> entry : cookieCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostActiveCookies.add(entry.getKey());
            }
        }

        return mostActiveCookies;
    }
}
