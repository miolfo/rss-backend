package fi.miolfo.rss.service;

import fi.miolfo.rss.model.FeedItem;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RssService {

    public Mono<List<FeedItem>> getFeed();
}
