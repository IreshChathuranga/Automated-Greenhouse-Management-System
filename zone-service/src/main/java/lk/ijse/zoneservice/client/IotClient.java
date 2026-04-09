package lk.ijse.zoneservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "iotClient", url = "${external.iot.base-url}")
public interface IotClient {
    @PostMapping("/auth/login")
    AuthResponse login(@RequestBody AuthRequest req);

    @PostMapping("/devices")
    DeviceResponse createDevice(@RequestHeader("Authorization") String bearer, @RequestBody DeviceRequest req);

    record AuthRequest(String username, String password) {}
    record AuthResponse(String accessToken, String refreshToken) {}

    record DeviceRequest(String name, String zoneId) {}
    record DeviceResponse(String deviceId, String name, String zoneId, String userId, String createAt) {}
}
