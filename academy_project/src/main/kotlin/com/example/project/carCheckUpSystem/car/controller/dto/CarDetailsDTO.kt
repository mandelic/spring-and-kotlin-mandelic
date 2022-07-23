package com.example.project.carCheckUpSystem.car.controller.dto

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.entity.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import java.time.LocalDate
import java.util.*

data class CarDetailsDTO(
    val car: Car,
    val checkUpNecessary: Boolean,
    val carCheckUpList: List<CarCheckUp>
) {
    constructor(carDetails: CarDetails) : this(
        carDetails.car,
        carDetails.checkupNecessary,
        carDetails.carCheckUpList
    )
}