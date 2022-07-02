package com.echo.quiz.domain.usecase

import com.echo.quiz.domain.repository.QuizRepo
import javax.inject.Inject

class GetQuestionsUseCase @Inject constructor(private val repo: QuizRepo) {
    suspend operator fun invoke(tourTypeId: Int) = repo.getQuestions(tourTypeId)
}