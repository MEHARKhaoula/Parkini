package com.example.ProjetParking

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters



@Database(entities = [UserModel::class, ReservationModel::class,PlaceModel::class,ParkingModel::class],version = 2)
@TypeConverters(Converter::class)

abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDo():UserDao
    abstract fun getReservationDo():ReservationDao
    abstract  fun getParkingDo():ParkingDao
    abstract  fun getPlacegDo():PlaceDao

        companion object {
            @Volatile
            private var INSTANCE: AppDatabase? = null
            fun buildDatabase(context: Context): AppDatabase? {
                if (INSTANCE == null) { synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,AppDatabase::class.java, "db_name")
                        .allowMainThreadQueries().build() } }
                return INSTANCE }
        }

}