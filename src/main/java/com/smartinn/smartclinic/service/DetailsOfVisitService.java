package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.DetailsOfVisit;
import com.smartinn.smartclinic.repository.DetailsOfVisitRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetailsOfVisit}.
 */
@Service
@Transactional
public class DetailsOfVisitService {

    private final Logger log = LoggerFactory.getLogger(DetailsOfVisitService.class);

    private final DetailsOfVisitRepository detailsOfVisitRepository;

    public DetailsOfVisitService(DetailsOfVisitRepository detailsOfVisitRepository) {
        this.detailsOfVisitRepository = detailsOfVisitRepository;
    }

    /**
     * Save a detailsOfVisit.
     *
     * @param detailsOfVisit the entity to save.
     * @return the persisted entity.
     */
    public DetailsOfVisit save(DetailsOfVisit detailsOfVisit) {
        log.debug("Request to save DetailsOfVisit : {}", detailsOfVisit);
        return detailsOfVisitRepository.save(detailsOfVisit);
    }

    /**
     * Partially update a detailsOfVisit.
     *
     * @param detailsOfVisit the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DetailsOfVisit> partialUpdate(DetailsOfVisit detailsOfVisit) {
        log.debug("Request to partially update DetailsOfVisit : {}", detailsOfVisit);

        return detailsOfVisitRepository
            .findById(detailsOfVisit.getId())
            .map(
                existingDetailsOfVisit -> {
                    if (detailsOfVisit.getDescriptionAilments() != null) {
                        existingDetailsOfVisit.setDescriptionAilments(detailsOfVisit.getDescriptionAilments());
                    }
                    if (detailsOfVisit.getNameOfDisease() != null) {
                        existingDetailsOfVisit.setNameOfDisease(detailsOfVisit.getNameOfDisease());
                    }
                    if (detailsOfVisit.getRecommendations() != null) {
                        existingDetailsOfVisit.setRecommendations(detailsOfVisit.getRecommendations());
                    }
                    if (detailsOfVisit.getMedicines() != null) {
                        existingDetailsOfVisit.setMedicines(detailsOfVisit.getMedicines());
                    }
                    if (detailsOfVisit.getDosage() != null) {
                        existingDetailsOfVisit.setDosage(detailsOfVisit.getDosage());
                    }

                    return existingDetailsOfVisit;
                }
            )
            .map(detailsOfVisitRepository::save);
    }

    /**
     * Get all the detailsOfVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DetailsOfVisit> findAll(Pageable pageable) {
        log.debug("Request to get all DetailsOfVisits");
        return detailsOfVisitRepository.findAll(pageable);
    }

    /**
     * Get one detailsOfVisit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DetailsOfVisit> findOne(Long id) {
        log.debug("Request to get DetailsOfVisit : {}", id);
        return detailsOfVisitRepository.findById(id);
    }

    /**
     * Delete the detailsOfVisit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DetailsOfVisit : {}", id);
        detailsOfVisitRepository.deleteById(id);
    }
}
