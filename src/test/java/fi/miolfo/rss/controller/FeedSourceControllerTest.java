package fi.miolfo.rss.controller;

import fi.miolfo.rss.model.persistence.FeedSource;
import fi.miolfo.rss.repository.FeedSourceRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static fi.miolfo.rss.util.RssTestUtils.getApiKeyHeaderInterceptor;
import static fi.miolfo.rss.util.RssTestUtils.getFeedApiUrl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FeedSourceControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Value("${miolfo.rss.api-key}")
    private String API_KEY;

    @BeforeAll
    public void setUpHeader() {
        restTemplate.getRestTemplate().getInterceptors().add(getApiKeyHeaderInterceptor(API_KEY));
    }

    @Test
    public void testGetSources() {
        final var res = this.restTemplate.getForEntity(
                getFeedApiUrl(port, "/api/v1/feed/1/source"),
                FeedSource[].class);
        assertNotNull(res.getBody());
        assertEquals(2, res.getBody().length);
        assertEquals("https://www.hs.fi/rss/tuoreimmat.xml", res.getBody()[0].getSource());
        assertEquals("https://feeds.yle.fi/uutiset/v1/majorHeadlines/YLE_UUTISET.rss", res.getBody()[1].getSource());
    }

    @Test
    public void testAddAndDeleteSource() {
        final FeedSource feedSource = new FeedSource();
        feedSource.setSource("mock-source");

        final var postRes = this.restTemplate.postForEntity(
                getFeedApiUrl(port, "/api/v1/feed/1/source"),
                feedSource,
                FeedSource.class
        );
        assertNotNull(postRes.getBody());
        assertEquals("mock-source", postRes.getBody().getSource());

        final var listRes = this.restTemplate.getForEntity(
                getFeedApiUrl(port, "/api/v1/feed/1/source"),
                FeedSource[].class);
        assertNotNull(listRes.getBody());
        assertEquals(3, listRes.getBody().length);

        this.restTemplate.delete(getFeedApiUrl(port, "/api/v1/feed/1/source/" + postRes.getBody().getId()));

        final var listRes2 = this.restTemplate.getForEntity(
                getFeedApiUrl(port, "/api/v1/feed/1/source"),
                FeedSource[].class);
        assertNotNull(listRes2.getBody());
        assertEquals(2, listRes2.getBody().length);
    }

    @Test
    public void testPutSource() {
        final FeedSource feedSource = new FeedSource();
        feedSource.setSource("mock-source");
        feedSource.setId(1);
        this.restTemplate.put(getFeedApiUrl(port, "/api/v1/feed/1/source/1"), feedSource);

        assertEquals("mock-source", feedSourceRepository.findById(1).get().getSource());

        feedSource.setSource("https://www.hs.fi/rss/tuoreimmat.xml");
        this.restTemplate.put(getFeedApiUrl(port, "/api/v1/feed/1/source/1"), feedSource);
        assertEquals("https://www.hs.fi/rss/tuoreimmat.xml", feedSourceRepository.findById(1).get().getSource());
    }
}
