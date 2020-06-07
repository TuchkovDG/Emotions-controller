package com.emotions.controller.presentation.ui.home.tabs.settings

import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.emotions.controller.R
import com.emotions.controller.databinding.FragmentTabSettingsBinding
import com.emotions.controller.domain.model.Event
import com.emotions.controller.presentation.internal.AppPreferences
import com.emotions.controller.presentation.internal.ReminderBroadcast
import com.emotions.controller.presentation.ui.base.BindingViewModelFragment
import org.jetbrains.anko.noButton
import org.jetbrains.anko.okButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.selector
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList


class TabSettingsFragment : BindingViewModelFragment<FragmentTabSettingsBinding>() {

    override val layoutId: Int = R.layout.fragment_tab_settings
    override val viewModel: TabSettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        viewModel.event.observe(this, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                when (it) {
                    "exit" -> {
                        cancel()
                        activity?.finish()
                    }
                    "time" -> {
                        showTimePicker { calendar ->
                            viewModel.calendar.set(calendar)
                            viewModel.saveTime(calendar.timeInMillis)
                            cancel()
                            setRepeating()
                        }
                    }
                    "clear" -> {
                        alert(getString(R.string.you_sure)) {
                            okButton {
                                viewModel.clearInfo()
                            }
                            noButton {}
                        }.show()
                    }
                    else -> {
                    }
                }
            }
        })
    }

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

        viewModel.setIsReminder()
        viewModel.setTime()
        binding.scTvReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setRepeating()
            } else {
                cancel()
            }
            viewModel.saveReminder(isChecked)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channelCreateEmotion",
                "Create Emotion Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Channel for Emotion Reminder"
            val system = context?.getSystemService(NotificationManager::class.java)
            system?.createNotificationChannel(channel)
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

    private fun setRepeating() {
        val intent = Intent(context, ReminderBroadcast::class.java)
        val pandingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager

        val time = Calendar.getInstance()
        viewModel.getTime().let {
            if (it == 0L) {
                time.add(Calendar.SECOND, 5)
                viewModel.calendar.set(time)
                viewModel.saveTime(time.timeInMillis)
            } else {
                time.timeInMillis = it
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            time.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pandingIntent
        )
    }

    private fun cancel() {
        val intent = Intent(context, ReminderBroadcast::class.java)
        val pandingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pandingIntent)
    }
}

class TabSettingsViewModel(
    private val preferences: AppPreferences
) : ViewModel() {

    val event = MutableLiveData<Event<String>>()

    val isReminder = ObservableBoolean(false)
    val calendar = ObservableField<Calendar>()

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

    fun setIsReminder() {
        isReminder.set(preferences.reminder)
    }

    fun saveReminder(reminder: Boolean) {
        preferences.reminder = reminder
        setIsReminder()
    }

    fun setTime() {
        val cal = Calendar.getInstance()
        cal.timeInMillis = preferences.time
        calendar.set(cal)
    }

    fun getTime() =
        preferences.time

    fun saveTime(timeInMillis: Long) {
        preferences.time = timeInMillis
        setTime()
    }

    fun clearInfo() {
        with(preferences) {
            list = ArrayList()
            modeNight = 0
            language = ""
            reminder = false
            time = 0L
        }
        sendEvent("exit")
    }

    fun changeTime() {
        sendEvent("time")
    }

    fun onClear() {
        sendEvent("clear")
    }

    private fun sendEvent(content: String) {
        event.value = Event(content)
    }
}