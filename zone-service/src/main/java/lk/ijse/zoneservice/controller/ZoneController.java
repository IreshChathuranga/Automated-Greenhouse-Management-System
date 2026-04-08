package lk.ijse.zoneservice.controller;

import lk.ijse.zoneservice.dto.ZoneCreateRequest;
import lk.ijse.zoneservice.dto.ZoneResponse;
import lk.ijse.zoneservice.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {
    private final ZoneService zoneService;

    @PostMapping
    public ZoneResponse create(@RequestBody ZoneCreateRequest req) {
        return zoneService.createZone(req);
    }

    @GetMapping("/{id}")
    public ZoneResponse get(@PathVariable String id) {
        return zoneService.getZone(id);
    }

    @PutMapping("/{id}")
    public ZoneResponse update(@PathVariable String id, @RequestBody ZoneCreateRequest req) {
        return zoneService.updateZone(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        zoneService.deleteZone(id);
    }
}
