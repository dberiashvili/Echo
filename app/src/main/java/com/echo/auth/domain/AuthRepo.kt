package com.echo.auth.domain

interface AuthRepo {
    fun registerUser(registerModel: RegisterModel) : Int
}