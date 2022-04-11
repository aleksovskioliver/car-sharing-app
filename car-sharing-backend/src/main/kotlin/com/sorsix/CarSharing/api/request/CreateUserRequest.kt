package com.sorsix.CarSharing.api.request

import com.sorsix.CarSharing.domain.Vehicle

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val role: String,
    val vehicle: Vehicle?
)