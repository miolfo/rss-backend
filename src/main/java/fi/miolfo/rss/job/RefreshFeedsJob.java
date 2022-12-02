package fi.miolfo.rss.job;

import fi.miolfo.rss.controller.FeedController;
import fi.miolfo.rss.service.FeedService;
import fi.miolfo.rss.service.RssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
@EnableScheduling
public class RefreshFeedsJob {

    private static final Logger log = LoggerFactory.getLogger(RefreshFeedsJob.class);

    private static final long FIVE_MINUTES = 60000L * 5;

    @Autowired
    private FeedService feedService;

    @Autowired
    private RssService rssService;

    @Scheduled(fixedRate = FIVE_MINUTES)
    void schedule() {
        log.info("Starting scheduled feed update");
        feedService.getFeeds().forEach(feed -> {
            rssService.refreshFeedItems(feed.getId());
        });
    }
}
