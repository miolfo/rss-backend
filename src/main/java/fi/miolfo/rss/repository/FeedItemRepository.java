package fi.miolfo.rss.repository;

import fi.miolfo.rss.model.persistence.FeedItem;
import fi.miolfo.rss.model.persistence.FeedSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface FeedItemRepository extends PagingAndSortingRepository<FeedItem, Integer> {

    public Optional<FeedItem> findByGuid(String guid);

    public List<FeedItem> findAllByFeedSourceInOrderByPubDateDesc(List<FeedSource> sources, Pageable pageable);
}
