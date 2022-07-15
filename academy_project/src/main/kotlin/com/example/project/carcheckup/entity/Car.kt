package com.example.project.carcheckup.entity

import java.time.LocalDate

data class Car(
    val id: Long,
    val dateAdded: LocalDate,
    val manufacturer: String,
    val model: String,
    val productionYear: Int,
    val vin: String,
    val carCheckUpList: MutableList<CarCheckUp>
)