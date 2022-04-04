package fi.miolfo.rss.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class XmlMapperConfiguration {

    @Bean
    public XmlMapper RssXmlMapper() {
        XmlMapper xmlMapper = new XmlMapper();
        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer =  new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss Z"));
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        xmlMapper.registerModule(module);
        return xmlMapper;
    }
}
