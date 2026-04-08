package lk.ijse.sensorservice.client;

import lk.ijse.sensorservice.dto.TelemetryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "automation-service")
public interface AutomationClient {
    @PostMapping("/api/automation/process")
    void process(TelemetryDTO dto);
}
