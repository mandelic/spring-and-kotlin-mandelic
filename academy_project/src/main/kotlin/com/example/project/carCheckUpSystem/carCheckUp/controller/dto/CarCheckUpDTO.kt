package com.example.project.carCheckUpSystem.carCheckUp.controller.dto

import com.example.project.carCheckUpSystem.car.controller.dto.CarDTO
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import java.time.LocalDateTime
import java.util.*

data class CarCheckUpDTO(
    val id: UUID,
    val performedAt: LocalDateTime,
    val workerName: String,
    val price: Long,
    val car: CarDTO
) {
    constructor(carCheckUp: CarCheckUp) : this (
        carCheckUp.id,
        carCheckUp.performedAt ,
        carCheckUp.workerName,
        carCheckUp.price,
        CarDTO(carCheckUp.car)
    )
}