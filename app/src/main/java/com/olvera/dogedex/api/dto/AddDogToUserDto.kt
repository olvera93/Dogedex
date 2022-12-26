package com.olvera.dogedex.api.dto

import com.squareup.moshi.Json

class AddDogToUserDto(
    @field:Json(name = "dog_id")
    val dogId: Long
)