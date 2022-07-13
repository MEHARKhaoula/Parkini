package com.example.ProjetParking

import androidx.room.*


@Dao
interface ParkingDao {

    @Insert
    fun addParkings(vararg parkings: ParkingModel)

    @Query("select * from parking")
    fun getParkings():List<ParkingModel>
}