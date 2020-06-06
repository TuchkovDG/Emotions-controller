package com.emotions.controller.presentation.ui.home.tabs.emotion

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentAddEmotionBinding
import com.emotions.controller.domain.model.Emotion
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEmotionFragment : BindingViewModelFragment<FragmentAddEmotionBinding>() {

    override val layoutId: Int = R.layout.fragment_add_emotion
    override val viewModel: AddEmotionViewModel by viewModel()

    private val args: AddEmotionFragmentArgs by navArgs()

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

class AddEmotionViewModel : ViewModel() {

    val emotion = ObservableField<String>()
}