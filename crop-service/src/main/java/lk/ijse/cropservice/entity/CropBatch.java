package lk.ijse.cropservice.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class CropBatch {
    @Id
    private String id;

    private String cropName;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    private Instant createdAt;
}
