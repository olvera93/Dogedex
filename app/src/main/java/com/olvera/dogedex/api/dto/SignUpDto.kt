package com.olvera.dogedex.api.dto

import com.squareup.moshi.Json

class SignUpDto(

    val email: String,
    val password: String,
    @field:Json(name = "password_confirmation")
    val passwordConfirmation: String
)