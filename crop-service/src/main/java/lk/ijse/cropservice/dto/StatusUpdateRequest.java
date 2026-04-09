package lk.ijse.cropservice.dto;

import lk.ijse.cropservice.entity.CropStatus;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    private CropStatus status;
}
