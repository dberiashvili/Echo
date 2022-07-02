package com.echo.quiz.domain.repository

import com.echo.quiz.domain.models.QuizQuestion

interface QuizRepo {
    suspend fun getQuestions(tourTypeId:Int): List<QuizQuestion>
}