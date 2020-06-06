package com.emotions.controller.presentation.ui.base

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.emotions.controller.R
import com.emotions.controller.domain.model.Emotion
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("textId")
fun TextView.setTextId(textId: Int) {
    hint = when (textId) {
        0 -> null
        else -> resources.getString(textId)
    }
}

@BindingAdapter("colorId")
fun MaterialCardView.setColorId(colorId: Int) {
    setBackgroundColor(ContextCompat.getColor(context, colorId))
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("date")
fun TextView.setDate(calendar: Calendar?) {
    calendar?.let {
        text = SimpleDateFormat("dd.MM.yyyy").format(calendar.time)
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("time")
fun TextView.setTime(calendar: Calendar?) {
    calendar?.let {
        text = SimpleDateFormat("HH:mm").format(calendar.time)
    }
}

@BindingAdapter("emotion")
fun TextView.setEmotion(emotion: Emotion) {
    text = when (emotion) {
        Emotion.ECSTASY -> resources.getString(R.string.ecstasy)
        Emotion.VIGILANCE -> resources.getString(R.string.vigilance)
        Emotion.RAGE -> resources.getString(R.string.rage)
        Emotion.LOATHING -> resources.getString(R.string.loathing)
        Emotion.GRIEF -> resources.getString(R.string.grief)
        Emotion.AMAZEMENT -> resources.getString(R.string.amazement)
        Emotion.TERROR -> resources.getString(R.string.terror)
        Emotion.ADMIRATION -> resources.getString(R.string.admiration)
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("dateTime")
fun TextView.setDateTime(timeInMillis: Long) {
    Calendar.getInstance().let {
        it.timeInMillis = timeInMillis
        text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(it.time)
    }
}