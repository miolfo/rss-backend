package fi.miolfo.rss.service.impl;

import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.model.persistence.FeedSource;
import fi.miolfo.rss.repository.FeedRepository;
import fi.miolfo.rss.repository.FeedSourceRepository;
import fi.miolfo.rss.service.FeedSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedSourceServiceImpl implements FeedSourceService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Override
    public Optional<FeedSource> updateFeedSource(int feedId, FeedSource feedSource) {
        final var feed = feedRepository.findById(feedId);
        if(feed.isEmpty()) {
            return Optional.empty();
        }
        feedSource.setFeed(feed.get());
        return Optional.of(feedSourceRepository.save(feedSource));
    }

    @Override
    public Optional<FeedSource> getFeedSource(int sourceId) {
        return feedSourceRepository.findById(sourceId);
    }

    @Override
    public void deleteFeedSource(FeedSource feedSource) {
        feedSourceRepository.delete(feedSource);
    }

    @Override
    public Optional<FeedSource> addFeedSource(int feedId, FeedSource feedSource) {
        final var feed = feedRepository.findById(feedId);
        if(feed.isEmpty()) {
            return Optional.empty();
        }
        feedSource.setFeed(feed.get());
        return Optional.of(feedSourceRepository.save(feedSource));
    }

    @Override
    public Optional<List<FeedSource>> getFeedSources(int feedId) {
        return feedRepository.findById(feedId).map(Feed::getFeedSources);
    }
}
