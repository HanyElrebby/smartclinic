package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Visit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query(
        value = "select v.id , null as patient_id , v.clinic_id , v.visit_type , v.date_of_visit from visit v where v.patient_id = ?1",
        nativeQuery = true
    )
    public Page<Visit> getVisitByPatientId(Long patientId, Pageable pageable);
}
