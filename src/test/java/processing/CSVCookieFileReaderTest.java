package processing;

import model.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CSVCookieFileReaderTest {

    private CSVCookieFileReader reader;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        reader = new CSVCookieFileReader();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    void testReadFileWithCorrectData(@TempDir Path tempDir) throws IOException, ParseException {
        File tempFile = tempDir.resolve("cookie_correct_data.csv").toFile();
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println("cookie,timestamp");
            out.println("cookie1,2018-12-09T14:19:00+00:00");
            out.println("cookie2,2018-12-09T10:13:00+00:00");
        }

        Date targetDate = dateFormat.parse("2018-12-09");
        Collection<Cookie> cookies = reader.readFile(Collections.singletonList(tempFile), targetDate);

        assertEquals(2, cookies.size(), "Should read 2 cookies for the target date.");
    }


    @Test
    void testReadFileWithEmptyLinesAndIncorrectFormat(@TempDir Path tempDir) throws IOException, ParseException {
        File tempFile = tempDir.resolve("cookie_incorrect_data.csv").toFile();
        try (PrintWriter out = new PrintWriter(tempFile)) {
            out.println("cookie,timestamp");
            out.println("cookie1,2018-12-09T14:19:00+00:00");
            out.println(","); // Empty line with commas
            out.println("cookie2,not-a-date"); // Incorrect date format
        }

        Date targetDate = dateFormat.parse("2018-12-09");
        Collection<Cookie> cookies = reader.readFile(Collections.singletonList(tempFile), targetDate);

        assertEquals(1, cookies.size(), "Should read 1 valid cookie, ignoring invalid lines.");
        assertTrue(cookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie1")),
                "Valid cookie 'cookie1' should be present.");
        assertFalse(cookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie2")),
                "Invalid cookie 'cookie2' should be ignored due to incorrect date format.");
    }
}
