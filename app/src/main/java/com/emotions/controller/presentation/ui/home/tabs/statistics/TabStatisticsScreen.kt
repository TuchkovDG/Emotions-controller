package com.emotions.controller.presentation.ui.home.tabs.statistics

import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabStatisticsBinding
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabStatisticsFragment : BindingViewModelFragment<FragmentTabStatisticsBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_statistics
    override val viewModel: TabStatisticsViewModel by viewModel()
}

class TabStatisticsViewModel : ViewModel()