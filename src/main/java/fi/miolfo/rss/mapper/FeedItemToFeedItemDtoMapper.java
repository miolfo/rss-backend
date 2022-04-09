package fi.miolfo.rss.mapper;

import fi.miolfo.rss.model.FeedItemDto;
import fi.miolfo.rss.model.persistence.FeedItem;
import org.springframework.stereotype.Service;

@Service
public class FeedItemToFeedItemDtoMapper {

    public FeedItemDto feedItemToFeedItemDto(FeedItem feedItem) {
        return FeedItemDto.builder()
                .title(feedItem.getTitle())
                .link(feedItem.getLink())
                .description(feedItem.getDescription())
                .pubDate(feedItem.getPubDate())
                .build();
    }
}
