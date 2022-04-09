package fi.miolfo.rss.mapper;

import fi.miolfo.rss.model.FeedItemDto;
import fi.miolfo.rss.model.persistence.FeedItem;
import fi.miolfo.rss.model.persistence.FeedSource;
import fi.miolfo.rss.model.xml.RssRoot;
import org.springframework.stereotype.Service;

@Service
public class RssToFeedItemMapper {

    public FeedItem rssItemToFeedItem(RssRoot.Item item, FeedSource feedSource) {
        return FeedItem.builder()
                .description(item.getDescription())
                .title(item.getTitle())
                .pubDate(item.getPubDate())
                .link(item.getLink())
                .feedSource(feedSource)
                .guid(item.getGuid())
                .build();
    }
}
