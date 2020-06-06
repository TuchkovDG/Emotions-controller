package com.emotions.controller.presentation.ui.base.input

data class EmotionViewModel(
    val textId: Int = 0,
    val colorId: Int = 0,
    var onClick: (() -> Unit)? = null,
    var onClickInfo: (() -> Unit)? = null
) {

    fun onClick() {
        onClick?.invoke()
    }

    fun onClickInfo() {
        onClickInfo?.invoke()
    }
}