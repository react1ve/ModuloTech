package com.modulo.data.database.mapper

import com.modulo.data.database.common.Mapper
import com.modulo.data.network.response.UserResp
import com.modulo.domain.model.Address
import com.modulo.domain.model.User

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
