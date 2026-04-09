package lk.ijse.sensorservice.client;

import lk.ijse.sensorservice.dto.TelemetryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "iotClient", url = "${external.iot.base-url}")
public interface IotClient {
    @PostMapping("/auth/login")
    AuthResponse login(@RequestBody AuthRequest req);

    @GetMapping("/devices/telemetry/{deviceId}")
    TelemetryDTO getTelemetry(@RequestHeader("Authorization") String bearer, @PathVariable String deviceId);

    record AuthRequest(String username, String password) {}
    record AuthResponse(String accessToken, String refreshToken) {}
}
