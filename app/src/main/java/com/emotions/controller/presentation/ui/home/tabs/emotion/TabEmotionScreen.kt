package com.emotions.controller.presentation.ui.home.tabs.emotion

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabEmotionBinding
import com.emotions.controller.domain.model.Emotion
import com.emotions.controller.domain.model.Event
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import com.emotions.controller.presentation.ui.base.input.EmotionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabEmotionFragment : BindingViewModelFragment<FragmentTabEmotionBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_emotion
    override val viewModel: TabEmotionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        R.color.dark
        viewModel.event.observe(this, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                findNavController().navigate(
                    TabEmotionFragmentDirections.actionTabsEmotionFragmentToAddEmotionFragment(
                        it.ordinal
                    )
                )
            }
        })
        viewModel.eventInfo.observe(this, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                findNavController().navigate(
                    TabEmotionFragmentDirections.actionTabsEmotionFragmentToInfoEmotionFragment(
                        it.ordinal
                    )
                )
            }
        })
    }
}

class TabEmotionViewModel : ViewModel() {

    val ecstasy = EmotionViewModel(
        R.string.ecstasy,
        R.color.ecstasy,
        onClick = ::pickEcstasy,
        onClickInfo = ::pickEcstasyInfo
    )

    val vigilance = EmotionViewModel(
        R.string.vigilance,
        R.color.vigilance,
        onClick = ::pickVigilance,
        onClickInfo = ::pickVigilanceInfo
    )

    val rage = EmotionViewModel(
        R.string.rage,
        R.color.rage,
        onClick = ::pickRage,
        onClickInfo = ::pickRageInfo
    )

    val loathing = EmotionViewModel(
        R.string.loathing,
        R.color.loathing,
        onClick = ::pickLoathing,
        onClickInfo = ::pickLoathingInfo
    )

    val grief = EmotionViewModel(
        R.string.grief,
        R.color.grief,
        onClick = ::pickGrief,
        onClickInfo = ::pickGriefInfo
    )

    val amazement = EmotionViewModel(
        R.string.amazement,
        R.color.amazement,
        onClick = ::pickAmazement,
        onClickInfo = ::pickAmazementInfo
    )

    val terror = EmotionViewModel(
        R.string.terror,
        R.color.terror,
        onClick = ::pickTerror,
        onClickInfo = ::pickTerrorInfo
    )

    val admiration = EmotionViewModel(
        R.string.admiration,
        R.color.admiration,
        onClick = ::pickAdmiration,
        onClickInfo = ::pickAdmirationInfo
    )

    val event = MutableLiveData<Event<Emotion>>()
    val eventInfo = MutableLiveData<Event<Emotion>>()

    private fun pickEcstasy() {
        sendEvent(Emotion.ECSTASY)
    }

    private fun pickEcstasyInfo() {
        sendEvent(Emotion.ECSTASY)
    }

    private fun pickVigilance() {
        sendEvent(Emotion.VIGILANCE)
    }

    private fun pickVigilanceInfo() {
        sendEvent(Emotion.VIGILANCE)
    }

    private fun pickRage() {
        sendEvent(Emotion.RAGE)
    }

    private fun pickRageInfo() {
        sendEvent(Emotion.RAGE)
    }

    private fun pickLoathing() {
        sendEvent(Emotion.LOATHING)
    }

    private fun pickLoathingInfo() {
        sendEvent(Emotion.LOATHING)
    }

    private fun pickGrief() {
        sendEvent(Emotion.GRIEF)
    }

    private fun pickGriefInfo() {
        sendEvent(Emotion.GRIEF)
    }

    private fun pickAmazement() {
        sendEvent(Emotion.AMAZEMENT)
    }

    private fun pickAmazementInfo() {
        sendEvent(Emotion.AMAZEMENT)
    }

    private fun pickTerror() {
        sendEvent(Emotion.TERROR)
    }

    private fun pickTerrorInfo() {
        sendEvent(Emotion.TERROR)
    }

    private fun pickAdmiration() {
        sendEvent(Emotion.ADMIRATION)
    }

    private fun pickAdmirationInfo() {
        sendEvent(Emotion.ADMIRATION)
    }

    private fun sendEvent(emotion: Emotion) {
        event.value = Event(emotion)
    }

    private fun sendEventInfo(emotion: Emotion) {
        eventInfo.value = Event(emotion)
    }
}