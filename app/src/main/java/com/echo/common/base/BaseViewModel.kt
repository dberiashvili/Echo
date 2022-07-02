package com.echo.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.echo.R
import com.echo.common.base.utils.DialogData
import com.echo.common.base.utils.Event
import com.echo.common.base.utils.UiErrorInterface
import com.echo.common.base.utils.navigation.NavigationCommand
import com.echo.common.base.utils.navigation.NavigationEvent

abstract class BaseViewModel: ViewModel(), UiErrorInterface {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _dialog = MutableLiveData<Event<DialogData>>()
    val dialog: LiveData<Event<DialogData>> get() = _dialog

    private val _navigation = MutableLiveData<NavigationEvent<NavigationCommand>>()
    val navigation: LiveData<NavigationEvent<NavigationCommand>> get() = _navigation

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationEvent(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = NavigationEvent(NavigationCommand.Back)
    }

    protected fun showLoading() = _loading.postValue(true)
    protected fun hideLoading() = _loading.postValue(false)

    private fun showDialog(data: DialogData) = _dialog.postValue(Event(data))

    override fun onNoInternet() {
        showDialog(
            DialogData(
                title = R.string.common_error,
                messageRes = R.string.common_no_connection
            )
        )
    }

    override fun onServerError(message: String) {
        _dialog.postValue(
            Event(
                DialogData(
                    title = R.string.common_error,
                    message = message
                )
            )
        )
    }

    override fun onUnauthorized() {}
}