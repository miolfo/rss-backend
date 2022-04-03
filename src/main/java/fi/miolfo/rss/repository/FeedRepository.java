package fi.miolfo.rss.repository;

import fi.miolfo.rss.model.persistence.Feed;
import org.springframework.data.repository.CrudRepository;

public interface FeedRepository extends CrudRepository<Feed, Integer> {
}
