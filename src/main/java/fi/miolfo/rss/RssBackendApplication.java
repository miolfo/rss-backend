package fi.miolfo.rss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"fi.miolfo.rss.service", "fi.miolfo.rss.controller", "fi.miolfo.rss.mapper"})
public class RssBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssBackendApplication.class, args);
    }

}
