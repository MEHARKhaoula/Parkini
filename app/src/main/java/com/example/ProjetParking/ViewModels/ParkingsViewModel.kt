package com.example.ProjetParking

import androidx.lifecycle.ViewModel
import com.example.ProjetParking.Models.ParkingModel
import kotlinx.coroutines.*

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
            if (  currDistance < 5) {
                minDistance = currDistance
                nearestParking = item
                searchData.add(nearestParking)
            }
        }
     /*   if (nearestParking != null) {
            searchData.add(nearestParking)
        }*/

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
        dist=dist*1.609344

        return dist;
    }




}


