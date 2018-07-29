package com.wegrzyn.marcin.fuelbills;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 25.07.2018.
 * wireamg@gmail.com
 */
public class CarsViewModel extends AndroidViewModel {

    private static final String TAG =CarsViewModel.class.getSimpleName() ;
    private CarRepository carRepository;

    private Car tempCar;
    private LiveData<List<Car>> allCars;
    private LiveData<List<Refueling>> allRefuellings;


    public CarsViewModel(@NonNull Application application) {
        super(application);
        carRepository = new CarRepository(application);
        allCars = carRepository.getAllCars();
        allRefuellings = carRepository.getAllRefuelings();
    }

    LiveData<Car> getCar(int id){return carRepository.getCar(id);}
    LiveData<List<Car>> getAllCars(){return allCars;}
    LiveData<List<Refueling>> getAllRefillings(){return allRefuellings;};

    public void insertCar(Car car) {
        carRepository.insertCar(car);
    }

    public void deleteCar(Car car){
        carRepository.deleteCar(car);
    }

    public void updateCar(Car car) {carRepository.updateCar(car);}
}
