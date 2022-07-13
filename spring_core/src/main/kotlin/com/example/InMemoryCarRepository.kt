package com.example

import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class InMemoryCarRepository : CarRepository{

    private val carMap = mutableMapOf<String, Car>()
    init {
        carMap["4T1BE46K19U448615"] = Car("Porsche", "Taycan", "4T1BE46K19U448615")
        carMap["1C4PJMDB6FW698828"] = Car("Audi", "A8", "1C4PJMDB6FW698828")
        carMap["5GAKRCKD6DJ199296"] = Car("Audi", "A3", "5GAKRCKD6DJ199296")
        carMap["2T3RFREV4EW154826"] = Car("Seat", "Ibiza", "2T3RFREV4EW154826")
    }

    override fun insert(manufacturer: String, model: String, vin: String): Boolean {
        carMap[vin] = Car(manufacturer, model, vin)
        return true
    }

    override fun findByVin(vin: String): Car {
        return carMap[vin] ?: throw CarNotFoundException(vin)
    }

    override fun deleteByVin(vin: String): Car {
        return carMap.remove(vin) ?: throw CarNotFoundException(vin)
    }

    override fun findAll(): List<Car> = carMap.toList().map{it.second}


}