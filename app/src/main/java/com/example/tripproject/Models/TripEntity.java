package com.example.tripproject.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "trips")
public class TripEntity {
    @PrimaryKey(autoGenerate = true)
    private int tripId;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    private String nameTrip;
    private String destination;

    private String dayOfTrip;
    private int riskEvaluate;
    private String note;

    public int getRiskEvaluate() {
        return riskEvaluate;
    }

    public String getChooseServices() {
        return chooseServices;
    }

    public void setChooseServices(String chooseServices) {
        this.chooseServices = chooseServices;
    }

    private String chooseServices;

    public TripEntity(){}
    public TripEntity(int id, String nameTrip, String destination, String dayOfTrip, int riskEvaluate, String note, String chooseService) {
        this.tripId = id;
        this.nameTrip = nameTrip;
        this.destination = destination;
        this.dayOfTrip = dayOfTrip;
        this.riskEvaluate = riskEvaluate;
        this.note = note;
        this.chooseServices = chooseService;
    }

    public TripEntity(String nameTrip, String destination, String dayOfTrip, int riskEvaluate, String note, String chooseService){
        this.nameTrip = nameTrip;
        this.destination = destination;
        this.dayOfTrip = dayOfTrip;
        this.riskEvaluate = riskEvaluate;
        this.note = note;
        this.chooseServices = chooseService;
    }

    public String getNameTrip() {
        return nameTrip;
    }

    public void setNameTrip(String nameTrip) {
        this.nameTrip = nameTrip;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDayOfTrip() {
        return dayOfTrip;
    }

    public void setDayOfTrip(String dayOfTrip) {
        this.dayOfTrip = dayOfTrip;
    }

    public int isRiskEvaluate() {
        return riskEvaluate;
    }

    public void setRiskEvaluate(int riskEvaluate) {
        this.riskEvaluate = riskEvaluate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
