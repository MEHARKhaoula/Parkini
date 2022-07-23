package com.example.ProjetParking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {
    lateinit var imageview : ImageView
    lateinit var parkingViewModel: ParkingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageview = findViewById(R.id.iv_note)
        imageview.alpha=0f
        imageview.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }


}