package com.example.ProjetParking

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ProjetParking.Models.ParkingModel
import java.text.DecimalFormat
import java.text.NumberFormat


class MyAdapter(val context: Context,
                var data:List<ParkingModel>, val search:Boolean

):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.parking_layout, parent, false))

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            etat.text = data[position].etat
            titre.text = data[position].nom
            location.text = data[position].commune
            kilom.text = data[position].distance.toString()+" km"
            time.text = data[position].tempsestime.toString()+" min"

            val REAL_FORMATTER = DecimalFormat("0.###")
            val num1 =( data[position].nbrplaceslibres).toDouble()
            val num2 =( data[position].nbrplaces).toDouble()
            val result: Double = (num1/num2)*100
            val nm: NumberFormat = NumberFormat.getNumberInstance()
            taux.text =REAL_FORMATTER.format(result)+" %";

            
          Glide.with(context).load( data[position].photo).into(holder.photo)
            parkingCard.setOnClickListener {

            }
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (view != null) {
                        val bundle = bundleOf(
                            "position" to position,
                            "search" to search,
                        )
                        view.findNavController()
                            .navigate(R.id.action_parkingListFragment_to_parkingDetailFragment, bundle)

                    }
                }
            })

        }




    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val etat = view.findViewById (R.id.textViewEtat) as TextView
        val titre = view.findViewById(R.id.textViewTitre) as TextView
        val location = view.findViewById(R.id.textViewlocation) as TextView
        val kilom = view.findViewById(R.id.textViewKilom) as TextView
        val time= view.findViewById(R.id.textViewTime) as TextView
        val parkingCard = view.findViewById(R.id.parkingCard) as ConstraintLayout
        val photo =view.findViewById<View>(R.id.imageView) as ImageView
        val taux= view.findViewById(R.id.textViewTaux) as TextView

    }

}



