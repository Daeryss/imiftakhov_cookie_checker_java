import processing.CookieFileProcessorImpl;

public class CookieAnalyzerApp {
    public static void main(String[] args) {
        CookieFileProcessorImpl processor = new CookieFileProcessorImpl();
        processor.parse(args);
    }
}
