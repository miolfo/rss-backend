package fi.miolfo.rss;

import fi.miolfo.rss.configuration.WebClientConfiguration;
import fi.miolfo.rss.configuration.XmlMapperConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan({
        "fi.miolfo.rss.service",
        "fi.miolfo.rss.controller",
        "fi.miolfo.rss.mapper",
        "fi.miolfo.rss.repository",
        "fi.miolfo.rss.filter",
        "fi.miolfo.rss.job"})
@Import({WebClientConfiguration.class, XmlMapperConfiguration.class})
public class RssBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssBackendApplication.class, args);
    }

}
