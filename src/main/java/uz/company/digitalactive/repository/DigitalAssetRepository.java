package uz.company.digitalactive.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.company.digitalactive.entity.DigitalAsset;

public interface DigitalAssetRepository extends JpaRepository<DigitalAsset, UUID> {}
