package lk.ijse.sensorservice.service;

import feign.FeignException;
import feign.RetryableException;
import lk.ijse.sensorservice.client.AutomationClient;
import lk.ijse.sensorservice.client.IotClient;
import lk.ijse.sensorservice.dto.TelemetryDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelemetryScheduler {
    private final IotClient iotClient;
    private final AutomationClient automationClient;

    @Value("${external.iot.user.username}")
    private String username;

    @Value("${external.iot.user.password}")
    private String password;

    @Value("${external.iot.device-id}")
    private String deviceId;

    @Value("${sensor.scheduler.login-cooldown-ms:30000}")
    private long loginCooldownMs;

    @Getter
    private volatile TelemetryDTO latest;

    private volatile String accessToken;
    private volatile long nextLoginAttemptAt;

    @Scheduled(
            fixedDelayString = "${sensor.scheduler.fixed-delay-ms:10000}",
            initialDelayString = "${sensor.scheduler.initial-delay-ms:5000}"
    )
    public void fetchAndPush() {
        if (!ensureLoggedIn()) {
            return;
        }

        try {
            TelemetryDTO telemetry = iotClient.getTelemetry("Bearer " + accessToken, deviceId);
            latest = telemetry;
            automationClient.process(telemetry);
        } catch (RetryableException e) {
            log.warn("IoT API is unreachable: {}", e.getMessage());
        } catch (FeignException e) {
            // Only clear token when auth is rejected; network failures should keep token intact.
            if (e.status() == 401 || e.status() == 403) {
                accessToken = null;
                scheduleNextLoginAttempt();
                log.warn("IoT token rejected (status {}). Will re-login on next attempt.", e.status());
            } else {
                log.warn("Failed to fetch telemetry from IoT API (status {}).", e.status());
            }
        } catch (Exception e) {
            log.error("Unexpected error while fetching/pushing telemetry.", e);
        }
    }

    private boolean ensureLoggedIn() {
        if (accessToken != null) {
            return true;
        }

        long now = System.currentTimeMillis();
        if (now < nextLoginAttemptAt) {
            return false;
        }

        try {
            IotClient.AuthResponse auth = iotClient.login(new IotClient.AuthRequest(username, password));
            if (auth == null || auth.accessToken() == null || auth.accessToken().isBlank()) {
                scheduleNextLoginAttempt();
                log.warn("IoT login returned an empty access token.");
                return false;
            }

            this.accessToken = auth.accessToken();
            return true;
        } catch (RetryableException e) {
            scheduleNextLoginAttempt();
            log.warn("IoT login timeout/unreachable: {}", e.getMessage());
            return false;
        } catch (FeignException e) {
            scheduleNextLoginAttempt();
            log.warn("IoT login failed with status {}.", e.status());
            return false;
        }
    }

    private void scheduleNextLoginAttempt() {
        this.nextLoginAttemptAt = System.currentTimeMillis() + loginCooldownMs;
    }
}
