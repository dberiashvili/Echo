package com.echo.quiz.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: Long,
    val question: String,
    val answers: List<QuizAnswer>
)
