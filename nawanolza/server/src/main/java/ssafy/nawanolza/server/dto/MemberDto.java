package ssafy.nawanolza.server.dto;

import lombok.Data;
import ssafy.nawanolza.server.domain.entity.Member;

@Data
public class MemberDto {
    private Long memberId;
    private String name;
    private String image;

    public MemberDto(Member member) {
        this.memberId = member.getId();
        this.name = member.getName();
        this.image = member.getImage();
    }
}
