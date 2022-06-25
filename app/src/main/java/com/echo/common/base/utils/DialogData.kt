package com.echo.common.base.utils

import androidx.annotation.StringRes

data class DialogData(
    @StringRes val title: Int,
    @StringRes val messageRes: Int? = null,
    val message: String? = null
)