package fi.miolfo.rss.service.impl;

import fi.miolfo.rss.model.FeedItem;
import fi.miolfo.rss.service.RssService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RssServiceImpl implements RssService {

    private static final String HS_XML = "https://www.hs.fi/rss/tuoreimmat.xml";

    @Override
    public Mono<String> getFeed() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        WebClient client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        var spec = client.get().uri(HS_XML);
        return spec.exchangeToMono(res -> {
            return res.bodyToMono(String.class);
        });
    }
}
