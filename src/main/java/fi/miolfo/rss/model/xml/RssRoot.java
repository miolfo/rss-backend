package fi.miolfo.rss.model.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JacksonXmlRootElement
@JsonIgnoreProperties
@Getter
@Setter
public class RssRoot {

    private Channel channel;

    @Getter
    @Setter
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
    public static class Item {
        private String link;
        private String title;
        private String description;
    }
}
