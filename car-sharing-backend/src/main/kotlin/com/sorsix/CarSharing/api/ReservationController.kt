package com.sorsix.CarSharing.api

import com.sorsix.CarSharing.api.request.CreateReservationRequest
import com.sorsix.CarSharing.api.request.addCustomerToReservation
import com.sorsix.CarSharing.domain.Reservation
import com.sorsix.CarSharing.service.ReservationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/reservation")
class ReservationController(private val reservationService: ReservationService) {

    @GetMapping("/findAll")
    fun getAllReservations(): List<Reservation> {
        return reservationService.getReservations()
    }

    @GetMapping("/find/{id}")
    fun getReservationById(@PathVariable id: Long): ResponseEntity<Reservation>{
        return ResponseEntity.ok(reservationService.getReservationById(id))
    }

    @PostMapping("/create")
    fun createReservation(@RequestBody newReservation: CreateReservationRequest){
        reservationService.createReservation(newReservation)
    }

    @PostMapping("/addCustomer")
    fun addCustomer(@RequestBody addNewCustomer: addCustomerToReservation){
        reservationService.addCustomerToReservation(addNewCustomer)
    }
}