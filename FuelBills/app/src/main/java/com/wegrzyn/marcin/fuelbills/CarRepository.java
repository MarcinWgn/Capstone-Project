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
    LiveData<Car> getCar(int id){ return carDao.getCar(id);}
    LiveData<List<Refueling>> getRefuelingById(int carId){return carDao.getCarRefueling(carId);}
    LiveData<Refueling> getRefueling(int id){return  carDao.getRefueling(id);}
    LiveData<Refueling> getMaxDist(){return  carDao.getMaxDistance();}

    public void insertCar(Car car){
        new insertCarTask(carDao).execute(car);
    }

    public void deleteCar(Car car){
        new deleteCarTask(carDao).execute(car);
    }

    public void updateCar(Car car) {new updateCarTask(carDao).execute(car);}

    public void insertRefueling(Refueling refueling){new insertRefuelingTask(carDao).execute(refueling);}

    public void deleteRefueling(Refueling refueling){new deleteRefuelingTask(carDao).execute(refueling);}

    public void updateRefueling(Refueling refueling){new updateRefuelingTask(carDao).execute(refueling);}


    private static class insertCarTask extends AsyncTask <Car, Void, Void>{

        private CarDao asyncTaskDao;

        insertCarTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Car... cars) {

            asyncTaskDao.insertCar(cars[0]);
            return null;
        }
    }

    private static class insertRefuelingTask extends AsyncTask <Refueling, Void, Void>{

        private CarDao asyncTaskDao;

        insertRefuelingTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Refueling... refuelings) {
            asyncTaskDao.insertRefueling(refuelings[0]);
            return null;
        }
    }

    private static class deleteCarTask extends AsyncTask <Car, Void, Void>{

        private CarDao asyncTaskDao;

        deleteCarTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Car... cars) {
            asyncTaskDao.deleteCars(cars);
            return null;
        }
    }

    private static class updateCarTask extends AsyncTask <Car, Void, Void>{

        private CarDao asyncTaskDao;

        updateCarTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Car... cars) {
            asyncTaskDao.updateCar(cars);
            return null;
        }
    }

    private static class deleteRefuelingTask extends AsyncTask <Refueling, Void, Void>{

        private CarDao asyncTaskDao;

        deleteRefuelingTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Refueling... refueling) {
            asyncTaskDao.deleteRefueling(refueling);
            return null;
        }
    }

    private static class updateRefuelingTask extends AsyncTask <Refueling, Void, Void>{

        private CarDao asyncTaskDao;

        updateRefuelingTask(CarDao carDao){asyncTaskDao = carDao; }

        @Override
        protected Void doInBackground(Refueling... refueling) {
            asyncTaskDao.updateRefueling(refueling);
            return null;
        }
    }
}
