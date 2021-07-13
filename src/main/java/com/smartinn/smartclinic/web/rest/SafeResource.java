package com.smartinn.smartclinic.web.rest;

import static com.smartinn.smartclinic.domain.Safe_.safeSecretary;

import com.smartinn.smartclinic.domain.Safe;
import com.smartinn.smartclinic.repository.SafeRepository;
import com.smartinn.smartclinic.service.SafeService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.smartinn.smartclinic.domain.Safe}.
 */
@RestController
@RequestMapping("/api")
public class SafeResource {

    private final Logger log = LoggerFactory.getLogger(SafeResource.class);

    private static final String ENTITY_NAME = "safe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SafeService safeService;

    private final SafeRepository safeRepository;

    public SafeResource(SafeService safeService, SafeRepository safeRepository) {
        this.safeService = safeService;
        this.safeRepository = safeRepository;
    }

    /**
     * {@code POST  /safes} : Create a new safe.
     *
     * @param safe the safe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new safe, or with status {@code 400 (Bad Request)} if the safe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/safes")
    public ResponseEntity<Safe> createSafe(@Valid @RequestBody Safe safe) throws URISyntaxException {
        log.debug("REST request to save Safe : {}", safe);
        if (safe.getId() != null) {
            throw new BadRequestAlertException("A new safe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Safe result = safeService.save(safe);
        return ResponseEntity
            .created(new URI("/api/safes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /safes/:id} : Updates an existing safe.
     *
     * @param id the id of the safe to save.
     * @param safe the safe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated safe,
     * or with status {@code 400 (Bad Request)} if the safe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the safe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/safes/{id}")
    public ResponseEntity<Safe> updateSafe(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Safe safe)
        throws URISyntaxException {
        log.debug("REST request to update Safe : {}, {}", id, safe);
        if (safe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, safe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!safeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Safe result = safeService.save(safe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, safe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /safes/:id} : Partial updates given fields of an existing safe, field will ignore if it is null
     *
     * @param id the id of the safe to save.
     * @param safe the safe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated safe,
     * or with status {@code 400 (Bad Request)} if the safe is not valid,
     * or with status {@code 404 (Not Found)} if the safe is not found,
     * or with status {@code 500 (Internal Server Error)} if the safe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/safes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Safe> partialUpdateSafe(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Safe safe
    ) throws URISyntaxException {
        log.debug("REST request to partial update Safe partially : {}, {}", id, safe);
        if (safe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, safe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!safeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Safe> result = safeService.partialUpdate(safe);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, safe.getId().toString())
        );
    }

    /**
     * {@code GET  /safes} : get all the safes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of safes in body.
     */
    @GetMapping("/safes")
    public List<Safe> getAllSafes() {
        log.debug("REST request to get all Safes");
        return safeService.findAll();
    }

    /**
     * {@code GET  /safes/:id} : get the "id" safe.
     *
     * @param id the id of the safe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the safe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/safes/id/{id}")
    public ResponseEntity<Safe> getSafe(@PathVariable Long id) {
        log.debug("REST request to get Safe : {}", id);
        Optional<Safe> safe = safeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(safe);
    }

    @GetMapping("/safes/secretary/safeSecretary")
    public ResponseEntity<Safe> getSafeBySecretary(@PathVariable String safeSecretary) {
        log.debug("REST request to get Safe : {}", safeSecretary);
        Optional<Safe> safe = safeService.getSafeBySecretary(safeSecretary);
        return ResponseUtil.wrapOrNotFound(safe);
    }

    /**
     * {@code DELETE  /safes/:id} : delete the "id" safe.
     *
     * @param id the id of the safe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/safes/{id}")
    public ResponseEntity<Void> deleteSafe(@PathVariable Long id) {
        log.debug("REST request to delete Safe : {}", id);
        safeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
