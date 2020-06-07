package com.emotions.controller.presentation.ui.home.tabs.settings

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabSettingsBinding
import com.emotions.controller.presentation.internal.AppPreferences
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.jetbrains.anko.support.v4.selector
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class TabSettingsFragment : BindingViewModelFragment<FragmentTabSettingsBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_settings
    override val viewModel: TabSettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scTvDarkTheme.isChecked = viewModel.getDarkTheme()
        binding.scTvDarkTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                viewModel.saveDarkTheme(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.saveDarkTheme(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        binding.tvLanguageValue.text = when (viewModel.getLanguage()) {
            "en" -> getString(R.string.english)
            "ru" -> getString(R.string.russian)
            "uk" -> getString(R.string.ukrainian)
            else -> getString(R.string.default_val)
        }
        binding.tvLanguageValue.setOnClickListener {
            selector(
                getString(R.string.language), listOf(
                    getString(R.string.english),
                    getString(R.string.russian),
                    getString(R.string.ukrainian)
                )
            ) { _, pos ->
                when (pos) {
                    0 -> viewModel.saveLanguage("en")
                    1 -> viewModel.saveLanguage("ru")
                    2 -> viewModel.saveLanguage("uk")
                }

                val dm: DisplayMetrics = resources.displayMetrics
                val config: Configuration = resources.configuration
                config.setLocale(Locale(viewModel.getLanguage()))
                resources.updateConfiguration(config, dm)

                binding.tvLanguageValue.text = when (viewModel.getLanguage()) {
                    "en" -> getString(R.string.english)
                    "ru" -> getString(R.string.russian)
                    "uk" -> getString(R.string.ukrainian)
                    else -> getString(R.string.default_val)
                }
            }
        }
    }
}

class TabSettingsViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    fun getDarkTheme(): Boolean =
        preferences.modeNight == 2

    fun saveDarkTheme(mode: Int) {
        preferences.modeNight = mode
    }

    fun getLanguage(): String =
        preferences.language

    fun saveLanguage(language: String) {
        preferences.language = language
    }
}