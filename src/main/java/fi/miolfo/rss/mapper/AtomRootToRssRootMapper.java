package fi.miolfo.rss.mapper;

import fi.miolfo.rss.model.xml.AtomRoot;
import fi.miolfo.rss.model.xml.RssRoot;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtomRootToRssRootMapper {

    public RssRoot atomRootToRssRoot(AtomRoot atomRoot) {

        RssRoot.Channel channel = RssRoot.Channel.builder()
                .title(atomRoot.getTitle())
                .link(atomRoot.getLink().getHref())
                .items(entriesToItems(atomRoot.getEntries()))
                .build();
        return RssRoot.builder()
                .channel(channel).build();
    }

    private List<RssRoot.Item> entriesToItems(List<AtomRoot.Entry> entries) {
        return entries.stream().map(this::entryToItem).collect(Collectors.toList());
    }

    private RssRoot.Item entryToItem(AtomRoot.Entry entry) {

        return RssRoot.Item.builder()
                .title(entry.getTitle())
                .guid(entry.getId())
                .link(entry.getLink().getHref())
                .pubDate(entry.getPublished())
                .build();
    }
}
