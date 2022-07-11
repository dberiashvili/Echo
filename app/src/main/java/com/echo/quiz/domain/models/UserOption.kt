package com.echo.quiz.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserOption (val  quizId: Int, val answerId: Int, val userId: Int)