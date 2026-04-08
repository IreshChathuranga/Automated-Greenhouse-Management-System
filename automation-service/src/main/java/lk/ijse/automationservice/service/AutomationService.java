package lk.ijse.automationservice.service;

import lk.ijse.automationservice.client.ZoneClient;
import lk.ijse.automationservice.dto.TelemetryDTO;
import lk.ijse.automationservice.entity.AutomationLog;
import lk.ijse.automationservice.repo.AutomationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutomationService {
    private final ZoneClient zoneClient;
    private final AutomationLogRepository logRepository;

    public void process(TelemetryDTO dto) {
        ZoneClient.ZoneResponse zone = zoneClient.getZone(dto.getZoneId());

        double temp = dto.getValue().getTemperature();
        String action = null;

        if (temp > zone.maxTemp()) action = "TURN_FAN_ON";
        else if (temp < zone.minTemp()) action = "TURN_HEATER_ON";

        if (action != null) {
            AutomationLog log = new AutomationLog();
            log.setId(UUID.randomUUID().toString());
            log.setZoneId(dto.getZoneId());
            log.setDeviceId(dto.getDeviceId());
            log.setTemperature(temp);
            log.setAction(action);
            log.setTriggeredAt(Instant.now());
            logRepository.save(log);
        }
    }

    public List<AutomationLog> logs() {
        return logRepository.findAll();
    }
}
