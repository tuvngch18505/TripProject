package com.example.tripproject.Models;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {TripEntity.class,ExpenseEntity.class}, version = 1)
public abstract class Database extends RoomDatabase{

    public abstract ExpenseDAO expenseDAO();
    public abstract TripDao tripDao();

}
