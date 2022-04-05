package com.sorsix.CarSharing.api

import com.sorsix.CarSharing.api.request.CreateVehicleRequest
import com.sorsix.CarSharing.service.VehicleService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/vehicle")
class VehicleController(private val vehicleService: VehicleService) {

    @PostMapping("/create")
    fun createVehicle(@RequestBody vehicleRequest: CreateVehicleRequest){
        vehicleService.createVehicle(vehicleRequest)
    }

    @GetMapping
    fun getVehicles() = vehicleService.getAllVehicles()
}