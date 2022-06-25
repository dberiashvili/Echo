package com.echo.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.echo.R
import com.echo.common.base.utils.DialogData
import com.echo.common.base.utils.Event

abstract class BaseViewModel: ViewModel(), UiErrorInterface {
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> get() = _loading

    private val _dialog = MutableLiveData<Event<DialogData>>()
    val dialog: LiveData<Event<DialogData>> get() = _dialog

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