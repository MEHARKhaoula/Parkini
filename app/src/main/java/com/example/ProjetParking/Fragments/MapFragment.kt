package com.example.ProjetParking



import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*


class MyMapFragment : SupportMapFragment(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    lateinit var parkingViewModel: ParkingViewModel
    lateinit var location: Location
  lateinit var fusedLocationProviderClient: FusedLocationProviderClient



   /* private fun fetchLastLocation() {

           if (ActivityCompat.checkSelfPermission(
               requireActivity(),
               Manifest.permission.ACCESS_FINE_LOCATION
           ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                   requireActivity(),
               Manifest.permission.ACCESS_COARSE_LOCATION
           ) != PackageManager.PERMISSION_GRANTED
       ) {
               ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                   101)
       }
       val locationResult:Task<Location> = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener(requireActivity()) { task ->
            if (task.result != null) {
                // Set the map's camera position to the current location of the device.
                location = task.result
                if (location != null) {
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        LatLng(location!!.latitude,
                            location!!.longitude), 10.0F))
                }
            } else {
                Log.d(2000.toString(), "Current location is null. Using defaults.")
              //  Log.e(2000.toString(), "Exception: %s", task.exception)
             /*   googleMap?.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(defaultLocation, 10.0F))*/
                googleMap?.uiSettings?.isMyLocationButtonEnabled = false
            }



        } } */



    override fun onMapReady(gmap: GoogleMap) {
        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        getParkings()
   fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        googleMap = gmap
      // fetchLastLocation()

        googleMap!!.addMarker(MarkerOptions().position(LatLng(36.73986,3.10583)).title("I am here").icon(
            this.activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_baseline_person_pin_circle_24) }))
        googleMap!!.animateCamera(CameraUpdateFactory.zoomTo( 10.0f))
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(36.73986,3.10583) ,
            10.0F
        ))

        for(item in  parkingViewModel.data )
        {

            googleMap!!.addMarker(MarkerOptions().position(LatLng(item.latitude,item.longitude)).title(item.nom).icon(
                this.activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_parking_location_svgrepo_com) }))
         //   googleMap!!.animateCamera(CameraUpdateFactory.zoomTo( 10.0f))
         /*   googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.latitude,item.longitude) ,
                10.0F
            ))*/
        }





    }

    init {
        getMapAsync(this)
    }


    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }


    fun getParkings(){
        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {


            }
        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getParkings()
            withContext(Dispatchers.Main) {


                if (response.isSuccessful && response.body() != null)  {

                    parkingViewModel.data = response.body()!!.toMutableList()
                    for(item in  parkingViewModel.data )
                    {

                        googleMap!!.addMarker(MarkerOptions().position(LatLng(item.latitude,item.longitude)).title(item.nom).icon(
                            requireActivity()?.let { bitmapDescriptorFromVector(it, R.drawable.ic_parking_location_svgrepo_com) }))
                        //   googleMap!!.animateCamera(CameraUpdateFactory.zoomTo( 10.0f))
                        /*   googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.latitude,item.longitude) ,
                               10.0F
                           ))*/
                    }



                } else
                {



                }
            }
        }
    }





}

