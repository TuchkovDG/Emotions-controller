package com.emotions.controller

import androidx.multidex.MultiDexApplication
import com.emotions.controller.data.di.dataModules
import com.emotions.controller.domain.di.domainModules
import com.emotions.controller.presentation.di.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class EmotionsControllerApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@EmotionsControllerApplication)
            modules(dataModules + domainModules + presentationModules)
        }
    }
}