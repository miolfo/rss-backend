package fi.miolfo.rss.model.persistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(unique = true)
    private String guid;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime pubDate;

    @ManyToOne
    @JoinColumn(name="feed_source_id")
    @JsonIgnore
    private FeedSource feedSource;
}
