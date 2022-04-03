package fi.miolfo.rss.service.impl;

import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.repository.FeedRepository;
import fi.miolfo.rss.repository.FeedSourceRepository;
import fi.miolfo.rss.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Override
    public Optional<Feed> getFeed(int id) {
        return feedRepository.findById(id);
    }

    @Override
    public List<Feed> getFeeds() {
        return (List<Feed>) feedRepository.findAll();
    }

    @Override
    public void deleteFeed(Feed feed) {
        feedSourceRepository.deleteAll(feed.getFeedSources());
        feedRepository.delete(feed);
    }

    @Override
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }
}
