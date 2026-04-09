package lk.ijse.cropservice.controller;
import lk.ijse.cropservice.dto.CropCreateRequest;
import lk.ijse.cropservice.dto.StatusUpdateRequest;
import lk.ijse.cropservice.entity.CropBatch;
import lk.ijse.cropservice.entity.CropStatus;
import lk.ijse.cropservice.repo.CropRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {
    private final CropRepository repo;

    @PostMapping
    public CropBatch create(@RequestBody CropCreateRequest req) {
        CropBatch b = new CropBatch();
        b.setId(UUID.randomUUID().toString());
        b.setCropName(req.getCropName());
        b.setQuantity(req.getQuantity());
        b.setStatus(CropStatus.SEEDLING);
        b.setCreatedAt(Instant.now());
        return repo.save(b);
    }

    @PutMapping("/{id}/status")
    public CropBatch updateStatus(@PathVariable String id, @RequestBody StatusUpdateRequest req) {
        CropBatch b = repo.findById(id).orElseThrow();
        b.setStatus(req.getStatus());
        return repo.save(b);
    }

    @GetMapping
    public List<CropBatch> list() {
        return repo.findAll();
    }
}
