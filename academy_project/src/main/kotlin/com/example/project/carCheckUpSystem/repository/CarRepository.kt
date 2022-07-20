package com.example.project.carCheckUpSystem.repository

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import java.time.LocalDate

interface CarRepository {
    fun insertCar(car: Car): Car
    fun findById(id: Long): Car?
    fun findByVin(vin: String): Car?
    fun findAll(): List<Car>
    fun deleteById(id: Long): Int
}