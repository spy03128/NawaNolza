package ssafy.nawanolza.server.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@Table(name = "collection")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Builder
public class Collection {

    @Id
    @Column(name = "collection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Character character;

    @Column(name = "current_level", columnDefinition = "TINYINT")
    private int currentLevel;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> historyList = new ArrayList<>();

    public Collection(Member member, Character character) {
        this.member = member;
        this.character = character;
        levelUp();
    }

    public void levelUp() {
        currentLevel++;
        historyList.add(new History(this, this.currentLevel));
    }
}
