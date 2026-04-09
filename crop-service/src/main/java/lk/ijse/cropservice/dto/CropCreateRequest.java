package lk.ijse.cropservice.dto;
import lombok.Data;

@Data
public class CropCreateRequest {
    private String cropName;
    private int quantity;
}
