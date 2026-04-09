package lk.ijse.cropservice.dto;

import lk.ijse.cropservice.entity.CropStatus;

public class StatusUpdateRequest {
    private CropStatus status;

    public CropStatus getStatus() {
        return status;
    }

    public void setStatus(CropStatus status) {
        this.status = status;
    }
}
