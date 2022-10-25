package ssafy.nawanolza.server.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Builder
public class Quiz {

    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String context;

    @Column(columnDefinition = "TINYINT")
    private boolean answer;
}
