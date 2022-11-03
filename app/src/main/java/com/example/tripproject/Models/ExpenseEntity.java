package com.example.tripproject.Models;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class ExpenseEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull private int expenseID;

    public TripEntity getTripEntity() {
        return tripEntity;
    }

    public void setTripEntity(TripEntity tripEntity) {
        this.tripEntity = tripEntity;
    }

    @Embedded
    private TripEntity tripEntity;

    @ColumnInfo(name = "type_expenses")
    private String typeOfExpenses;

    @ColumnInfo(name = "amount_expenses")
    private String amountOfTheExpenses;

    @ColumnInfo(name = "time_expenses")
    private String timeOfTheExpenses;

    @ColumnInfo(name = "additional_comments")
    private String addComments;

    public ExpenseEntity() {
    }

    public ExpenseEntity(TripEntity tripEntity, String typeOfExpenses, String amountOfTheExpenses, String timeOfTheExpenses, String addComments) {
        this.tripEntity = tripEntity;
        this.typeOfExpenses = typeOfExpenses;
        this.amountOfTheExpenses = amountOfTheExpenses;
        this.timeOfTheExpenses = timeOfTheExpenses;
        this.addComments = addComments;
    }

    public ExpenseEntity(String typeOfExpenses, String amountOfTheExpenses, String timeOfTheExpenses, String addComments) {
        this.typeOfExpenses = typeOfExpenses;
        this.amountOfTheExpenses = amountOfTheExpenses;
        this.timeOfTheExpenses = timeOfTheExpenses;
        this.addComments = addComments;
    }



    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getTypeOfExpenses() {
        return typeOfExpenses;
    }

    public void setTypeOfExpenses(String typeOfExpenses) {
        this.typeOfExpenses = typeOfExpenses;
    }

    public String getAmountOfTheExpenses() {
        return amountOfTheExpenses;
    }

    public void setAmountOfTheExpenses(String amountOfTheExpenses) {
        this.amountOfTheExpenses = amountOfTheExpenses;
    }

    public String getTimeOfTheExpenses() {
        return timeOfTheExpenses;
    }

    public void setTimeOfTheExpenses(String timeOfTheExpenses) {
        this.timeOfTheExpenses = timeOfTheExpenses;
    }

    public String getAddComments() {
        return addComments;
    }

    public void setAddComments(String addComments) {
        this.addComments = addComments;
    }




}
