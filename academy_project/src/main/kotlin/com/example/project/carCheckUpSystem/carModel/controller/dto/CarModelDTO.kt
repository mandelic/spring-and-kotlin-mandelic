package com.example.project.carCheckUpSystem.carModel.controller.dto

import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import java.io.Serializable

data class CarModelDTO (
    val manufacturer: String,
    val model: String
) {
    constructor(carModel: CarModel) : this (
        carModel.manufacturer,
        carModel.model
    )
}