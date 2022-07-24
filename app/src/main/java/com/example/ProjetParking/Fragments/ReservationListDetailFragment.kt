package com.example.ProjetParking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.ProjetParking.Models.PlaceModel
import com.example.ProjetParking.Models.ReservationModel
import com.example.ProjetParking.ViewModels.PlaceViewModel
import com.example.ProjetParking.ViewModels.ReservationListViewModel
import com.example.ProjetParking.ViewModels.UserViewModel
import java.text.SimpleDateFormat


class ReservationListDetailFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var parkingViewModel :ParkingViewModel
    lateinit var heuredebut :String
    lateinit var heurefin : String
    lateinit var userViewModel: UserViewModel
    lateinit var sdf:SimpleDateFormat

    var  position: Int = 0
    var placeVide = mutableListOf<PlaceModel>()
    lateinit var reservationListViewModel: ReservationListViewModel
    lateinit var placeViewModel: PlaceViewModel
    var reservationAdded = mutableListOf<ReservationModel>()






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_list_detail , container, false)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        //reserver = view.findViewById(R.id.payer) as Button
        navController = Navigation.findNavController(view)
        reservationListViewModel = ViewModelProvider(requireActivity()).get(ReservationListViewModel::class.java)
        placeViewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)


        position= arguments?.getInt("position")!!





        val reservation= position?.let { reservationListViewModel.data.get(it) }
        if (reservation != null) {

            view.findViewById<TextView>(R.id.numeroreservation).text ="Reservation numero "+reservation.numeroreservation.toString()
            view.findViewById<TextView>(R.id.dateres).text = reservation.date
            view.findViewById<TextView>(R.id.detail).text = reservation.heure_entree
            view.findViewById<TextView>(R.id.heurefin).text = reservation.heure_sortie
           val codeQR = view.findViewById(R.id.CodeQR) as ImageView

            val encoder = QRGEncoder(reservation.numeroreservation.toString(), null , QRGContents.Type.TEXT, 800  )


            codeQR.setImageBitmap(encoder.bitmap)
        }




    }





}