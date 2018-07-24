package com.wegrzyn.marcin.fuelbills;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Marcin Węgrzyn on 23.07.2018.
 * wireamg@gmail.com
 */

@Entity(tableName = "car_table")
public class Car {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    private int carId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "vin")
    private String vin;
    @ColumnInfo(name = "fuel")
    private int fuelType;
    @ColumnInfo(name = "plate")
    private String plate;
    @ColumnInfo(name = "tank_size")
    private int tankSize;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getFuelType() {
        return fuelType;
    }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getTankSize() {
        return tankSize;
    }

    public void setTankSize(int tankSize) {
        this.tankSize = tankSize;
    }
}
