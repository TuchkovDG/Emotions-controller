package com.emotions.controller.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emotions.controller.presentation.ui.home.HomeActivity
import org.jetbrains.anko.intentFor

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(intentFor<HomeActivity>())
        finish()
    }
}