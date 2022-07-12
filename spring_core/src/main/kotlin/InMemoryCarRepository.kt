import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Component
class InMemoryCarRepository : CarRepository{

    private val carMap = mutableMapOf<String, Car>()

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