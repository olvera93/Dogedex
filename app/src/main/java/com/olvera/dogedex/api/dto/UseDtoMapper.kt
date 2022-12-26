package com.olvera.dogedex.api.dto

import com.olvera.dogedex.model.User

class UseDtoMapper {

     fun fromUserDtoToUserDomain(userDto: UserDto): User {
        return User(
            userDto.id,
            userDto.email,
            userDto.authenticationToken
        )
    }
}