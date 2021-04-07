package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.Medicine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Medicine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {}
