package com.example.tripproject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.tripproject.Models.Database;
import com.example.tripproject.Models.DatabaseHelper;
import com.example.tripproject.Models.ExpenseEntity;
import com.example.tripproject.Models.TripDao;
import com.example.tripproject.Models.TripEntity;

import java.util.List;

public class MainViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    MutableLiveData<List<TripEntity>> tripList;
    MutableLiveData<List<ExpenseEntity>> expenseList;

    Database db;

    public MainViewModel(){
        tripList = new MutableLiveData<>();
        expenseList = new MutableLiveData<>();
    }

    public MutableLiveData<List<TripEntity>> getData() {
        List<TripEntity> data = db.tripDao().getAllTrips();
        tripList.setValue(data);
        return tripList;
    }



    public MutableLiveData<List<ExpenseEntity>>  getDataExpenses(int tripId){
        List<ExpenseEntity> data = db.expenseDAO().getTripById(tripId);
        expenseList.setValue(data);
        return expenseList;

    }


    public MutableLiveData<List<ExpenseEntity>>  getAllExpenses(){
        List<ExpenseEntity> data = db.expenseDAO().getAllExpenses();
        expenseList.setValue(data);
        return expenseList;

    }

//    public LiveData<List<TripEntity>> getDatabyID(int ID) {
//        List<TripEntity> data = (List<TripEntity>) db.getTrip(ID);
//        tripList.setValue(data);
//        return tripList;
//    }


    public void setDatabase(Database db){
        this.db = db;
    }

}