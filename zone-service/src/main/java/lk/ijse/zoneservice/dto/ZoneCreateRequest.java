package lk.ijse.zoneservice.dto;

import lombok.Data;

@Data
public class ZoneCreateRequest {
    private String id;
    private String name;
    private double minTemp;
    private double maxTemp;
}
