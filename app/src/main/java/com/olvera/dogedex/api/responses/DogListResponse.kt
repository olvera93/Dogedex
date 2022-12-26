package com.olvera.dogedex.api.responses

import com.olvera.dogedex.api.dto.DogDto

data class DogListResponse(val dogs: List<DogDto>)