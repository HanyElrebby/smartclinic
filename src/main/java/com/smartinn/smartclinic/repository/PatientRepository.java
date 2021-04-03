package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.Patient;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(value = "select * from patient p where UPPER(p.phone_number)  LIKE ?1 or UPPER(p.name) LIKE ?2", nativeQuery = true)
    Page<Patient> searchPatients(String phoneNumber, String name, Pageable pageable);

    @Query(value = "select * from patient p order by p.file_number desc Limit 1", nativeQuery = true)
    List<Patient> getLastPatient();
}
