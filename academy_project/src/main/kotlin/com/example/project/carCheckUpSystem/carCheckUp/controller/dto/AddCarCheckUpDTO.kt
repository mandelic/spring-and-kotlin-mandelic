package com.example.project.carCheckUpSystem.carCheckUp.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import java.time.LocalDateTime
import java.util.*

data class AddCarCheckUpDTO (
        val performedAt: LocalDateTime,
        val workerName: String,
        val price: Long,
        val carId: UUID
        ) {
        fun toCarCheckUp(carFetcher: (UUID) -> Car): CarCheckUp {
                return CarCheckUp(
                        performedAt = performedAt,
                        workerName = workerName,
                        price = price,
                        car = carFetcher.invoke(carId)
                )
        }
}