package com.example.ProjetParking

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ProjetParking.Dao.ParkingDao
import com.example.ProjetParking.Dao.PlaceDao
import com.example.ProjetParking.Dao.ReservationDao
import com.example.ProjetParking.Dao.UserDao
import com.example.ProjetParking.Models.ParkingModel
import com.example.ProjetParking.Models.PlaceModel
import com.example.ProjetParking.Models.ReservationModel
import com.example.ProjetParking.Models.UserModel


@Database(entities = [UserModel::class, ReservationModel::class, PlaceModel::class, ParkingModel::class],version = 2)
@TypeConverters(Converter::class)

abstract class AppDatabase: RoomDatabase() {
    abstract fun getUserDo(): UserDao
    abstract fun getReservationDo(): ReservationDao
    abstract  fun getParkingDo(): ParkingDao
    abstract  fun getPlacegDo(): PlaceDao

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