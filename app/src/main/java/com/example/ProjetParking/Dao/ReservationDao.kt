package com.example.ProjetParking.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ProjetParking.Models.ParkingModel
import com.example.ProjetParking.Models.ReservationModel


@Dao
interface ReservationDao {

    @Insert
    fun addReservation(reservation: ReservationModel)

    @Query("select * from reservation natural join users where users.iduser=:iduser")
    fun getReservationsByUser(iduser: Int):List<ReservationModel>

    @Query("select * from reservation natural join place natural join parking where reservation.idplace = place.idplace AND place.idparking= parking.idparking AND numeroreservation=:idres")
    fun getParkingByReservation(idres: Int):List<ParkingModel>


    @Query("select * from reservation")
    fun getReservations():List<ReservationModel>




}