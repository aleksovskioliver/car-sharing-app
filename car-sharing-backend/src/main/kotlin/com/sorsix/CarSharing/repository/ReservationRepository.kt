package com.sorsix.CarSharing.repository

import com.sorsix.CarSharing.domain.Location
import com.sorsix.CarSharing.domain.Reservation
import com.sorsix.CarSharing.domain.ReservationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ReservationRepository : JpaRepository<Reservation,Long>{
    fun findAllByPickupLocationAndStatusAndEndTimeAfter(pickupLocation: Location,reservationStatus: ReservationStatus,endTime: LocalDateTime): List<Reservation>
    fun findALlByDropoutLocationAndStatusAndEndTimeAfter(dropoutLocation: Location,reservationStatus: ReservationStatus,endTime: LocalDateTime): List<Reservation>
    fun findAllByPickupLocationAndDropoutLocationAndStatusAndEndTimeAfter(pickupLocation: Location,dropoutLocation: Location,reservationStatus: ReservationStatus,endTime: LocalDateTime): List<Reservation>
    fun findAllByStatusAndEndTimeAfter(reservationStatus: ReservationStatus,endTime: LocalDateTime): List<Reservation>
}