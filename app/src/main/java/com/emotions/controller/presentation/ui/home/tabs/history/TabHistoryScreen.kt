package com.emotions.controller.presentation.ui.home.tabs.history

import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabHistoryBinding
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabHistoryFragment : BindingViewModelFragment<FragmentTabHistoryBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_history
    override val viewModel: TabHistoryViewModel by viewModel()
}

class TabHistoryViewModel : ViewModel()