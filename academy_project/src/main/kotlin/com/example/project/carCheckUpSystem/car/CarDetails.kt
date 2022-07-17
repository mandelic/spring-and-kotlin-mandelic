package com.example.project.carCheckUpSystem.car

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.service.CarCheckUpSystem
import java.time.LocalDate

data class CarDetails(private val carCheckUpSystem: CarCheckUpSystem,
                      private val vin: String) {

    val checkUpNecessary: Boolean
    val id: Long
    val dateAdded: LocalDate
    val manufacturer: String
    val model: String
    val productionYear: Int
    val vinRet: String
    val carCheckUpList: List<CarCheckUp>

    init {
        val car = carCheckUpSystem.carRepository.findAll().first {it.vin == vin}
        checkUpNecessary = carCheckUpSystem.isCheckUpNecessary(vin)
        id = car.id
        dateAdded = car.dateAdded
        manufacturer = car.manufacturer
        model = car.model
        productionYear = car.productionYear
        vinRet = vin
        carCheckUpList = carCheckUpSystem.getCheckUps(vin).sortedByDescending { it.performedAt }
    }

}