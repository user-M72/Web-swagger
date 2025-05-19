package uz.company.digitalactive.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.company.digitalactive.entity.DigitalAsset;

public interface DigitalAssetRepository extends JpaRepository<DigitalAsset, UUID> {
    List<DigitalAsset> findByExpirationDateBetween(LocalDateTime from, LocalDateTime to);

}
