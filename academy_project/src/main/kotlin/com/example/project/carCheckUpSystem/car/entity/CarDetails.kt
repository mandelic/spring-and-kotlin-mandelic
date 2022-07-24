package com.example.project.carCheckUpSystem.car.entity

import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import javax.persistence.Entity

data class CarDetails(
    val car: Car,
    val checkupNecessary: Boolean,
    val carCheckUpList: List<CarCheckUp>
)