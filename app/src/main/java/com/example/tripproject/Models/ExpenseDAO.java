package com.example.tripproject.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert
    void insert(ExpenseEntity... expenses);

    @Update
    void update(ExpenseEntity... expenses);

    @Delete
    void delete(ExpenseEntity... expenses);

    @Query("DELETE FROM expenses WHERE expenseId = :id")
    void deleteById(int id);



    @Query("SELECT * FROM expenses")
    List<ExpenseEntity> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE tripID = :tripId")
    List<ExpenseEntity> getTripById(int tripId);

    @Query("SELECT * FROM expenses WHERE expenseID = :id")
    ExpenseEntity getExpenseById(int id);

    @Query("SELECT * FROM expenses WHERE type_expenses LIKE :expense")
    ExpenseEntity findByName(String expense);

    @Query("DELETE FROM expenses")
    void deleteAllExpense();
}
