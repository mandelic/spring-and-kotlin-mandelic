import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
    private val checkUpDate: LocalDateTime,
    private val car: Car
    ){
    fun getCar():Car {
        return car
    }
    fun getCheckUpDate():LocalDateTime{
        return checkUpDate
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
            CheckUp(LocalDateTime.of(2018, 2,17,11,45), cars[1]),
            CheckUp(LocalDateTime.of(2019, 1,3,11,45), cars[2]),
            CheckUp(LocalDateTime.of(2021, 10,30,11,45), cars[3]),
            CheckUp(LocalDateTime.of(2020, 10,3,11,45), cars[0]),
            CheckUp(LocalDateTime.of(2019, 5,28,11,45), cars[1]),
            CheckUp(LocalDateTime.of(2020, 7,3,11,45), cars[2]),
            CheckUp(LocalDateTime.of(2022, 7,1,11,45), cars[3]),
            CheckUp(LocalDateTime.of(2021, 3,17,11,45), cars[0]),
            CheckUp(LocalDateTime.of(2020, 9,21,11,45), cars[1])
        )
    }

    fun isCheckUpNecessary(vin: String):Boolean? {
        val vinCheckUp = checkUps.filter {checkUp -> checkUp.getCar().getVin() == vin}
        val lastCheckUp = vinCheckUp.maxByOrNull {checkUp -> checkUp.getCheckUpDate()}
        val lastCheckUpYear = lastCheckUp?.getCheckUpDate()
        var today = LocalDateTime.now()
        return lastCheckUpYear?.isBefore(today.minusYears(1))
    }

    fun addCheckUp(vin: String):CheckUp? {
        try {
            //var checkedCar: Car? = null
            val vinCar = cars.filter {car -> car.getVin() == vin}
            if (vinCar.size == 0) throw Exception("There is no car with a given VIN")
            var newCheckUp = CheckUp(LocalDateTime.now(), vinCar[0])
            checkUps += newCheckUp
            return newCheckUp
        } catch (e: Exception) {
            println(e)
        } finally {
            return null
        }
    }

    fun getCheckUps(vin:String):List<CheckUp> {
        var vinCheckUps = checkUps.filter {checkUp -> checkUp.getCar().getVin() == vin}
        if(vinCheckUps.size == 0) throw Exception("There is no car with a given VIN")
        return vinCheckUps
    }

    fun countCheckUps(manufacturer: String):Int {
        val manCheckUps = checkUps.count {checkUp -> checkUp.getCar().getManufacturer() == manufacturer}
        return manCheckUps
    }
}

fun main() {
    println(CarCheckUpSystem.isCheckUpNecessary("1C4PJMDB6FW698828"))
    println(CarCheckUpSystem.getCheckUps("5GAKRCKD6DJ199296"))
    println(CarCheckUpSystem.countCheckUps("Audi"))
}
