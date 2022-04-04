package fi.miolfo.rss.repository;

import fi.miolfo.rss.model.persistence.FeedItem;
import org.springframework.data.repository.CrudRepository;

public interface FeedItemRepository extends CrudRepository<FeedItem, Integer> {
}
