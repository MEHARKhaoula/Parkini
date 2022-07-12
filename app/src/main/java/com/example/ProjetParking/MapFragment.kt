package com.example.ProjetParking



import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
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

            googleMap!!.addMarker(MarkerOptions().position(LatLng(item.latitude,item.longitude)).title(item.nom).icon(
                this.activity?.let { bitmapDescriptorFromVector(it, R.drawable.ic_parking_location_svgrepo_com) }))
            googleMap!!.animateCamera(CameraUpdateFactory.zoomTo( 10.0f))
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(item.latitude,item.longitude) ,
                10.0F
            ))
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
}