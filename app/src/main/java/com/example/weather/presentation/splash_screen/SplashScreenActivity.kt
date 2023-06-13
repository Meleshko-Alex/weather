package com.example.weather.presentation.splash_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.common.Constants

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setUpActionBar()
        setUpStatusBar()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, Constants.SPLASH_SCREEN_DISPLAYING_TIME)
    }

    private fun setUpActionBar() {
        supportActionBar?.hide()
    }

    private fun setUpStatusBar() {
        window.apply {
            // change background color
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(context, R.color.blue)

            // change  text color
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false

            // change navigation bar background color
            navigationBarColor = ContextCompat.getColor(context, R.color.blue)
        }
    }
}