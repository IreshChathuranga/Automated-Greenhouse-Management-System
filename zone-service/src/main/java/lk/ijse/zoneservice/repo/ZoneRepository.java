package lk.ijse.zoneservice.repo;

import lk.ijse.zoneservice.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, String> {
}
