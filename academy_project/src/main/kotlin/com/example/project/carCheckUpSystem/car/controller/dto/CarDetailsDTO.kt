package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.entity.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp

data class CarDetailsDTO(
    val car: Car,
    val checkUpNecessary: Boolean
) {
    constructor(carDetails: CarDetails) : this(
        carDetails.car,
        carDetails.checkupNecessary
    )

    fun toCarDetails(): CarDetails {
        return CarDetails(car, checkUpNecessary)
    }
}