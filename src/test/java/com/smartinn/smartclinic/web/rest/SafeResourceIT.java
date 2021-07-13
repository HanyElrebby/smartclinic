package com.smartinn.smartclinic.web.rest;

import static com.smartinn.smartclinic.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartinn.smartclinic.IntegrationTest;
import com.smartinn.smartclinic.domain.Safe;
import com.smartinn.smartclinic.repository.SafeRepository;
import com.smartinn.smartclinic.service.criteria.SafeCriteria;
import java.math.BigDecimal;
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
 * Integration tests for the {@link SafeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SafeResourceIT {

    private static final String DEFAULT_SAFE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SAFE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SAFE_SECRETARY = "AAAAAAAAAA";
    private static final String UPDATED_SAFE_SECRETARY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SAFE_VALUE = new BigDecimal(0);
    private static final BigDecimal UPDATED_SAFE_VALUE = new BigDecimal(1);
    private static final BigDecimal SMALLER_SAFE_VALUE = new BigDecimal(0 - 1);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/safes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SafeRepository safeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSafeMockMvc;

    private Safe safe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Safe createEntity(EntityManager em) {
        Safe safe = new Safe()
            .safeName(DEFAULT_SAFE_NAME)
            .safeSecretary(DEFAULT_SAFE_SECRETARY)
            .safeValue(DEFAULT_SAFE_VALUE)
            .notes(DEFAULT_NOTES);
        return safe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Safe createUpdatedEntity(EntityManager em) {
        Safe safe = new Safe()
            .safeName(UPDATED_SAFE_NAME)
            .safeSecretary(UPDATED_SAFE_SECRETARY)
            .safeValue(UPDATED_SAFE_VALUE)
            .notes(UPDATED_NOTES);
        return safe;
    }

    @BeforeEach
    public void initTest() {
        safe = createEntity(em);
    }

    @Test
    @Transactional
    void createSafe() throws Exception {
        int databaseSizeBeforeCreate = safeRepository.findAll().size();
        // Create the Safe
        restSafeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isCreated());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeCreate + 1);
        Safe testSafe = safeList.get(safeList.size() - 1);
        assertThat(testSafe.getSafeName()).isEqualTo(DEFAULT_SAFE_NAME);
        assertThat(testSafe.getSafeSecretary()).isEqualTo(DEFAULT_SAFE_SECRETARY);
        assertThat(testSafe.getSafeValue()).isEqualByComparingTo(DEFAULT_SAFE_VALUE);
        assertThat(testSafe.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createSafeWithExistingId() throws Exception {
        // Create the Safe with an existing ID
        safe.setId(1L);

        int databaseSizeBeforeCreate = safeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSafeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isBadRequest());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSafeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = safeRepository.findAll().size();
        // set the field null
        safe.setSafeName(null);

        // Create the Safe, which fails.

        restSafeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isBadRequest());

        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSafeSecretaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = safeRepository.findAll().size();
        // set the field null
        safe.setSafeSecretary(null);

        // Create the Safe, which fails.

        restSafeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isBadRequest());

        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSafeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = safeRepository.findAll().size();
        // set the field null
        safe.setSafeValue(null);

        // Create the Safe, which fails.

        restSafeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isBadRequest());

        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = safeRepository.findAll().size();
        // set the field null
        safe.setNotes(null);

        // Create the Safe, which fails.

        restSafeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isBadRequest());

        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSafes() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList
        restSafeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safe.getId().intValue())))
            .andExpect(jsonPath("$.[*].safeName").value(hasItem(DEFAULT_SAFE_NAME)))
            .andExpect(jsonPath("$.[*].safeSecretary").value(hasItem(DEFAULT_SAFE_SECRETARY)))
            .andExpect(jsonPath("$.[*].safeValue").value(hasItem(sameNumber(DEFAULT_SAFE_VALUE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getSafe() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get the safe
        restSafeMockMvc
            .perform(get(ENTITY_API_URL_ID, safe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(safe.getId().intValue()))
            .andExpect(jsonPath("$.safeName").value(DEFAULT_SAFE_NAME))
            .andExpect(jsonPath("$.safeSecretary").value(DEFAULT_SAFE_SECRETARY))
            .andExpect(jsonPath("$.safeValue").value(sameNumber(DEFAULT_SAFE_VALUE)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getSafesByIdFiltering() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        Long id = safe.getId();

        defaultSafeShouldBeFound("id.equals=" + id);
        defaultSafeShouldNotBeFound("id.notEquals=" + id);

        defaultSafeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSafeShouldNotBeFound("id.greaterThan=" + id);

        defaultSafeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSafeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSafesBySafeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeName equals to DEFAULT_SAFE_NAME
        defaultSafeShouldBeFound("safeName.equals=" + DEFAULT_SAFE_NAME);

        // Get all the safeList where safeName equals to UPDATED_SAFE_NAME
        defaultSafeShouldNotBeFound("safeName.equals=" + UPDATED_SAFE_NAME);
    }

    @Test
    @Transactional
    void getAllSafesBySafeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeName not equals to DEFAULT_SAFE_NAME
        defaultSafeShouldNotBeFound("safeName.notEquals=" + DEFAULT_SAFE_NAME);

        // Get all the safeList where safeName not equals to UPDATED_SAFE_NAME
        defaultSafeShouldBeFound("safeName.notEquals=" + UPDATED_SAFE_NAME);
    }

    @Test
    @Transactional
    void getAllSafesBySafeNameIsInShouldWork() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeName in DEFAULT_SAFE_NAME or UPDATED_SAFE_NAME
        defaultSafeShouldBeFound("safeName.in=" + DEFAULT_SAFE_NAME + "," + UPDATED_SAFE_NAME);

        // Get all the safeList where safeName equals to UPDATED_SAFE_NAME
        defaultSafeShouldNotBeFound("safeName.in=" + UPDATED_SAFE_NAME);
    }

    @Test
    @Transactional
    void getAllSafesBySafeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeName is not null
        defaultSafeShouldBeFound("safeName.specified=true");

        // Get all the safeList where safeName is null
        defaultSafeShouldNotBeFound("safeName.specified=false");
    }

    @Test
    @Transactional
    void getAllSafesBySafeNameContainsSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeName contains DEFAULT_SAFE_NAME
        defaultSafeShouldBeFound("safeName.contains=" + DEFAULT_SAFE_NAME);

        // Get all the safeList where safeName contains UPDATED_SAFE_NAME
        defaultSafeShouldNotBeFound("safeName.contains=" + UPDATED_SAFE_NAME);
    }

    @Test
    @Transactional
    void getAllSafesBySafeNameNotContainsSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeName does not contain DEFAULT_SAFE_NAME
        defaultSafeShouldNotBeFound("safeName.doesNotContain=" + DEFAULT_SAFE_NAME);

        // Get all the safeList where safeName does not contain UPDATED_SAFE_NAME
        defaultSafeShouldBeFound("safeName.doesNotContain=" + UPDATED_SAFE_NAME);
    }

    @Test
    @Transactional
    void getAllSafesBySafeSecretaryIsEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeSecretary equals to DEFAULT_SAFE_SECRETARY
        defaultSafeShouldBeFound("safeSecretary.equals=" + DEFAULT_SAFE_SECRETARY);

        // Get all the safeList where safeSecretary equals to UPDATED_SAFE_SECRETARY
        defaultSafeShouldNotBeFound("safeSecretary.equals=" + UPDATED_SAFE_SECRETARY);
    }

    @Test
    @Transactional
    void getAllSafesBySafeSecretaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeSecretary not equals to DEFAULT_SAFE_SECRETARY
        defaultSafeShouldNotBeFound("safeSecretary.notEquals=" + DEFAULT_SAFE_SECRETARY);

        // Get all the safeList where safeSecretary not equals to UPDATED_SAFE_SECRETARY
        defaultSafeShouldBeFound("safeSecretary.notEquals=" + UPDATED_SAFE_SECRETARY);
    }

    @Test
    @Transactional
    void getAllSafesBySafeSecretaryIsInShouldWork() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeSecretary in DEFAULT_SAFE_SECRETARY or UPDATED_SAFE_SECRETARY
        defaultSafeShouldBeFound("safeSecretary.in=" + DEFAULT_SAFE_SECRETARY + "," + UPDATED_SAFE_SECRETARY);

        // Get all the safeList where safeSecretary equals to UPDATED_SAFE_SECRETARY
        defaultSafeShouldNotBeFound("safeSecretary.in=" + UPDATED_SAFE_SECRETARY);
    }

    @Test
    @Transactional
    void getAllSafesBySafeSecretaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeSecretary is not null
        defaultSafeShouldBeFound("safeSecretary.specified=true");

        // Get all the safeList where safeSecretary is null
        defaultSafeShouldNotBeFound("safeSecretary.specified=false");
    }

    @Test
    @Transactional
    void getAllSafesBySafeSecretaryContainsSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeSecretary contains DEFAULT_SAFE_SECRETARY
        defaultSafeShouldBeFound("safeSecretary.contains=" + DEFAULT_SAFE_SECRETARY);

        // Get all the safeList where safeSecretary contains UPDATED_SAFE_SECRETARY
        defaultSafeShouldNotBeFound("safeSecretary.contains=" + UPDATED_SAFE_SECRETARY);
    }

    @Test
    @Transactional
    void getAllSafesBySafeSecretaryNotContainsSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeSecretary does not contain DEFAULT_SAFE_SECRETARY
        defaultSafeShouldNotBeFound("safeSecretary.doesNotContain=" + DEFAULT_SAFE_SECRETARY);

        // Get all the safeList where safeSecretary does not contain UPDATED_SAFE_SECRETARY
        defaultSafeShouldBeFound("safeSecretary.doesNotContain=" + UPDATED_SAFE_SECRETARY);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue equals to DEFAULT_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.equals=" + DEFAULT_SAFE_VALUE);

        // Get all the safeList where safeValue equals to UPDATED_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.equals=" + UPDATED_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue not equals to DEFAULT_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.notEquals=" + DEFAULT_SAFE_VALUE);

        // Get all the safeList where safeValue not equals to UPDATED_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.notEquals=" + UPDATED_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsInShouldWork() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue in DEFAULT_SAFE_VALUE or UPDATED_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.in=" + DEFAULT_SAFE_VALUE + "," + UPDATED_SAFE_VALUE);

        // Get all the safeList where safeValue equals to UPDATED_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.in=" + UPDATED_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue is not null
        defaultSafeShouldBeFound("safeValue.specified=true");

        // Get all the safeList where safeValue is null
        defaultSafeShouldNotBeFound("safeValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue is greater than or equal to DEFAULT_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.greaterThanOrEqual=" + DEFAULT_SAFE_VALUE);

        // Get all the safeList where safeValue is greater than or equal to UPDATED_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.greaterThanOrEqual=" + UPDATED_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue is less than or equal to DEFAULT_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.lessThanOrEqual=" + DEFAULT_SAFE_VALUE);

        // Get all the safeList where safeValue is less than or equal to SMALLER_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.lessThanOrEqual=" + SMALLER_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsLessThanSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue is less than DEFAULT_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.lessThan=" + DEFAULT_SAFE_VALUE);

        // Get all the safeList where safeValue is less than UPDATED_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.lessThan=" + UPDATED_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesBySafeValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where safeValue is greater than DEFAULT_SAFE_VALUE
        defaultSafeShouldNotBeFound("safeValue.greaterThan=" + DEFAULT_SAFE_VALUE);

        // Get all the safeList where safeValue is greater than SMALLER_SAFE_VALUE
        defaultSafeShouldBeFound("safeValue.greaterThan=" + SMALLER_SAFE_VALUE);
    }

    @Test
    @Transactional
    void getAllSafesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where notes equals to DEFAULT_NOTES
        defaultSafeShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the safeList where notes equals to UPDATED_NOTES
        defaultSafeShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSafesByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where notes not equals to DEFAULT_NOTES
        defaultSafeShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the safeList where notes not equals to UPDATED_NOTES
        defaultSafeShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSafesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultSafeShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the safeList where notes equals to UPDATED_NOTES
        defaultSafeShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSafesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where notes is not null
        defaultSafeShouldBeFound("notes.specified=true");

        // Get all the safeList where notes is null
        defaultSafeShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllSafesByNotesContainsSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where notes contains DEFAULT_NOTES
        defaultSafeShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the safeList where notes contains UPDATED_NOTES
        defaultSafeShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllSafesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        // Get all the safeList where notes does not contain DEFAULT_NOTES
        defaultSafeShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the safeList where notes does not contain UPDATED_NOTES
        defaultSafeShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSafeShouldBeFound(String filter) throws Exception {
        restSafeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(safe.getId().intValue())))
            .andExpect(jsonPath("$.[*].safeName").value(hasItem(DEFAULT_SAFE_NAME)))
            .andExpect(jsonPath("$.[*].safeSecretary").value(hasItem(DEFAULT_SAFE_SECRETARY)))
            .andExpect(jsonPath("$.[*].safeValue").value(hasItem(sameNumber(DEFAULT_SAFE_VALUE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restSafeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSafeShouldNotBeFound(String filter) throws Exception {
        restSafeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSafeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSafe() throws Exception {
        // Get the safe
        restSafeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSafe() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        int databaseSizeBeforeUpdate = safeRepository.findAll().size();

        // Update the safe
        Safe updatedSafe = safeRepository.findById(safe.getId()).get();
        // Disconnect from session so that the updates on updatedSafe are not directly saved in db
        em.detach(updatedSafe);
        updatedSafe.safeName(UPDATED_SAFE_NAME).safeSecretary(UPDATED_SAFE_SECRETARY).safeValue(UPDATED_SAFE_VALUE).notes(UPDATED_NOTES);

        restSafeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSafe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSafe))
            )
            .andExpect(status().isOk());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
        Safe testSafe = safeList.get(safeList.size() - 1);
        assertThat(testSafe.getSafeName()).isEqualTo(UPDATED_SAFE_NAME);
        assertThat(testSafe.getSafeSecretary()).isEqualTo(UPDATED_SAFE_SECRETARY);
        assertThat(testSafe.getSafeValue()).isEqualTo(UPDATED_SAFE_VALUE);
        assertThat(testSafe.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingSafe() throws Exception {
        int databaseSizeBeforeUpdate = safeRepository.findAll().size();
        safe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSafeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, safe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(safe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSafe() throws Exception {
        int databaseSizeBeforeUpdate = safeRepository.findAll().size();
        safe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSafeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(safe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSafe() throws Exception {
        int databaseSizeBeforeUpdate = safeRepository.findAll().size();
        safe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSafeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSafeWithPatch() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        int databaseSizeBeforeUpdate = safeRepository.findAll().size();

        // Update the safe using partial update
        Safe partialUpdatedSafe = new Safe();
        partialUpdatedSafe.setId(safe.getId());

        partialUpdatedSafe.safeSecretary(UPDATED_SAFE_SECRETARY).notes(UPDATED_NOTES);

        restSafeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSafe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSafe))
            )
            .andExpect(status().isOk());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
        Safe testSafe = safeList.get(safeList.size() - 1);
        assertThat(testSafe.getSafeName()).isEqualTo(DEFAULT_SAFE_NAME);
        assertThat(testSafe.getSafeSecretary()).isEqualTo(UPDATED_SAFE_SECRETARY);
        assertThat(testSafe.getSafeValue()).isEqualByComparingTo(DEFAULT_SAFE_VALUE);
        assertThat(testSafe.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateSafeWithPatch() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        int databaseSizeBeforeUpdate = safeRepository.findAll().size();

        // Update the safe using partial update
        Safe partialUpdatedSafe = new Safe();
        partialUpdatedSafe.setId(safe.getId());

        partialUpdatedSafe
            .safeName(UPDATED_SAFE_NAME)
            .safeSecretary(UPDATED_SAFE_SECRETARY)
            .safeValue(UPDATED_SAFE_VALUE)
            .notes(UPDATED_NOTES);

        restSafeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSafe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSafe))
            )
            .andExpect(status().isOk());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
        Safe testSafe = safeList.get(safeList.size() - 1);
        assertThat(testSafe.getSafeName()).isEqualTo(UPDATED_SAFE_NAME);
        assertThat(testSafe.getSafeSecretary()).isEqualTo(UPDATED_SAFE_SECRETARY);
        assertThat(testSafe.getSafeValue()).isEqualByComparingTo(UPDATED_SAFE_VALUE);
        assertThat(testSafe.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingSafe() throws Exception {
        int databaseSizeBeforeUpdate = safeRepository.findAll().size();
        safe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSafeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, safe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(safe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSafe() throws Exception {
        int databaseSizeBeforeUpdate = safeRepository.findAll().size();
        safe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSafeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(safe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSafe() throws Exception {
        int databaseSizeBeforeUpdate = safeRepository.findAll().size();
        safe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSafeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(safe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Safe in the database
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSafe() throws Exception {
        // Initialize the database
        safeRepository.saveAndFlush(safe);

        int databaseSizeBeforeDelete = safeRepository.findAll().size();

        // Delete the safe
        restSafeMockMvc
            .perform(delete(ENTITY_API_URL_ID, safe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Safe> safeList = safeRepository.findAll();
        assertThat(safeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
