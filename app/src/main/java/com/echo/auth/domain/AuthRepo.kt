package com.echo.auth.domain

import com.echo.auth.data.AuthResponse

interface AuthRepo {
    suspend fun registerUser(registerModel: RegisterModel) : AuthResponse
}