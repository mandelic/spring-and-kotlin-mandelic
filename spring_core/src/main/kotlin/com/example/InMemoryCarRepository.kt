package com.example

import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class InMemoryCarRepository : com.example.CarRepository{

    private val carMap = mutableMapOf<String, com.example.Car>()

    override fun insert(manufacturer: String, model: String, vin: String): Boolean {
        carMap[vin] = com.example.Car(manufacturer, model, vin)
        return true
    }

    override fun findByVin(vin: String): com.example.Car {
        return carMap[vin] ?: throw com.example.CarNotFoundException(vin)
    }

    override fun deleteByVin(vin: String): com.example.Car {
        return carMap.remove(vin) ?: throw com.example.CarNotFoundException(vin)
    }

    override fun findAll(): List<com.example.Car> = carMap.toList().map{it.second}


}