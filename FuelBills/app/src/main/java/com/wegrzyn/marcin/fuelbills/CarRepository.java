package com.wegrzyn.marcin.fuelbills;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 25.07.2018.
 * wireamg@gmail.com
 */
public class CarRepository {

    private CarDao carDao;
    private LiveData<List<Car>> allCars;
    private LiveData<List<Refueling>> allRefuelings;


    CarRepository(Application application){

        CarRoomDatabase database = CarRoomDatabase.getDatabase(application);
        carDao = database.carDao();
        allCars = carDao.getAllCar();
        allRefuelings = carDao.getAllRefueling();
    }

    LiveData<List<Car>> getAllCars(){
        return allCars;
    }
    LiveData<List<Refueling>> getAllRefuelings(){
        return allRefuelings;
    }

    public void insertCar(Car car){
        new insertCarTask(carDao).execute(car);
    }

    public void deleteCar(Car car){
        new deleteCarTask(carDao).execute(car);
    }

    private static class insertCarTask extends AsyncTask <Car, Void, Void>{

        private CarDao asyncTaskDao;

        insertCarTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Car... cars) {

            asyncTaskDao.insertCar(cars[0]);
            asyncTaskDao.insertRefueling(new Refueling("opis tankowania"));
            return null;
        }
    }

    private static class deleteCarTask extends AsyncTask <Car, Void, Void>{

        private CarDao asyncTaskDao;

        deleteCarTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Car... cars) {

            asyncTaskDao.deleteCars(cars);
//            asyncTaskDao.insertCar(cars[0]);
            return null;
        }
    }


}
