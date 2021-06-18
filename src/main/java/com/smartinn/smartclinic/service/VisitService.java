package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Visit;
import com.smartinn.smartclinic.repository.VisitRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Visit}.
 */
@Service
@Transactional
public class VisitService {

    private final Logger log = LoggerFactory.getLogger(VisitService.class);

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * Save a visit.
     *
     * @param visit the entity to save.
     * @return the persisted entity.
     */
    public Visit save(Visit visit) {
        log.debug("Request to save Visit : {}", visit);
        return visitRepository.save(visit);
    }

    /**
     * Partially update a visit.
     *
     * @param visit the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Visit> partialUpdate(Visit visit) {
        log.debug("Request to partially update Visit : {}", visit);

        return visitRepository
            .findById(visit.getId())
            .map(
                existingVisit -> {
                    if (visit.getDateOfVisit() != null) {
                        existingVisit.setDateOfVisit(visit.getDateOfVisit());
                    }

                    return existingVisit;
                }
            )
            .map(visitRepository::save);
    }

    /**
     * Get all the visits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Visit> findAll(Pageable pageable) {
        log.debug("Request to get all Visits");
        return visitRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Visit> findAllByPatientId(Long patientId, Pageable pageable) {
        log.debug("Request to get all Visits");
        return visitRepository.getVisitByPatientId(patientId, pageable);
    }

    @Transactional(readOnly = true)
    public List<Visit> findAllWaited() {
        log.debug("Request to get all Visits");
        return visitRepository.getWaitedVisits();
    }

    @Transactional(readOnly = true)
    public Page<Visit> findAllByDate(String date, Pageable pageable) {
        log.debug("Request to get all Visits");

        String startDate = date + "T00:00:00+02:00";
        String endDate = date + "T23:59:59+02:00";

        ZonedDateTime localTimeObjStart = ZonedDateTime.parse(startDate);
        ZonedDateTime localTimeObjEnd = ZonedDateTime.parse(endDate);

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(localTimeObjStart);
        System.out.println(localTimeObjEnd);
        System.out.println();
        System.out.println();
        System.out.println();

        return visitRepository.getVisitByDates(localTimeObjStart, localTimeObjEnd, pageable);
    }

    /**
     * Get one visit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Visit> findOne(Long id) {
        log.debug("Request to get Visit : {}", id);
        return visitRepository.findById(id);
    }

    /**
     * Delete the visit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Visit : {}", id);
        visitRepository.deleteById(id);
    }
}
