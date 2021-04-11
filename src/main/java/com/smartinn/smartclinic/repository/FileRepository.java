package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.File;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the File entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    @Query(value = "select * from file where patient_id = ?1", nativeQuery = true)
    List<File> getFilesByPatientId(Long patientId);
}
