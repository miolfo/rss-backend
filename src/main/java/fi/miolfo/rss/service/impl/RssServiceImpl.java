package fi.miolfo.rss.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fi.miolfo.rss.exception.FeedNotFoundException;
import fi.miolfo.rss.mapper.RssToFeedItemMapper;
import fi.miolfo.rss.model.FeedItem;
import fi.miolfo.rss.model.xml.RssRoot;
import fi.miolfo.rss.service.FeedService;
import fi.miolfo.rss.service.FeedSourceService;
import fi.miolfo.rss.service.RssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RssServiceImpl implements RssService {

    private static final String HS_XML = "https://www.hs.fi/rss/tuoreimmat.xml";

    private static final Logger log = LoggerFactory.getLogger(RssServiceImpl.class);

    @Autowired
    private FeedService feedService;

    @Autowired
    private FeedSourceService feedSourceService;

    @Autowired
    private RssToFeedItemMapper rssToFeedItemMapper;

    @Autowired
    private WebClient rssWebClient;

    @Override
    public void refreshFeedItems(int feedId) throws FeedNotFoundException {

        final var feedOpt = feedService.getFeed(feedId);
        if(feedOpt.isEmpty()) {
            log.error("Unable to refresh non existing feed " + feedId);
            throw new FeedNotFoundException();
        }
        log.info("Starting refresh on feed " + feedId);
        final var sources = feedOpt.get().getFeedSources();
        final var monos = sources.stream().map(it -> getFeed(it.getSource())).collect(Collectors.toList());
        Flux.merge(monos).subscribe(this::handleRss);
    }

    @Override
    public Mono<Optional<RssRoot>> getFeed(String url) {

        var spec = rssWebClient.get().uri(url);
        return spec.exchangeToMono(res -> res.bodyToMono(String.class).map(this::readToRssRoot));
    }

    private void handleRss(Optional<RssRoot> rssRoot) {
        log.info("fetched something");
    }

    private Optional<RssRoot> readToRssRoot(String xml) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            RssRoot root = xmlMapper.readValue(xml, RssRoot.class);
            return Optional.of(root);
        } catch (JsonProcessingException e) {
            log.error("Error processing json", e);
            return Optional.empty();
        }
    }
}
