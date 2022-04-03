package fi.miolfo.rss.controller;

import fi.miolfo.rss.model.FeedItem;
import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.service.FeedService;
import fi.miolfo.rss.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    @Autowired
    private RssService rssService;

    @Autowired
    private FeedService feedService;

    @GetMapping(
            value = "/items",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<FeedItem>> getFeedItems() {
        return rssService.getFeed();
    }

    @PostMapping(
            value="/",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Feed createFeed(@RequestBody Feed feed) {
        return feedService.createFeed(feed);
    }
}
