import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Component
class CarCheckUpSystem (val carCheckUpRepository: CarCheckUpRepository,
                        val carRepository: CarRepository) {

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        return carCheckUpRepository.getCheckUps().values.none {it.car.vin == vin && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCheckUp(vin: String): CarCheckUp{
        val insertCar = carRepository.findByVin(vin)
        val id = carCheckUpRepository.insert(LocalDateTime.now(),insertCar)
        return carCheckUpRepository.findById(id)
    }

    fun getCheckUps(vin: String): List<CarCheckUp>? {
        return carCheckUpRepository.getCheckUps().values.filter{it.car.vin == vin}
    }

    fun countCheckUps(manufacturer: String): Int = carCheckUpRepository.getCheckUps().values.count {it.car.manufacturer == manufacturer}
}