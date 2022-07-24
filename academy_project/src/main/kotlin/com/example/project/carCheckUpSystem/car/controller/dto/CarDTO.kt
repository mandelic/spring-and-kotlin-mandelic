package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import java.time.LocalDate
import java.util.*

data class CarDTO(
    val id: UUID,
    val dateAdded: LocalDate,
    val manufacturer: String,
    val model: String,
    val productionYear: Int,
    val vin: String
) {
    constructor(car: Car) : this(
        car.id,
        car.dateAdded,
        car.manufacturer,
        car.model,
        car.productionYear,
        car.vin
    )
}