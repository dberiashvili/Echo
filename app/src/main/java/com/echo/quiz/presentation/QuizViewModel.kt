package com.echo.quiz.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.echo.common.QuizTimer
import com.echo.common.base.BaseViewModel
import com.echo.common.base.utils.handleNetworkError
import com.echo.common.model.Resource
import com.echo.quiz.domain.models.QuizQuestion
import com.echo.quiz.domain.usecase.GetQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuestionsUseCase: GetQuestionsUseCase,
): BaseViewModel() {
    val index = MutableLiveData(0)
    val timer: QuizTimer = QuizTimer(3000, 1000)
    private val _questions: MutableStateFlow<Resource<List<QuizQuestion>>> =
        MutableStateFlow(Resource.Loading())
    val questions: StateFlow<Resource<List<QuizQuestion>>> = _questions
    val isFinished = timer.isFinished
    suspend fun getQuestions(id: Int) {
        viewModelScope.launch {
            try {
                val response = getQuestionsUseCase(id)
                _questions.emit(Resource.Success(response))
            } catch (e: Exception) {
                handleNetworkError(e)
                _questions.emit(Resource.Error(e.localizedMessage ?: ""))
            } finally {
                hideLoading()
            }

        }
    }

    fun nextQuestion() {
        if (questions.value.data!!.size-1>index.value!!) {
            index.postValue(index.value?.plus(1) ?: index.value)
        }

    }
}

