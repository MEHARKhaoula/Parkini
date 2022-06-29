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

    override fun onMapReady(gmap: GoogleMap) {

        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        googleMap = gmap

        for(item in  parkingViewModel.data )
        {

            googleMap!!.addMarker(MarkerOptions().position(LatLng(item.latitude,item.longitude)).title(item.nom))
            googleMap!!.animateCamera(CameraUpdateFactory.zoomTo( 10.0f))
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.latitude,item.longitude) ,
                10.0F
            ))
        }





    }

    init {
        getMapAsync(this)
    }
}