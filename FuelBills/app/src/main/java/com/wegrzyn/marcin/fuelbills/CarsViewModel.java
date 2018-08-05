package com.wegrzyn.marcin.fuelbills;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.sql.Ref;
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
    private LiveData<List<Refueling>> allRefueling;


    public CarsViewModel(@NonNull Application application) {
        super(application);
        carRepository = new CarRepository(application);
        allCars = carRepository.getAllCars();
        allRefueling = carRepository.getAllRefuelings();
    }

    LiveData<Car> getCar(int id){return carRepository.getCar(id);}
    LiveData<List<Car>> getAllCars(){return allCars;}
    LiveData<List<Refueling>> getAllRefillings(){return allRefueling;};
    LiveData<List<Refueling>> getRefuelingById(int carId){return carRepository.getRefuelingById(carId);}
    LiveData<Refueling> getRefueling(int id){return carRepository.getRefueling(id);}
    LiveData<Refueling> getMaxDist(int id){return carRepository.getMaxDist(id);}

    public void insertCar(Car car) {
        carRepository.insertCar(car);
    }

    public void deleteCar(Car car){
        carRepository.deleteCar(car);
    }

    public void updateCar(Car car) {carRepository.updateCar(car);}

    public void insertRefueling(Refueling refueling){carRepository.insertRefueling(refueling);}

    public void deleteRefueling(Refueling refueling) {
        carRepository.deleteRefueling(refueling);
    }

    public void updateRefueling(Refueling refueling) {
        carRepository.updateRefueling(refueling);
    }
}
