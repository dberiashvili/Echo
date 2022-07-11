package com.echo.quiz.domain.usecase

import com.echo.quiz.domain.models.UserOption
import com.echo.quiz.domain.repository.QuizRepo
import javax.inject.Inject

class GetCorrectAnswerUseCase @Inject constructor(private val repo: QuizRepo) {
    suspend operator fun invoke(option: UserOption) = repo.getCorrectAnswer(option)
}