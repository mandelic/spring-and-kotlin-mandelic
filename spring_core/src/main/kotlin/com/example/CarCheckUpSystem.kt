package com.example
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class CarCheckUpSystem (@Qualifier("inMemoryCarCheckUpRepository") val carCheckUpRepository: CarCheckUpRepository,
                        val carRepository: CarRepository) {

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        return carCheckUpRepository.getAllCheckUps().values.none {it.car.vin == vin && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCheckUp(vin: String): CarCheckUp{
        val insertCar = carRepository.findByVin(vin)
        val id = carCheckUpRepository.insert(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),insertCar)
        return carCheckUpRepository.findById(id)
    }

    fun getCheckUps(vin: String): List<CarCheckUp> {
        return carCheckUpRepository.getAllCheckUps().values.filter{it.car.vin == vin}
    }

    fun getAll(): Map<Long, CarCheckUp> = carCheckUpRepository.getAllCheckUps()

    fun countCheckUps(manufacturer: String): Int = carCheckUpRepository.getAllCheckUps().values.count {it.car.manufacturer == manufacturer}
}