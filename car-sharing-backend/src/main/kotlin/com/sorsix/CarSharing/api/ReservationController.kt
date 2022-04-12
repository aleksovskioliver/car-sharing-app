package com.sorsix.CarSharing.api

import com.sorsix.CarSharing.api.request.CreateReservationRequest
import com.sorsix.CarSharing.domain.Reservation
import com.sorsix.CarSharing.service.ReservationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/reservation")
class ReservationController(private val reservationService: ReservationService) {

    @GetMapping
    fun getAllReservations(@RequestParam(required = false) pickupCity: String?,
                           @RequestParam(required = false) dropoutCity:String?)
    : List<Reservation> {
        return if (pickupCity != null && (dropoutCity == null || dropoutCity != "")){
            reservationService.filterReservationByPickupLocation(pickupCity)
        } else if((pickupCity == null || pickupCity == "") && dropoutCity != null){
            reservationService.filterReservationByDropoutLocation(dropoutCity)
        } else if((pickupCity != null || pickupCity == "") && (dropoutCity != null || dropoutCity == "")){
            reservationService.filterReservationByPickupLocationAndDropoutLocation(pickupCity,dropoutCity)
        }else{
            reservationService.getReservations()
        }
    }

    @GetMapping("/find/{id}")
    fun getReservationById(@PathVariable id: Long): ResponseEntity<Reservation>{
        return ResponseEntity.ok(reservationService.getReservationById(id))
    }

    @PostMapping("/create")
    fun createReservation(@RequestBody newReservation: CreateReservationRequest){
        reservationService.createReservation(newReservation)
    }

    @PostMapping("/addCustomer/{id}")
    fun addCustomer(@PathVariable id: Long){
        reservationService.addCustomerToReservation(id)
    }
}