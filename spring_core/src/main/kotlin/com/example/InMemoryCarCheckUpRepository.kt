package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InMemoryCarCheckUpRepository (private val dataSource: DataSource)

    : com.example.CarCheckUpRepository {

    init {
        println(dataSource.dbName)
        println(dataSource.username)
        println(dataSource.password)
    }
    private val carCheckUpMap = mutableMapOf<Long, com.example.CarCheckUp>()
    override fun insert(performedAt: LocalDateTime, car: com.example.Car): Long {
        val id = (carCheckUpMap.keys.maxOrNull() ?: 0) + 1
        carCheckUpMap[id] = com.example.CarCheckUp(id = id, performedAt = performedAt, car = car)
        return id
    }
    override fun findById(id: Long): com.example.CarCheckUp {
        return carCheckUpMap[id] ?: throw com.example.CarCheckUpNotFoundException(id)
    }
    override fun deleteById(id: Long): com.example.CarCheckUp {
        return carCheckUpMap.remove(id) ?: throw com.example.CarCheckUpNotFoundException(id)
    }

    override fun getCheckUps(): MutableMap<Long, com.example.CarCheckUp> = carCheckUpMap
}