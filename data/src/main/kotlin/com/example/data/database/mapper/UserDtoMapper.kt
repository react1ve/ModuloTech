package com.example.data.database.mapper

import com.example.data.network.response.UserResp
import com.example.domain.common.Mapper
import com.example.domain.model.Address
import com.example.domain.model.User

internal object UserDtoMapper : Mapper<User, UserResp>(

    fromDtoMapper = { dto: UserResp ->
        User(
            dto.firstName,
            dto.lastName,
            Address(dto.address.city, dto.address.postalCode, dto.address.street, dto.address.country),
            dto.birthDate
        )
    }
)
