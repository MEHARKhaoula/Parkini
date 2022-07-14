package com.example.ProjetParking.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.ProjetParking.Models.ReservationModel
import com.example.ProjetParking.R


class ReservationAdapter(val context: Context,
                         var data:List<ReservationModel>,
                ):RecyclerView.Adapter<ReservationAdapter.MyViewHolder1>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1
    {
        return MyViewHolder1(LayoutInflater.from(context).inflate(R.layout.reservation_layout, parent, false))

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder1, @SuppressLint("RecyclerView") position: Int){
        holder.apply {
            titre.text = "Reservation"+data[position].numeroreservation.toString()
            date.text = data[position].date.toString()
            heuredebut.text = data[position].heure_entree
            heurefin.text = data[position].heure_sortie

            ReservationCard.setOnClickListener {

            }
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (view != null) {
                        val bundle = bundleOf(
                            "position" to position,
                        )
                      /*  view.findNavController()
                            .navigate(R.id.action_reservationFragment_to_reservationListDetailFragment, bundle)*/

                    }
                }
            })

        }


    }

    class MyViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val heuredebut = view.findViewById (R.id.heuredebut) as TextView
        val heurefin = view.findViewById(R.id.heurefin) as TextView
        val date = view.findViewById(R.id.textViewdate) as TextView
        val titre = view.findViewById(R.id.textViewTitre) as TextView
        val ReservationCard = view.findViewById(R.id.reservationCard) as ConstraintLayout


    }



}



