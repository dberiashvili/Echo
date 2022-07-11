package com.echo.quiz.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CorrectAnswer(
    val isCorrect: Boolean,
    val CorrectAnswerId: Int
)
