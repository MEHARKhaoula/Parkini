package com.example.ProjetParking.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.ProjetParking.Models.PlaceModel
import com.example.ProjetParking.Models.ReservationModel
import com.example.ProjetParking.ParkingViewModel
import com.example.ProjetParking.R
import com.example.ProjetParking.ViewModels.PlaceViewModel
import com.example.ProjetParking.ViewModels.ReservationViewModel
import com.example.ProjetParking.ViewModels.UserViewModel
import java.text.SimpleDateFormat
import java.util.*


class ParkingDetailFragment : Fragment() {
    lateinit var reserver: Button
    lateinit var navController: NavController
    lateinit var parkingViewModel : ParkingViewModel
    lateinit var heuredebut :String
    lateinit var heurefin : String
    lateinit var userViewModel: UserViewModel
    lateinit var sdf:SimpleDateFormat
    lateinit var  myCalendar:Calendar
    var  position: Int = 0
    var placeVide = mutableListOf<PlaceModel>()
    lateinit var reservationViewModel: ReservationViewModel
    lateinit var placeViewModel: PlaceViewModel
    var reservationAdded = mutableListOf<ReservationModel>()
    lateinit  var positionbutton :ImageView
    lateinit  var Img :ImageView

    var latitude:Double= 0.0
    var longitude:Double=0.0





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_parking_detail, container, false)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        reserver = view.findViewById(R.id.payer) as Button
        navController = Navigation.findNavController(view)
        reservationViewModel =
            ViewModelProvider(requireActivity()).get(ReservationViewModel::class.java)
        placeViewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)
        positionbutton = view.findViewById(R.id.position) as ImageView
        Img = view.findViewById(R.id.idimage) as ImageView


        position = arguments?.getInt("position")!!




        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        val parking = position?.let { parkingViewModel.data.get(it) }
        if (parking != null) {
            view.findViewById<TextView>(R.id.textViewTitre).text = parking.nom
            view.findViewById<TextView>(R.id.textViewKilom).text =
                parking.distance.toString() + "Km"
            view.findViewById<TextView>(R.id.textViewEtat).text = parking.etat
            view.findViewById<TextView>(R.id.TextViewTaux).text =
                ((parking.nbrplaceslibres.toDouble() / parking.nbrplaces.toDouble())*100).toInt().toString() + "%"
            view.findViewById<TextView>(R.id.textViewTime).text = parking.tempsestime.toString()
            view.findViewById<TextView>(R.id.textViewdate).text = parking.commune
            view.findViewById<TextView>(R.id.numeroreservation).text = "Dimanche"
            view.findViewById<TextView>(R.id.TextViewHeure).text =
                parking.heuredebut.format("HH:MM") + " " + "a" + " " + parking.heurefin.format("HH:MM")
            view.findViewById<TextView>(R.id.TextViewPrix).text = parking.tarif.toString() + "DA"
            latitude = parking.latitude
            longitude = parking.longitude

            Glide.with(requireContext()).load( "https://images.unsplash.com/photo-1590674899484-d5640e854abe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cGFya2luZyUyMGxvdHxlbnwwfHwwfHw%3D&w=1000&q=80").into(Img)






            System.out.print(placeVide.toString())
            //Toast.makeText(requireActivity(),placeVide.toString(),Toast.LENGTH_LONG).show()


        }



        positionbutton.setOnClickListener {

            val lati = latitude
            val longi = longitude
            val geoLocation =
                Uri.parse("http://maps.google.com/maps?daddr=" + lati + "," + longi)
            val intent = Intent(Intent.ACTION_VIEW, geoLocation)
            requireActivity().startActivity(intent)


        }

        reserver.setOnClickListener {


            val pref = this.getActivity()?.getSharedPreferences("data", Context.MODE_PRIVATE)
            val userConnected = pref?.getBoolean("Connected", false)
            if (userConnected == true) {
                val bundle = bundleOf(
                    "id" to parkingViewModel.data.get(position).idparking,
                    "nom" to parkingViewModel.data.get(position).nom,
                    "tarif" to parkingViewModel.data.get(position).tarif
                )
                navController.navigate(
                    R.id.action_parkingDetailFragment_to_paymentFragment,
                    bundle
                )


            } else {

                Toast.makeText(
                    requireActivity(),
                    "You should login to accomplish this action",
                    Toast.LENGTH_LONG
                ).show()

                navController.navigate(
                    R.id.action_parkingDetailFragment_to_loginFragment

                )


            }


        }

    }





}













