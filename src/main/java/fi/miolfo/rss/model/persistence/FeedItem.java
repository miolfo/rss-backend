package fi.miolfo.rss.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class FeedItem {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String link;

    @Column
    private LocalDateTime pubDate;

    @ManyToOne
    @JoinColumn(name="feed_source_id")
    @JsonIgnore
    private FeedSource feedSource;
}
