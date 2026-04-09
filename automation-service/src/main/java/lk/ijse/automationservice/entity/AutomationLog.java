package lk.ijse.automationservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class AutomationLog {
    @Id
    private String id;
    private String zoneId;
    private String deviceId;
    private double temperature;
    private String action;
    private Instant triggeredAt;
}
