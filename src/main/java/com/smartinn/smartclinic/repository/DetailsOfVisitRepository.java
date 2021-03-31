package com.smartinn.smartclinic.repository;

import com.smartinn.smartclinic.domain.DetailsOfVisit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DetailsOfVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetailsOfVisitRepository extends JpaRepository<DetailsOfVisit, Long> {}
