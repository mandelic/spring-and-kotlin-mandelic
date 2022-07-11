import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
object CarCheckUpSystem{
    private val cars: MutableList<Car>
    private var checkUps: MutableList<CheckUp>
    init {
        cars = mutableListOf(
            Car("Porsche", "Taycan", "4T1BE46K19U448615"),
            Car("Audi", "A8", "1C4PJMDB6FW698828"),
            Car("Audi", "A3", "5GAKRCKD6DJ199296"),
            Car("Seat", "Ibiza", "2T3RFREV4EW154826"))
        checkUps = mutableListOf(
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

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        return checkUps.none {it.car.vin == vin && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCheckUp(vin: String): CheckUp {
        val vinCar = cars.firstOrNull { it.vin == vin }
        vinCar ?: throw Exception("There is no car with a given VIN")
        val newCheckUp = CheckUp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), vinCar)
        checkUps.add(newCheckUp)
        return newCheckUp
    }

    fun getCheckUps(vin:String):List<CheckUp>? {
        val vinCheckUps = checkUps.filter { it.car.vin == vin }
        if (vinCheckUps.isEmpty() && cars.none{it.vin == vin}) throw Exception("There is no car with a given VIN")
        return vinCheckUps
    }

    fun countCheckUps(manufacturer: String):Int = checkUps.count {it.car.manufacturer == manufacturer}
}
