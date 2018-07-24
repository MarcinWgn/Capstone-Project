package com.wegrzyn.marcin.fuelbills;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Marcin Węgrzyn on 25.07.2018.
 * wireamg@gmail.com
 */
@Database(entities = {Car.class, Refueling.class}, version = 1,exportSchema = false)
public abstract class CarRoomDatabase extends RoomDatabase {

public abstract CarDao carDao();
private static  CarRoomDatabase INSTANCE;

    static CarRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CarRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CarRoomDatabase.class, "car_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
