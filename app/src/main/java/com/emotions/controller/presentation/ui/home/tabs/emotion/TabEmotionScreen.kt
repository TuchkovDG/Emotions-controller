package com.emotions.controller.presentation.ui.home.tabs.emotion

import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabEmotionBinding
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabEmotionFragment : BindingViewModelFragment<FragmentTabEmotionBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_emotion
    override val viewModel: TabEmotionViewModel by viewModel()
}

class TabEmotionViewModel : ViewModel()