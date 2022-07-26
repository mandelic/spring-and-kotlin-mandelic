package com.example.project.carCheckUpSystem.carModel.controller.dto

import com.example.project.carCheckUpSystem.carModel.entity.CarModel

data class AddCarModelDTO (
    val manufacturer: String,
    val model: String
    ) {
    fun toCarModel() = CarModel(manufacturer = manufacturer, model = model)
}