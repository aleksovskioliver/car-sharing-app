package com.sorsix.CarSharing.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    @ManyToMany(mappedBy = "customers")
    @JsonIgnore
    val reservation: MutableList<Reservation> = mutableListOf(),
    @OneToOne
    var vehicle: Vehicle? = null
)
