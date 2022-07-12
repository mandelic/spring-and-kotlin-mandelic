package com.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InMemoryCarCheckUpRepository (private val dataSource: DataSource)

    : com.example.CarCheckUpRepository {
    private val carCheckUpMap = mutableMapOf<Long, com.example.CarCheckUp>()
    private val cars = mutableListOf(
    Car("Porsche", "Taycan", "4T1BE46K19U448615"),
    Car("Audi", "A8", "1C4PJMDB6FW698828"),
    Car("Audi", "A3", "5GAKRCKD6DJ199296"),
    Car("Seat", "Ibiza", "2T3RFREV4EW154826"))
    init {
        println(dataSource.dbName)
        println(dataSource.username)
        println(dataSource.password)

        carCheckUpMap[1] = CarCheckUp(1,LocalDateTime.of(2018,12,31,11,45), cars[0])
        carCheckUpMap[2] = CarCheckUp(2,LocalDateTime.of(2018, 2,17,12,15), cars[1])
        carCheckUpMap[3] = CarCheckUp(3,LocalDateTime.of(2019, 1,3,13,0), cars[2])
        carCheckUpMap[4] = CarCheckUp(4,LocalDateTime.of(2021, 10,30,14,17), cars[3])
        carCheckUpMap[5] = CarCheckUp(5,LocalDateTime.of(2020, 10,3,15,36), cars[0])
        carCheckUpMap[6] = CarCheckUp(6,LocalDateTime.of(2019, 5,28,16,28), cars[1])
        carCheckUpMap[7] = CarCheckUp(7,LocalDateTime.of(2020, 7,3,17,40), cars[2])
        carCheckUpMap[8] = CarCheckUp(8,LocalDateTime.of(2022, 7,1,18,17), cars[3])
        carCheckUpMap[9] = CarCheckUp(9,LocalDateTime.of(2021, 3,17,19,10), cars[0])
        carCheckUpMap[10] = CarCheckUp(10,LocalDateTime.of(2020, 9,21,20,30), cars[1])
    }


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

    override fun getAllCheckUps(): MutableMap<Long, com.example.CarCheckUp> = carCheckUpMap
}