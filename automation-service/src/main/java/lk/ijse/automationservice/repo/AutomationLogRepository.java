package lk.ijse.automationservice.repo;

import lk.ijse.automationservice.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomationLogRepository extends JpaRepository<AutomationLog, String> {
}
