package fi.miolfo.rss.mapper;

import fi.miolfo.rss.model.FeedItem;
import fi.miolfo.rss.model.xml.RssRoot;
import org.springframework.stereotype.Service;

@Service
public class RssToFeedItemMapper {

    public FeedItem itemToFeedItem(RssRoot.Item item) {
        return FeedItem.builder()
                .description(item.getDescription())
                .link(item.getLink())
                .title(item.getTitle())
                .build();
    }
}
