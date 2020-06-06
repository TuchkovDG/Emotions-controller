package com.emotions.controller.presentation.ui.home.tabs.settings

import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabSettingsBinding
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabSettingsFragment : BindingViewModelFragment<FragmentTabSettingsBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_settings
    override val viewModel: TabSettingsViewModel by viewModel()
}

class TabSettingsViewModel : ViewModel()