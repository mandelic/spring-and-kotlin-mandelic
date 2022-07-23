package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import java.time.LocalDate

data class AddCarDTO (
    val dateAdded: LocalDate,
    val manufacturer: String,
    val model: String,
    val productionYear: Int,
    val vin: String
) {
    fun toCar() = Car(dateAdded = dateAdded, manufacturer = manufacturer, model = model, productionYear = productionYear, vin = vin)
}