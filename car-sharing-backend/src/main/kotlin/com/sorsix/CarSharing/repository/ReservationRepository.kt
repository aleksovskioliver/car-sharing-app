package com.sorsix.CarSharing.repository

import com.sorsix.CarSharing.domain.Location
import com.sorsix.CarSharing.domain.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation,Long>{
    fun findAllByPickupLocation(pickupLocation: Location): List<Reservation>
    fun findALlByDropoutLocation(dropoutLocation: Location): List<Reservation>
    fun findAllByPickupLocationAndDropoutLocation(pickupLocation: Location,dropoutLocation: Location): List<Reservation>
}