package com.example

interface CarRepository {
    fun insert(manufacturer: String, model: String, vin: String): Boolean
    fun findByVin(vin: String): Car
    fun deleteByVin(vin: String): Car
    fun findAll(): List<Car>
}