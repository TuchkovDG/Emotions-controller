package com.emotions.controller.presentation.ui.base

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibility")
fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}