package com.example.project.carCheckUpSystem.repository

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.repository.rowmapper.CarCheckUpRowMapper
import com.example.project.carCheckUpSystem.repository.rowmapper.CarRowMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class JdbcCarCheckUpRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
): CarCheckUpRepository {
    override fun insertCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp {
        val sql = "INSERT INTO checkups(performedAt, workerName, price, carId)" +
                "VALUES(:performedAt, :workerName, :price, :carId)"
        jdbcTemplate.update(sql,
            mapOf("performedAt"  to carCheckUp.performedAt,
                  "workerName" to carCheckUp.workerName,
                  "price" to carCheckUp.price,
                  "carId" to carCheckUp.carId))
        carCheckUp.id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM checkups", mapOf("id" to carCheckUp.id), Long::class.java) ?: 1
        return carCheckUp
    }


    override fun findById(id: Long): CarCheckUp? {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM checkups WHERE id = :id",
            mapOf("id" to id),
            CarCheckUpRowMapper()
        )
    }

    override fun deleteById(id: Long): Int {
        return jdbcTemplate.update("DELETE FROM checkups" +
                                        "WHERE id = :id",
                                    mapOf("id" to id))
    }

    override fun getAllCheckUps(): List<CarCheckUp> {
        return jdbcTemplate.query("SELECT * FROM checkups",
                                    CarCheckUpRowMapper())
    }
}