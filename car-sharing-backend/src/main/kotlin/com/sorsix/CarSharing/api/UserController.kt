package com.sorsix.CarSharing.api

import com.sorsix.CarSharing.api.request.CreateUserRequest
import com.sorsix.CarSharing.api.response.GetUserResponse
import com.sorsix.CarSharing.api.response.GetUserResponseFailed
import com.sorsix.CarSharing.api.response.GetUserResponseSuccess
import com.sorsix.CarSharing.domain.Reservation
import com.sorsix.CarSharing.domain.exception.UserAlreadyExists
import com.sorsix.CarSharing.domain.exception.UserNotFoundException
import com.sorsix.CarSharing.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping("/create")
    fun createUser(@RequestBody userRequest: CreateUserRequest): ResponseEntity<Any>{
        return try {
            ResponseEntity.ok().body(userService.createUser(userRequest))
        } catch (exception: UserAlreadyExists) {
            ResponseEntity.badRequest().body(exception.message)
        }
    }

    @GetMapping("/get")
    fun getUser(): ResponseEntity<GetUserResponse> = when (val user = userService.getLoggedInUser()) {
        is GetUserResponseSuccess -> ResponseEntity.ok(user)
        is GetUserResponseFailed -> ResponseEntity.badRequest().body(user)
    }

    @GetMapping("/reservations")
    fun getUserReservations(): MutableList<Reservation> {
        val user = when (val user = userService.getLoggedInUser()) {
            is GetUserResponseSuccess -> user
            is GetUserResponseFailed -> throw UserNotFoundException("User not found")
        }
        return user.user.reservation
    }

}