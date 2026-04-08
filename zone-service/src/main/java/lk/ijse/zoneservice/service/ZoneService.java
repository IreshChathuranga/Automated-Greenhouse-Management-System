package lk.ijse.zoneservice.service;
import lk.ijse.zoneservice.client.IotClient;
import lk.ijse.zoneservice.dto.ZoneCreateRequest;
import lk.ijse.zoneservice.dto.ZoneResponse;
import lk.ijse.zoneservice.entity.Zone;
import lk.ijse.zoneservice.repo.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final IotClient iotClient;

    @Value("${external.iot.user.username}")
    private String iotUsername;

    @Value("${external.iot.user.password}")
    private String iotPassword;

    public ZoneResponse createZone(ZoneCreateRequest req) {
        if (req.getMinTemp() >= req.getMaxTemp()) {
            throw new IllegalArgumentException("minTemp must be strictly less than maxTemp");
        }

        Zone zone = new Zone();
        zone.setId(req.getId() != null ? req.getId() : UUID.randomUUID().toString());
        zone.setName(req.getName());
        zone.setMinTemp(req.getMinTemp());
        zone.setMaxTemp(req.getMaxTemp());

        // login to external IoT and create device
        IotClient.AuthResponse auth = iotClient.login(new IotClient.AuthRequest(iotUsername, iotPassword));
        String bearer = "Bearer " + auth.accessToken();
        IotClient.DeviceResponse device = iotClient.createDevice(
                bearer,
                new IotClient.DeviceRequest(zone.getName() + "-sensor", zone.getId())
        );
        zone.setDeviceId(device.deviceId());

        Zone saved = zoneRepository.save(zone);
        return ZoneResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .minTemp(saved.getMinTemp())
                .maxTemp(saved.getMaxTemp())
                .deviceId(saved.getDeviceId())
                .build();
    }

    public ZoneResponse getZone(String id) {
        Zone z = zoneRepository.findById(id).orElseThrow();
        return ZoneResponse.builder()
                .id(z.getId())
                .name(z.getName())
                .minTemp(z.getMinTemp())
                .maxTemp(z.getMaxTemp())
                .deviceId(z.getDeviceId())
                .build();
    }

    public ZoneResponse updateZone(String id, ZoneCreateRequest req) {
        if (req.getMinTemp() >= req.getMaxTemp()) {
            throw new IllegalArgumentException("minTemp must be strictly less than maxTemp");
        }
        Zone z = zoneRepository.findById(id).orElseThrow();
        z.setName(req.getName());
        z.setMinTemp(req.getMinTemp());
        z.setMaxTemp(req.getMaxTemp());
        Zone saved = zoneRepository.save(z);
        return ZoneResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .minTemp(saved.getMinTemp())
                .maxTemp(saved.getMaxTemp())
                .deviceId(saved.getDeviceId())
                .build();
    }

    public void deleteZone(String id) {
        zoneRepository.deleteById(id);
    }
}
