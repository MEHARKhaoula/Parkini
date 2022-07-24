package com.example.ProjetParking
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.lifecycle.ViewModelProvider
import com.example.ProjetParking.Models.PlaceModel
import com.example.ProjetParking.Models.ReservationModel
import com.example.ProjetParking.ViewModels.PlaceViewModel
import com.example.ProjetParking.ViewModels.ReservationViewModel
import kotlinx.coroutines.*

class ReservatindetailsFragment : Fragment() {


    lateinit var codeQR : ImageView
    var reservationAdded = mutableListOf<ReservationModel>()
    var reservationPlace = mutableListOf<PlaceModel>()
    lateinit var parkingPlace: TextView
    lateinit var numeroreservation: TextView
    lateinit var reservationViewModel: ReservationViewModel
    lateinit var placeViewModel: PlaceViewModel







    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_detail, container, false)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationViewModel = ViewModelProvider(requireActivity()).get(ReservationViewModel::class.java)
        placeViewModel = ViewModelProvider(requireActivity()).get(PlaceViewModel::class.java)
        codeQR = view.findViewById(R.id.CodeQR) as ImageView
        parkingPlace = view.findViewById(R.id.idplace) as TextView
        numeroreservation = view.findViewById(R.id.numeroreservation) as TextView




        parkingPlace.text = placeViewModel.data.get(0).numero_place.toString()
        numeroreservation.text = "Réservation numéro "+ reservationViewModel.data.get(0).numeroreservation.toString()


        val encoder = QRGEncoder(reservationViewModel.data.get(0).numeroreservation.toString(), null , QRGContents.Type.TEXT, 800  )


        codeQR.setImageBitmap(encoder.bitmap)



    }



    fun getPlaceById(){

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }




        CoroutineScope(Dispatchers.IO+exceptionHandler).launch {

            val response = Endpoint.createEndpoint().getPlaceById(reservationViewModel.data.get(0).idplace)

            withContext(Dispatchers.Main) {

                if (response.isSuccessful && response.body() != null)  {

                    reservationPlace = response.body()!!.toMutableList()

                } else
                {

                    Toast.makeText(requireActivity(),"Erreur!", Toast.LENGTH_LONG).show()


                }
            }

        }

    }

    fun getReservationAdded(){

        val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            requireActivity().runOnUiThread {

                Toast.makeText(requireActivity(), "Une erreur s'est produite", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = Endpoint.createEndpoint().getAddedReservation()

            withContext(Dispatchers.Main) {

                if (response.isSuccessful && response.body() != null) {

                    reservationAdded = response.body()!!.toMutableList()

                } else {

                    Toast.makeText(requireActivity(), "Erreur!", Toast.LENGTH_LONG).show()


                }
            }
        }


    }




}