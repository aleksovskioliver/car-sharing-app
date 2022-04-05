package com.sorsix.CarSharing.service

import com.sorsix.CarSharing.api.request.CreateReservationRequest
import com.sorsix.CarSharing.api.request.addCustomerToReservation
import com.sorsix.CarSharing.domain.*
import com.sorsix.CarSharing.domain.exception.ReservationNotFound
import com.sorsix.CarSharing.repository.LocationRepository
import com.sorsix.CarSharing.repository.UserRepository
import com.sorsix.CarSharing.repository.ReservationRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ReservationService(
    val reservationRepository: ReservationRepository,
    val userRepository: UserRepository,
    val locationRepository: LocationRepository
) {
    var formatter = DateTimeFormatter.ofPattern("d/MM/yyyy HH:mm")

    fun getReservations(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun getReservationById(id: Long): Reservation {
        return reservationRepository.findByIdOrNull(id) ?: throw ReservationNotFound("Reservation is not created")
    }

    fun createReservation(newReservationRequest: CreateReservationRequest): Reservation {
        val userName = SecurityContextHolder.getContext().authentication.name
        val driver = userRepository.findByEmail(userName)!!
        val startTime = LocalDateTime.parse(newReservationRequest.startTime, formatter)
        val endTime = LocalDateTime.parse(newReservationRequest.endTime, formatter)
        val pickupLocation = locationRepository.findByCity(newReservationRequest.pickupLocation)
        val dropoutLocation = locationRepository.findByCity(newReservationRequest.dropoutLocation)
        return reservationRepository.save(
            Reservation(
                0, driver, mutableListOf<User>(), startTime, endTime, pickupLocation, dropoutLocation,
                newReservationRequest.availableSeats, ReservationStatus.ACTIVE, newReservationRequest.tripCost
            )
        )
    }

    fun addCustomerToReservation(addNewCustomer: addCustomerToReservation): Reservation {
        val reservation = reservationRepository.findByIdOrNull(addNewCustomer.reservationId) ?: throw Exception()
        val userName = SecurityContextHolder.getContext().authentication.name
        val customer = userRepository.findByEmail(userName)!!
        val reservationList = reservation.customers
        reservationList.add(customer)
        return reservationRepository.save(Reservation(reservation.id,reservation.driver, reservationList,
                reservation.startTime, reservation.endTime, reservation.pickupLocation, reservation.dropoutLocation,
                reservation.tripCost, reservation.status, reservation.availableSeats - 1
            )
        )
    }

    fun deleteReservation(id: Long) {
        val reservation = reservationRepository.findByIdOrNull(id) ?: throw Exception()
        reservationRepository.delete(reservation)
    }
}