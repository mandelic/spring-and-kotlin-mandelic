package com.example.project.carCheckUpSystem.car

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import java.time.LocalDate

data class Car(
    var id: Long = -1,
    val dateAdded: LocalDate = LocalDate.now(),
    val manufacturer: String,
    val model: String,
    val productionYear: Int,
    val vin: String,
    //val carCheckUpList: MutableList<CarCheckUp> = mutableListOf<CarCheckUp>()
)