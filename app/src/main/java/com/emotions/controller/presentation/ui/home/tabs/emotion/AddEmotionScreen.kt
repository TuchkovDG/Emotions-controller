package com.emotions.controller.presentation.ui.home.tabs.emotion

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentAddEmotionBinding
import com.emotions.controller.domain.model.Emotion
import com.emotions.controller.domain.model.EmotionItem
import com.emotions.controller.domain.model.Event
import com.emotions.controller.presentation.internal.AppPreferences
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class AddEmotionFragment : BindingViewModelFragment<FragmentAddEmotionBinding>() {

    override val layoutId: Int = R.layout.fragment_add_emotion
    override val viewModel: AddEmotionViewModel by viewModel()

    private val args: AddEmotionFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.event.observe(this, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                when (it) {
                    "date" -> {
                        val minDateCalendar = Calendar.getInstance()
                        minDateCalendar.add(Calendar.YEAR, -1)
                        showDatePicker(minDateCalendar) { calendar ->
                            viewModel.calendar.set(calendar)
                        }
                    }
                    "time" -> {
                        showTimePicker { calendar ->
                            viewModel.calendar.set(calendar)
                        }
                    }
                    "save" -> {
                        viewModel.calendar.get()?.let { calendar ->
                            EmotionItem(
                                Calendar.getInstance().timeInMillis,
                                Emotion.values()[args.emotion],
                                calendar.timeInMillis,
                                viewModel.input.get() ?: ""
                            )
                        }?.let { item ->
                            viewModel.onSaveInPreferences(item)
                        }
                    }
                    "back" -> activity?.onBackPressed()
                    else -> {
                    }
                }
            }
        })

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

        args.item?.let {
            Gson().fromJson(it, EmotionItem::class.java)?.let { item ->
                val cal = Calendar.getInstance()
                cal.timeInMillis = item.timeInMillis
                viewModel.id.set(item.id)
                viewModel.calendar.set(cal)
                viewModel.input.set(item.input)
            }
        } ?: run {
            viewModel.calendar.set(Calendar.getInstance())
        }
    }

    private fun showDatePicker(
        minDateCalendar: Calendar?,
        maxDateCalendar: Calendar = Calendar.getInstance(),
        onDatePicked: (Calendar) -> Unit
    ) {
        context?.let {
            val dateDialog = DatePickerDialog(
                it,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        viewModel.calendar.get()?.let { date ->
                            set(Calendar.HOUR_OF_DAY, date.get(Calendar.HOUR_OF_DAY))
                            set(Calendar.MINUTE, date.get(Calendar.MINUTE))
                        }
                    }.let(onDatePicked)
                },
                maxDateCalendar[Calendar.YEAR],
                maxDateCalendar[Calendar.MONTH],
                maxDateCalendar[Calendar.DAY_OF_MONTH]
            )

            dateDialog.apply {
                minDateCalendar?.let {
                    datePicker.minDate = minDateCalendar.timeInMillis
                }
                datePicker.maxDate = maxDateCalendar.timeInMillis
            }.show()
        }
    }

    private fun showTimePicker(
        onTimePicked: (Calendar) -> Unit
    ) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            Calendar.getInstance().apply {
                viewModel.calendar.get()?.let { date ->
                    set(Calendar.YEAR, date.get(Calendar.YEAR))
                    set(Calendar.MONTH, date.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH))
                }
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }.let(onTimePicked)
        }
        viewModel.calendar.get()?.let { cal ->
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
    }
}

class AddEmotionViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    val event = MutableLiveData<Event<String>>()

    val emotion = ObservableField<String>()
    val id = ObservableField<Long>()
    val calendar = ObservableField<Calendar>()
    val input = ObservableField<String>("")

    fun changeDate() {
        sendEvent("date")
    }

    fun changeTime() {
        sendEvent("time")
    }

    fun onSave() {
        sendEvent("save")
    }

    fun onSaveInPreferences(item: EmotionItem) {
        preferences.list?.let { list ->
            id.get()?.let { id ->
                list.remove(list.find { it.id == id })
                list.add(EmotionItem(id, item.emotion, item.timeInMillis, item.input))
                preferences.list = list
            } ?: run {
                list.add(item)
                preferences.list = list
            }
        } ?: run {
            val list = ArrayList<EmotionItem>()
            list.add(item)
            preferences.list = list
        }
        sendEvent("back")
    }

    private fun sendEvent(content: String) {
        event.value = Event(content)
    }
}