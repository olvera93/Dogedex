package com.olvera.dogedex.core.api.dto

import com.olvera.dogedex.core.model.Dog

class DogDtoMapper {

     fun fromDogDtoToDogDomain(dogDto: DogDto): Dog {
        return Dog(
            dogDto.id,
            dogDto.index,
            dogDto.name,
            dogDto.type,
            dogDto.heightFemale,
            dogDto.heightMale,
            dogDto.imageUrl,
            dogDto.lifeExpectancy,
            dogDto.temperament,
            dogDto.weightFemale,
            dogDto.weightMale
            )
    }

    fun fromDogDtoListToDogDomainList(dogDtoList: List<DogDto>): List<Dog> {
        return dogDtoList.map { fromDogDtoToDogDomain(it) }
    }

}