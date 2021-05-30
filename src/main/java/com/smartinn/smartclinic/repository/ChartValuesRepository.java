package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.ChartValues;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ChartValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChartValuesRepository extends JpaRepository<ChartValues, Long> {
    @Query(value = "select * from chart_values where patient_id = ?1", nativeQuery = true)
    List<ChartValues> getByPatientId(Long patientId);
}
