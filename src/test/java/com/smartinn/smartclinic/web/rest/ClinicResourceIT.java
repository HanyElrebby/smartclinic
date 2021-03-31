package com.smartinn.smartclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartinn.smartclinic.IntegrationTest;
import com.smartinn.smartclinic.domain.Clinic;
import com.smartinn.smartclinic.repository.ClinicRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ClinicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClinicResourceIT {

    private static final String DEFAULT_NAME_OF_CLINIC = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_CLINIC = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clinics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClinicMockMvc;

    private Clinic clinic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinic createEntity(EntityManager em) {
        Clinic clinic = new Clinic()
            .nameOfClinic(DEFAULT_NAME_OF_CLINIC)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .street(DEFAULT_STREET);
        return clinic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinic createUpdatedEntity(EntityManager em) {
        Clinic clinic = new Clinic()
            .nameOfClinic(UPDATED_NAME_OF_CLINIC)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE)
            .street(UPDATED_STREET);
        return clinic;
    }

    @BeforeEach
    public void initTest() {
        clinic = createEntity(em);
    }

    @Test
    @Transactional
    void createClinic() throws Exception {
        int databaseSizeBeforeCreate = clinicRepository.findAll().size();
        // Create the Clinic
        restClinicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isCreated());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate + 1);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getNameOfClinic()).isEqualTo(DEFAULT_NAME_OF_CLINIC);
        assertThat(testClinic.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClinic.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testClinic.getStreet()).isEqualTo(DEFAULT_STREET);
    }

    @Test
    @Transactional
    void createClinicWithExistingId() throws Exception {
        // Create the Clinic with an existing ID
        clinic.setId(1L);

        int databaseSizeBeforeCreate = clinicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameOfClinicIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicRepository.findAll().size();
        // set the field null
        clinic.setNameOfClinic(null);

        // Create the Clinic, which fails.

        restClinicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicRepository.findAll().size();
        // set the field null
        clinic.setCity(null);

        // Create the Clinic, which fails.

        restClinicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicRepository.findAll().size();
        // set the field null
        clinic.setPostalCode(null);

        // Create the Clinic, which fails.

        restClinicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicRepository.findAll().size();
        // set the field null
        clinic.setStreet(null);

        // Create the Clinic, which fails.

        restClinicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isBadRequest());

        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClinics() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get all the clinicList
        restClinicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinic.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOfClinic").value(hasItem(DEFAULT_NAME_OF_CLINIC)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)));
    }

    @Test
    @Transactional
    void getClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        // Get the clinic
        restClinicMockMvc
            .perform(get(ENTITY_API_URL_ID, clinic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clinic.getId().intValue()))
            .andExpect(jsonPath("$.nameOfClinic").value(DEFAULT_NAME_OF_CLINIC))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET));
    }

    @Test
    @Transactional
    void getNonExistingClinic() throws Exception {
        // Get the clinic
        restClinicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic
        Clinic updatedClinic = clinicRepository.findById(clinic.getId()).get();
        // Disconnect from session so that the updates on updatedClinic are not directly saved in db
        em.detach(updatedClinic);
        updatedClinic.nameOfClinic(UPDATED_NAME_OF_CLINIC).city(UPDATED_CITY).postalCode(UPDATED_POSTAL_CODE).street(UPDATED_STREET);

        restClinicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClinic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClinic))
            )
            .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getNameOfClinic()).isEqualTo(UPDATED_NAME_OF_CLINIC);
        assertThat(testClinic.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClinic.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testClinic.getStreet()).isEqualTo(UPDATED_STREET);
    }

    @Test
    @Transactional
    void putNonExistingClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();
        clinic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clinic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clinic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();
        clinic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clinic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();
        clinic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClinicWithPatch() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic using partial update
        Clinic partialUpdatedClinic = new Clinic();
        partialUpdatedClinic.setId(clinic.getId());

        partialUpdatedClinic.postalCode(UPDATED_POSTAL_CODE).street(UPDATED_STREET);

        restClinicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClinic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClinic))
            )
            .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getNameOfClinic()).isEqualTo(DEFAULT_NAME_OF_CLINIC);
        assertThat(testClinic.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClinic.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testClinic.getStreet()).isEqualTo(UPDATED_STREET);
    }

    @Test
    @Transactional
    void fullUpdateClinicWithPatch() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();

        // Update the clinic using partial update
        Clinic partialUpdatedClinic = new Clinic();
        partialUpdatedClinic.setId(clinic.getId());

        partialUpdatedClinic.nameOfClinic(UPDATED_NAME_OF_CLINIC).city(UPDATED_CITY).postalCode(UPDATED_POSTAL_CODE).street(UPDATED_STREET);

        restClinicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClinic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClinic))
            )
            .andExpect(status().isOk());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
        Clinic testClinic = clinicList.get(clinicList.size() - 1);
        assertThat(testClinic.getNameOfClinic()).isEqualTo(UPDATED_NAME_OF_CLINIC);
        assertThat(testClinic.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClinic.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testClinic.getStreet()).isEqualTo(UPDATED_STREET);
    }

    @Test
    @Transactional
    void patchNonExistingClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();
        clinic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clinic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clinic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();
        clinic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clinic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClinic() throws Exception {
        int databaseSizeBeforeUpdate = clinicRepository.findAll().size();
        clinic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClinicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clinic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clinic in the database
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClinic() throws Exception {
        // Initialize the database
        clinicRepository.saveAndFlush(clinic);

        int databaseSizeBeforeDelete = clinicRepository.findAll().size();

        // Delete the clinic
        restClinicMockMvc
            .perform(delete(ENTITY_API_URL_ID, clinic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clinic> clinicList = clinicRepository.findAll();
        assertThat(clinicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
