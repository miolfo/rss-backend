package fi.miolfo.rss.mapper;

import fi.miolfo.rss.model.FeedItemDto;
import fi.miolfo.rss.model.xml.RssRoot;
import org.springframework.stereotype.Service;

@Service
public class RssToFeedItemMapper {

    public FeedItemDto itemToFeedItem(RssRoot.Item item) {
        return FeedItemDto.builder()
                .description(item.getDescription())
                .link(item.getLink())
                .title(item.getTitle())
                .build();
    }
}
