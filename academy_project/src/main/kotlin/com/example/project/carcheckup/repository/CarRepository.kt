package com.example.project.carcheckup.repository

import com.example.project.carcheckup.entity.Car
import com.example.project.carcheckup.entity.CarCheckUp
import java.time.LocalDate

interface CarRepository {
    fun insertData(dateAdded: LocalDate, manufacturer: String, model: String, productionYear: Int, vin: String): Long
    fun insertCar(car: Car): Car
    fun findById(id: Long): Car
    fun deleteById(id: Long): Car
    fun findAll(): List<Car>
    fun addCarCheckUp(id: Long, carCheckUp: CarCheckUp): Boolean?
}