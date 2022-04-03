package fi.miolfo.rss.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class FeedSource {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String source;

    @ManyToOne
    @JoinColumn(name="feed_id")
    @JsonIgnore
    private Feed feed;
}
