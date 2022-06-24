package com.echo.auth.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val name: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val school: String,
    @SerialName("class")
    val grade: String
)
