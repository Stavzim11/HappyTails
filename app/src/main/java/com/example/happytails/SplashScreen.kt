package com.example.happytails

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class SplashScreen : AppCompatActivity() {

    //Splash Screen duration
    private val splashScreen: Long = 5000

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.splash_screen)


        //Animations
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)


        //Finding ImageView and TextView
        val imageView = findViewById<View>(R.id.splashscreen_imageView)
        val textView = findViewById<View>(R.id.splashscreen_textView)


        //Initiate anim on text and image views

        imageView.startAnimation(topAnimation)
        textView.startAnimation(bottomAnimation)

        //Handler to start the MainActivity page after a certian delay

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //Close the SplashScreen
            finish()
        }, splashScreen)


    }
}