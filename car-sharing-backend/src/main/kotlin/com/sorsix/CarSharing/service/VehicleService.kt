package com.sorsix.CarSharing.service

import com.sorsix.CarSharing.api.request.CreateUserRequest
import com.sorsix.CarSharing.api.request.CreateVehicleRequest
import com.sorsix.CarSharing.domain.Vehicle
import com.sorsix.CarSharing.domain.exception.VehicleNotFound
import com.sorsix.CarSharing.repository.VehicleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VehicleService(
    val vehicleRepository: VehicleRepository) {

    fun createVehicle(newVehicle: CreateVehicleRequest): Vehicle{
        return vehicleRepository.save(Vehicle(0, newVehicle.model, newVehicle.make, newVehicle.seats))
    }

    fun getAllVehicles() = vehicleRepository.findAll()

    fun getVehicleById(id: Long): Vehicle {
        return vehicleRepository.findByIdOrNull(id) ?: throw VehicleNotFound("Vehicle with id $id is not found")
    }

    @Transactional
    fun updateUserById(id: Long, vehicle: Vehicle){
        vehicleRepository.updateVehicle(id,vehicle.make,vehicle.model,vehicle.seats)
    }

    fun deleteVehicle(id: Long){
        val vehicle: Vehicle = vehicleRepository.findByIdOrNull(id) ?: throw VehicleNotFound("Vehicle with id $id is not found")
        vehicleRepository.delete(vehicle)
    }
}