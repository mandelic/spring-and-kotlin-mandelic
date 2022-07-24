package com.example.project.carCheckUpSystem.car.repository

import com.example.project.carCheckUpSystem.car.entity.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

interface CarRepository : JpaRepository<Car, UUID>{
    fun findByVin(vin: String): Car?

}