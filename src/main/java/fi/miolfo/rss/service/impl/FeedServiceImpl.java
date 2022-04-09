package fi.miolfo.rss.service.impl;

import fi.miolfo.rss.mapper.FeedItemToFeedItemDtoMapper;
import fi.miolfo.rss.mapper.RssToFeedItemMapper;
import fi.miolfo.rss.model.FeedItemDto;
import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.model.persistence.FeedItem;
import fi.miolfo.rss.model.persistence.FeedSource;
import fi.miolfo.rss.repository.FeedItemRepository;
import fi.miolfo.rss.repository.FeedRepository;
import fi.miolfo.rss.repository.FeedSourceRepository;
import fi.miolfo.rss.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Autowired
    private FeedItemRepository feedItemRepository;

    @Autowired
    private FeedItemToFeedItemDtoMapper feedItemToFeedItemDtoMapper;

    @Override
    public Optional<Feed> getFeed(int id) {
        return feedRepository.findById(id);
    }

    @Override
    public List<Feed> getFeeds() {
        return (List<Feed>) feedRepository.findAll();
    }

    @Override
    public List<FeedItemDto> getFeedItems(Feed feed, int page, int count) {
        List<FeedItem> items = feedItemRepository.findAllByFeedSourceInOrderByPubDateDesc(
                feed.getFeedSources(), Pageable.ofSize(count).withPage(page));
        return items.stream().map(feedItemToFeedItemDtoMapper::feedItemToFeedItemDto).collect(Collectors.toList());
    }

    @Override
    public void deleteFeed(Feed feed) {
        feed.getFeedSources().forEach(feedSource -> {
            feedItemRepository.deleteAll(feedSource.getFeedItems());
        });
        feedSourceRepository.deleteAll(feed.getFeedSources());
        feedRepository.delete(feed);
    }

    @Override
    public Feed save(Feed feed) {
        return feedRepository.save(feed);
    }
}
