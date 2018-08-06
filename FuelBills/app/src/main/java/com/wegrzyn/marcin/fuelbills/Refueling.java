package com.wegrzyn.marcin.fuelbills;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Marcin Węgrzyn on 23.07.2018.
 * wireamg@gmail.com
 */
@Entity(tableName = "refueling_table", foreignKeys = {
        @ForeignKey(entity = Car.class,
                parentColumns = "car_id",
                childColumns = "car_id",
                onDelete = ForeignKey.CASCADE)})
public class Refueling {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "refueling_id")
    private int refuelingId;

    @ColumnInfo(name = "car_id")
    private int carId;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "trip_dist")
    private int tripDist;

    @ColumnInfo(name = "distance")
    private int dist;

    @ColumnInfo(name = "quantity")
    private float quantity;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "total_price")
    private double totalPrice;

    @ColumnInfo (name = "avg")
    private float avg;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "fuel_unit")
    private String fuelUnit;

    @ColumnInfo(name = "currency")
    private String currency;

    public Refueling(int carId, String date, int tripDist,int dist, float quantity,
                     double price, double totalPrice,float avg, String note, String fuelUnit, String currency) {
        this.carId = carId;
        this.date = date;
        this.tripDist = tripDist;
        this.dist = dist;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.avg = avg;
        this.note = note;
        this.fuelUnit = fuelUnit;
        this.currency = currency;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public int getRefuelingId() {
        return refuelingId;
    }

    public void setRefuelingId(int refuelingId) {
        this.refuelingId = refuelingId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTripDist() {
        return tripDist;
    }

    public void setTripDist(int tripDist) {
        this.tripDist = tripDist;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public int getDist() {
        return dist;
    }
    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String fuelUnit) {
        this.fuelUnit = fuelUnit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
