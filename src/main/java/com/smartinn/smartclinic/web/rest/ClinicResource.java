package com.smartinn.smartclinic.web.rest;

import com.smartinn.smartclinic.domain.Clinic;
import com.smartinn.smartclinic.repository.ClinicRepository;
import com.smartinn.smartclinic.service.ClinicService;
import com.smartinn.smartclinic.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.smartinn.smartclinic.domain.Clinic}.
 */
@RestController
@RequestMapping("/api")
public class ClinicResource {

    private final Logger log = LoggerFactory.getLogger(ClinicResource.class);

    private static final String ENTITY_NAME = "clinic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClinicService clinicService;

    private final ClinicRepository clinicRepository;

    public ClinicResource(ClinicService clinicService, ClinicRepository clinicRepository) {
        this.clinicService = clinicService;
        this.clinicRepository = clinicRepository;
    }

    /**
     * {@code POST  /clinics} : Create a new clinic.
     *
     * @param clinic the clinic to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clinic, or with status {@code 400 (Bad Request)} if the clinic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clinics")
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody Clinic clinic) throws URISyntaxException {
        log.debug("REST request to save Clinic : {}", clinic);
        if (clinic.getId() != null) {
            throw new BadRequestAlertException("A new clinic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clinic result = clinicService.save(clinic);
        return ResponseEntity
            .created(new URI("/api/clinics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clinics/:id} : Updates an existing clinic.
     *
     * @param id the id of the clinic to save.
     * @param clinic the clinic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clinic,
     * or with status {@code 400 (Bad Request)} if the clinic is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clinic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clinics/{id}")
    public ResponseEntity<Clinic> updateClinic(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Clinic clinic
    ) throws URISyntaxException {
        log.debug("REST request to update Clinic : {}, {}", id, clinic);
        if (clinic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clinic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clinicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Clinic result = clinicService.save(clinic);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clinic.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /clinics/:id} : Partial updates given fields of an existing clinic, field will ignore if it is null
     *
     * @param id the id of the clinic to save.
     * @param clinic the clinic to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clinic,
     * or with status {@code 400 (Bad Request)} if the clinic is not valid,
     * or with status {@code 404 (Not Found)} if the clinic is not found,
     * or with status {@code 500 (Internal Server Error)} if the clinic couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/clinics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Clinic> partialUpdateClinic(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Clinic clinic
    ) throws URISyntaxException {
        log.debug("REST request to partial update Clinic partially : {}, {}", id, clinic);
        if (clinic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clinic.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clinicRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Clinic> result = clinicService.partialUpdate(clinic);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clinic.getId().toString())
        );
    }

    /**
     * {@code GET  /clinics} : get all the clinics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clinics in body.
     */
    @GetMapping("/clinics")
    public ResponseEntity<List<Clinic>> getAllClinics(Pageable pageable) {
        log.debug("REST request to get a page of Clinics");
        Page<Clinic> page = clinicService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /clinics/:id} : get the "id" clinic.
     *
     * @param id the id of the clinic to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clinic, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clinics/{id}")
    public ResponseEntity<Clinic> getClinic(@PathVariable Long id) {
        log.debug("REST request to get Clinic : {}", id);
        Optional<Clinic> clinic = clinicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clinic);
    }

    /**
     * {@code DELETE  /clinics/:id} : delete the "id" clinic.
     *
     * @param id the id of the clinic to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clinics/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        log.debug("REST request to delete Clinic : {}", id);
        clinicService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
