package fi.miolfo.rss.util;

import org.springframework.http.client.ClientHttpRequestInterceptor;

public class RssTestUtils {

    public static String getFeedApiUrl(int port, String url) {
        return "http://localhost:" + port + url;
    }

    public static ClientHttpRequestInterceptor getApiKeyHeaderInterceptor(String apiKey) {
        return (request, body, execution) -> {
            request.getHeaders().add("API-KEY", apiKey);
            return execution.execute(request, body);
        };
    }
}
