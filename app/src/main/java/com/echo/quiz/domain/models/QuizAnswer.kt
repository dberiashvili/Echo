package com.echo.quiz.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class QuizAnswer(
    val id: Long,
    val answer: String
)
