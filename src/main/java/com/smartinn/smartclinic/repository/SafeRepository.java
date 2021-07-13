package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.Safe;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Safe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SafeRepository extends JpaRepository<Safe, Long>, JpaSpecificationExecutor<Safe> {
    Optional<Safe> findBySafeSecretary(String name);
}
