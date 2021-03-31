package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.Clinic;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clinic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    @Query("select clinic from Clinic clinic where clinic.user.login = ?#{principal.username}")
    List<Clinic> findByUserIsCurrentUser();
}
