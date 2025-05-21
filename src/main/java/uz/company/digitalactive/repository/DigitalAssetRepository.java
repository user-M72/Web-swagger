package uz.company.digitalactive.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.company.digitalactive.entity.DigitalAsset;
import uz.company.digitalactive.entity.enums.AssetStatus;

public interface DigitalAssetRepository extends JpaRepository<DigitalAsset, UUID> {
  List<DigitalAsset> findByExpirationDateBetween(LocalDateTime from, LocalDateTime to);

  @Query(
      """
            SELECT d
            FROM DigitalAsset d
            WHERE
                (
                    :search IS NULL
                    OR LOWER(d.name) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                    OR LOWER(d.type.name) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                    OR LOWER(d.owner) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                    OR LOWER(d.issuer) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                )
                AND (
                    :status IS NULL OR d.status = :status
                )
            """)
  Page<DigitalAsset> findAssetsPage(String search, AssetStatus status, Pageable pageable);
}
