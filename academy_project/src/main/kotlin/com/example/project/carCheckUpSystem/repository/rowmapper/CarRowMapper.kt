package com.example.project.carCheckUpSystem.repository.rowmapper

import com.example.project.carCheckUpSystem.car.Car
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.time.LocalDate

class CarRowMapper: RowMapper<Car> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Car {
        return Car(rs.getLong("id"),
        LocalDate.parse(rs.getString("dateadded")),
        rs.getString("manufacturer"),
        rs.getString("model"),
        rs.getInt("productionYear"),
        rs.getString("vin"))
    }

}