package com.example.project.carCheckUpSystem.car.repository

import com.example.project.carCheckUpSystem.car.entity.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CarRepository : JpaRepository<Car, UUID>{
    @Query(value = "select * from Car where Car.vin = :vin", nativeQuery = true)
    fun findByVin(vin: String): Car?
}