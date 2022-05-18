package fi.miolfo.rss.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fi.miolfo.rss.exception.FeedNotFoundException;
import fi.miolfo.rss.mapper.AtomRootToRssRootMapper;
import fi.miolfo.rss.mapper.RssToFeedItemMapper;
import fi.miolfo.rss.model.UpdateStatus;
import fi.miolfo.rss.model.persistence.Feed;
import fi.miolfo.rss.model.persistence.FeedItem;
import fi.miolfo.rss.model.persistence.FeedSource;
import fi.miolfo.rss.model.xml.AtomRoot;
import fi.miolfo.rss.model.xml.RssRoot;
import fi.miolfo.rss.repository.FeedItemRepository;
import fi.miolfo.rss.repository.FeedRepository;
import fi.miolfo.rss.repository.FeedSourceRepository;
import fi.miolfo.rss.service.FeedService;
import fi.miolfo.rss.service.FeedSourceService;
import fi.miolfo.rss.service.RssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RssServiceImpl implements RssService {

    private static final Logger log = LoggerFactory.getLogger(RssServiceImpl.class);

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedSourceService feedSourceService;

    @Autowired
    private FeedItemRepository feedItemRepository;

    @Autowired
    private FeedSourceRepository feedSourceRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private RssToFeedItemMapper rssToFeedItemMapper;

    @Autowired
    private WebClient rssWebClient;

    @Autowired
    private XmlMapper rssXmlMapper;

    @Autowired
    private AtomRootToRssRootMapper atomRootToRssRootMapper;

    @Override
    public void refreshFeedItems(int feedId) throws FeedNotFoundException {

        final var feedOpt = feedService.getFeed(feedId);

        if(feedOpt.isEmpty()) {

            log.error("Unable to refresh non existing feed " + feedId);
            throw new FeedNotFoundException();
        }

        log.info("Starting refresh on feed " + feedId);
        final var sources = feedOpt.get().getFeedSources();
        final var monos =
                sources.stream()
                        .map(source -> getFeed(source.getSource())
                                .map(rssRoot -> Tuples.of(source, rssRoot)))
                        .collect(Collectors.toList());
        Flux.merge(monos).subscribe(tuple -> handleRss(tuple.getT2(), feedOpt.get(), tuple.getT1()));
    }

    @Override
    public Mono<Optional<RssRoot>> getFeed(String url) {
        var spec = rssWebClient.get().uri(url);
        return spec.exchangeToMono(res -> {
            if(res.statusCode() == HttpStatus.OK) {
                return res.bodyToMono(String.class).map(this::readToRssRoot);
            } else {
                log.warn("Unexpected status code " + res.statusCode() + " from url " + url);
                return Mono.just(Optional.empty());
            }
        });
    }

    @Override
    public Mono<Boolean> checkValidRssUrl(String url) {

        var spec = rssWebClient.get().uri(url);
        return spec.exchangeToMono(res -> {
            if(res.statusCode() == HttpStatus.OK) {
                return res.bodyToMono(String.class).map(this::readToRssRoot).map(Optional::isPresent);
            } else {
                log.warn("Unexpected status code " + res.statusCode() + " from url " + url);
                return Mono.just(false);
            }
        });
    }

    private void handleRss(Optional<RssRoot> rssRoot, Feed feed, FeedSource feedSource) {

        final LocalDateTime now = LocalDateTime.now();

        if(rssRoot.isPresent() && rssRoot.get().getChannel() != null) {

            AtomicInteger addedCount = new AtomicInteger();
            List<FeedItem> feedItems = rssRoot.get().getChannel().getItems().stream()
                    .map(item -> rssToFeedItemMapper.rssItemToFeedItem(item, feedSource)).toList();
            feedItems.forEach(feedItem -> {
                Optional<FeedItem> existing = feedItemRepository.findByGuid(feedItem.getGuid());
                if(existing.isEmpty()) {
                    addedCount.getAndIncrement();
                    feedItemRepository.save(feedItem);
                }
            });

            feed.setLastUpdated(now);
            feedSource.setLastUpdated(now);
            feedSource.setUpdateStatus(UpdateStatus.SUCCESS);
            log.info("Updated feed " + feed.getId() + " with " + addedCount + " items");
        } else {

            log.warn("Rss object was not parsed, check logs for error, feed source " + feedSource.getId());
            feedSource.setUpdateStatus(UpdateStatus.FAIL);
        }
        feedSourceRepository.save(feedSource);
        feedRepository.save(feed);
    }

    private Optional<RssRoot> readToRssRoot(String xml) {

        try {
            if(isAtom(xml)) {
                return Optional.of(readAtomToRssRoot(xml));
            } else {
                RssRoot root = rssXmlMapper.readValue(xml, RssRoot.class);
                return Optional.of(root);
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing json", e);
            return Optional.empty();
        }
    }

    private RssRoot readAtomToRssRoot(String xml) throws JsonProcessingException {
        AtomRoot root = rssXmlMapper.readValue(xml, AtomRoot.class);
        return atomRootToRssRootMapper.atomRootToRssRoot(root);
    }

    private boolean isAtom(String xml) {
        //TODO: Actually parse into an object instead of doing this hacky method
        final String ATOM_STRING = "xmlns=\"http://www.w3.org/2005/Atom\"";
        return xml.contains(ATOM_STRING);
    }
}
