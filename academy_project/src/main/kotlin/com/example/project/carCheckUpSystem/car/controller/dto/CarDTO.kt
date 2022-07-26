package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.carModel.controller.dto.CarModelDTO
import java.time.LocalDate
import java.util.*

data class CarDTO(
    val id: UUID,
    val dateAdded: LocalDate,
    val model: CarModelDTO,
    val productionYear: Int,
    val vin: String
) {
    constructor(car: Car) : this(
        car.id,
        car.dateAdded,
        CarModelDTO(car.carModel),
        car.productionYear,
        car.vin
    )
}