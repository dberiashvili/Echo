package com.echo.quiz.data

import com.echo.quiz.domain.models.CorrectAnswer
import com.echo.quiz.domain.models.QuizQuestion
import com.echo.quiz.domain.models.UserOption
import com.echo.quiz.domain.repository.QuizRepo
import javax.inject.Inject

class QuizRepoImpl @Inject constructor (private val quizService: QuizService): QuizRepo {
    override suspend fun getQuestions(tourTypeId: Int): List<QuizQuestion> {
        return quizService.getQuestions(tourTypeId)
    }

    override suspend fun getCorrectAnswer(option: UserOption): CorrectAnswer {
        return quizService.checkQuestion(option)
    }
}