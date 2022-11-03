package ssafy.nawanolza.server.domain.entity.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@ToString
public class HideAndSeekProperties {
    private double lat;
    private double lng;
    private long playTime;
    private long hideTime;
    private int range;
}
