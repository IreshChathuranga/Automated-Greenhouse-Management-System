package lk.ijse.automationservice.dto;
import lombok.Data;

@Data
public class TelemetryDTO {
    private String deviceId;
    private String zoneId;
    private ValueDTO value;
    private String capturedAt;

    @Data
    public static class ValueDTO {
        private double temperature;
        private String tempUnit;
        private double humidity;
        private String humidityUnit;
    }
}
