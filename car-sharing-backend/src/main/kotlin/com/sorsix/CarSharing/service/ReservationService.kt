package com.sorsix.CarSharing.service

import com.sorsix.CarSharing.api.request.CreateReservationRequest
import com.sorsix.CarSharing.domain.*
import com.sorsix.CarSharing.domain.exception.CustomerAlreadyReserved
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

    fun getReservations(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun getActiveReservation(): List<Reservation>{
        return reservationRepository.findAllByStatus(ReservationStatus.ACTIVE).reversed()
    }

    fun getReservationById(id: Long): Reservation {
        return reservationRepository.findByIdOrNull(id) ?: throw ReservationNotFound("Reservation is not created")
    }

    fun createReservation(newReservationRequest: CreateReservationRequest): Reservation {
        val driver = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)!!
        val startTime = LocalDateTime.parse(newReservationRequest.startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val endTime = LocalDateTime.parse(newReservationRequest.endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val pickupLocation = locationRepository.findByCity(newReservationRequest.pickupLocation)
        val dropoutLocation = locationRepository.findByCity(newReservationRequest.dropoutLocation)
        return reservationRepository.save(
            Reservation(
                0, driver, mutableListOf<User>(), startTime, endTime, pickupLocation, dropoutLocation,
                newReservationRequest.tripCost, ReservationStatus.ACTIVE, newReservationRequest.availableSeats
            )
        )
    }

    fun addCustomerToReservation(reservationId: Long): Reservation {
        val reservation = reservationRepository.findByIdOrNull(reservationId) ?: throw ReservationNotFound("Reservation with id $reservationId is not found")
        val customer = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)!!
        if(reservation.customers.contains(customer)) throw CustomerAlreadyReserved("You have already reserved this reservation!")
        reservation.customers.add(customer)
        val savedReservation = reservationRepository.save(
            Reservation(
                reservation.id, reservation.driver, reservation.customers,
                reservation.startTime, reservation.endTime, reservation.pickupLocation, reservation.dropoutLocation,
                reservation.tripCost,
                if (reservation.availableSeats <=1) ReservationStatus.FINISHED else ReservationStatus.ACTIVE,
                reservation.availableSeats - 1
            )
        )
        customer.reservation.add(savedReservation)
        userRepository.save(customer)
        return savedReservation
    }

    fun removeCustomerFromReservation(reservationId: Long): Reservation {
        val reservation = reservationRepository.findByIdOrNull(reservationId) ?: throw ReservationNotFound("Reservation with id $reservationId is not found")
        val customer = userRepository.findByEmail(SecurityContextHolder.getContext().authentication.name)!!
        reservation.customers.remove(customer)
        val savedReservation = reservationRepository.save(
            Reservation(
                reservation.id, reservation.driver, reservation.customers,
                reservation.startTime, reservation.endTime, reservation.pickupLocation, reservation.dropoutLocation,
                reservation.tripCost,
                if (reservation.availableSeats >=0) ReservationStatus.ACTIVE else ReservationStatus.FINISHED,
                reservation.availableSeats + 1
            )
        )
        customer.reservation.remove(savedReservation)
        userRepository.save(customer)
        return savedReservation
    }


    fun deleteReservation(id: Long) {
        val reservation = reservationRepository.findByIdOrNull(id) ?: throw ReservationNotFound("Reservation with id $id is not found")
        reservationRepository.delete(reservation)
    }

    fun filterReservationByPickupLocation(city: String): List<Reservation> {
        val pickupLocation = locationRepository.findByCity(city)
        return reservationRepository.findAllByPickupLocationAndStatus(pickupLocation,ReservationStatus.ACTIVE).reversed()
    }

    fun filterReservationByDropoutLocation(city: String): List<Reservation> {
        val dropoutLocation = locationRepository.findByCity(city)
        return reservationRepository.findALlByDropoutLocationAndStatus(dropoutLocation,ReservationStatus.ACTIVE).reversed()
    }

    fun filterReservationByPickupLocationAndDropoutLocation(
        pickupCity: String,
        dropoutCity: String,
    ): List<Reservation> {
        val pickupLocation = locationRepository.findByCity(pickupCity)
        val dropoutLocation = locationRepository.findByCity(dropoutCity)
        return reservationRepository.findAllByPickupLocationAndDropoutLocationAndStatus(pickupLocation, dropoutLocation,ReservationStatus.ACTIVE).reversed()
    }
}