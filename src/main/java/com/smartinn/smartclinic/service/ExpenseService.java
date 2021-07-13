package com.smartinn.smartclinic.service;

import com.smartinn.smartclinic.domain.Expense;
import com.smartinn.smartclinic.repository.ExpenseRepository;
import com.smartinn.smartclinic.repository.UserRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Expense}.
 */
@Service
@Transactional
public class ExpenseService {

    private final Logger log = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;

    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a expense.
     *
     * @param expense the entity to save.
     * @return the persisted entity.
     */
    public Expense save(Expense expense) {
        log.debug("Request to save Expense : {}", expense);
        return expenseRepository.save(expense);
    }

    /**
     * Partially update a expense.
     *
     * @param expense the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Expense> partialUpdate(Expense expense) {
        log.debug("Request to partially update Expense : {}", expense);

        return expenseRepository
            .findById(expense.getId())
            .map(
                existingExpense -> {
                    if (expense.getExpenseType() != null) {
                        existingExpense.setExpenseType(expense.getExpenseType());
                    }
                    if (expense.getExpenseDate() != null) {
                        existingExpense.setExpenseDate(expense.getExpenseDate());
                    }
                    if (expense.getPrice() != null) {
                        existingExpense.setPrice(expense.getPrice());
                    }
                    if (expense.getStatement() != null) {
                        existingExpense.setStatement(expense.getStatement());
                    }
                    if (expense.getNotes() != null) {
                        existingExpense.setNotes(expense.getNotes());
                    }

                    return existingExpense;
                }
            )
            .map(expenseRepository::save);
    }

    /**
     * Get all the expenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Expense> findAll(Pageable pageable) {
        log.debug("Request to get all Expenses");
        return expenseRepository.findAll(pageable);
    }

    /**
     * Get one expense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Expense> findOne(Long id) {
        log.debug("Request to get Expense : {}", id);
        return expenseRepository.findById(id);
    }

    /**
     * Delete the expense by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Expense : {}", id);
        expenseRepository.deleteById(id);
    }
}
