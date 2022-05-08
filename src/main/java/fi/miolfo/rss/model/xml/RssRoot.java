package fi.miolfo.rss.model.xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JacksonXmlRootElement
@JsonIgnoreProperties
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RssRoot {

    private Channel channel;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Channel {
        private String title;
        private String link;

        @JacksonXmlProperty(localName = "item")
        @JacksonXmlCData
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Item> items;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String link;
        private String title;
        private String description;
        private String guid;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, d MMM yyyy HH:mm:ss Z")
        private LocalDateTime pubDate;
        @JacksonXmlProperty(localName = "category")
        @JacksonXmlCData
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<String> categories;
    }
}
