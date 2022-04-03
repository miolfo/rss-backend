package fi.miolfo.rss.service;

import fi.miolfo.rss.model.persistence.FeedSource;

import java.util.List;
import java.util.Optional;

public interface FeedSourceService {

    public Optional<List<FeedSource>> getFeedSources(int feedId);

    public Optional<FeedSource> getFeedSource(int sourceId);

    public Optional<FeedSource> addFeedSource(int feedId, FeedSource feedSource);

    public Optional<FeedSource> updateFeedSource(int feedId, FeedSource feedSource);

    public void deleteFeedSource(FeedSource feedSource);
}
