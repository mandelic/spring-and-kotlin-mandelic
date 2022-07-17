package com.example.project.carCheckUpSystem.car

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp

data class CarDetails(
    val car: Car,
    val checkupNecessary: Boolean,
    val carCheckUpList: List<CarCheckUp>
)