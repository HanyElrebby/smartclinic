package com.smartinn.smartclinic.web.rest;

import com.smartinn.smartclinic.domain.Medicine;
import com.smartinn.smartclinic.repository.MedicineRepository;
import com.smartinn.smartclinic.service.MedicineService;
import com.smartinn.smartclinic.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.smartinn.smartclinic.domain.Medicine}.
 */
@RestController
@RequestMapping("/api")
public class MedicineResource {

    private final Logger log = LoggerFactory.getLogger(MedicineResource.class);

    private static final String ENTITY_NAME = "medicine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineService medicineService;

    private final MedicineRepository medicineRepository;

    public MedicineResource(MedicineService medicineService, MedicineRepository medicineRepository) {
        this.medicineService = medicineService;
        this.medicineRepository = medicineRepository;
    }

    /**
     * {@code POST  /medicines} : Create a new medicine.
     *
     * @param medicine the medicine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicine, or with status {@code 400 (Bad Request)} if the medicine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicines")
    public ResponseEntity<Medicine> createMedicine(@RequestBody Medicine medicine) throws URISyntaxException {
        log.debug("REST request to save Medicine : {}", medicine);
        if (medicine.getId() != null) {
            throw new BadRequestAlertException("A new medicine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medicine result = medicineService.save(medicine);
        return ResponseEntity
            .created(new URI("/api/medicines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicines/:id} : Updates an existing medicine.
     *
     * @param id the id of the medicine to save.
     * @param medicine the medicine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicine,
     * or with status {@code 400 (Bad Request)} if the medicine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicines/{id}")
    public ResponseEntity<Medicine> updateMedicine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Medicine medicine
    ) throws URISyntaxException {
        log.debug("REST request to update Medicine : {}, {}", id, medicine);
        if (medicine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Medicine result = medicineService.save(medicine);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicine.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medicines/:id} : Partial updates given fields of an existing medicine, field will ignore if it is null
     *
     * @param id the id of the medicine to save.
     * @param medicine the medicine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicine,
     * or with status {@code 400 (Bad Request)} if the medicine is not valid,
     * or with status {@code 404 (Not Found)} if the medicine is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medicines/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Medicine> partialUpdateMedicine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Medicine medicine
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medicine partially : {}, {}", id, medicine);
        if (medicine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicine.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Medicine> result = medicineService.partialUpdate(medicine);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicine.getId().toString())
        );
    }

    /**
     * {@code GET  /medicines} : get all the medicines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicines in body.
     */
    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines(Pageable pageable) {
        log.debug("REST request to get a page of Medicines");
        Page<Medicine> page = medicineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicines/:id} : get the "id" medicine.
     *
     * @param id the id of the medicine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicines/{id}")
    public ResponseEntity<Medicine> getMedicine(@PathVariable Long id) {
        log.debug("REST request to get Medicine : {}", id);
        Optional<Medicine> medicine = medicineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicine);
    }

    /**
     * {@code DELETE  /medicines/:id} : delete the "id" medicine.
     *
     * @param id the id of the medicine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.debug("REST request to delete Medicine : {}", id);
        medicineService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
