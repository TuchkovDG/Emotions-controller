package com.emotions.controller.presentation.ui.home.tabs.emotion

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentInfoEmotionBinding
import com.emotions.controller.domain.model.Emotion
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoEmotionFragment : BindingViewModelFragment<FragmentInfoEmotionBinding>() {

    override val layoutId: Int = R.layout.fragment_info_emotion
    override val viewModel: InfoEmotionViewModel by viewModel()

    private val args: InfoEmotionFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (Emotion.values()[args.emotion]) {
            Emotion.ECSTASY -> getString(R.string.ecstasy)
            Emotion.VIGILANCE -> getString(R.string.vigilance)
            Emotion.RAGE -> getString(R.string.rage)
            Emotion.LOATHING -> getString(R.string.loathing)
            Emotion.GRIEF -> getString(R.string.grief)
            Emotion.AMAZEMENT -> getString(R.string.amazement)
            Emotion.TERROR -> getString(R.string.terror)
            Emotion.ADMIRATION -> getString(R.string.admiration)
        }.let {
            viewModel.emotion.set(it)
        }
    }
}

class InfoEmotionViewModel : ViewModel() {

    val emotion = ObservableField<String>()
}