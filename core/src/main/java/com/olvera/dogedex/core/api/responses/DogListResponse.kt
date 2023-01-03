package com.olvera.dogedex.core.api.responses

import com.olvera.dogedex.core.api.dto.DogDto

data class DogListResponse(val dogs: List<DogDto>)