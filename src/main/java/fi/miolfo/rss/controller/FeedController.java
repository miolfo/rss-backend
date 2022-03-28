package fi.miolfo.rss.controller;

import fi.miolfo.rss.model.FeedItem;
import fi.miolfo.rss.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FeedController {

    @Autowired
    private RssService rssService;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> index() {
        return rssService.getFeed();
    }
}
