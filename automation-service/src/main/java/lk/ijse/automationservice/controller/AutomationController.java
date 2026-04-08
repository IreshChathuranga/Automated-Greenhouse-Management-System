package lk.ijse.automationservice.controller;

import lk.ijse.automationservice.dto.TelemetryDTO;
import lk.ijse.automationservice.entity.AutomationLog;
import lk.ijse.automationservice.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {
    private final AutomationService automationService;

    @PostMapping("/process")
    public void process(@RequestBody TelemetryDTO dto) {
        automationService.process(dto);
    }

    @GetMapping("/logs")
    public List<AutomationLog> logs() {
        return automationService.logs();
    }
}
