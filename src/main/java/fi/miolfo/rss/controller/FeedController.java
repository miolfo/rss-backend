package fi.miolfo.rss.controller;

import fi.miolfo.rss.exception.FeedNotFoundException;
import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.service.FeedService;
import fi.miolfo.rss.service.RssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    @Autowired
    private RssService rssService;

    @Autowired
    private FeedService feedService;

    private static final Logger log = LoggerFactory.getLogger(FeedController.class);

    @GetMapping(
            value = "/{id}/refresh"
    )
    public ResponseEntity refreshFeedItems(@PathVariable int id) {
        try {
            rssService.refreshFeedItems(id);
        } catch (FeedNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    /*
    @GetMapping(
            value = "/items",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<FeedItem>> getFeedItems() {

    }
    */

    @PostMapping(
            value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Feed createFeed(@RequestBody Feed feed) {

        return feedService.save(feed);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Feed> getFeed(@PathVariable int id) {

        final var feed = feedService.getFeed(id);
        return ResponseEntity.of(feed);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Feed> deleteFeed(@PathVariable int id) {

        final var feed = feedService.getFeed(id);
        if(feed.isPresent()) {
            feedService.deleteFeed(feed.get());
            return ResponseEntity.ok(feed.get());
        } else {
            return new ResponseEntity<Feed>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Feed> updateFeed(@RequestBody Feed feed, @PathVariable int id) {

        if(feed.getId() != id) {
            return new ResponseEntity<Feed>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(feedService.save(feed));
    }

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Feed> getFeeds() {
        return feedService.getFeeds();
    }
}
