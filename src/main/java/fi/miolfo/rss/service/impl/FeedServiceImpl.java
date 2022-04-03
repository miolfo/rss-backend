package fi.miolfo.rss.service.impl;

import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.repository.FeedRepository;
import fi.miolfo.rss.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Override
    public Feed createFeed(Feed feed) {
        return feedRepository.save(feed);
    }
}
