package fi.miolfo.rss.controller;

import fi.miolfo.rss.model.CheckUrlDto;
import fi.miolfo.rss.model.persistence.FeedSource;
import fi.miolfo.rss.service.FeedSourceService;
import fi.miolfo.rss.service.RssService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedSourceController {

    @Autowired
    private FeedSourceService feedSourceService;

    @Autowired
    private RssService rssService;

    @PostMapping(
            value = "/check-url",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Mono<ResponseEntity<Boolean>> checkValidRssUrl(@RequestBody CheckUrlDto urlDto) {

        return rssService.checkValidRssUrl(urlDto.url())
                .map(ResponseEntity::ok);
    }

    @GetMapping(
            value = "/{feedId}/source",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FeedSource>> getFeedSources(@PathVariable int feedId) {
        return ResponseEntity.of(feedSourceService.getFeedSources(feedId));
    }

    @PostMapping(
            value="/{feedId}/source",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FeedSource> addFeedSource(@PathVariable int feedId, @RequestBody FeedSource feedSource) {

        return ResponseEntity.of(feedSourceService.addFeedSource(feedId, feedSource));
    }

    @PutMapping(
            value = "/{feedId}/source/{sourceId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FeedSource> updateFeedSource(@PathVariable int feedId, @PathVariable int sourceId, @RequestBody FeedSource feedSource) {

        if(sourceId != feedSource.getId()) {
            return new ResponseEntity<FeedSource>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.of(feedSourceService.updateFeedSource(feedId, feedSource));
    }

    @DeleteMapping(
            value = "/{feedId}/source/{sourceId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FeedSource> deleteFeedSource(@PathVariable int feedId, @PathVariable int sourceId) {

        final var feedSource = feedSourceService.getFeedSource(sourceId);
        if(feedSource.isEmpty()) {
            return new ResponseEntity<FeedSource>(HttpStatus.NOT_FOUND);
        }
        if(feedSource.get().getFeed().getId() != feedId) {
            return new ResponseEntity<FeedSource>(HttpStatus.BAD_REQUEST);
        }

        feedSourceService.deleteFeedSource(feedSource.get());
        return ResponseEntity.of(feedSource);
    }
}
