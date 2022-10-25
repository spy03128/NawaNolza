package ssafy.nawanolza.server.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "game")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Builder
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int time;
}
