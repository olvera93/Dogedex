package com.olvera.dogedex.core.api.dto

import com.squareup.moshi.Json

class UserDto(
    val id: Long,
    val email: String,
    @field:Json(name = "authentication_token")
    val authenticationToken: String
)