package com.modulo.data.database.mapper

import com.modulo.data.network.response.UserResp
import com.modulo.domain.model.Address
import com.modulo.domain.model.User

internal object UserModelMapper {

    fun fromNetworkToModel(resp: UserResp): User {
        return User(
            resp.firstName,
            resp.lastName,
            Address(resp.address.city, resp.address.postalCode, resp.address.street, resp.address.country),
            resp.birthDate
        )
    }
}

