package com.sorsix.CarSharing.repository

import com.sorsix.CarSharing.domain.Location
import com.sorsix.CarSharing.domain.Reservation
import com.sorsix.CarSharing.domain.ReservationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation,Long>{
    fun findAllByPickupLocationAndStatus(pickupLocation: Location,reservationStatus: ReservationStatus): List<Reservation>
    fun findALlByDropoutLocationAndStatus(dropoutLocation: Location,reservationStatus: ReservationStatus): List<Reservation>
    fun findAllByPickupLocationAndDropoutLocationAndStatus(pickupLocation: Location,dropoutLocation: Location,reservationStatus: ReservationStatus): List<Reservation>
    fun findAllByStatus(reservationStatus: ReservationStatus): List<Reservation>
}