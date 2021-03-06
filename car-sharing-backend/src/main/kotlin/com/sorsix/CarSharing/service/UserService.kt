package com.sorsix.CarSharing.service

import com.sorsix.CarSharing.api.request.CreateUserRequest
import com.sorsix.CarSharing.api.response.GetUserResponse
import com.sorsix.CarSharing.api.response.GetUserResponseFailed
import com.sorsix.CarSharing.api.response.GetUserResponseSuccess
import com.sorsix.CarSharing.domain.Role
import com.sorsix.CarSharing.domain.User
import com.sorsix.CarSharing.domain.exception.UserAlreadyExists
import com.sorsix.CarSharing.domain.exception.UserNotFoundException
import com.sorsix.CarSharing.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val vehicleService: VehicleService,
    val passwordEncoder: PasswordEncoder
) {

    val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun findUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw UserNotFoundException("User with id $id is not found.")
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getLoggedInUser(): GetUserResponse {
        val username = SecurityContextHolder.getContext().authentication.name
        return when (val user = getUserByEmail(username)) {
            is User -> GetUserResponseSuccess(user)
            else -> GetUserResponseFailed("No user with that email")
        }
    }


    @Transactional
    fun updateUserById(id: Long, user: CreateUserRequest){
        userRepository.updateUser(id,user.firstName,user.lastName,user.phoneNumber)
    }

    fun createUser(newUser: CreateUserRequest): User {
        if (getUserByEmail(newUser.email) != null) {
            throw UserAlreadyExists("User already exists")
        }

        val user = User(
            0,
            newUser.firstName,
            newUser.lastName,
            newUser.phoneNumber,
            newUser.email,
            passwordEncoder.encode(newUser.password),
            if (newUser.role.contains("driver"))
                Role.ROLE_DRIVER
            else
                Role.ROLE_CUSTOMER
        )
        logger.info("[{}]", user)

        if (user.role == Role.ROLE_DRIVER)
            user.vehicle = vehicleService.createVehicle(newUser.vehicle!!)

        return userRepository.save(user)
    }
}