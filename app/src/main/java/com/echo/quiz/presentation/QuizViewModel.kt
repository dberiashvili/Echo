package com.echo.quiz.presentation

import androidx.lifecycle.viewModelScope
import com.echo.common.base.BaseViewModel
import com.echo.common.base.utils.handleNetworkError
import com.echo.common.model.Resource
import com.echo.quiz.domain.models.QuizQuestion
import com.echo.quiz.domain.usecase.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
): BaseViewModel() {
    private val _questions:MutableStateFlow<Resource<List<QuizQuestion>>> = MutableStateFlow(Resource.Loading())
    val questions:StateFlow<Resource<List<QuizQuestion>>> = _questions
    fun getQuestions(id: Int) {
        viewModelScope.launch {
            val response = getQuestionsUseCase(id)
            try {
                _questions.emit(Resource.Success(response))
            } catch (e: Exception) {
                handleNetworkError(e)
                _questions.emit(Resource.Error(e.message?: "დაფიქსირდა შეცდომა"))
            }
        }

    }

}