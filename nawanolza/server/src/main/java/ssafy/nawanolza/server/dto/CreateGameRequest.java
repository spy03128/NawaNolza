package ssafy.nawanolza.server.dto;

import lombok.Data;
import ssafy.nawanolza.server.domain.entity.dto.HideAndSeekProperties;

@Data
public class CreateGameRequest {
    private Long hostId;
    private double lat;
    private double lng;
    private long playTime;
    private long hideTime;
    private int range;

    public HideAndSeekProperties makeHideAndSeekProperties() {
        return new HideAndSeekProperties(lat, lng, playTime, hideTime, range);
    }
}
