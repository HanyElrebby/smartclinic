package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Clinic;
import com.smartinn.smartclinic.repository.ClinicRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Clinic}.
 */
@Service
@Transactional
public class ClinicService {

    private final Logger log = LoggerFactory.getLogger(ClinicService.class);

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    /**
     * Save a clinic.
     *
     * @param clinic the entity to save.
     * @return the persisted entity.
     */
    public Clinic save(Clinic clinic) {
        log.debug("Request to save Clinic : {}", clinic);
        return clinicRepository.save(clinic);
    }

    /**
     * Partially update a clinic.
     *
     * @param clinic the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Clinic> partialUpdate(Clinic clinic) {
        log.debug("Request to partially update Clinic : {}", clinic);

        return clinicRepository
            .findById(clinic.getId())
            .map(
                existingClinic -> {
                    if (clinic.getNameOfClinic() != null) {
                        existingClinic.setNameOfClinic(clinic.getNameOfClinic());
                    }
                    if (clinic.getCity() != null) {
                        existingClinic.setCity(clinic.getCity());
                    }
                    if (clinic.getPostalCode() != null) {
                        existingClinic.setPostalCode(clinic.getPostalCode());
                    }
                    if (clinic.getStreet() != null) {
                        existingClinic.setStreet(clinic.getStreet());
                    }

                    return existingClinic;
                }
            )
            .map(clinicRepository::save);
    }

    /**
     * Get all the clinics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Clinic> findAll(Pageable pageable) {
        log.debug("Request to get all Clinics");
        return clinicRepository.findAll(pageable);
    }

    /**
     * Get one clinic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Clinic> findOne(Long id) {
        log.debug("Request to get Clinic : {}", id);
        return clinicRepository.findById(id);
    }

    /**
     * Delete the clinic by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Clinic : {}", id);
        clinicRepository.deleteById(id);
    }
}
