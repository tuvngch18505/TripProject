package com.example.tripproject.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TripDao {
    @Insert
    void insert(TripEntity... trips);

    @Update
    void update(TripEntity... trips);

    @Delete
    void delete(TripEntity... trips);

    @Query("SELECT * FROM trips")
    List<TripEntity> getAllTrips();

    @Query("SELECT * FROM trips WHERE tripId = :id")
    TripEntity getTripById(int id);

    @Query("DELETE FROM trips WHERE tripId = :id")
    void deleteById(int id);

    @Query("DELETE FROM trips")
    void deleteAllTrip();
}
