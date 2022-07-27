package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.carModel.controller.dto.CarModelDTO
import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import java.time.LocalDate
import java.util.*

data class AddCarMDTO (
    val dateAdded: LocalDate,
    val carModel: CarModelDTO,
    val productionYear: Int,
    val vin: String
) {
    fun toCar(id: UUID): Car {
        return Car(
                dateAdded = dateAdded,
                carModel = CarModel(id, carModel.manufacturer, carModel.model),
                productionYear = productionYear,
                vin = vin
                )
    }
}