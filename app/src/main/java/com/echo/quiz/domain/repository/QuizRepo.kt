package com.echo.quiz.domain.repository

import com.echo.quiz.domain.models.CorrectAnswer
import com.echo.quiz.domain.models.QuizQuestion
import com.echo.quiz.domain.models.UserOption

interface QuizRepo {
    suspend fun getQuestions(tourTypeId:Int): List<QuizQuestion>
    suspend fun getCorrectAnswer(option: UserOption): CorrectAnswer
}