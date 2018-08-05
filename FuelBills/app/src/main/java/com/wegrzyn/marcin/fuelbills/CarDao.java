package com.wegrzyn.marcin.fuelbills;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.sql.Ref;
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

    @Query("SELECT * FROM car_table WHERE car_id= :id")
    LiveData<Car>getCar(int id);

    @Query("SELECT * FROM refueling_table WHERE refueling_id= :id")
    LiveData<Refueling>getRefueling(int id);

    @Query("SELECT * FROM refueling_table WHERE car_id=:id ORDER BY distance DESC LIMIT 1")
    LiveData<Refueling>getMaxDistance(int id);

    @Update
    void updateCar(Car... car);

    @Delete
    void deleteCars(Car... cars);

    @Update
    void updateRefueling(Refueling... refueling);

    @Delete
    void deleteRefueling(Refueling... refueling);
}
