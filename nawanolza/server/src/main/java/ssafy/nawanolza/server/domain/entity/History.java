package ssafy.nawanolza.server.domain.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@Table(name = "histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class History {

    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @Column(columnDefinition = "TINYINT")
    private int level;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public History(Collection collection, int level) {
        this.collection = collection;
        this.level = level;
    }
}
