package fi.miolfo.rss.controller;

import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.util.RssTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetFeed() {
        final var res = this.restTemplate.getForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/1"), Feed.class);
        assertNotNull(res.getBody());
        assertEquals(1, res.getBody().getId());
        assertEquals("Sample Feed", res.getBody().getName());
    }

    @Test
    public void testGetFeed404() {
        final var res = this.restTemplate.getForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/123"), Feed.class);
        assertEquals(res.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testPostFeed() {
        final Feed feed = new Feed();
        feed.setName("news feed");
        final var res = this.restTemplate.postForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/"), feed, Feed.class);
        assertNotNull(res.getBody());
        assertEquals("news feed", res.getBody().getName());

        final var addedFeed = this.restTemplate.getForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/" + res.getBody().getId()), Feed.class);
        assertNotNull(addedFeed.getBody());
        assertEquals(res.getBody().getId(), addedFeed.getBody().getId());
        assertEquals("news feed", addedFeed.getBody().getName());
    }

    @Test
    public void testDeleteFeed() {
        final Feed feed = new Feed();
        feed.setName("news feed");
        final var res = this.restTemplate.postForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/"), feed, Feed.class);
        assertNotNull(res.getBody());
        assertEquals("news feed", res.getBody().getName());

        this.restTemplate.delete(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/" + res.getBody().getId()));
        final var deletedRes = this.restTemplate.getForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/" + res.getBody().getId()), Feed.class);
        assertEquals(deletedRes.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateFeed() {
        final Feed updatedFeed = new Feed();
        updatedFeed.setName("test name");
        updatedFeed.setId(2);
        this.restTemplate.put(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/2"), updatedFeed);

        final var updatedRes = this.restTemplate.getForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/2"), Feed.class);
        assertNotNull(updatedRes.getBody());
        assertEquals(2, updatedRes.getBody().getId());
        assertEquals("test name", updatedRes.getBody().getName());
    }

    @Test
    public void testGetFeeds() {
        final var res = this.restTemplate.getForEntity(RssTestUtils.getFeedApiUrl(port, "/api/v1/feed/"), List.class);
        assertNotNull(res.getBody());
        assertEquals(3, res.getBody().size());
    }
}
