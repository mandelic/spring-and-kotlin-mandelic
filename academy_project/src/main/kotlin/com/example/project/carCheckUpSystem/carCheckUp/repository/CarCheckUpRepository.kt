package com.example.project.carCheckUpSystem.carCheckUp.repository

import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CarCheckUpRepository: JpaRepository<CarCheckUp, UUID>{
    @Query(value = "select * from carcheckup join car on carcheckup.car_id = car.id where car.vin = :vin", nativeQuery = true)
    fun findByVin(vin: String): List<CarCheckUp>
}