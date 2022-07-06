import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Car (
    private val manufacturer: String,
    private val model: String,
    private val vin: String
){
    fun getManufacturer():String {
        return manufacturer
    }
    fun getModel():String{
        return model
    }
    fun getVin():String{
        return vin
    }
}

class CheckUp (
    private val performedAt: LocalDateTime,
    private val car: Car
    ){
    fun getCar():Car {
        return car
    }
    fun getPerformedAt():LocalDateTime{
        return performedAt
    }
}

object CarCheckUpSystem{
    private val cars: List<Car>
    private var checkUps: List<CheckUp>
    init {
        cars = listOf(
            Car("Porsche", "Taycan", "4T1BE46K19U448615"),
            Car("Audi", "A8", "1C4PJMDB6FW698828"),
            Car("Audi", "A3", "5GAKRCKD6DJ199296"),
            Car("Seat", "Ibiza", "2T3RFREV4EW154826"))
        checkUps = listOf(
            CheckUp(LocalDateTime.of(2018,12,31,11,45), cars[0]),
            CheckUp(LocalDateTime.of(2018, 2,17,12,15), cars[1]),
            CheckUp(LocalDateTime.of(2019, 1,3,13,0), cars[2]),
            CheckUp(LocalDateTime.of(2021, 10,30,14,17), cars[3]),
            CheckUp(LocalDateTime.of(2020, 10,3,15,36), cars[0]),
            CheckUp(LocalDateTime.of(2019, 5,28,16,28), cars[1]),
            CheckUp(LocalDateTime.of(2020, 7,3,17,40), cars[2]),
            CheckUp(LocalDateTime.of(2022, 7,1,18,17), cars[3]),
            CheckUp(LocalDateTime.of(2021, 3,17,19,10), cars[0]),
            CheckUp(LocalDateTime.of(2020, 9,21,20,30), cars[1])
        )
    }

    fun isCheckUpNecessary(vin: String):Boolean? {
        val vinCheckUp = checkUps.filter {checkUp -> checkUp.getCar().getVin() == vin}
        val lastCheckUp = vinCheckUp.maxByOrNull {checkUp -> checkUp.getPerformedAt()}
        val lastCheckUpYear = lastCheckUp?.getPerformedAt()
        val today = LocalDateTime.now()
        return lastCheckUpYear?.isBefore(today.minusYears(1))
    }

    fun addCheckUp(vin: String):CheckUp? {
        return try {
            val vinCar = cars.filter {car -> car.getVin() == vin}
            if (vinCar.isEmpty()) throw Exception("There is no car with a given VIN")
            val newCheckUp = CheckUp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), vinCar[0])
            checkUps += newCheckUp
            newCheckUp
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    fun getCheckUps(vin:String):List<CheckUp>? {
        return try {
            val vinCheckUps = checkUps.filter { checkUp -> checkUp.getCar().getVin() == vin }
            if (vinCheckUps.isEmpty()) throw Exception("There is no car with a given VIN")
            vinCheckUps
        } catch (e: Exception) {
            println(e)
            null
        }
    }

    fun countCheckUps(manufacturer: String):Int {
        return checkUps.count {checkUp -> checkUp.getCar().getManufacturer() == manufacturer}
    }
}

fun main() {
    if (CarCheckUpSystem.isCheckUpNecessary("1C4PJMDB6FW698828") == true) println("The car check-up is necessary.")
    else println("The car check-up is not necessary.")
    val newCheckUp = CarCheckUpSystem.addCheckUp("5GAKRCKD6DJ199296")
    if(newCheckUp != null) println("New check-up added: (Performed at: ${newCheckUp.getPerformedAt()}, vin: ${newCheckUp.getCar().getVin()})")
    val vinCheckUps = CarCheckUpSystem.getCheckUps("5GAKRCKD6DJ199296")
    vinCheckUps?.forEach { checkUp -> println("Performed at: ${checkUp.getPerformedAt()}, vin: ${checkUp.getCar().getVin()}")}
    println(CarCheckUpSystem.countCheckUps("Audi"))
}
