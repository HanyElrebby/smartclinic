package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Safe;
import com.smartinn.smartclinic.repository.SafeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Safe}.
 */
@Service
@Transactional
public class SafeService {

    private final Logger log = LoggerFactory.getLogger(SafeService.class);

    private final SafeRepository safeRepository;

    public SafeService(SafeRepository safeRepository) {
        this.safeRepository = safeRepository;
    }

    /**
     * Save a safe.
     *
     * @param safe the entity to save.
     * @return the persisted entity.
     */
    public Safe save(Safe safe) {
        log.debug("Request to save Safe : {}", safe);
        return safeRepository.save(safe);
    }

    /**
     * Partially update a safe.
     *
     * @param safe the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Safe> partialUpdate(Safe safe) {
        log.debug("Request to partially update Safe : {}", safe);

        return safeRepository
            .findById(safe.getId())
            .map(
                existingSafe -> {
                    if (safe.getSafeName() != null) {
                        existingSafe.setSafeName(safe.getSafeName());
                    }
                    if (safe.getSafeSecretary() != null) {
                        existingSafe.setSafeSecretary(safe.getSafeSecretary());
                    }
                    if (safe.getSafeValue() != null) {
                        existingSafe.setSafeValue(safe.getSafeValue());
                    }
                    if (safe.getNotes() != null) {
                        existingSafe.setNotes(safe.getNotes());
                    }

                    return existingSafe;
                }
            )
            .map(safeRepository::save);
    }

    /**
     * Get all the safes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Safe> findAll() {
        log.debug("Request to get all Safes");
        return safeRepository.findAll();
    }

    /**
     * Get one safe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Safe> findOne(Long id) {
        log.debug("Request to get Safe : {}", id);
        return safeRepository.findById(id);
    }

    public Optional<Safe> getSafeBySecretary(String safeSecretary) {
        log.debug("Request to get Safe : {}", safeSecretary);
        return safeRepository.findBySafeSecretary(safeSecretary);
    }

    /**
     * Delete the safe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Safe : {}", id);
        safeRepository.deleteById(id);
    }
}
