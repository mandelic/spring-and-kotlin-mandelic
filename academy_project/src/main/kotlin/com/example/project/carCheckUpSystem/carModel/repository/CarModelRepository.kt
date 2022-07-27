package com.example.project.carCheckUpSystem.carModel.repository

import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import com.example.project.carCheckUpSystem.carModel.service.CarManufacturers
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CarModelRepository : JpaRepository<CarModel, UUID>{
    fun existsByManufacturerAndModel(manufacturer: String, model: String): Boolean
    fun findByManufacturerAndModel(manufacturer: String, model: String): CarModel
}