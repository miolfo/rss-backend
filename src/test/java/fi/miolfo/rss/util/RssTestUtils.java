package fi.miolfo.rss.util;

public class RssTestUtils {

    public static String getFeedApiUrl(int port, String url) {
        return "http://localhost:" + port + url;
    }
}
