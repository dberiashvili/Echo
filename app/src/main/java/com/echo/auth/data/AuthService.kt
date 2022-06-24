package com.echo.auth.data

import com.echo.auth.domain.RegisterModel
import retrofit2.http.POST

interface AuthService {
    @POST("Users/sign_up")
    fun registerUser(user: RegisterModel): Int
}