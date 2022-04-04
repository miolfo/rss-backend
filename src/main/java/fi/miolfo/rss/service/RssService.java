package fi.miolfo.rss.service;

import fi.miolfo.rss.exception.FeedNotFoundException;
import fi.miolfo.rss.model.xml.RssRoot;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface RssService {

    public void refreshFeedItems(int feedId) throws FeedNotFoundException;

    public Mono<Optional<RssRoot>> getFeed(String url);
}
