package com.example.ProjetParking



import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



class MyMapFragment : SupportMapFragment(), OnMapReadyCallback {
    private var googleMap: GoogleMap? = null
    lateinit var parkingViewModel: ParkingViewModel
     var  arrayList = ArrayList<LatLng>()
    override fun onMapReady(gmap: GoogleMap) {

        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        googleMap = gmap

        for(item in  parkingViewModel.data )
        {

            googleMap!!.addMarker(MarkerOptions().position(LatLng(item.latitude,item.longitude)).title("Mark" +
                    "er in Algeria"))
            googleMap!!.animateCamera(CameraUpdateFactory.zoomTo( 10.0f))
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.latitude,item.longitude) ,
                18.0F
            ))
        }




        /*googleMap!!.addMarker(MarkerOptions().position(parkingJardindessai).title("Mark" +
                "er in Algeria"))
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(parkingJardindessai))
        googleMap!!.setOnMapClickListener { latLng ->
            val markerOptions = MarkerOptions()36
            markerOptions.position(latLng)
            markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
            // Clear previously click position.
            googleMap!!.clear()
            // Zoom the Marker
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
            // Add Marker on Map
            googleMap!!.addMarker(markerOptions)
        }*/
    }

    init {
        getMapAsync(this)
    }
}