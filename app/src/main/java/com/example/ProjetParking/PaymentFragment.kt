package com.example.ProjetParking

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle

import android.os.Looper
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.stream.*;
class PaymentFragment : Fragment() {


    lateinit var date: TextView
    lateinit var heuredebut: TextView
    lateinit var heurefin: TextView
    lateinit var payer: Button
    lateinit var navController: NavController
    lateinit var reserver: Button
    lateinit var parkingViewModel :ParkingViewModel
    lateinit var heuredeb :String
    lateinit var heuref : String
    lateinit var userViewModel: UserViewModel
    lateinit var sdf: SimpleDateFormat
    lateinit var  myCalendar:Calendar
    var  position: Int = 0
    var placeVide = mutableListOf<PlaceModel>()
    lateinit var reservationViewModel:ReservationViewModel
    lateinit var placeViewModel:PlaceViewModel
    var reservationAdded = mutableListOf<ReservationModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        payer = view.findViewById(R.id.payer) as Button
        heuredebut = view.findViewById(R.id.heuredebut) as TextView
        heurefin = view.findViewById(R.id.heurefin) as TextView
        date = view.findViewById(R.id.dateres) as TextView
        navController = Navigation.findNavController(view)
        reservationViewModel = ViewModelProvider(requireActivity()).get(ReservationViewModel::class.java)
        placeViewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)
        getPlaceVide()
        position = arguments?.getInt("id")!!

        date.setOnClickListener {

            myCalendar = Calendar.getInstance()

            val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updatelabele( myCalendar)

                date.text = sdf.format(myCalendar.time).toString()



            }

            DatePickerDialog(requireContext(),
                R.style.MyDatePickerDialogTheme, datePicker ,myCalendar.get(Calendar.YEAR) , myCalendar.get(
                    Calendar.YEAR)
                , myCalendar.get(Calendar.DAY_OF_MONTH)).show()

        }

        heuredebut.setOnClickListener {

            openTimePicker1()

        }

        heurefin.setOnClickListener {

            openTimePicker2()

        }

        payer.setOnClickListener{


            CoroutineScope(Dispatchers.IO ).launch {

                val response = Endpoint.createEndpoint().getAddedReservation()

                withContext(Dispatchers.Main) {

                    if (response.isSuccessful && response.body() != null) {

                        setReservation(ReservationModel(7, sdf.format(myCalendar.time) ,heuredeb ,heuref , 3 , placeVide.get(0).idplace))

                        reservationViewModel.data = response.body()!!.toMutableList()

                        placeViewModel.data.add(placeVide.get(0))
                        navController.navigate(R.id.action_paymentFragment_to_reservatindetailsFragment)



                    } else {

                        Toast.makeText(requireActivity(), "Erreur!", Toast.LENGTH_LONG).show()


                    }
                }
            }



        }



    }











    fun updatelabele(mycalendar:Calendar){
        val myformat ="dd-MM-YYYY"
        sdf = SimpleDateFormat(myformat,Locale.FRANCE)

        print(sdf.format(mycalendar.time))


    }

    fun openTimePicker1(){

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat= if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker1= MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Heure Debut de réservation")
            .build()

        picker1.show(childFragmentManager, "TAG")

        picker1.addOnPositiveButtonClickListener {

            heuredeb =picker1.hour.toString() +":"+picker1.minute.toString()
            heuredebut.text = picker1.hour.toString() +":"+picker1.minute.toString()




        }

    }

    fun openTimePicker2(){

        val isSystem24Hour = DateFormat.is24HourFormat(requireContext())
        val clockFormat= if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker1= MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Heure fin de réservation" )
            .build()

        picker1.show(childFragmentManager, "TAG")

        picker1.addOnPositiveButtonClickListener {

            heuref = picker1.hour.toString()+":"+picker1.minute.toString()
            heurefin.text = picker1.hour.toString()+":"+picker1.minute.toString()








        }


    }




    fun getPlaceVide(){

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
                throwable.printStackTrace()
            }
        }

        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().getPlaceVide( position)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful && response.body() != null)  {

                    placeVide = response.body()!!.toMutableList()

                } else
                {

                    Toast.makeText(requireActivity(),"Erreur!",Toast.LENGTH_LONG).show()


                }
            }
        }
    }


    fun setReservation(res: ReservationModel) {
        val exceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable ->
            println("222    "+throwable.localizedMessage)

        }



        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val response = Endpoint.createEndpoint().setReservation(res)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful )  {

                } else
                {
                    Toast.makeText(requireActivity(),"Erreur!",Toast.LENGTH_LONG).show()

                }
            }
        }
    }






}