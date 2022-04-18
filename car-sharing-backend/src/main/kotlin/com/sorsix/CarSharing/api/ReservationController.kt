package com.sorsix.CarSharing.api

import com.sorsix.CarSharing.api.request.CreateReservationRequest
import com.sorsix.CarSharing.domain.Reservation
import com.sorsix.CarSharing.domain.exception.CustomerAlreadyReserved
import com.sorsix.CarSharing.service.ReservationService
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/reservation")
class ReservationController(private val reservationService: ReservationService) {

    @GetMapping
    fun getAllReservations(
        @RequestParam(required = false) pickupCity: String?,
        @RequestParam(required = false) dropoutCity: String?
    )
            : List<Reservation> {
        return if (pickupCity != null && (dropoutCity == null || dropoutCity == "")) {
            reservationService.filterReservationByPickupLocation(pickupCity)
        } else if ((pickupCity == null || pickupCity == "") && dropoutCity != null) {
            reservationService.filterReservationByDropoutLocation(dropoutCity)
        } else if ((pickupCity != null || pickupCity == "") && (dropoutCity != null || dropoutCity == "")) {
            reservationService.filterReservationByPickupLocationAndDropoutLocation(pickupCity, dropoutCity)
        } else {
            reservationService.getActiveReservation()
        }
    }

    @GetMapping("/find/{id}")
    fun getReservationById(@PathVariable id: Long): ResponseEntity<Reservation> {
        return ok(reservationService.getReservationById(id))
    }

    @GetMapping("/driver")
    fun getDriverCreatedReservations(): ResponseEntity<List<Reservation>> {
        return ok().body(reservationService.getDriverCreatedReservations())
    }

    @PostMapping("/create")
    fun createReservation(@RequestBody newReservation: CreateReservationRequest) {
        reservationService.createReservation(newReservation)
    }

    @PostMapping("/addCustomer/{id}")
    fun addCustomer(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            reservationService.addCustomerToReservation(id)
        } catch (e: CustomerAlreadyReserved) {
            return ResponseEntity.badRequest().body(e.message)
        }
        return ok().build()
    }

    @PostMapping("/removeCustomer/{id}")
    fun removeCustomer(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            reservationService.removeCustomerFromReservation(id)
        } catch (e: CustomerAlreadyReserved) {
            return ResponseEntity.badRequest().body(e.message)
        }
        return ok().build()
    }

    @DeleteMapping("/delete/{id}")
    fun deleteReservation(@PathVariable id:Long){
        reservationService.deleteReservation(id)
    }
}