package fi.miolfo.rss.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.miolfo.rss.model.UpdateStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Feed {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private LocalDateTime lastUpdated;

    @OneToMany(mappedBy = "feed")
    @JsonIgnore
    private List<FeedSource> feedSources;
}
