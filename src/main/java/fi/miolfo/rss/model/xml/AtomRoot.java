package fi.miolfo.rss.model.xml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JacksonXmlRootElement
@JsonIgnoreProperties
@Getter
@Setter
public class AtomRoot {

    private String title;
    private Link link;
    @JacksonXmlProperty(localName = "entry")
    @JacksonXmlCData
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Entry> entries;

    @Getter
    @Setter
    public static class Link {
        @JacksonXmlProperty(isAttribute = true)
        private String href;
    }

    @Getter
    @Setter
    public static class Entry {
        private Link link;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        private LocalDateTime published;
        private String title;
        private String id;
    }
}
