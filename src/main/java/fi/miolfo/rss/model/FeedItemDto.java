package fi.miolfo.rss.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class FeedItemDto {

    private String title;
    private String description;
    private String link;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pubDate;
}
