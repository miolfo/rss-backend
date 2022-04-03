package fi.miolfo.rss.service;

import fi.miolfo.rss.model.persistence.Feed;

import java.util.List;
import java.util.Optional;

public interface FeedService {

    public Feed save(Feed feed);

    public Optional<Feed> getFeed(int id);

    public void deleteFeed(Feed feed);

    public List<Feed> getFeeds();
}
