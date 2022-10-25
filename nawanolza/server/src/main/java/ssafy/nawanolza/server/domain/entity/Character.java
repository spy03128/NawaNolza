package ssafy.nawanolza.server.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "characters")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Builder
public class Character {

    @Id
    @Column(name = "character_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(columnDefinition = "TINYINT")
    private boolean rare;
}
