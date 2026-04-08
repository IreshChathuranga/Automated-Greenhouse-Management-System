package lk.ijse.sensorservice.controller;

import lk.ijse.sensorservice.dto.TelemetryDTO;
import lk.ijse.sensorservice.service.TelemetryScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {
    private final TelemetryScheduler scheduler;

    @GetMapping("/latest")
    public TelemetryDTO latest() {
        return scheduler.getLatest();
    }
}
