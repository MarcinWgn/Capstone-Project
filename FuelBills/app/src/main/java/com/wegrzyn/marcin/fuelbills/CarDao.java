package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 24.07.2018.
 * wireamg@gmail.com
 */
@Dao
public interface CarDao {

    @Insert()
    void insertCar(Car car);

    @Insert
    void insertRefueling(Refueling refueling);

    @Query("SELECT * FROM car_table")
    LiveData<List<Car>> getAllCar();

    @Query("SELECT * FROM refueling_table")
    LiveData<List<Refueling>> getAllRefueling();

    @Query("SELECT * FROM refueling_table WHERE refueling_table.car_id= :id")
    LiveData<List<Refueling>> getCarRefueling(int id);

    @Query("DELETE FROM car_table WHERE car_table.car_id= :id")
    void deleteCar(int id);

    @Query("DELETE FROM refueling_table WHERE refueling_table.refueling_id= :id")
    void deleteRefueling(int id);

    @Delete
    public void deleteCars(Car... cars);
}
