package com.example.project.carCheckUpSystem.carCheckUp.repository

import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface CarCheckUpRepository: JpaRepository<CarCheckUp, UUID>{
    fun findByCarVin(vin: String): List<CarCheckUp>
    fun findByCarId(id: UUID): List<CarCheckUp>
    fun findAllByCarId(id: UUID, pageable: Pageable): Page<CarCheckUp>
    @Query(value = "select case when count(c) > 0 then 'false' else 'true' end from carcheckup c where c.car_id = :car_id and c.performed_at > current_date - interval '1 year'", nativeQuery = true)
    fun isCheckUpNecessary(car_id: UUID): Boolean
}