package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Doctor;
import com.smartinn.smartclinic.repository.DoctorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Doctor}.
 */
@Service
@Transactional
public class DoctorService {

    private final Logger log = LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Save a doctor.
     *
     * @param doctor the entity to save.
     * @return the persisted entity.
     */
    public Doctor save(Doctor doctor) {
        log.debug("Request to save Doctor : {}", doctor);
        return doctorRepository.save(doctor);
    }

    /**
     * Partially update a doctor.
     *
     * @param doctor the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Doctor> partialUpdate(Doctor doctor) {
        log.debug("Request to partially update Doctor : {}", doctor);

        return doctorRepository
            .findById(doctor.getId())
            .map(
                existingDoctor -> {
                    if (doctor.getFirstName() != null) {
                        existingDoctor.setFirstName(doctor.getFirstName());
                    }
                    if (doctor.getLastName() != null) {
                        existingDoctor.setLastName(doctor.getLastName());
                    }
                    if (doctor.getSpecialization() != null) {
                        existingDoctor.setSpecialization(doctor.getSpecialization());
                    }
                    if (doctor.getPhoneNumber() != null) {
                        existingDoctor.setPhoneNumber(doctor.getPhoneNumber());
                    }

                    return existingDoctor;
                }
            )
            .map(doctorRepository::save);
    }

    /**
     * Get all the doctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Doctor> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        return doctorRepository.findAll(pageable);
    }

    /**
     * Get one doctor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Doctor> findOne(Long id) {
        log.debug("Request to get Doctor : {}", id);
        return doctorRepository.findById(id);
    }

    /**
     * Delete the doctor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Doctor : {}", id);
        doctorRepository.deleteById(id);
    }
}
