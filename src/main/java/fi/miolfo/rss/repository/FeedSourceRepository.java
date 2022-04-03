package fi.miolfo.rss.repository;

import fi.miolfo.rss.model.persistence.FeedSource;
import org.springframework.data.repository.CrudRepository;

public interface FeedSourceRepository extends CrudRepository<FeedSource, Integer> {
}
