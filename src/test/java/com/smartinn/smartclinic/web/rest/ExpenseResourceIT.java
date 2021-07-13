package com.smartinn.smartclinic.web.rest;

import static com.smartinn.smartclinic.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smartinn.smartclinic.IntegrationTest;
import com.smartinn.smartclinic.domain.Expense;
import com.smartinn.smartclinic.domain.Safe;
import com.smartinn.smartclinic.repository.ExpenseRepository;
import com.smartinn.smartclinic.service.criteria.ExpenseCriteria;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ExpenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpenseResourceIT {

    private static final String DEFAULT_EXPENSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_TYPE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(0 - 1);

    private static final String DEFAULT_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_STATEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILED_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DETAILED_STATEMENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EXPENSE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPENSE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_EXPENSE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpenseMockMvc;

    private Expense expense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expense createEntity(EntityManager em) {
        Expense expense = new Expense()
            .expenseType(DEFAULT_EXPENSE_TYPE)
            .price(DEFAULT_PRICE)
            .statement(DEFAULT_STATEMENT)
            .detailedStatement(DEFAULT_DETAILED_STATEMENT)
            .expenseDate(DEFAULT_EXPENSE_DATE)
            .notes(DEFAULT_NOTES);
        // Add required entity
        Safe safe;
        if (TestUtil.findAll(em, Safe.class).isEmpty()) {
            safe = SafeResourceIT.createEntity(em);
            em.persist(safe);
            em.flush();
        } else {
            safe = TestUtil.findAll(em, Safe.class).get(0);
        }
        expense.setSafe(safe);
        return expense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Expense createUpdatedEntity(EntityManager em) {
        Expense expense = new Expense()
            .expenseType(UPDATED_EXPENSE_TYPE)
            .price(UPDATED_PRICE)
            .statement(UPDATED_STATEMENT)
            .detailedStatement(UPDATED_DETAILED_STATEMENT)
            .expenseDate(UPDATED_EXPENSE_DATE)
            .notes(UPDATED_NOTES);
        // Add required entity
        Safe safe;
        if (TestUtil.findAll(em, Safe.class).isEmpty()) {
            safe = SafeResourceIT.createUpdatedEntity(em);
            em.persist(safe);
            em.flush();
        } else {
            safe = TestUtil.findAll(em, Safe.class).get(0);
        }
        expense.setSafe(safe);
        return expense;
    }

    @BeforeEach
    public void initTest() {
        expense = createEntity(em);
    }

    @Test
    @Transactional
    void createExpense() throws Exception {
        int databaseSizeBeforeCreate = expenseRepository.findAll().size();
        // Create the Expense
        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isCreated());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeCreate + 1);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getExpenseType()).isEqualTo(DEFAULT_EXPENSE_TYPE);
        assertThat(testExpense.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testExpense.getStatement()).isEqualTo(DEFAULT_STATEMENT);
        assertThat(testExpense.getDetailedStatement()).isEqualTo(DEFAULT_DETAILED_STATEMENT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(DEFAULT_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void createExpenseWithExistingId() throws Exception {
        // Create the Expense with an existing ID
        expense.setId(1L);

        int databaseSizeBeforeCreate = expenseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkExpenseTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setExpenseType(null);

        // Create the Expense, which fails.

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setPrice(null);

        // Create the Expense, which fails.

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatementIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setStatement(null);

        // Create the Expense, which fails.

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDetailedStatementIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setDetailedStatement(null);

        // Create the Expense, which fails.

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpenseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setExpenseDate(null);

        // Create the Expense, which fails.

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNotesIsRequired() throws Exception {
        int databaseSizeBeforeTest = expenseRepository.findAll().size();
        // set the field null
        expense.setNotes(null);

        // Create the Expense, which fails.

        restExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isBadRequest());

        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExpenses() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expense.getId().intValue())))
            .andExpect(jsonPath("$.[*].expenseType").value(hasItem(DEFAULT_EXPENSE_TYPE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].statement").value(hasItem(DEFAULT_STATEMENT)))
            .andExpect(jsonPath("$.[*].detailedStatement").value(hasItem(DEFAULT_DETAILED_STATEMENT)))
            .andExpect(jsonPath("$.[*].expenseDate").value(hasItem(DEFAULT_EXPENSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));
    }

    @Test
    @Transactional
    void getExpense() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get the expense
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL_ID, expense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expense.getId().intValue()))
            .andExpect(jsonPath("$.expenseType").value(DEFAULT_EXPENSE_TYPE))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.statement").value(DEFAULT_STATEMENT))
            .andExpect(jsonPath("$.detailedStatement").value(DEFAULT_DETAILED_STATEMENT))
            .andExpect(jsonPath("$.expenseDate").value(DEFAULT_EXPENSE_DATE.toString()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES));
    }

    @Test
    @Transactional
    void getExpensesByIdFiltering() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        Long id = expense.getId();

        defaultExpenseShouldBeFound("id.equals=" + id);
        defaultExpenseShouldNotBeFound("id.notEquals=" + id);

        defaultExpenseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpenseShouldNotBeFound("id.greaterThan=" + id);

        defaultExpenseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpenseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseType equals to DEFAULT_EXPENSE_TYPE
        defaultExpenseShouldBeFound("expenseType.equals=" + DEFAULT_EXPENSE_TYPE);

        // Get all the expenseList where expenseType equals to UPDATED_EXPENSE_TYPE
        defaultExpenseShouldNotBeFound("expenseType.equals=" + UPDATED_EXPENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseType not equals to DEFAULT_EXPENSE_TYPE
        defaultExpenseShouldNotBeFound("expenseType.notEquals=" + DEFAULT_EXPENSE_TYPE);

        // Get all the expenseList where expenseType not equals to UPDATED_EXPENSE_TYPE
        defaultExpenseShouldBeFound("expenseType.notEquals=" + UPDATED_EXPENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseType in DEFAULT_EXPENSE_TYPE or UPDATED_EXPENSE_TYPE
        defaultExpenseShouldBeFound("expenseType.in=" + DEFAULT_EXPENSE_TYPE + "," + UPDATED_EXPENSE_TYPE);

        // Get all the expenseList where expenseType equals to UPDATED_EXPENSE_TYPE
        defaultExpenseShouldNotBeFound("expenseType.in=" + UPDATED_EXPENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseType is not null
        defaultExpenseShouldBeFound("expenseType.specified=true");

        // Get all the expenseList where expenseType is null
        defaultExpenseShouldNotBeFound("expenseType.specified=false");
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseTypeContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseType contains DEFAULT_EXPENSE_TYPE
        defaultExpenseShouldBeFound("expenseType.contains=" + DEFAULT_EXPENSE_TYPE);

        // Get all the expenseList where expenseType contains UPDATED_EXPENSE_TYPE
        defaultExpenseShouldNotBeFound("expenseType.contains=" + UPDATED_EXPENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseTypeNotContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseType does not contain DEFAULT_EXPENSE_TYPE
        defaultExpenseShouldNotBeFound("expenseType.doesNotContain=" + DEFAULT_EXPENSE_TYPE);

        // Get all the expenseList where expenseType does not contain UPDATED_EXPENSE_TYPE
        defaultExpenseShouldBeFound("expenseType.doesNotContain=" + UPDATED_EXPENSE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price equals to DEFAULT_PRICE
        defaultExpenseShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the expenseList where price equals to UPDATED_PRICE
        defaultExpenseShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price not equals to DEFAULT_PRICE
        defaultExpenseShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the expenseList where price not equals to UPDATED_PRICE
        defaultExpenseShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultExpenseShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the expenseList where price equals to UPDATED_PRICE
        defaultExpenseShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price is not null
        defaultExpenseShouldBeFound("price.specified=true");

        // Get all the expenseList where price is null
        defaultExpenseShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price is greater than or equal to DEFAULT_PRICE
        defaultExpenseShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the expenseList where price is greater than or equal to UPDATED_PRICE
        defaultExpenseShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price is less than or equal to DEFAULT_PRICE
        defaultExpenseShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the expenseList where price is less than or equal to SMALLER_PRICE
        defaultExpenseShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price is less than DEFAULT_PRICE
        defaultExpenseShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the expenseList where price is less than UPDATED_PRICE
        defaultExpenseShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where price is greater than DEFAULT_PRICE
        defaultExpenseShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the expenseList where price is greater than SMALLER_PRICE
        defaultExpenseShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllExpensesByStatementIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where statement equals to DEFAULT_STATEMENT
        defaultExpenseShouldBeFound("statement.equals=" + DEFAULT_STATEMENT);

        // Get all the expenseList where statement equals to UPDATED_STATEMENT
        defaultExpenseShouldNotBeFound("statement.equals=" + UPDATED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByStatementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where statement not equals to DEFAULT_STATEMENT
        defaultExpenseShouldNotBeFound("statement.notEquals=" + DEFAULT_STATEMENT);

        // Get all the expenseList where statement not equals to UPDATED_STATEMENT
        defaultExpenseShouldBeFound("statement.notEquals=" + UPDATED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByStatementIsInShouldWork() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where statement in DEFAULT_STATEMENT or UPDATED_STATEMENT
        defaultExpenseShouldBeFound("statement.in=" + DEFAULT_STATEMENT + "," + UPDATED_STATEMENT);

        // Get all the expenseList where statement equals to UPDATED_STATEMENT
        defaultExpenseShouldNotBeFound("statement.in=" + UPDATED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByStatementIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where statement is not null
        defaultExpenseShouldBeFound("statement.specified=true");

        // Get all the expenseList where statement is null
        defaultExpenseShouldNotBeFound("statement.specified=false");
    }

    @Test
    @Transactional
    void getAllExpensesByStatementContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where statement contains DEFAULT_STATEMENT
        defaultExpenseShouldBeFound("statement.contains=" + DEFAULT_STATEMENT);

        // Get all the expenseList where statement contains UPDATED_STATEMENT
        defaultExpenseShouldNotBeFound("statement.contains=" + UPDATED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByStatementNotContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where statement does not contain DEFAULT_STATEMENT
        defaultExpenseShouldNotBeFound("statement.doesNotContain=" + DEFAULT_STATEMENT);

        // Get all the expenseList where statement does not contain UPDATED_STATEMENT
        defaultExpenseShouldBeFound("statement.doesNotContain=" + UPDATED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByDetailedStatementIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where detailedStatement equals to DEFAULT_DETAILED_STATEMENT
        defaultExpenseShouldBeFound("detailedStatement.equals=" + DEFAULT_DETAILED_STATEMENT);

        // Get all the expenseList where detailedStatement equals to UPDATED_DETAILED_STATEMENT
        defaultExpenseShouldNotBeFound("detailedStatement.equals=" + UPDATED_DETAILED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByDetailedStatementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where detailedStatement not equals to DEFAULT_DETAILED_STATEMENT
        defaultExpenseShouldNotBeFound("detailedStatement.notEquals=" + DEFAULT_DETAILED_STATEMENT);

        // Get all the expenseList where detailedStatement not equals to UPDATED_DETAILED_STATEMENT
        defaultExpenseShouldBeFound("detailedStatement.notEquals=" + UPDATED_DETAILED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByDetailedStatementIsInShouldWork() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where detailedStatement in DEFAULT_DETAILED_STATEMENT or UPDATED_DETAILED_STATEMENT
        defaultExpenseShouldBeFound("detailedStatement.in=" + DEFAULT_DETAILED_STATEMENT + "," + UPDATED_DETAILED_STATEMENT);

        // Get all the expenseList where detailedStatement equals to UPDATED_DETAILED_STATEMENT
        defaultExpenseShouldNotBeFound("detailedStatement.in=" + UPDATED_DETAILED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByDetailedStatementIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where detailedStatement is not null
        defaultExpenseShouldBeFound("detailedStatement.specified=true");

        // Get all the expenseList where detailedStatement is null
        defaultExpenseShouldNotBeFound("detailedStatement.specified=false");
    }

    @Test
    @Transactional
    void getAllExpensesByDetailedStatementContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where detailedStatement contains DEFAULT_DETAILED_STATEMENT
        defaultExpenseShouldBeFound("detailedStatement.contains=" + DEFAULT_DETAILED_STATEMENT);

        // Get all the expenseList where detailedStatement contains UPDATED_DETAILED_STATEMENT
        defaultExpenseShouldNotBeFound("detailedStatement.contains=" + UPDATED_DETAILED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByDetailedStatementNotContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where detailedStatement does not contain DEFAULT_DETAILED_STATEMENT
        defaultExpenseShouldNotBeFound("detailedStatement.doesNotContain=" + DEFAULT_DETAILED_STATEMENT);

        // Get all the expenseList where detailedStatement does not contain UPDATED_DETAILED_STATEMENT
        defaultExpenseShouldBeFound("detailedStatement.doesNotContain=" + UPDATED_DETAILED_STATEMENT);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate equals to DEFAULT_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.equals=" + DEFAULT_EXPENSE_DATE);

        // Get all the expenseList where expenseDate equals to UPDATED_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.equals=" + UPDATED_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate not equals to DEFAULT_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.notEquals=" + DEFAULT_EXPENSE_DATE);

        // Get all the expenseList where expenseDate not equals to UPDATED_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.notEquals=" + UPDATED_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsInShouldWork() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate in DEFAULT_EXPENSE_DATE or UPDATED_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.in=" + DEFAULT_EXPENSE_DATE + "," + UPDATED_EXPENSE_DATE);

        // Get all the expenseList where expenseDate equals to UPDATED_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.in=" + UPDATED_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate is not null
        defaultExpenseShouldBeFound("expenseDate.specified=true");

        // Get all the expenseList where expenseDate is null
        defaultExpenseShouldNotBeFound("expenseDate.specified=false");
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate is greater than or equal to DEFAULT_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.greaterThanOrEqual=" + DEFAULT_EXPENSE_DATE);

        // Get all the expenseList where expenseDate is greater than or equal to UPDATED_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.greaterThanOrEqual=" + UPDATED_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate is less than or equal to DEFAULT_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.lessThanOrEqual=" + DEFAULT_EXPENSE_DATE);

        // Get all the expenseList where expenseDate is less than or equal to SMALLER_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.lessThanOrEqual=" + SMALLER_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsLessThanSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate is less than DEFAULT_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.lessThan=" + DEFAULT_EXPENSE_DATE);

        // Get all the expenseList where expenseDate is less than UPDATED_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.lessThan=" + UPDATED_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByExpenseDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where expenseDate is greater than DEFAULT_EXPENSE_DATE
        defaultExpenseShouldNotBeFound("expenseDate.greaterThan=" + DEFAULT_EXPENSE_DATE);

        // Get all the expenseList where expenseDate is greater than SMALLER_EXPENSE_DATE
        defaultExpenseShouldBeFound("expenseDate.greaterThan=" + SMALLER_EXPENSE_DATE);
    }

    @Test
    @Transactional
    void getAllExpensesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where notes equals to DEFAULT_NOTES
        defaultExpenseShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the expenseList where notes equals to UPDATED_NOTES
        defaultExpenseShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllExpensesByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where notes not equals to DEFAULT_NOTES
        defaultExpenseShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the expenseList where notes not equals to UPDATED_NOTES
        defaultExpenseShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllExpensesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultExpenseShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the expenseList where notes equals to UPDATED_NOTES
        defaultExpenseShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllExpensesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where notes is not null
        defaultExpenseShouldBeFound("notes.specified=true");

        // Get all the expenseList where notes is null
        defaultExpenseShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllExpensesByNotesContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where notes contains DEFAULT_NOTES
        defaultExpenseShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the expenseList where notes contains UPDATED_NOTES
        defaultExpenseShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllExpensesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        // Get all the expenseList where notes does not contain DEFAULT_NOTES
        defaultExpenseShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the expenseList where notes does not contain UPDATED_NOTES
        defaultExpenseShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllExpensesBySafeIsEqualToSomething() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);
        Safe safe = SafeResourceIT.createEntity(em);
        em.persist(safe);
        em.flush();
        expense.setSafe(safe);
        expenseRepository.saveAndFlush(expense);
        Long safeId = safe.getId();

        // Get all the expenseList where safe equals to safeId
        defaultExpenseShouldBeFound("safeId.equals=" + safeId);

        // Get all the expenseList where safe equals to (safeId + 1)
        defaultExpenseShouldNotBeFound("safeId.equals=" + (safeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpenseShouldBeFound(String filter) throws Exception {
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expense.getId().intValue())))
            .andExpect(jsonPath("$.[*].expenseType").value(hasItem(DEFAULT_EXPENSE_TYPE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].statement").value(hasItem(DEFAULT_STATEMENT)))
            .andExpect(jsonPath("$.[*].detailedStatement").value(hasItem(DEFAULT_DETAILED_STATEMENT)))
            .andExpect(jsonPath("$.[*].expenseDate").value(hasItem(DEFAULT_EXPENSE_DATE.toString())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)));

        // Check, that the count call also returns 1
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpenseShouldNotBeFound(String filter) throws Exception {
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpenseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExpense() throws Exception {
        // Get the expense
        restExpenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExpense() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();

        // Update the expense
        Expense updatedExpense = expenseRepository.findById(expense.getId()).get();
        // Disconnect from session so that the updates on updatedExpense are not directly saved in db
        em.detach(updatedExpense);
        updatedExpense
            .expenseType(UPDATED_EXPENSE_TYPE)
            .price(UPDATED_PRICE)
            .statement(UPDATED_STATEMENT)
            .detailedStatement(UPDATED_DETAILED_STATEMENT)
            .expenseDate(UPDATED_EXPENSE_DATE)
            .notes(UPDATED_NOTES);

        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedExpense.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getExpenseType()).isEqualTo(UPDATED_EXPENSE_TYPE);
        assertThat(testExpense.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testExpense.getStatement()).isEqualTo(UPDATED_STATEMENT);
        assertThat(testExpense.getDetailedStatement()).isEqualTo(UPDATED_DETAILED_STATEMENT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void putNonExistingExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expense.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExpenseWithPatch() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();

        // Update the expense using partial update
        Expense partialUpdatedExpense = new Expense();
        partialUpdatedExpense.setId(expense.getId());

        partialUpdatedExpense.expenseDate(UPDATED_EXPENSE_DATE);

        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getExpenseType()).isEqualTo(DEFAULT_EXPENSE_TYPE);
        assertThat(testExpense.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testExpense.getStatement()).isEqualTo(DEFAULT_STATEMENT);
        assertThat(testExpense.getDetailedStatement()).isEqualTo(DEFAULT_DETAILED_STATEMENT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(DEFAULT_NOTES);
    }

    @Test
    @Transactional
    void fullUpdateExpenseWithPatch() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();

        // Update the expense using partial update
        Expense partialUpdatedExpense = new Expense();
        partialUpdatedExpense.setId(expense.getId());

        partialUpdatedExpense
            .expenseType(UPDATED_EXPENSE_TYPE)
            .price(UPDATED_PRICE)
            .statement(UPDATED_STATEMENT)
            .detailedStatement(UPDATED_DETAILED_STATEMENT)
            .expenseDate(UPDATED_EXPENSE_DATE)
            .notes(UPDATED_NOTES);

        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpense))
            )
            .andExpect(status().isOk());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
        Expense testExpense = expenseList.get(expenseList.size() - 1);
        assertThat(testExpense.getExpenseType()).isEqualTo(UPDATED_EXPENSE_TYPE);
        assertThat(testExpense.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testExpense.getStatement()).isEqualTo(UPDATED_STATEMENT);
        assertThat(testExpense.getDetailedStatement()).isEqualTo(UPDATED_DETAILED_STATEMENT);
        assertThat(testExpense.getExpenseDate()).isEqualTo(UPDATED_EXPENSE_DATE);
        assertThat(testExpense.getNotes()).isEqualTo(UPDATED_NOTES);
    }

    @Test
    @Transactional
    void patchNonExistingExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expense))
            )
            .andExpect(status().isBadRequest());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExpense() throws Exception {
        int databaseSizeBeforeUpdate = expenseRepository.findAll().size();
        expense.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(expense)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Expense in the database
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExpense() throws Exception {
        // Initialize the database
        expenseRepository.saveAndFlush(expense);

        int databaseSizeBeforeDelete = expenseRepository.findAll().size();

        // Delete the expense
        restExpenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, expense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Expense> expenseList = expenseRepository.findAll();
        assertThat(expenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
