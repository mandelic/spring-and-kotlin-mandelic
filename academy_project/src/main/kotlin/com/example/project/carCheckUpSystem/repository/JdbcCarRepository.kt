package com.example.project.carCheckUpSystem.repository

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarIdNotFoundException
import com.example.project.carCheckUpSystem.exceptions.CarVinNotFoundException
import com.example.project.carCheckUpSystem.repository.rowmapper.CarCheckUpRowMapper
import com.example.project.carCheckUpSystem.repository.rowmapper.CarRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class JdbcCarRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
): CarRepository {
    override fun insertCar(car: Car): Car {
        val sql = "INSERT INTO cars(dateAdded, manufacturer, model, productionYear, vin)" +
                "VALUES(:dateAdded, :manufacturer, :model, :productionYear, :vin)"
        jdbcTemplate.update(sql,
            mapOf("car" to car.model,
                    "dateAdded" to LocalDate.now(),
                    "manufacturer" to car.manufacturer,
                    "model" to car.model,
                    "productionYear" to car.productionYear,
                    "vin" to car.vin))
        car.id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM cars", mapOf("id" to car.id), Long::class.java) ?: 1
        return car
    }


    override fun findById(id: Long): Car? {
        return try {jdbcTemplate.queryForObject(
            "SELECT * FROM cars WHERE id = :id",
            mapOf("id" to id),
            CarRowMapper()
        )} catch (e: EmptyResultDataAccessException) { return null }
    }

    override fun findByVin(vin: String): Car? {
        return try { jdbcTemplate.queryForObject(
            "SELECT * FROM cars WHERE vin = :vin",
            mapOf("vin" to vin),
            CarRowMapper()
        )} catch(e: EmptyResultDataAccessException) { return null }
    }

    override fun deleteById(id: Long): Int {
        return jdbcTemplate.update("DELETE FROM cars WHERE id = :id",
                                mapOf("id" to id))
    }

    override fun findAll(): List<Car> {
        return jdbcTemplate.query("SELECT * FROM cars",
        CarRowMapper())
    }

}