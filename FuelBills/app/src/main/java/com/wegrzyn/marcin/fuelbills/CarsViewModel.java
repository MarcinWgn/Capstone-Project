package com.wegrzyn.marcin.fuelbills;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 25.07.2018.
 * wireamg@gmail.com
 */
public class CarsViewModel extends AndroidViewModel {

    private CarRepository carRepository;

    private LiveData<List<Car>> allCars;
    private LiveData<List<Refueling>> allRefulings;

    public CarsViewModel(@NonNull Application application) {
        super(application);
        carRepository = new CarRepository(application);
        allCars = carRepository.getAllCars();
        allRefulings = carRepository.getAllRefuelings();
    }

    LiveData<List<Car>> getAllCars(){return allCars;};
    LiveData<List<Refueling>> getAllRefillings(){return allRefulings;};

    public void insertCar(Car car) {
        carRepository.insertCar(car);
    }

    public void deleteCar(Car car){
        carRepository.deleteCar(car);
    }
}
