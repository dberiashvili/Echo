package com.echo.auth.presentation

import android.util.Patterns
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
import com.echo.common.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCase: RegisterUseCase
) : BaseViewModel() {
    private val _authResponse: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val authResponse: LiveData<Resource<AuthResponse>> = _authResponse
    private val _validateFields = MutableLiveData(false)
    val validateFields: LiveData<Boolean> = _validateFields
    private val _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    val isEmailValid: LiveData<Boolean> = _isEmailValid

    suspend fun registerUser(registerModel: RegisterModel) {
        viewModelScope.launch {
            showLoading()
            try {
                val response = useCase.invoke(registerModel)
                _authResponse.postValue(Resource.Success(response))
            } catch (e: Exception) {
                handleNetworkError(e)
                _authResponse.postValue(Resource.Error(e.localizedMessage ?: ""))
            } finally {
                hideLoading()
            }
        }

    }

    fun checkFields(inputs: List<EditText>) {
        for (input in inputs) {
            input.doAfterTextChanged {
                if (inputs.none { it.text.isEmpty() } &&_isEmailValid.value == true) {
                    _validateFields.postValue(true)
                } else {
                    _validateFields.postValue(false)
                }
            }
        }
    }

    fun navigateToChooser() {
        navigate(RegisterScreenDirections.actionRegisterScreenToTourChooserScreen())
    }

    fun checkEmail(email: String) {

        _isEmailValid.postValue(
            email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )

    }
}