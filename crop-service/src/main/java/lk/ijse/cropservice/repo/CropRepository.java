package lk.ijse.cropservice.repo;

import lk.ijse.cropservice.entity.CropBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<CropBatch, String> {
}
