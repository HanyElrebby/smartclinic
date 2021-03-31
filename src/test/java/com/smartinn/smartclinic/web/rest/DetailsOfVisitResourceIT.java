package com.smartinn.smartclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartinn.smartclinic.IntegrationTest;
import com.smartinn.smartclinic.domain.DetailsOfVisit;
import com.smartinn.smartclinic.repository.DetailsOfVisitRepository;
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
 * Integration tests for the {@link DetailsOfVisitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DetailsOfVisitResourceIT {

    private static final String DEFAULT_DESCRIPTION_AILMENTS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_AILMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_OF_DISEASE = "AAAAAAAAAA";
    private static final String UPDATED_NAME_OF_DISEASE = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMMENDATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICINES = "AAAAAAAAAA";
    private static final String UPDATED_MEDICINES = "BBBBBBBBBB";

    private static final String DEFAULT_DOSAGE = "AAAAAAAAAA";
    private static final String UPDATED_DOSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/details-of-visits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetailsOfVisitRepository detailsOfVisitRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetailsOfVisitMockMvc;

    private DetailsOfVisit detailsOfVisit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsOfVisit createEntity(EntityManager em) {
        DetailsOfVisit detailsOfVisit = new DetailsOfVisit()
            .descriptionAilments(DEFAULT_DESCRIPTION_AILMENTS)
            .nameOfDisease(DEFAULT_NAME_OF_DISEASE)
            .recommendations(DEFAULT_RECOMMENDATIONS)
            .medicines(DEFAULT_MEDICINES)
            .dosage(DEFAULT_DOSAGE);
        return detailsOfVisit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetailsOfVisit createUpdatedEntity(EntityManager em) {
        DetailsOfVisit detailsOfVisit = new DetailsOfVisit()
            .descriptionAilments(UPDATED_DESCRIPTION_AILMENTS)
            .nameOfDisease(UPDATED_NAME_OF_DISEASE)
            .recommendations(UPDATED_RECOMMENDATIONS)
            .medicines(UPDATED_MEDICINES)
            .dosage(UPDATED_DOSAGE);
        return detailsOfVisit;
    }

    @BeforeEach
    public void initTest() {
        detailsOfVisit = createEntity(em);
    }

    @Test
    @Transactional
    void createDetailsOfVisit() throws Exception {
        int databaseSizeBeforeCreate = detailsOfVisitRepository.findAll().size();
        // Create the DetailsOfVisit
        restDetailsOfVisitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isCreated());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeCreate + 1);
        DetailsOfVisit testDetailsOfVisit = detailsOfVisitList.get(detailsOfVisitList.size() - 1);
        assertThat(testDetailsOfVisit.getDescriptionAilments()).isEqualTo(DEFAULT_DESCRIPTION_AILMENTS);
        assertThat(testDetailsOfVisit.getNameOfDisease()).isEqualTo(DEFAULT_NAME_OF_DISEASE);
        assertThat(testDetailsOfVisit.getRecommendations()).isEqualTo(DEFAULT_RECOMMENDATIONS);
        assertThat(testDetailsOfVisit.getMedicines()).isEqualTo(DEFAULT_MEDICINES);
        assertThat(testDetailsOfVisit.getDosage()).isEqualTo(DEFAULT_DOSAGE);
    }

    @Test
    @Transactional
    void createDetailsOfVisitWithExistingId() throws Exception {
        // Create the DetailsOfVisit with an existing ID
        detailsOfVisit.setId(1L);

        int databaseSizeBeforeCreate = detailsOfVisitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetailsOfVisitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescriptionAilmentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsOfVisitRepository.findAll().size();
        // set the field null
        detailsOfVisit.setDescriptionAilments(null);

        // Create the DetailsOfVisit, which fails.

        restDetailsOfVisitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameOfDiseaseIsRequired() throws Exception {
        int databaseSizeBeforeTest = detailsOfVisitRepository.findAll().size();
        // set the field null
        detailsOfVisit.setNameOfDisease(null);

        // Create the DetailsOfVisit, which fails.

        restDetailsOfVisitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDetailsOfVisits() throws Exception {
        // Initialize the database
        detailsOfVisitRepository.saveAndFlush(detailsOfVisit);

        // Get all the detailsOfVisitList
        restDetailsOfVisitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detailsOfVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].descriptionAilments").value(hasItem(DEFAULT_DESCRIPTION_AILMENTS)))
            .andExpect(jsonPath("$.[*].nameOfDisease").value(hasItem(DEFAULT_NAME_OF_DISEASE)))
            .andExpect(jsonPath("$.[*].recommendations").value(hasItem(DEFAULT_RECOMMENDATIONS)))
            .andExpect(jsonPath("$.[*].medicines").value(hasItem(DEFAULT_MEDICINES)))
            .andExpect(jsonPath("$.[*].dosage").value(hasItem(DEFAULT_DOSAGE)));
    }

    @Test
    @Transactional
    void getDetailsOfVisit() throws Exception {
        // Initialize the database
        detailsOfVisitRepository.saveAndFlush(detailsOfVisit);

        // Get the detailsOfVisit
        restDetailsOfVisitMockMvc
            .perform(get(ENTITY_API_URL_ID, detailsOfVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detailsOfVisit.getId().intValue()))
            .andExpect(jsonPath("$.descriptionAilments").value(DEFAULT_DESCRIPTION_AILMENTS))
            .andExpect(jsonPath("$.nameOfDisease").value(DEFAULT_NAME_OF_DISEASE))
            .andExpect(jsonPath("$.recommendations").value(DEFAULT_RECOMMENDATIONS))
            .andExpect(jsonPath("$.medicines").value(DEFAULT_MEDICINES))
            .andExpect(jsonPath("$.dosage").value(DEFAULT_DOSAGE));
    }

    @Test
    @Transactional
    void getNonExistingDetailsOfVisit() throws Exception {
        // Get the detailsOfVisit
        restDetailsOfVisitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDetailsOfVisit() throws Exception {
        // Initialize the database
        detailsOfVisitRepository.saveAndFlush(detailsOfVisit);

        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();

        // Update the detailsOfVisit
        DetailsOfVisit updatedDetailsOfVisit = detailsOfVisitRepository.findById(detailsOfVisit.getId()).get();
        // Disconnect from session so that the updates on updatedDetailsOfVisit are not directly saved in db
        em.detach(updatedDetailsOfVisit);
        updatedDetailsOfVisit
            .descriptionAilments(UPDATED_DESCRIPTION_AILMENTS)
            .nameOfDisease(UPDATED_NAME_OF_DISEASE)
            .recommendations(UPDATED_RECOMMENDATIONS)
            .medicines(UPDATED_MEDICINES)
            .dosage(UPDATED_DOSAGE);

        restDetailsOfVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetailsOfVisit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetailsOfVisit))
            )
            .andExpect(status().isOk());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
        DetailsOfVisit testDetailsOfVisit = detailsOfVisitList.get(detailsOfVisitList.size() - 1);
        assertThat(testDetailsOfVisit.getDescriptionAilments()).isEqualTo(UPDATED_DESCRIPTION_AILMENTS);
        assertThat(testDetailsOfVisit.getNameOfDisease()).isEqualTo(UPDATED_NAME_OF_DISEASE);
        assertThat(testDetailsOfVisit.getRecommendations()).isEqualTo(UPDATED_RECOMMENDATIONS);
        assertThat(testDetailsOfVisit.getMedicines()).isEqualTo(UPDATED_MEDICINES);
        assertThat(testDetailsOfVisit.getDosage()).isEqualTo(UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void putNonExistingDetailsOfVisit() throws Exception {
        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();
        detailsOfVisit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsOfVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detailsOfVisit.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetailsOfVisit() throws Exception {
        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();
        detailsOfVisit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsOfVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetailsOfVisit() throws Exception {
        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();
        detailsOfVisit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsOfVisitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detailsOfVisit)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetailsOfVisitWithPatch() throws Exception {
        // Initialize the database
        detailsOfVisitRepository.saveAndFlush(detailsOfVisit);

        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();

        // Update the detailsOfVisit using partial update
        DetailsOfVisit partialUpdatedDetailsOfVisit = new DetailsOfVisit();
        partialUpdatedDetailsOfVisit.setId(detailsOfVisit.getId());

        partialUpdatedDetailsOfVisit.nameOfDisease(UPDATED_NAME_OF_DISEASE).medicines(UPDATED_MEDICINES).dosage(UPDATED_DOSAGE);

        restDetailsOfVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailsOfVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsOfVisit))
            )
            .andExpect(status().isOk());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
        DetailsOfVisit testDetailsOfVisit = detailsOfVisitList.get(detailsOfVisitList.size() - 1);
        assertThat(testDetailsOfVisit.getDescriptionAilments()).isEqualTo(DEFAULT_DESCRIPTION_AILMENTS);
        assertThat(testDetailsOfVisit.getNameOfDisease()).isEqualTo(UPDATED_NAME_OF_DISEASE);
        assertThat(testDetailsOfVisit.getRecommendations()).isEqualTo(DEFAULT_RECOMMENDATIONS);
        assertThat(testDetailsOfVisit.getMedicines()).isEqualTo(UPDATED_MEDICINES);
        assertThat(testDetailsOfVisit.getDosage()).isEqualTo(UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void fullUpdateDetailsOfVisitWithPatch() throws Exception {
        // Initialize the database
        detailsOfVisitRepository.saveAndFlush(detailsOfVisit);

        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();

        // Update the detailsOfVisit using partial update
        DetailsOfVisit partialUpdatedDetailsOfVisit = new DetailsOfVisit();
        partialUpdatedDetailsOfVisit.setId(detailsOfVisit.getId());

        partialUpdatedDetailsOfVisit
            .descriptionAilments(UPDATED_DESCRIPTION_AILMENTS)
            .nameOfDisease(UPDATED_NAME_OF_DISEASE)
            .recommendations(UPDATED_RECOMMENDATIONS)
            .medicines(UPDATED_MEDICINES)
            .dosage(UPDATED_DOSAGE);

        restDetailsOfVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetailsOfVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetailsOfVisit))
            )
            .andExpect(status().isOk());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
        DetailsOfVisit testDetailsOfVisit = detailsOfVisitList.get(detailsOfVisitList.size() - 1);
        assertThat(testDetailsOfVisit.getDescriptionAilments()).isEqualTo(UPDATED_DESCRIPTION_AILMENTS);
        assertThat(testDetailsOfVisit.getNameOfDisease()).isEqualTo(UPDATED_NAME_OF_DISEASE);
        assertThat(testDetailsOfVisit.getRecommendations()).isEqualTo(UPDATED_RECOMMENDATIONS);
        assertThat(testDetailsOfVisit.getMedicines()).isEqualTo(UPDATED_MEDICINES);
        assertThat(testDetailsOfVisit.getDosage()).isEqualTo(UPDATED_DOSAGE);
    }

    @Test
    @Transactional
    void patchNonExistingDetailsOfVisit() throws Exception {
        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();
        detailsOfVisit.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetailsOfVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detailsOfVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetailsOfVisit() throws Exception {
        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();
        detailsOfVisit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsOfVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetailsOfVisit() throws Exception {
        int databaseSizeBeforeUpdate = detailsOfVisitRepository.findAll().size();
        detailsOfVisit.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetailsOfVisitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detailsOfVisit))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetailsOfVisit in the database
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetailsOfVisit() throws Exception {
        // Initialize the database
        detailsOfVisitRepository.saveAndFlush(detailsOfVisit);

        int databaseSizeBeforeDelete = detailsOfVisitRepository.findAll().size();

        // Delete the detailsOfVisit
        restDetailsOfVisitMockMvc
            .perform(delete(ENTITY_API_URL_ID, detailsOfVisit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetailsOfVisit> detailsOfVisitList = detailsOfVisitRepository.findAll();
        assertThat(detailsOfVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
