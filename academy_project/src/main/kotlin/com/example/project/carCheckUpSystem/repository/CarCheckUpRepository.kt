package com.example.project.carCheckUpSystem.repository

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import java.time.LocalDateTime

interface CarCheckUpRepository {
    fun insertCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp
    fun findById(id: Long): CarCheckUp?
    fun deleteById(id: Long): Int
    fun getAllCheckUps(): List<CarCheckUp>
}