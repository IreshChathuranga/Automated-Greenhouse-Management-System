package lk.ijse.zoneservice.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZoneResponse {
    private String id;
    private String name;
    private double minTemp;
    private double maxTemp;
    private String deviceId;
}
