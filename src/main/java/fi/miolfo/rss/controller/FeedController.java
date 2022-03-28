package fi.miolfo.rss.controller;

import fi.miolfo.rss.model.FeedItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class FeedController {

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<FeedItem> index() {
        final var item = FeedItem.builder().title("With completable future").description("desc").build();
        return CompletableFuture.completedFuture(item);
    }
}
