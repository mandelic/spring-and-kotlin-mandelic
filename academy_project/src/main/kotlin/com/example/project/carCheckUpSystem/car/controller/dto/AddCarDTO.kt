package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import java.time.LocalDate
import java.util.*

data class AddCarDTO (
    val dateAdded: LocalDate,
    val model_id: UUID,
    val productionYear: Int,
    val vin: String
) {
    fun toCar(modelFetcher: (UUID) -> CarModel): Car {
        return Car(
            dateAdded = dateAdded,
            carModel = modelFetcher.invoke(model_id),
            productionYear = productionYear,
            vin = vin)
    }
}