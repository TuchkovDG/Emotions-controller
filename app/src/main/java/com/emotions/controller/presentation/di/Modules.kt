package com.emotions.controller.presentation.di

import com.emotions.controller.presentation.internal.AppPreferences
import com.emotions.controller.presentation.ui.home.tabs.emotion.TabEmotionViewModel
import com.emotions.controller.presentation.ui.home.tabs.history.TabHistoryViewModel
import com.emotions.controller.presentation.ui.home.tabs.settings.TabSettingsViewModel
import com.emotions.controller.presentation.ui.home.tabs.statistics.TabStatisticsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appPrefModule = module {
    single { AppPreferences(androidContext()) }
}

val viewModelsModule = module {
    viewModel { TabEmotionViewModel() }
    viewModel { TabHistoryViewModel() }
    viewModel { TabStatisticsViewModel() }
    viewModel { TabSettingsViewModel() }
}

val modules = module {}

val presentationModules = listOf(appPrefModule, viewModelsModule, modules)

//val registerFragmentModule = module {
//    factory { RegisterFragment() }
//}


//
//val listOfFragment = listOf(registerFragmentModule, loginFragmentModule)
//
//val listOfViewModel = listOf(
//    calculatorViewModelModule,
//    registerViewModelModule,
//    loginViewModelModule,
//    stepViewModel,
//    tabsViewModelModule
//)
//
//val presentationModules = listOf(appPrefModule) + listOfFragment + listOfViewModel