package com.emotions.controller.domain.model

class EmotionItem(
    val id: Long,
    val emotion: Emotion,
    val timeInMillis: Long,
    val input: String
)