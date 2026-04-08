package lk.ijse.zoneservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Zone {
    @Id
    private String id;
    private String name;
    private double minTemp;
    private double maxTemp;

    // returned by external IoT backend when registering device
    private String deviceId;
}
