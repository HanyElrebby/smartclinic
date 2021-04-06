package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Patient;
import com.smartinn.smartclinic.repository.PatientRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Save a patient.
     *
     * @param patient the entity to save.
     * @return the persisted entity.
     */
    public Patient save(Patient patient) {
        log.debug("Request to save Patient : {}", patient);
        String fileNumber = "1";
        List<Patient> patients = patientRepository.getLastPatient();
        if (patients != null && patients.size() > 0) {
            Patient lastPatient = patients.get(0);
            int lastFileNumber = Integer.parseInt(lastPatient.getFileNumber());
            fileNumber = (lastFileNumber + 1) + "";
        }
        patient.setFileNumber(fileNumber);

        return patientRepository.save(patient);
    }

    public List<Patient> getLatestPatient() {
        return patientRepository.getLastPatient();
    }

    /**
     * Partially update a patient.
     *
     * @param patient the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Patient> partialUpdate(Patient patient) {
        log.debug("Request to partially update Patient : {}", patient);

        return patientRepository
            .findById(patient.getId())
            .map(
                existingPatient -> {
                    if (patient.getName() != null) {
                        existingPatient.setName(patient.getName());
                    }
                    if (patient.getFileNumber() != null) {
                        existingPatient.setFileNumber(patient.getFileNumber());
                    }
                    if (patient.getAge() != null) {
                        existingPatient.setAge(patient.getAge());
                    }
                    if (patient.getGender() != null) {
                        existingPatient.setGender(patient.getGender());
                    }
                    if (patient.getPlaceOfResidence() != null) {
                        existingPatient.setPlaceOfResidence(patient.getPlaceOfResidence());
                    }
                    if (patient.getDateOfBirth() != null) {
                        existingPatient.setDateOfBirth(patient.getDateOfBirth());
                    }
                    if (patient.getBloodGroup() != null) {
                        existingPatient.setBloodGroup(patient.getBloodGroup());
                    }
                    if (patient.getPhoneNumber() != null) {
                        existingPatient.setPhoneNumber(patient.getPhoneNumber());
                    }

                    return existingPatient;
                }
            )
            .map(patientRepository::save);
    }

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Patient> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Patient> searchPatients(String search, Pageable pageable) {
        log.debug("Request to get all Patients");
        if (search == null || search.equals("zZnull")) {
            search = "";
        }
        search = search.toUpperCase();

        return patientRepository.searchPatients("%" + search + "%", "%" + search + "%", search, pageable);
    }

    /**
     * Get one patient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Patient> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id);
    }

    /**
     * Delete the patient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}
