package com.example.project.carCheckUpSystem.repository.rowmapper

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CarCheckUpRowMapper: RowMapper<CarCheckUp> {
    override fun mapRow(rs: ResultSet, rowNum: Int): CarCheckUp? {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return CarCheckUp(rs.getLong("id"),
            LocalDateTime.parse(rs.getString("performedAt"), pattern),
            rs.getString("workerName"),
            rs.getLong("price"),
            rs.getLong("carId"))
    }
}