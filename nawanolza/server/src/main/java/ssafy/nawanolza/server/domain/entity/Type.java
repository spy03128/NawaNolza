package ssafy.nawanolza.server.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Getter
@Table(name = "types")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor @Builder
public class Type {

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
