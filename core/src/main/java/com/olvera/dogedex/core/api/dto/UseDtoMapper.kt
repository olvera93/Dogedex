package com.olvera.dogedex.core.api.dto

import com.olvera.dogedex.core.model.User

class UseDtoMapper {

     fun fromUserDtoToUserDomain(userDto: UserDto): User {
        return User(
            userDto.id,
            userDto.email,
            userDto.authenticationToken
        )
    }
}