import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Component
class InMemoryCarCheckUpRepository(@Value("\${dbName}") private val dbName: String,
                                   @Value("\${username}") private val username: String,
                                   @Value("\${password}") private val password: String)

    : CarCheckUpRepository {
    init {
        println(dbName)
        println(username)
        println(password)
    }
    private val carCheckUpMap = mutableMapOf<Long, CarCheckUp>()
    override fun insert(performedAt: LocalDateTime, car: Car): Long {
        val id = (carCheckUpMap.keys.maxOrNull() ?: 0) + 1
        carCheckUpMap[id] = CarCheckUp(id = id, performedAt = performedAt, car = car)
        return id
    }
    override fun findById(id: Long): CarCheckUp {
        return carCheckUpMap[id] ?: throw CarCheckUpNotFoundException(id)
    }
    override fun deleteById(id: Long): CarCheckUp {
        return carCheckUpMap.remove(id) ?: throw CarCheckUpNotFoundException(id)
    }

    override fun getCheckUps(): MutableMap<Long, CarCheckUp> = carCheckUpMap
}