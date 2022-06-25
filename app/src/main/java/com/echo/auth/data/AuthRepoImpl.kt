package com.echo.auth.data

import com.echo.auth.domain.AuthRepo
import com.echo.auth.domain.RegisterModel
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(private val service: AuthService): AuthRepo {
    override suspend fun registerUser(registerModel: RegisterModel): AuthResponse {
        return service.registerUser(registerModel)
    }
}