package com.example.project.carcheckup.repository

import com.example.project.carcheckup.entity.Car
import com.example.project.carcheckup.entity.CarCheckUp
import java.time.LocalDateTime

interface CarCheckUpRepository {
    fun insertData(performedAt: LocalDateTime, workerName: String, price: Long, carId: Long): Long
    fun insertCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun getAllCheckUps(): Map<Long, CarCheckUp>
}