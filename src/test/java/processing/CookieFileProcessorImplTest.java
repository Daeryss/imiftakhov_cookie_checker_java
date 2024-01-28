package processing;

import model.Cookie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CookieFileProcessorImplTest {

    private CookieFileProcessorImpl processor;

    @BeforeEach
    public void setUp() {
        processor = new CookieFileProcessorImpl();
    }

    @Test
    public void testGetMostActiveCookies() throws Exception {
        List<File> files = List.of(new File("src/test/resources/cookie_one_line.csv"));

        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-09");
        Collection<Cookie> mostActiveCookies = processor.getMostActiveCookies(files, testDate);

        assertTrue(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie1")),
                "The expected most active cookie 'cookie1' was not found.");

        int expectedActiveCookieCount = 1;
        assertEquals(expectedActiveCookieCount, mostActiveCookies.size(),
                "The number of most active cookies does not match the expected value.");
    }

    @Test
    public void testSeveralActiveCookieInOneFile() throws Exception {
        List<File> files = List.of(new File("src/test/resources/cookie_three_most_active_cookies.csv"));

        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-09");
        Collection<Cookie> mostActiveCookies = processor.getMostActiveCookies(files, testDate);

        assertTrue(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie1")),
                "The expected most active cookie 'cookie1' was not found.");
        assertTrue(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie2")),
                "The expected most active cookie 'cookie2' was not found.");
        assertTrue(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie3")),
                "The expected most active cookie 'cookie3' was not found.");

        int expectedActiveCookieCount = 3;
        assertEquals(expectedActiveCookieCount, mostActiveCookies.size(),
                "The number of most active cookies does not match the expected value.");
    }

    @Test
    public void testReadSeveralFiles() throws Exception{
        List<File> files = List.of(new File("src/test/resources/cookie_three_most_active_cookies.csv"),
                new File("src/test/resources/cookie_one_line.csv"));

        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-09");
        Collection<Cookie> mostActiveCookies = processor.getMostActiveCookies(files, testDate);

        assertTrue(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie1")),
                "The expected most active cookie 'cookie1' was not found.");

        assertFalse(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie2")),
                "The expected most active cookie 'cookie2' was found.");
        assertFalse(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("cookie3")),
                "The expected most active cookie 'cookie3' was  found.");

        int expectedActiveCookieCount = 1;
        assertEquals(expectedActiveCookieCount, mostActiveCookies.size(),
                "The number of most active cookies does not match the expected value.");
    }

    @Test
    public void testGivenSource() throws Exception {
        List<File> files = List.of(new File("src/test/resources/cookie_given_source.csv"));

        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-09");
        Collection<Cookie> mostActiveCookies = processor.getMostActiveCookies(files, testDate);

        assertTrue(mostActiveCookies.stream().anyMatch(cookie -> cookie.getCookieId().equals("AtY0laUfhglK3lC7")),
                "The expected most active cookie 'AtY0laUfhglK3lC7' was not found.");

        int expectedActiveCookieCount = 1;
        assertEquals(expectedActiveCookieCount, mostActiveCookies.size(),
                "The number of most active cookies does not match the expected value.");
    }

    @Test
    public void testEmptyLineFile() throws Exception {
        List<File> files = List.of(new File("src/test/resources/cookie_empty_line.csv"));

        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-09");
        Collection<Cookie> mostActiveCookies = processor.getMostActiveCookies(files, testDate);

        int expectedActiveCookieCount = 0;
        assertEquals(expectedActiveCookieCount, mostActiveCookies.size(),
                "The number of most active cookies does not match the expected value.");
    }

    @Test
    public void incorrectInput() throws Exception {
        List<File> files = List.of(new File("src/test/resources/incorrect_format.txt"),
                new File("src/test/resources/directory"));

        Date testDate = new SimpleDateFormat("yyyy-MM-dd").parse("2018-12-09");
        Collection<Cookie> mostActiveCookies = processor.getMostActiveCookies(files, testDate);

        int expectedActiveCookieCount = 0;
        assertEquals(expectedActiveCookieCount, mostActiveCookies.size(),
                "The number of most active cookies does not match the expected value.");
    }
}