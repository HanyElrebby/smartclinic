package com.smartinn.smartclinic.web.rest;

import com.smartinn.smartclinic.domain.ChartValues;
import com.smartinn.smartclinic.domain.Patient;
import com.smartinn.smartclinic.repository.ChartValuesRepository;
import com.smartinn.smartclinic.repository.PatientRepository;
import com.smartinn.smartclinic.web.rest.errors.BadRequestAlertException;
import com.smartinn.smartclinic.web.rest.vm.ChartModel;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.smartinn.smartclinic.domain.ChartValues}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChartValuesResource {

    private final Logger log = LoggerFactory.getLogger(ChartValuesResource.class);

    private static final String ENTITY_NAME = "chartValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChartValuesRepository chartValuesRepository;

    private final PatientRepository patientRepository;

    public ChartValuesResource(ChartValuesRepository chartValuesRepository, PatientRepository patientRepository) {
        this.chartValuesRepository = chartValuesRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * {@code POST  /chart-values} : Create a new chartValues.
     *
     * @param chartValues the chartValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chartValues, or with status {@code 400 (Bad Request)} if the chartValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chart-values")
    public ResponseEntity<ChartValues> createChartValues(@RequestBody ChartValues chartValues) throws URISyntaxException {
        log.debug("REST request to save ChartValues : {}", chartValues);
        if (chartValues.getId() != null) {
            throw new BadRequestAlertException("A new chartValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChartValues result = chartValuesRepository.save(chartValues);
        return ResponseEntity
            .created(new URI("/api/chart-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chart-values/:id} : Updates an existing chartValues.
     *
     * @param id the id of the chartValues to save.
     * @param chartValues the chartValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chartValues,
     * or with status {@code 400 (Bad Request)} if the chartValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chartValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chart-values/{id}")
    public ResponseEntity<ChartValues> updateChartValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChartValues chartValues
    ) throws URISyntaxException {
        log.debug("REST request to update ChartValues : {}, {}", id, chartValues);
        if (chartValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chartValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chartValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChartValues result = chartValuesRepository.save(chartValues);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chartValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chart-values/:id} : Partial updates given fields of an existing chartValues, field will ignore if it is null
     *
     * @param id the id of the chartValues to save.
     * @param chartValues the chartValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chartValues,
     * or with status {@code 400 (Bad Request)} if the chartValues is not valid,
     * or with status {@code 404 (Not Found)} if the chartValues is not found,
     * or with status {@code 500 (Internal Server Error)} if the chartValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chart-values/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ChartValues> partialUpdateChartValues(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ChartValues chartValues
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChartValues partially : {}, {}", id, chartValues);
        if (chartValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chartValues.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chartValuesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChartValues> result = chartValuesRepository
            .findById(chartValues.getId())
            .map(
                existingChartValues -> {
                    if (chartValues.getAge() != null) {
                        existingChartValues.setAge(chartValues.getAge());
                    }
                    if (chartValues.getLength() != null) {
                        existingChartValues.setLength(chartValues.getLength());
                    }
                    if (chartValues.getType() != null) {
                        existingChartValues.setType(chartValues.getType());
                    }

                    return existingChartValues;
                }
            )
            .map(chartValuesRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, chartValues.getId().toString())
        );
    }

    /**
     * {@code GET  /chart-values} : get all the chartValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chartValues in body.
     */
    @GetMapping("/chart-values")
    public ResponseEntity<List<ChartValues>> getAllChartValues(Pageable pageable) {
        log.debug("REST request to get a page of ChartValues");
        Page<ChartValues> page = chartValuesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/chart-values/byPatientId/{patientId}")
    public ResponseEntity<List<ChartValues>> getAllChartValues(@PathVariable Long patientId) {
        log.debug("REST request to get a page of ChartValues");
        List<ChartValues> page = chartValuesRepository.getByPatientId(patientId);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping("/chart-values/getForChart")
    public ResponseEntity<List<ChartModel>> getAllChartValues() {
        log.debug("REST request to get a page of ChartValues");

        List<Patient> patients = patientRepository.findAll();
        List<ChartModel> models = new ArrayList<>();

        for (Patient p : patients) {
            ChartModel model = new ChartModel();
            model.setName(p.getName());
            model.setId(p.getId());
            List<List<Double>> data = new ArrayList<>();
            List<ChartValues> chartValues = chartValuesRepository.getByPatientId(p.getId());
            for (ChartValues value : chartValues) {
                List<Double> dataItem = new ArrayList<>();
                dataItem.add(value.getAge());
                dataItem.add(value.getLength());

                data.add(dataItem);
            }

            model.setLengthData(data);

            if (data.size() > 0) {
                models.add(model);
            }
        }

        return ResponseEntity.ok().body(models);
    }

    @GetMapping("/chart-values/getForChart/byPatientId/{patientId}")
    public ResponseEntity<ChartModel> getAllChartValuesorChartByPatientId(@PathVariable Long patientId) {
        log.debug("REST request to get a page of ChartValues");

        List<Patient> patients = patientRepository.findAll();
        Patient p = patientRepository.findById(patientId).get();

        ChartModel model = new ChartModel();
        model.setName(p.getName());
        model.setId(p.getId());
        List<List<Double>> lengthData = new ArrayList<>();
        List<List<Double>> weightData = new ArrayList<>();
        List<ChartValues> chartValues = chartValuesRepository.getByPatientId(patientId);
        for (ChartValues value : chartValues) {
            List<Double> dataItem = new ArrayList<>();
            dataItem.add(value.getAge());
            dataItem.add(value.getLength());

            lengthData.add(dataItem);

            List<Double> dataItemWeight = new ArrayList<>();
            dataItemWeight.add(value.getAge());
            if (value.getWeight() != null) {
                dataItemWeight.add(value.getWeight());
            } else {
                dataItemWeight.add(0.0);
            }

            weightData.add(dataItemWeight);
        }

        model.setLengthData(lengthData);
        model.setWeightData(weightData);

        return ResponseEntity.ok().body(model);
    }

    /**
     * {@code GET  /chart-values/:id} : get the "id" chartValues.
     *
     * @param id the id of the chartValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chartValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chart-values/{id}")
    public ResponseEntity<ChartValues> getChartValues(@PathVariable Long id) {
        log.debug("REST request to get ChartValues : {}", id);
        Optional<ChartValues> chartValues = chartValuesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chartValues);
    }

    /**
     * {@code DELETE  /chart-values/:id} : delete the "id" chartValues.
     *
     * @param id the id of the chartValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chart-values/{id}")
    public ResponseEntity<Void> deleteChartValues(@PathVariable Long id) {
        log.debug("REST request to delete ChartValues : {}", id);
        chartValuesRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
