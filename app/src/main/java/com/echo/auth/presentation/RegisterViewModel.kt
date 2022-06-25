package com.echo.auth.presentation

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.echo.auth.data.AuthResponse
import com.echo.auth.domain.RegisterModel
import com.echo.auth.domain.RegisterUseCase
import com.echo.common.base.BaseViewModel
import com.echo.common.base.handleNetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : BaseViewModel() {
    private val _authResponse: MutableLiveData<AuthResponse> = MutableLiveData()
    val authResponse: LiveData<AuthResponse> = _authResponse
    private val _areTheFieldsFilled = MutableLiveData(false)
    val areTheFieldsFilled: LiveData<Boolean> = _areTheFieldsFilled

    suspend fun registerUser(registerModel: RegisterModel) {
        viewModelScope.launch {
            showLoading()
            try {
                val response = useCase.invoke(registerModel)
                _authResponse.postValue(response)
            } catch (e: Exception) {
                handleNetworkError(e)
            } finally {
                hideLoading()
            }
        }

    }

    fun checkFields(inputs: List<EditText>) {
        for(input in inputs){
            input.doAfterTextChanged {
                if (inputs.none { it.text.isEmpty() }){
                    _areTheFieldsFilled.postValue(true)
                } else {
                    _areTheFieldsFilled.postValue(false)
                }
            }
        }
    }

    fun navigateToChooser() {
        navigate(RegisterScreenDirections.actionRegisterScreenToTourChooserScreen())
    }
}