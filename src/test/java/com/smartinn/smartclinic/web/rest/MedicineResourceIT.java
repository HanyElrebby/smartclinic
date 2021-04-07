package com.smartinn.smartclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartinn.smartclinic.IntegrationTest;
import com.smartinn.smartclinic.domain.Medicine;
import com.smartinn.smartclinic.repository.MedicineRepository;
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
 * Integration tests for the {@link MedicineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medicines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicineMockMvc;

    private Medicine medicine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicine createEntity(EntityManager em) {
        Medicine medicine = new Medicine()
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .notes(DEFAULT_NOTES)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedBy(DEFAULT_UPDATED_BY);
        return medicine;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicine createUpdatedEntity(EntityManager em) {
        Medicine medicine = new Medicine()
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .notes(UPDATED_NOTES)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);
        return medicine;
    }

    @BeforeEach
    public void initTest() {
        medicine = createEntity(em);
    }

    @Test
    @Transactional
    void createMedicine() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();
        // Create the Medicine
        restMedicineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isCreated());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate + 1);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicine.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testMedicine.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testMedicine.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMedicine.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createMedicineWithExistingId() throws Exception {
        // Create the Medicine with an existing ID
        medicine.setId(1L);

        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicines() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList
        restMedicineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }

    @Test
    @Transactional
    void getMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get the medicine
        restMedicineMockMvc
            .perform(get(ENTITY_API_URL_ID, medicine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    void getNonExistingMedicine() throws Exception {
        // Get the medicine
        restMedicineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Update the medicine
        Medicine updatedMedicine = medicineRepository.findById(medicine.getId()).get();
        // Disconnect from session so that the updates on updatedMedicine are not directly saved in db
        em.detach(updatedMedicine);
        updatedMedicine
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .notes(UPDATED_NOTES)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restMedicineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedicine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMedicine))
            )
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMedicine.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMedicine.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMedicine.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();
        medicine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();
        medicine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();
        medicine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicineWithPatch() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Update the medicine using partial update
        Medicine partialUpdatedMedicine = new Medicine();
        partialUpdatedMedicine.setId(medicine.getId());

        partialUpdatedMedicine.name(UPDATED_NAME).quantity(UPDATED_QUANTITY).notes(UPDATED_NOTES);

        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicine))
            )
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMedicine.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMedicine.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMedicine.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateMedicineWithPatch() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Update the medicine using partial update
        Medicine partialUpdatedMedicine = new Medicine();
        partialUpdatedMedicine.setId(medicine.getId());

        partialUpdatedMedicine
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .notes(UPDATED_NOTES)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicine))
            )
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicine.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testMedicine.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testMedicine.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMedicine.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();
        medicine.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicine.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();
        medicine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicine))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();
        medicine.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        int databaseSizeBeforeDelete = medicineRepository.findAll().size();

        // Delete the medicine
        restMedicineMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
