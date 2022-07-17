package com.example.project.carCheckUpSystem.repository

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarCheckUpNotFoundException
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class InMemoryCarCheckUpRepository : CarCheckUpRepository {
    private val carCheckUpMap = mutableMapOf<Long, CarCheckUp>()
    init {

        carCheckUpMap[1] = CarCheckUp(1, LocalDateTime.of(2018,12,31,11,45), "John", 1000,1)
        carCheckUpMap[2] = CarCheckUp(2, LocalDateTime.of(2018, 2,17,12,15), "Amanda",1500,2)
        carCheckUpMap[3] = CarCheckUp(3, LocalDateTime.of(2019, 1,3,13,0),"John", 1750,3)
        carCheckUpMap[4] = CarCheckUp(4, LocalDateTime.of(2021, 10,30,14,17), "Coleen",820,4)
        carCheckUpMap[5] = CarCheckUp(5, LocalDateTime.of(2020, 10,3,15,36), "Amanda",550,1)
        carCheckUpMap[6] = CarCheckUp(6, LocalDateTime.of(2019, 5,28,16,28), "Amanda",480,2)
        carCheckUpMap[7] = CarCheckUp(7, LocalDateTime.of(2020, 7,3,17,40), "Mark",1300,3)
        carCheckUpMap[8] = CarCheckUp(8, LocalDateTime.of(2022, 7,1,18,17), "John",1000,4)
        carCheckUpMap[9] = CarCheckUp(9, LocalDateTime.of(2021, 3,17,19,10), "John",2000,1)
        carCheckUpMap[10] = CarCheckUp(10, LocalDateTime.of(2020, 9,21,20,30), "Mark",1000,2)
    }

    override fun insertCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp {
        val id = (carCheckUpMap.keys.maxOrNull() ?: 0) + 1
        carCheckUp.id = id
        carCheckUpMap[id] = carCheckUp
        return carCheckUp
    }
    override fun findById(id: Long): CarCheckUp {
        return carCheckUpMap[id] ?: throw CarCheckUpNotFoundException(id)
    }
    override fun deleteById(id: Long): CarCheckUp {
        return carCheckUpMap.remove(id) ?: throw CarCheckUpNotFoundException(id)
    }

    override fun getAllCheckUps(): MutableMap<Long, CarCheckUp> = carCheckUpMap
}