package fi.miolfo.rss.repository;

import fi.miolfo.rss.model.persistence.FeedItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FeedItemRepository extends CrudRepository<FeedItem, Integer> {

    public Optional<FeedItem> findByGuid(String guid);
}
