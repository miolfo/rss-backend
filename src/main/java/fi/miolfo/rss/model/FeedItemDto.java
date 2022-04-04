package fi.miolfo.rss.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FeedItemDto {

    private String title;
    private String description;
    private String link;
    private String guid;
    private String pubDate;
}
