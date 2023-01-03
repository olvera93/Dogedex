package com.olvera.dogedex.core.api.dto

import com.squareup.moshi.Json

class AddDogToUserDto(
    @field:Json(name = "dog_id")
    val dogId: Long
)