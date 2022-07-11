package com.echo.quiz.data

import com.echo.quiz.domain.models.UserOption
import com.echo.quiz.domain.models.CorrectAnswer
import com.echo.quiz.domain.models.QuizQuestion
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QuizService {
    @GET("Quiz")
    suspend fun getQuestions(@Query("tourTypeId") tourTypeId: Int):List<QuizQuestion>

    @POST("Quiz/check_question")
    suspend fun checkQuestion(@Body question: UserOption): CorrectAnswer
}