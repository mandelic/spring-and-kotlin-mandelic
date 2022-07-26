package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import java.time.LocalDate

data class AddCarMDTO (
    val dateAdded: LocalDate,
    val model: CarModel,
    val productionYear: Int,
    val vin: String
) {
    fun toCar(): Car = Car(dateAdded = dateAdded, carModel = model, productionYear = productionYear, vin = vin)
}