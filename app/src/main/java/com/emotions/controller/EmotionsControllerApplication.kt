package com.emotions.controller

import android.content.res.Configuration
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.emotions.controller.data.di.dataModules
import com.emotions.controller.domain.di.domainModules
import com.emotions.controller.presentation.di.presentationModules
import com.emotions.controller.presentation.internal.AppPreferences
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

class EmotionsControllerApplication : MultiDexApplication() {

    private val preferences: AppPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@EmotionsControllerApplication)
            modules(dataModules + domainModules + presentationModules)
        }

        AppCompatDelegate.setDefaultNightMode(preferences.modeNight)

        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        config.setLocale(Locale(preferences.language))
        resources.updateConfiguration(config, dm)
    }
}