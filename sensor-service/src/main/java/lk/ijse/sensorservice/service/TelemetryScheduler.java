package lk.ijse.sensorservice.service;

import lk.ijse.sensorservice.client.AutomationClient;
import lk.ijse.sensorservice.client.IotClient;
import lk.ijse.sensorservice.dto.TelemetryDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelemetryScheduler {
    private final IotClient iotClient;
    private final AutomationClient automationClient;

    @Value("${external.iot.user.username}")
    private String username;

    @Value("${external.iot.user.password}")
    private String password;

    @Value("${external.iot.device-id}")
    private String deviceId;

    @Getter
    private volatile TelemetryDTO latest;

    private volatile String accessToken;

    @Scheduled(fixedRateString = "10000")
    public void fetchAndPush() {
        ensureLoggedIn();

        try {
            TelemetryDTO telemetry = iotClient.getTelemetry("Bearer " + accessToken, deviceId);
            latest = telemetry;
            automationClient.process(telemetry);
        } catch (Exception e) {
            // token might be expired; force re-login next cycle
            accessToken = null;
        }
    }

    private void ensureLoggedIn() {
        if (accessToken != null) return;
        IotClient.AuthResponse auth = iotClient.login(new IotClient.AuthRequest(username, password));
        this.accessToken = auth.accessToken();
    }
}
