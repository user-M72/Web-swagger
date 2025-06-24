package uz.company.digitalactive.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.company.digitalactive.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  Optional<User> findByPhoneNumber(String phoneNumber);

  @Query(
      """
                    SELECT u
                    FROM users u
                    WHERE
                      (
                        :search IS NULL
                        OR LOWER(u.firstname) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                        OR LOWER(u.lastname) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                        OR LOWER(u.email) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                        OR LOWER(u.phoneNumber) LIKE LOWER(cast(CONCAT('%',:search,'%') as string))
                      )
                    """)
  Page<User> findUserPage(String search, Pageable pageable);
}
