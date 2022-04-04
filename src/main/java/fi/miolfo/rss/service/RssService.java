package fi.miolfo.rss.service;

import fi.miolfo.rss.exception.FeedNotFoundException;
import fi.miolfo.rss.model.FeedItem;
import fi.miolfo.rss.model.xml.RssRoot;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface RssService {

    public void refreshFeedItems(int feedId) throws FeedNotFoundException;

    public Mono<Optional<RssRoot>> getFeed(String url);
}
