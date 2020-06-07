package com.emotions.controller.presentation.internal

import android.content.Context
import java.util.*

object LocalizationUtil {

    @SuppressWarnings("Deprecated in Android 17")
    fun applyLanguage(context: Context, language: String): Context {
        val locale = Locale(language)
        val configuration = context.resources.configuration
        val displayMetrics = context.resources.displayMetrics

        Locale.setDefault(locale)

        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
}