package lk.ijse.automationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneClient {
    @GetMapping("/api/zones/{id}")
    ZoneResponse getZone(@PathVariable("id") String id);

    record ZoneResponse(String id, String name, double minTemp, double maxTemp, String deviceId) {}
}
