package com.echo.auth.domain

import com.echo.auth.data.AuthResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repo: AuthRepo) {
    suspend operator fun invoke(user: RegisterModel): AuthResponse{
       return repo.registerUser(user)
    }
}