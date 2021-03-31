package com.smartinn.smartclinic.web.rest;

import com.smartinn.smartclinic.domain.DetailsOfVisit;
import com.smartinn.smartclinic.repository.DetailsOfVisitRepository;
import com.smartinn.smartclinic.service.DetailsOfVisitService;
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
 * REST controller for managing {@link com.smartinn.smartclinic.domain.DetailsOfVisit}.
 */
@RestController
@RequestMapping("/api")
public class DetailsOfVisitResource {

    private final Logger log = LoggerFactory.getLogger(DetailsOfVisitResource.class);

    private static final String ENTITY_NAME = "detailsOfVisit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetailsOfVisitService detailsOfVisitService;

    private final DetailsOfVisitRepository detailsOfVisitRepository;

    public DetailsOfVisitResource(DetailsOfVisitService detailsOfVisitService, DetailsOfVisitRepository detailsOfVisitRepository) {
        this.detailsOfVisitService = detailsOfVisitService;
        this.detailsOfVisitRepository = detailsOfVisitRepository;
    }

    /**
     * {@code POST  /details-of-visits} : Create a new detailsOfVisit.
     *
     * @param detailsOfVisit the detailsOfVisit to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detailsOfVisit, or with status {@code 400 (Bad Request)} if the detailsOfVisit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/details-of-visits")
    public ResponseEntity<DetailsOfVisit> createDetailsOfVisit(@Valid @RequestBody DetailsOfVisit detailsOfVisit)
        throws URISyntaxException {
        log.debug("REST request to save DetailsOfVisit : {}", detailsOfVisit);
        if (detailsOfVisit.getId() != null) {
            throw new BadRequestAlertException("A new detailsOfVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetailsOfVisit result = detailsOfVisitService.save(detailsOfVisit);
        return ResponseEntity
            .created(new URI("/api/details-of-visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /details-of-visits/:id} : Updates an existing detailsOfVisit.
     *
     * @param id the id of the detailsOfVisit to save.
     * @param detailsOfVisit the detailsOfVisit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsOfVisit,
     * or with status {@code 400 (Bad Request)} if the detailsOfVisit is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detailsOfVisit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/details-of-visits/{id}")
    public ResponseEntity<DetailsOfVisit> updateDetailsOfVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DetailsOfVisit detailsOfVisit
    ) throws URISyntaxException {
        log.debug("REST request to update DetailsOfVisit : {}, {}", id, detailsOfVisit);
        if (detailsOfVisit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsOfVisit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsOfVisitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetailsOfVisit result = detailsOfVisitService.save(detailsOfVisit);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detailsOfVisit.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /details-of-visits/:id} : Partial updates given fields of an existing detailsOfVisit, field will ignore if it is null
     *
     * @param id the id of the detailsOfVisit to save.
     * @param detailsOfVisit the detailsOfVisit to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detailsOfVisit,
     * or with status {@code 400 (Bad Request)} if the detailsOfVisit is not valid,
     * or with status {@code 404 (Not Found)} if the detailsOfVisit is not found,
     * or with status {@code 500 (Internal Server Error)} if the detailsOfVisit couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/details-of-visits/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DetailsOfVisit> partialUpdateDetailsOfVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DetailsOfVisit detailsOfVisit
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetailsOfVisit partially : {}, {}", id, detailsOfVisit);
        if (detailsOfVisit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detailsOfVisit.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detailsOfVisitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetailsOfVisit> result = detailsOfVisitService.partialUpdate(detailsOfVisit);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, detailsOfVisit.getId().toString())
        );
    }

    /**
     * {@code GET  /details-of-visits} : get all the detailsOfVisits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detailsOfVisits in body.
     */
    @GetMapping("/details-of-visits")
    public ResponseEntity<List<DetailsOfVisit>> getAllDetailsOfVisits(Pageable pageable) {
        log.debug("REST request to get a page of DetailsOfVisits");
        Page<DetailsOfVisit> page = detailsOfVisitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /details-of-visits/:id} : get the "id" detailsOfVisit.
     *
     * @param id the id of the detailsOfVisit to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detailsOfVisit, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/details-of-visits/{id}")
    public ResponseEntity<DetailsOfVisit> getDetailsOfVisit(@PathVariable Long id) {
        log.debug("REST request to get DetailsOfVisit : {}", id);
        Optional<DetailsOfVisit> detailsOfVisit = detailsOfVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detailsOfVisit);
    }

    /**
     * {@code DELETE  /details-of-visits/:id} : delete the "id" detailsOfVisit.
     *
     * @param id the id of the detailsOfVisit to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/details-of-visits/{id}")
    public ResponseEntity<Void> deleteDetailsOfVisit(@PathVariable Long id) {
        log.debug("REST request to delete DetailsOfVisit : {}", id);
        detailsOfVisitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
