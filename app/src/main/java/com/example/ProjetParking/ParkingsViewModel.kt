package com.example.ProjetParking

import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.*
import java.io.IOException

class ParkingViewModel: ViewModel() {
    var data = mutableListOf<ParkingModel>()
    var searchData = mutableListOf<ParkingModel>()


    fun getParkings(){
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println(throwable.localizedMessage)

        }
        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getParkings()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body() != null)  {
                    data = response.body() as MutableList<ParkingModel>
                    print(data)

                } else
                {



                }
            }
        }
    }





    fun getSearchParking(targetLat:Double, targetLng:Double): MutableList<ParkingModel>
    {
        var minDistance =-1.0
        var nearestParking: ParkingModel?= null
        searchData=mutableListOf<ParkingModel>()


        for (item in data) {

            val currParkingLat =item!!.latitude
            val currParkingLng = item!!.longitude
            val currDistance = calcDistance(targetLat, targetLng, currParkingLat, currParkingLng)
            if ( minDistance == -1.0 || currDistance < minDistance) {
                minDistance = currDistance
                nearestParking = item
            }
        }
        if (nearestParking != null) {
            searchData.add(nearestParking)
        }
        data = mutableListOf<ParkingModel>()
        return searchData

    }




    fun  calcDistance(lat1:Double, lng1:Double, lat2:Double, lng2:Double): Double {
        var radlat1 = Math.PI * lat1/180
        var radlat2 = Math.PI * lat2/180
        var theta = lng1-lng2
        var radtheta = Math.PI * theta/180
        var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);

        dist = Math.acos(dist)
        dist = dist * 180/Math.PI
        dist = dist * 60 * 1.1515

        return dist;
    }




}


