package com.sorsix.CarSharing.api.response

import com.sorsix.CarSharing.domain.User

sealed interface GetUserResponse

data class GetUserResponseSuccess(val user: User): GetUserResponse
data class GetUserResponseFailed(val error: String): GetUserResponse