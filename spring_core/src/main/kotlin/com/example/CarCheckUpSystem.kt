package com.example
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CarCheckUpSystem (val carCheckUpRepository: com.example.InMemoryCarCheckUpRepository,
                        val carRepository: com.example.InMemoryCarRepository) {

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        return carCheckUpRepository.getCheckUps().values.none {it.car.vin == vin && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCheckUp(vin: String): com.example.CarCheckUp{
        val insertCar = carRepository.findByVin(vin)
        val id = carCheckUpRepository.insert(LocalDateTime.now(),insertCar)
        return carCheckUpRepository.findById(id)
    }

    fun getCheckUps(vin: String): List<com.example.CarCheckUp>? {
        return carCheckUpRepository.getCheckUps().values.filter{it.car.vin == vin}
    }

    fun countCheckUps(manufacturer: String): Int = carCheckUpRepository.getCheckUps().values.count {it.car.manufacturer == manufacturer}
}