package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Medicine;
import com.smartinn.smartclinic.repository.MedicineRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Medicine}.
 */
@Service
@Transactional
public class MedicineService {

    private final Logger log = LoggerFactory.getLogger(MedicineService.class);

    private final MedicineRepository medicineRepository;

    public MedicineService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     * Save a medicine.
     *
     * @param medicine the entity to save.
     * @return the persisted entity.
     */
    public Medicine save(Medicine medicine) {
        log.debug("Request to save Medicine : {}", medicine);
        return medicineRepository.save(medicine);
    }

    /**
     * Partially update a medicine.
     *
     * @param medicine the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Medicine> partialUpdate(Medicine medicine) {
        log.debug("Request to partially update Medicine : {}", medicine);

        return medicineRepository
            .findById(medicine.getId())
            .map(
                existingMedicine -> {
                    if (medicine.getName() != null) {
                        existingMedicine.setName(medicine.getName());
                    }
                    if (medicine.getQuantity() != null) {
                        existingMedicine.setQuantity(medicine.getQuantity());
                    }
                    if (medicine.getNotes() != null) {
                        existingMedicine.setNotes(medicine.getNotes());
                    }
                    if (medicine.getCreatedBy() != null) {
                        existingMedicine.setCreatedBy(medicine.getCreatedBy());
                    }
                    if (medicine.getUpdatedBy() != null) {
                        existingMedicine.setUpdatedBy(medicine.getUpdatedBy());
                    }

                    return existingMedicine;
                }
            )
            .map(medicineRepository::save);
    }

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Medicine> findAll(Pageable pageable) {
        log.debug("Request to get all Medicines");
        return medicineRepository.findAll(pageable);
    }

    /**
     * Get one medicine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Medicine> findOne(Long id) {
        log.debug("Request to get Medicine : {}", id);
        return medicineRepository.findById(id);
    }

    /**
     * Delete the medicine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Medicine : {}", id);
        medicineRepository.deleteById(id);
    }
}
