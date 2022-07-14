package com.example.ProjetParking.Fragments



import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ProjetParking.*


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.example.ProjetParking.R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)




        val displayMetrics = DisplayMetrics()
        this.activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels


       // set the params for relativelayout
        val layout: RelativeLayout = view.findViewById(R.id.relativelayout)
        layout.getLayoutParams().height = ((140/375.0)*width).toInt()
        layout.getLayoutParams().width = ((140/375.0)*width).toInt()


        // set the params for linearlayout01
        val layout01: LinearLayout = view.findViewById(R.id.linearlayout01)
        layout01.getLayoutParams().height = ((140/375.0)*width).toInt()
        layout01.getLayoutParams().width = ((120/375.0)*width).toInt()


        // set the params for relativelayout
        val layout2: LinearLayout = view.findViewById(R.id.linearlayout0)
        layout2.getLayoutParams().height = ((140/375.0)*width).toInt()



        //set the params for the imageview
        val imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.getLayoutParams().width = ((140/375.0)*width).toInt()
        imageView.getLayoutParams().height = ((140/375.0)*width).toInt()

        //set the params for the linearlayout1
        val linearlayout: LinearLayout = view.findViewById(R.id.linearlayout1)
        linearlayout.getLayoutParams().height = ((40 / 812.0) * height).toInt()


        //set the params for the user name
        val userName :TextView = view.findViewById(R.id.username)
        val params = userName.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(((100/375.0)*width).toInt(),((20/375.0)*width).toInt(),0,((20/375.0)*width).toInt(),)
        userName.layoutParams = params







    }

}


