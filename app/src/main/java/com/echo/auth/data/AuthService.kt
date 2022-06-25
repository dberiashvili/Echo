package com.echo.auth.data

import com.echo.auth.domain.RegisterModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("Users/sign_up")
    suspend fun registerUser(@Body user: RegisterModel): AuthResponse
}