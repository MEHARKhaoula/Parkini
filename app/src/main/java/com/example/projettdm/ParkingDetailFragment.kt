package com.example.projettdm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ParkingDetailFragment : Fragment() {

    lateinit var parkingViewModel:ParkingViewModel
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
        val position= arguments?.getInt("position")
        parkingViewModel = ViewModelProvider(requireActivity()).get(ParkingViewModel::class.java)
        val parking= position?.let { parkingViewModel.data.get(it) }
        if (parking != null) {
            view.findViewById<TextView>(R.id.textViewTitre).text = parking.nom
        }

    }
}