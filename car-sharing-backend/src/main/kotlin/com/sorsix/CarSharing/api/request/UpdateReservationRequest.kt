package com.sorsix.CarSharing.api.request

data class UpdateReservationRequest(
    val startTime: String,
    val endTime: String,
    val tripCost: Int
)