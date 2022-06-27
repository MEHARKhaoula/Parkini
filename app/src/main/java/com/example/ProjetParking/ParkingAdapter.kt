package com.example.ProjetParking

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class MyAdapter(val context: Context,
                var data:List<ParkingModel>,

):RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.parking_layout, parent, false))

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            etat.text = data[position].etat
            titre.text = data[position].nom
            location.text = data[position].commune
            kilom.text = data[position].distance.toString()+" km"
            time.text = data[position].tempsestime.toString()+" min"
            
          Glide.with(context).load( "https://images.unsplash.com/photo-1590674899484-d5640e854abe?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8cGFya2luZyUyMGxvdHxlbnwwfHwwfHw%3D&w=1000&q=80").into(holder.photo)
            parkingCard.setOnClickListener {

            }
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (view != null) {
                        val bundle = bundleOf(
                            "position" to position,
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
        val ratingBar=view.findViewById<RatingBar>(R.id.rating) as RatingBar

    }

}



