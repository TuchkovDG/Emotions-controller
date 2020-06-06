package com.emotions.controller.presentation.ui.base

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

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