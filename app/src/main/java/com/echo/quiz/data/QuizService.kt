package com.echo.quiz.data

import com.echo.quiz.domain.models.QuizQuestion
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizService {
    @GET("Quiz")
    suspend fun getQuestions(@Query("tourTypeId") tourTypeId: Int):List<QuizQuestion>
}