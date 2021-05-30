package com.smartinn.smartclinic.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartinn.smartclinic.IntegrationTest;
import com.smartinn.smartclinic.domain.ChartValues;
import com.smartinn.smartclinic.repository.ChartValuesRepository;
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
 * Integration tests for the {@link ChartValuesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChartValuesResourceIT {

    private static final Double DEFAULT_X_VALUE = 1D;
    private static final Double UPDATED_X_VALUE = 2D;

    private static final Double DEFAULT_Y_VALUE = 1D;
    private static final Double UPDATED_Y_VALUE = 2D;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chart-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ChartValuesRepository chartValuesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChartValuesMockMvc;

    private ChartValues chartValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChartValues createEntity(EntityManager em) {
        ChartValues chartValues = new ChartValues().xValue(DEFAULT_X_VALUE).yValue(DEFAULT_Y_VALUE).type(DEFAULT_TYPE);
        return chartValues;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChartValues createUpdatedEntity(EntityManager em) {
        ChartValues chartValues = new ChartValues().xValue(UPDATED_X_VALUE).yValue(UPDATED_Y_VALUE).type(UPDATED_TYPE);
        return chartValues;
    }

    @BeforeEach
    public void initTest() {
        chartValues = createEntity(em);
    }

    @Test
    @Transactional
    void createChartValues() throws Exception {
        int databaseSizeBeforeCreate = chartValuesRepository.findAll().size();
        // Create the ChartValues
        restChartValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chartValues)))
            .andExpect(status().isCreated());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeCreate + 1);
        ChartValues testChartValues = chartValuesList.get(chartValuesList.size() - 1);
        assertThat(testChartValues.getxValue()).isEqualTo(DEFAULT_X_VALUE);
        assertThat(testChartValues.getyValue()).isEqualTo(DEFAULT_Y_VALUE);
        assertThat(testChartValues.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createChartValuesWithExistingId() throws Exception {
        // Create the ChartValues with an existing ID
        chartValues.setId(1L);

        int databaseSizeBeforeCreate = chartValuesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChartValuesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chartValues)))
            .andExpect(status().isBadRequest());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllChartValues() throws Exception {
        // Initialize the database
        chartValuesRepository.saveAndFlush(chartValues);

        // Get all the chartValuesList
        restChartValuesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chartValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].xValue").value(hasItem(DEFAULT_X_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].yValue").value(hasItem(DEFAULT_Y_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getChartValues() throws Exception {
        // Initialize the database
        chartValuesRepository.saveAndFlush(chartValues);

        // Get the chartValues
        restChartValuesMockMvc
            .perform(get(ENTITY_API_URL_ID, chartValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chartValues.getId().intValue()))
            .andExpect(jsonPath("$.xValue").value(DEFAULT_X_VALUE.doubleValue()))
            .andExpect(jsonPath("$.yValue").value(DEFAULT_Y_VALUE.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingChartValues() throws Exception {
        // Get the chartValues
        restChartValuesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewChartValues() throws Exception {
        // Initialize the database
        chartValuesRepository.saveAndFlush(chartValues);

        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();

        // Update the chartValues
        ChartValues updatedChartValues = chartValuesRepository.findById(chartValues.getId()).get();
        // Disconnect from session so that the updates on updatedChartValues are not directly saved in db
        em.detach(updatedChartValues);
        updatedChartValues.xValue(UPDATED_X_VALUE).yValue(UPDATED_Y_VALUE).type(UPDATED_TYPE);

        restChartValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChartValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChartValues))
            )
            .andExpect(status().isOk());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
        ChartValues testChartValues = chartValuesList.get(chartValuesList.size() - 1);
        assertThat(testChartValues.getxValue()).isEqualTo(UPDATED_X_VALUE);
        assertThat(testChartValues.getyValue()).isEqualTo(UPDATED_Y_VALUE);
        assertThat(testChartValues.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingChartValues() throws Exception {
        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();
        chartValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChartValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chartValues.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chartValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChartValues() throws Exception {
        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();
        chartValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChartValuesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chartValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChartValues() throws Exception {
        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();
        chartValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChartValuesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chartValues)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChartValuesWithPatch() throws Exception {
        // Initialize the database
        chartValuesRepository.saveAndFlush(chartValues);

        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();

        // Update the chartValues using partial update
        ChartValues partialUpdatedChartValues = new ChartValues();
        partialUpdatedChartValues.setId(chartValues.getId());

        partialUpdatedChartValues.xValue(UPDATED_X_VALUE).yValue(UPDATED_Y_VALUE).type(UPDATED_TYPE);

        restChartValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChartValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChartValues))
            )
            .andExpect(status().isOk());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
        ChartValues testChartValues = chartValuesList.get(chartValuesList.size() - 1);
        assertThat(testChartValues.getxValue()).isEqualTo(UPDATED_X_VALUE);
        assertThat(testChartValues.getyValue()).isEqualTo(UPDATED_Y_VALUE);
        assertThat(testChartValues.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateChartValuesWithPatch() throws Exception {
        // Initialize the database
        chartValuesRepository.saveAndFlush(chartValues);

        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();

        // Update the chartValues using partial update
        ChartValues partialUpdatedChartValues = new ChartValues();
        partialUpdatedChartValues.setId(chartValues.getId());

        partialUpdatedChartValues.xValue(UPDATED_X_VALUE).yValue(UPDATED_Y_VALUE).type(UPDATED_TYPE);

        restChartValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChartValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChartValues))
            )
            .andExpect(status().isOk());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
        ChartValues testChartValues = chartValuesList.get(chartValuesList.size() - 1);
        assertThat(testChartValues.getxValue()).isEqualTo(UPDATED_X_VALUE);
        assertThat(testChartValues.getyValue()).isEqualTo(UPDATED_Y_VALUE);
        assertThat(testChartValues.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingChartValues() throws Exception {
        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();
        chartValues.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChartValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chartValues.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chartValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChartValues() throws Exception {
        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();
        chartValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChartValuesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chartValues))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChartValues() throws Exception {
        int databaseSizeBeforeUpdate = chartValuesRepository.findAll().size();
        chartValues.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChartValuesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chartValues))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChartValues in the database
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChartValues() throws Exception {
        // Initialize the database
        chartValuesRepository.saveAndFlush(chartValues);

        int databaseSizeBeforeDelete = chartValuesRepository.findAll().size();

        // Delete the chartValues
        restChartValuesMockMvc
            .perform(delete(ENTITY_API_URL_ID, chartValues.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChartValues> chartValuesList = chartValuesRepository.findAll();
        assertThat(chartValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
